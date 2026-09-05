package iris.kmtproto.crypto

internal expect object PlatformCrypto {
    fun sha1(data: ByteArray): ByteArray
    fun sha256(data: ByteArray): ByteArray
    fun md5(data: ByteArray): ByteArray
    fun hmacSha512(key: ByteArray, data: ByteArray): ByteArray
    fun aesEcbEncrypt(key: ByteArray, data: ByteArray): ByteArray
    fun aesEcbDecrypt(key: ByteArray, data: ByteArray): ByteArray
    fun randomBytes(n: Int): ByteArray
    fun currentTimeMillis(): Long
    fun gunzip(data: ByteArray): ByteArray
}

/** AES-ECB session: one key schedule for many 16-byte blocks. Not shared across threads. */
internal expect class AesEcb(key: ByteArray, encrypt: Boolean) {
    fun block(src: ByteArray, srcOff: Int, dst: ByteArray, dstOff: Int)
}

internal expect class Md5Hasher() {
    fun update(data: ByteArray, offset: Int, length: Int)
    fun digest(): ByteArray
}

internal expect class MpInt {
    fun toUnsignedBytes(): ByteArray
    fun modPow(exp: MpInt, mod: MpInt): MpInt
    fun multiply(other: MpInt): MpInt
    fun remainder(other: MpInt): MpInt
    fun divide(other: MpInt): MpInt
    fun add(other: MpInt): MpInt
    fun subtract(other: MpInt): MpInt
    fun compare(other: MpInt): Int
    fun gcd(other: MpInt): MpInt
    fun bitLength(): Int
    fun shiftLeft(n: Int): MpInt
    fun testBit(n: Int): Boolean
}

internal expect fun mpIntFromUnsigned(bytes: ByteArray): MpInt
internal expect fun mpIntFromLong(value: Long): MpInt
internal expect val MP_ZERO: MpInt
internal expect val MP_ONE: MpInt
internal expect val MP_TWO: MpInt
