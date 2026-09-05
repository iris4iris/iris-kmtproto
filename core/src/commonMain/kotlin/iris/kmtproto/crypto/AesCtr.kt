package iris.kmtproto.crypto

/**
 * AES-256-CTR as a pure stream. Java `Cipher.update` buffers incomplete blocks,
 * which breaks obfuscated transport (4-byte length prefix, then payload).
 */
internal class AesCtr(key: ByteArray, iv: ByteArray) {
    init {
        require(key.size == 32) { "AES-256 key" }
        require(iv.size == 16) { "CTR iv" }
    }

    private val aes = AesEcb(key, encrypt = true)
    private val counter = iv.copyOf()
    private val keystream = ByteArray(16)
    private var offset = 16

    fun process(data: ByteArray): ByteArray {
        val out = ByteArray(data.size)
        processInto(data, 0, out, 0, data.size)
        return out
    }

    /** [src] and [dst] may be the same array (in-place xor). */
    fun processInto(src: ByteArray, srcOff: Int, dst: ByteArray, dstOff: Int, len: Int) {
        require(srcOff >= 0 && dstOff >= 0 && len >= 0)
        require(srcOff + len <= src.size && dstOff + len <= dst.size)
        offset = ctrProcessInto(aes, counter, keystream, offset, src, srcOff, dst, dstOff, len)
    }
}
