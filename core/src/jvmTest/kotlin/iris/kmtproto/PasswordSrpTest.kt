package iris.kmtproto

import iris.kmtproto.crypto.DH_PRIME_HEX
import iris.kmtproto.crypto.PasswordSrp
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.crypto.mpIntFromLong
import iris.kmtproto.tl.gen.PasswordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PasswordSrpTest {
    @Test
    fun hmacSha512Is64Bytes() {
        val mac = PlatformCrypto.hmacSha512("key".encodeToByteArray(), "data".encodeToByteArray())
        assertEquals(64, mac.size)
        assertContentEquals(mac, PlatformCrypto.hmacSha512("key".encodeToByteArray(), "data".encodeToByteArray()))
    }

    @Test
    fun pbkdf2OneIterationIsHmacOfSaltAndBlockIndex() {
        val password = "password".encodeToByteArray()
        val salt = "salt".encodeToByteArray()
        val ours = PasswordSrp.pbkdf2HmacSha512(password, salt, iterations = 1, dkLen = 64)
        val u1 = PlatformCrypto.hmacSha512(password, concat(salt, byteArrayOf(0, 0, 0, 1)))
        assertContentEquals(u1, ours)
    }

    @Test
    fun srpProduces256ByteAAnd32ByteM1() {
        val p = DH_PRIME_HEX.hexToBytes()
        val algo = PasswordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow(
            salt1 = ByteArray(8) { 1 },
            salt2 = ByteArray(8) { 2 },
            g = 3,
            p = p,
        )
        val b = PlatformCrypto.randomBytes(256)
        val gB = mpIntFromLong(3).modPow(
            iris.kmtproto.crypto.mpIntFromUnsigned(b),
            iris.kmtproto.crypto.mpIntFromUnsigned(p),
        ).toUnsignedBytes().padLeft(256)
        val a = ByteArray(256) { 7 }
        val out = PasswordSrp.compute(algo, "test-password".encodeToByteArray(), gB, srpId = 99L, aBytes = a)
        assertEquals(99L, out.srpId)
        assertEquals(256, out.A.size)
        assertEquals(32, out.M1.size)
        assertTrue(out.A.any { it != 0.toByte() })
        assertTrue(out.M1.any { it != 0.toByte() })
    }
}
