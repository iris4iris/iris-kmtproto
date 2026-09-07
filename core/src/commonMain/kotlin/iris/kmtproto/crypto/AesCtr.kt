package iris.kmtproto.crypto

/**
 * AES-256-CTR stream. JVM actual is `Cipher(AES/CTR/NoPadding)` (HotSpot
 * CounterMode AES-NI, including unaligned 4-byte MTProxy prefixes).
 * [src] and [dst] may be the same array.
 */
internal expect class AesCtr(key: ByteArray, iv: ByteArray) {
    fun process(data: ByteArray): ByteArray

    fun processInto(src: ByteArray, srcOff: Int, dst: ByteArray, dstOff: Int, len: Int)
}
