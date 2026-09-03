package iris.kmtproto

import iris.kmtproto.crypto.AesIge
import iris.kmtproto.crypto.Factorize
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.crypto.ServerKeys
import iris.kmtproto.crypto.mpIntFromLong
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
    fun pingRoundtrip() {
        val ping = Ping(0x1122334455667788)
        val bytes = ping.toBytes()
        val back = TlReader(bytes).readObject() as Ping
        assertEquals(ping.pingId, back.pingId)
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
    fun aesIgeInverts() {
        val key = PlatformCrypto.randomBytes(32)
        val iv = PlatformCrypto.randomBytes(32)
        val plain = PlatformCrypto.randomBytes(64)
        val cipher = AesIge.encrypt(key, iv, plain)
        val back = AesIge.decrypt(key, iv, cipher)
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
}
