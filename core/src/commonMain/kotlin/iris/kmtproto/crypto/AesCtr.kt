package iris.kmtproto.crypto

/**
 * AES-256-CTR as a pure stream. Java `Cipher.update` buffers incomplete blocks,
 * which breaks obfuscated transport (4-byte length prefix, then payload).
 */
internal class AesCtr(private val key: ByteArray, iv: ByteArray) {
    init {
        require(key.size == 32) { "AES-256 key" }
        require(iv.size == 16) { "CTR iv" }
    }

    private val counter = iv.copyOf()
    private val keystream = ByteArray(16)
    private var offset = 16

    fun process(data: ByteArray): ByteArray {
        val out = ByteArray(data.size)
        for (i in data.indices) {
            if (offset == 16) {
                PlatformCrypto.aesEcbEncrypt(key, counter).copyInto(keystream)
                offset = 0
                increment(counter)
            }
            out[i] = (data[i].toInt() xor keystream[offset++].toInt()).toByte()
        }
        return out
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
