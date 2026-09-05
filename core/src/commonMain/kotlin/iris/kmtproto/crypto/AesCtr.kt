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
        var i = 0
        while (i < len && offset != 16) {
            dst[dstOff + i] = (src[srcOff + i].toInt() xor keystream[offset++].toInt()).toByte()
            i++
        }
        while (i + 16 <= len) {
            aes.block(counter, 0, keystream, 0)
            increment(counter)
            val s = srcOff + i
            val d = dstOff + i
            var b = 0
            while (b < 16) {
                dst[d + b] = (src[s + b].toInt() xor keystream[b].toInt()).toByte()
                b++
            }
            i += 16
            offset = 16
        }
        while (i < len) {
            if (offset == 16) {
                aes.block(counter, 0, keystream, 0)
                offset = 0
                increment(counter)
            }
            dst[dstOff + i] = (src[srcOff + i].toInt() xor keystream[offset++].toInt()).toByte()
            i++
        }
    }

    private fun increment(block: ByteArray) {
        var i = block.lastIndex
        while (i >= 0) {
            val next = (block[i].toInt() and 0xff) + 1
            block[i] = next.toByte()
            if (next < 256) break
            i--
        }
    }
}
