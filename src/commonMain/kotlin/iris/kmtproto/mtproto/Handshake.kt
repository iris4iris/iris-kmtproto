package iris.kmtproto.mtproto

import iris.kmtproto.concat
import iris.kmtproto.crypto.AesIge
import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.DH_PRIME_HEX
import iris.kmtproto.crypto.Factorize
import iris.kmtproto.crypto.MP_ONE
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.crypto.RsaPad
import iris.kmtproto.crypto.ServerKeys
import iris.kmtproto.crypto.mpIntFromLong
import iris.kmtproto.crypto.mpIntFromUnsigned
import iris.kmtproto.crypto.tmpAesFromNonce
import iris.kmtproto.hexToBytes
import iris.kmtproto.padLeft
import iris.kmtproto.tl.ClientDhInnerData
import iris.kmtproto.tl.DhGenFail
import iris.kmtproto.tl.DhGenOk
import iris.kmtproto.tl.DhGenRetry
import iris.kmtproto.tl.PqInnerDataDc
import iris.kmtproto.tl.ReqDhParams
import iris.kmtproto.tl.ReqPqMulti
import iris.kmtproto.tl.ResPq
import iris.kmtproto.tl.ServerDhInnerData
import iris.kmtproto.tl.ServerDhParamsFail
import iris.kmtproto.tl.ServerDhParamsOk
import iris.kmtproto.tl.SetClientDhParams
import iris.kmtproto.tl.TlObject
import iris.kmtproto.tl.TlReader
import iris.kmtproto.tl.toBytes
import iris.kmtproto.transport.MtprotoTransport
import iris.kmtproto.xor

class HandshakeResult(
    val authKey: AuthKey,
    val serverSalt: Long,
    val timeOffset: Int,
)

class HandshakeException(message: String) : RuntimeException(message)

internal object Handshake {
    suspend fun perform(transport: MtprotoTransport, dcId: Int): HandshakeResult {
        val plain = PlainConnection(transport)

        val nonce = PlatformCrypto.randomBytes(16)
        val resPq = plain.send(ReqPqMulti(nonce)) as? ResPq
            ?: throw HandshakeException("expected ResPQ, got unexpected constructor")
        if (!resPq.nonce.contentEquals(nonce)) throw HandshakeException("nonce mismatch in ResPQ")

        val key = resPq.fingerprints.toList().firstNotNullOfOrNull { ServerKeys.byFingerprint(it) }
            ?: throw HandshakeException(
                "no matching RSA key; server fingerprints=" +
                    resPq.fingerprints.joinToString { it.toULong().toString(16) },
            )

        val (p, q) = Factorize.factorize(resPq.pq)
        val pInt = mpIntFromUnsigned(p)
        val qInt = mpIntFromUnsigned(q)
        val pqInt = mpIntFromUnsigned(resPq.pq)
        if (pInt.multiply(qInt).compare(pqInt) != 0) {
            throw HandshakeException("pq factorization mismatch")
        }
        if (pInt.compare(qInt) >= 0) {
            throw HandshakeException("p must be < q")
        }
        val newNonce = PlatformCrypto.randomBytes(32)
        val inner = PqInnerDataDc(
            pq = resPq.pq,
            p = p,
            q = q,
            nonce = nonce,
            serverNonce = resPq.serverNonce,
            newNonce = newNonce,
            dc = dcId,
        )
        val innerBytes = inner.toBytes()
        // DC2 answers MTProto 1.0 RSA (SHA1+data+padding → 255 bytes). RSA_PAD 2.0
        // is implemented but currently ignored by the server (no DH reply).
        var encrypted = RsaPad.encryptLegacy(innerBytes, key)

        fun reqDh(blob: ByteArray) = ReqDhParams(
            nonce = nonce,
            serverNonce = resPq.serverNonce,
            p = p,
            q = q,
            publicKeyFingerprint = key.fingerprint,
            encryptedData = blob,
        )

        var dhParams: TlObject = plain.send(reqDh(encrypted))

        if (dhParams is ServerDhParamsFail) {
            encrypted = RsaPad.encrypt(innerBytes, key)
            dhParams = plain.send(reqDh(encrypted))
        }

        val ok = dhParams as? ServerDhParamsOk
            ?: throw HandshakeException("DH params failed: $dhParams")
        if (!ok.nonce.contentEquals(nonce) || !ok.serverNonce.contentEquals(resPq.serverNonce)) {
            throw HandshakeException("nonce mismatch in server_DH_params_ok")
        }

        val (tmpKey, tmpIv) = tmpAesFromNonce(resPq.serverNonce, newNonce)
        val answer = AesIge.decrypt(tmpKey, tmpIv, ok.encryptedAnswer)
        require(answer.size >= 20)
        val serverInnerBytes = answer.copyOfRange(20, answer.size)
        val serverInner = TlReader(serverInnerBytes).readObject() as? ServerDhInnerData
            ?: throw HandshakeException("expected server_DH_inner_data")
        if (!serverInner.nonce.contentEquals(nonce) ||
            !serverInner.serverNonce.contentEquals(resPq.serverNonce)
        ) {
            throw HandshakeException("nonce mismatch in server_DH_inner_data")
        }

        val knownPrime = DH_PRIME_HEX.hexToBytes()
        if (!serverInner.dhPrime.contentEquals(knownPrime)) {
            throw HandshakeException("unexpected dh_prime (refusing non-official prime)")
        }
        if (serverInner.g !in 2..7) throw HandshakeException("bad g=${serverInner.g}")

        val dhPrime = mpIntFromUnsigned(serverInner.dhPrime)
        val gA = mpIntFromUnsigned(serverInner.gA)
        val bound = MP_ONE.shiftLeft(2048 - 64)
        val upper = dhPrime.subtract(bound)
        if (gA.compare(bound) < 0 || gA.compare(upper) > 0) {
            throw HandshakeException("g_a out of range")
        }

        plain.msgIds.syncFromServer(serverInner.serverTime)

        val b = mpIntFromUnsigned(PlatformCrypto.randomBytes(256))
        val g = mpIntFromLong(serverInner.g.toLong())
        val gB = g.modPow(b, dhPrime)
        val gBBytes = gB.toUnsignedBytes()
        if (gB.compare(bound) < 0 || gB.compare(upper) > 0) {
            throw HandshakeException("g_b out of range")
        }

        val gab = gA.modPow(b, dhPrime)
        val authKeyBytes = gab.toUnsignedBytes().padLeft(256)
        val authKey = AuthKey(authKeyBytes)

        val clientInner = ClientDhInnerData(
            nonce = nonce,
            serverNonce = resPq.serverNonce,
            retryId = 0,
            gB = gBBytes,
        )
        val clientInnerBytes = clientInner.toBytes()
        val hashed = concat(PlatformCrypto.sha1(clientInnerBytes), clientInnerBytes)
        val pad = (16 - (hashed.size % 16)) % 16
        val padded = if (pad == 0) hashed else concat(hashed, PlatformCrypto.randomBytes(pad))
        val clientEncrypted = AesIge.encrypt(tmpKey, tmpIv, padded)

        when (val answerDh = plain.send(SetClientDhParams(nonce, resPq.serverNonce, clientEncrypted))) {
            is DhGenOk -> {
                val expected = authKey.newNonceHash(newNonce, 1)
                if (!expected.contentEquals(answerDh.newNonceHash1)) {
                    throw HandshakeException("new_nonce_hash1 mismatch")
                }
            }
            is DhGenRetry -> throw HandshakeException("dh_gen_retry")
            is DhGenFail -> throw HandshakeException("dh_gen_fail")
            else -> throw HandshakeException("unexpected DH answer $answerDh")
        }

        val saltBytes = newNonce.copyOfRange(0, 8).xor(resPq.serverNonce.copyOfRange(0, 8))
        var salt = 0L
        for (i in 0 until 8) salt = salt or ((saltBytes[i].toLong() and 0xff) shl (8 * i))

        return HandshakeResult(authKey, salt, plain.msgIds.offset)
    }
}
