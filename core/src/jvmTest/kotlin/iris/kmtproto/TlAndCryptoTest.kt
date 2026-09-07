package iris.kmtproto

import iris.kmtproto.crypto.AesIge
import iris.kmtproto.crypto.AesEcb
import iris.kmtproto.crypto.IgeCtx
import iris.kmtproto.crypto.Factorize
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.crypto.ServerKeys
import iris.kmtproto.crypto.mpIntFromLong
import iris.kmtproto.tl.MsgContainer
import iris.kmtproto.tl.MsgsAck
import iris.kmtproto.tl.MtMessage
import iris.kmtproto.tl.Ping
import iris.kmtproto.tl.Pong
import iris.kmtproto.tl.ReqPqMulti
import iris.kmtproto.tl.ResPq
import iris.kmtproto.tl.TlReader
import iris.kmtproto.tl.toBytes
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TlAndCryptoTest {
    @Test
    fun msgsAckBatchesManyIds() {
        val ids = LongArray(40) { i -> (i + 1) * 4L }
        val back = TlReader(MsgsAck(ids).toBytes()).readObject() as MsgsAck
        assertContentEquals(ids, back.msgIds)
    }

    @Test
    fun msgContainerRoundtrip() {
        val packed = MsgContainer(
            listOf(
                MtMessage(4L, 1, Ping(11)),
                MtMessage(8L, 3, Ping(22)),
            ),
        )
        val back = TlReader(packed.toBytes()).readObject() as MsgContainer
        assertEquals(2, back.messages.size)
        assertEquals(4L, back.messages[0].msgId)
        assertEquals(11L, (back.messages[0].body as Ping).pingId)
        assertEquals(22L, (back.messages[1].body as Ping).pingId)
    }

    @Test
    fun pingRoundtrip() {
        val ping = Ping(0x1122334455667788)
        val bytes = ping.toBytes()
        val back = TlReader(bytes).readObject() as Ping
        assertEquals(ping.pingId, back.pingId)
    }

    @Test
    fun tlReaderEndIgnoresPadding() {
        val ping = Ping(99).toBytes()
        val framed = ByteArray(32) + ping + ByteArray(12) { 0x5a }
        val back = TlReader(framed, pos = 32, end = 32 + ping.size).readObject() as Ping
        assertEquals(99L, back.pingId)
        val tooFar = runCatching {
            TlReader(framed, pos = 32, end = 32 + ping.size).apply {
                readObject()
                readByte()
            }
        }
        assertTrue(tooFar.exceptionOrNull() is IllegalStateException)
    }

    @Test
    fun reqPqRoundtrip() {
        val nonce = ByteArray(16) { it.toByte() }
        val req = ReqPqMulti(nonce)
        val bytes = req.toBytes()
        // constructor + 16 bytes
        assertEquals(20, bytes.size)
        val res = ResPq(
            nonce = nonce,
            serverNonce = ByteArray(16) { (it + 1).toByte() },
            pq = byteArrayOf(0x17, 0xED.toByte(), 0x48, 0x9B.toByte(), 0xC3.toByte(), 0x1A),
            fingerprints = longArrayOf(1L, 2L),
        )
        val encoded = res.toBytes()
        val decoded = TlReader(encoded).readObject() as ResPq
        assertContentEquals(res.nonce, decoded.nonce)
        assertContentEquals(res.pq, decoded.pq)
        assertEquals(2, decoded.fingerprints.size)
    }

    @Test
    fun msgKeyAndDeriveMatchBufferedPath() {
        val auth = PlatformCrypto.randomBytes(256)
        val msgKey = PlatformCrypto.randomBytes(16)
        val (k1, iv1) = iris.kmtproto.crypto.MsgKeys.deriveAes(auth, msgKey, x = 8)
        val k2 = ByteArray(32)
        val iv2 = ByteArray(32)
        iris.kmtproto.crypto.MsgKeys.deriveAesInto(auth, msgKey, 0, 8, k2, iv2, ByteArray(32), ByteArray(32))
        assertContentEquals(k1, k2)
        assertContentEquals(iv1, iv2)
        val plain = PlatformCrypto.randomBytes(80)
        val mk = iris.kmtproto.crypto.MsgKeys.msgKey(auth, plain, x = 8)
        val buf = ByteArray(32)
        assertTrue(iris.kmtproto.crypto.MsgKeys.msgKeyMatches(auth, plain, plain.size, 8, mk, 0, buf))
        mk[0] = (mk[0].toInt() xor 1).toByte()
        assertTrue(!iris.kmtproto.crypto.MsgKeys.msgKeyMatches(auth, plain, plain.size, 8, mk, 0, buf))
    }

    @Test
    fun aesCryptHotSpotIsOpen() {
        assertTrue(iris.kmtproto.crypto.aesCryptAvailable(), "--add-opens java.base/com.sun.crypto.provider=ALL-UNNAMED")
    }

    @Test
    fun aesIgeInverts() {
        val key = PlatformCrypto.randomBytes(32)
        val iv = PlatformCrypto.randomBytes(32)
        val plain = PlatformCrypto.randomBytes(64)
        val cipher = AesIge.encrypt(key, iv, plain)
        val back = AesIge.decrypt(key, iv, cipher)
        assertContentEquals(plain, back)
    }

    @Test
    fun md5EmptyIsRfc() {
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", PlatformCrypto.md5(ByteArray(0)).toHex())
    }

    @Test
    fun aesIgeDecryptsSlice() {
        val key = PlatformCrypto.randomBytes(32)
        val iv = PlatformCrypto.randomBytes(32)
        val plain = PlatformCrypto.randomBytes(48)
        val cipher = AesIge.encrypt(key, iv, plain)
        val framed = ByteArray(8 + cipher.size)
        cipher.copyInto(framed, 8)
        val back = AesIge.decrypt(key, iv, framed, 8, framed.size)
        assertContentEquals(plain, back)
    }

    @Test
    fun factorizeKnownProduct() {
        val p = 1_000_003L
        val q = 1_000_033L
        val pq = mpIntFromLong(p).multiply(mpIntFromLong(q)).toUnsignedBytes()
        val (a, b) = Factorize.factorize(pq)
        val av = a.fold(0L) { acc, byte -> (acc shl 8) or (byte.toLong() and 0xff) }
        val bv = b.fold(0L) { acc, byte -> (acc shl 8) or (byte.toLong() and 0xff) }
        assertEquals(p, av)
        assertEquals(q, bv)
    }

    @Test
    fun serverKeysParseAndFingerprint() {
        assertEquals(4, ServerKeys.keys.size)
        val fps = ServerKeys.keys.map { it.fingerprint }.toSet()
        assertTrue(fps.contains(847625836280919973L), "key0 fingerprint")
        assertTrue(fps.contains(1562291298945373506L), "key1 fingerprint")
    }

    @Test
    fun pongRoundtrip() {
        val pong = Pong(msgId = 99L, pingId = 7L)
        val back = TlReader(pong.toBytes()).readObject() as Pong
        assertEquals(99L, back.msgId)
        assertEquals(7L, back.pingId)
    }

    @Test
    fun msgIdTracksUnixTime() {
        val id = iris.kmtproto.mtproto.MsgIdFactory().next()
        val unix = id ushr 32
        val now = System.currentTimeMillis() / 1000L
        assertEquals(0L, id and 3L)
        assertTrue(kotlin.math.abs(unix - now) <= 2, "msg_id unix=$unix now=$now raw=$id")
    }

    @Test
    fun aesCtrHandlesPartialBlocks() {
        val key = PlatformCrypto.randomBytes(32)
        val iv = PlatformCrypto.randomBytes(16)
        val a = iris.kmtproto.crypto.AesCtr(key, iv.copyOf())
        val b = iris.kmtproto.crypto.AesCtr(key, iv.copyOf())
        val plain = PlatformCrypto.randomBytes(44)
        val cipher = ByteArray(0) + a.process(plain.copyOfRange(0, 4)) + a.process(plain.copyOfRange(4, 44))
        val back = b.process(cipher)
        assertContentEquals(plain, back)
    }

    @Test
    fun aesCtrMatchesEcbKeystream() {
        val key = PlatformCrypto.randomBytes(32)
        val iv = PlatformCrypto.randomBytes(16)
        val plain = PlatformCrypto.randomBytes(100)
        val got = iris.kmtproto.crypto.AesCtr(key, iv.copyOf()).process(plain)
        assertContentEquals(ecbCtr(key, iv, plain), got)
    }

    @Test
    fun aesIgeUnalignedOffset() {
        val key = PlatformCrypto.randomBytes(32)
        val iv = PlatformCrypto.randomBytes(32)
        val plain = PlatformCrypto.randomBytes(48)
        val cipher = AesIge.encrypt(key, iv, plain)
        val framed = ByteArray(4 + cipher.size)
        cipher.copyInto(framed, 4)
        val back = AesIge.decrypt(key, iv, framed, 4, framed.size)
        assertContentEquals(plain, back)
    }

    @Test
    fun aesIgeInPlace() {
        val key = PlatformCrypto.randomBytes(32)
        val iv = PlatformCrypto.randomBytes(32)
        val plain = PlatformCrypto.randomBytes(64)
        val cipher = AesIge.encrypt(key, iv, plain)
        val buf = cipher.copyOf()
        val back = IgeCtx(encrypt = false).crypt(key, iv, buf, 0, buf.size, buf, 0)
        assertContentEquals(plain, back)
        assertContentEquals(plain, buf)
    }

    @Test
    fun aesCtrInPlaceMatchesCopy() {
        val key = PlatformCrypto.randomBytes(32)
        val iv = PlatformCrypto.randomBytes(16)
        val plain = PlatformCrypto.randomBytes(2252)
        val copy = iris.kmtproto.crypto.AesCtr(key, iv.copyOf()).process(plain)
        val inplace = plain.copyOf()
        iris.kmtproto.crypto.AesCtr(key, iv.copyOf()).processInto(inplace, 0, inplace, 0, inplace.size)
        assertContentEquals(copy, inplace)
        val back = ByteArray(inplace.size)
        iris.kmtproto.crypto.AesCtr(key, iv.copyOf()).processInto(inplace, 0, back, 0, inplace.size)
        assertContentEquals(plain, back)
    }
}

private fun ecbCtr(key: ByteArray, iv: ByteArray, data: ByteArray): ByteArray {
    val aes = AesEcb(key, encrypt = true)
    val counter = iv.copyOf()
    val ks = ByteArray(16)
    val out = ByteArray(data.size)
    var i = 0
    while (i < data.size) {
        aes.block(counter, 0, ks, 0)
        var c = counter.lastIndex
        while (c >= 0) {
            val next = (counter[c].toInt() and 0xff) + 1
            counter[c] = next.toByte()
            if (next < 256) break
            c--
        }
        val n = minOf(16, data.size - i)
        var b = 0
        while (b < n) {
            out[i + b] = (data[i + b].toInt() xor ks[b].toInt()).toByte()
            b++
        }
        i += n
    }
    return out
}

fun main() {
    TlAndCryptoTest().run {
        aesCryptHotSpotIsOpen()
        aesIgeInverts()
        aesIgeDecryptsSlice()
        aesIgeUnalignedOffset()
        aesIgeInPlace()
        aesCtrHandlesPartialBlocks()
        aesCtrInPlaceMatchesCopy()
        aesCtrMatchesEcbKeystream()
    }
    println("TlAndCryptoTest crypto ok")
}
