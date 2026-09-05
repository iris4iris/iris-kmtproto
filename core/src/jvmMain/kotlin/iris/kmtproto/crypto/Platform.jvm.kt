package iris.kmtproto.crypto

import java.io.ByteArrayInputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.zip.GZIPInputStream
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private val rng = SecureRandom()

internal actual object PlatformCrypto {
    private val sha1 = ThreadLocal.withInitial { MessageDigest.getInstance("SHA-1") }
    private val sha256 = ThreadLocal.withInitial { MessageDigest.getInstance("SHA-256") }
    private val md5 = ThreadLocal.withInitial { MessageDigest.getInstance("MD5") }
    private val hmacSha512 = ThreadLocal.withInitial { Mac.getInstance("HmacSHA512") }
    private val aesEcb = ThreadLocal.withInitial { Cipher.getInstance("AES/ECB/NoPadding") }

    actual fun sha1(data: ByteArray): ByteArray = sha1.get().digest(data)

    actual fun sha256(data: ByteArray): ByteArray = sha256.get().digest(data)

    actual fun md5(data: ByteArray): ByteArray = md5.get().digest(data)

    actual fun hmacSha512(key: ByteArray, data: ByteArray): ByteArray {
        val mac = hmacSha512.get()
        val specKey = if (key.isEmpty()) ByteArray(128) else key
        mac.init(SecretKeySpec(specKey, "HmacSHA512"))
        return mac.doFinal(data)
    }

    actual fun aesEcbEncrypt(key: ByteArray, data: ByteArray): ByteArray {
        val c = aesEcb.get()
        c.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"))
        return c.doFinal(data)
    }

    actual fun aesEcbDecrypt(key: ByteArray, data: ByteArray): ByteArray {
        val c = aesEcb.get()
        c.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"))
        return c.doFinal(data)
    }

    actual fun randomBytes(n: Int): ByteArray = ByteArray(n).also { rng.nextBytes(it) }

    actual fun currentTimeMillis(): Long = System.currentTimeMillis()

    actual fun gunzip(data: ByteArray): ByteArray =
        GZIPInputStream(ByteArrayInputStream(data)).use { it.readBytes() }
}

internal actual class AesEcb actual constructor(key: ByteArray, encrypt: Boolean) {
    private val cipher = Cipher.getInstance("AES/ECB/NoPadding")

    init {
        cipher.init(
            if (encrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE,
            SecretKeySpec(key, "AES"),
        )
    }

    actual fun block(src: ByteArray, srcOff: Int, dst: ByteArray, dstOff: Int) {
        cipher.doFinal(src, srcOff, 16, dst, dstOff)
    }
}

internal actual class Md5Hasher actual constructor() {
    private val digest = MessageDigest.getInstance("MD5")
    actual fun update(data: ByteArray, offset: Int, length: Int) {
        digest.update(data, offset, length)
    }
    actual fun digest(): ByteArray = digest.digest()
}

internal actual class MpInt(val inner: BigInteger) {
    actual fun toUnsignedBytes(): ByteArray {
        var b = inner.toByteArray()
        if (b.size > 1 && b[0] == 0.toByte()) b = b.copyOfRange(1, b.size)
        return b
    }

    actual fun modPow(exp: MpInt, mod: MpInt): MpInt = MpInt(inner.modPow(exp.inner, mod.inner))
    actual fun multiply(other: MpInt): MpInt = MpInt(inner.multiply(other.inner))
    actual fun remainder(other: MpInt): MpInt = MpInt(inner.remainder(other.inner))
    actual fun divide(other: MpInt): MpInt = MpInt(inner.divide(other.inner))
    actual fun add(other: MpInt): MpInt = MpInt(inner.add(other.inner))
    actual fun subtract(other: MpInt): MpInt = MpInt(inner.subtract(other.inner))
    actual fun compare(other: MpInt): Int = inner.compareTo(other.inner)
    actual fun gcd(other: MpInt): MpInt = MpInt(inner.gcd(other.inner))
    actual fun bitLength(): Int = inner.bitLength()
    actual fun shiftLeft(n: Int): MpInt = MpInt(inner.shiftLeft(n))
    actual fun testBit(n: Int): Boolean = inner.testBit(n)
}

internal actual fun mpIntFromUnsigned(bytes: ByteArray): MpInt = MpInt(BigInteger(1, bytes))
internal actual fun mpIntFromLong(value: Long): MpInt = MpInt(BigInteger.valueOf(value))
internal actual val MP_ZERO: MpInt = MpInt(BigInteger.ZERO)
internal actual val MP_ONE: MpInt = MpInt(BigInteger.ONE)
internal actual val MP_TWO: MpInt = MpInt(BigInteger.valueOf(2))