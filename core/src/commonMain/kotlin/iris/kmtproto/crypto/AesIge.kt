package iris.kmtproto.crypto

internal object AesIge {
    fun encrypt(
        key: ByteArray,
        iv: ByteArray,
        data: ByteArray,
        start: Int = 0,
        end: Int = data.size,
        dest: ByteArray? = null,
        destOff: Int = 0,
    ): ByteArray {
        val n = end - start
        require(start >= 0 && end <= data.size && n >= 0 && n % 16 == 0) {
            "IGE input must be multiple of 16, got $n"
        }
        require(iv.size == 32)
        val out = dest ?: ByteArray(n)
        require(destOff >= 0 && destOff + n <= out.size) { "IGE dest too small" }
        val iv1 = iv.copyOfRange(0, 16)
        val iv2 = iv.copyOfRange(16, 32)
        val tmp = ByteArray(16)
        val aes = AesEcb(key, encrypt = true)
        var offset = 0
        while (offset < n) {
            val src = start + offset
            val dst = destOff + offset
            for (i in 0 until 16) tmp[i] = (data[src + i].toInt() xor iv1[i].toInt()).toByte()
            aes.block(tmp, 0, tmp, 0)
            for (i in 0 until 16) {
                val c = (tmp[i].toInt() xor iv2[i].toInt()).toByte()
                out[dst + i] = c
                iv2[i] = data[src + i]
                iv1[i] = c
            }
            offset += 16
        }
        return out
    }

    fun decrypt(
        key: ByteArray,
        iv: ByteArray,
        data: ByteArray,
        start: Int = 0,
        end: Int = data.size,
        dest: ByteArray? = null,
        destOff: Int = 0,
    ): ByteArray {
        val n = end - start
        require(start >= 0 && end <= data.size && n >= 0 && n % 16 == 0) {
            "IGE input must be multiple of 16, got $n"
        }
        require(iv.size == 32)
        val out = dest ?: ByteArray(n)
        require(destOff >= 0 && destOff + n <= out.size) { "IGE dest too small" }
        val iv1 = iv.copyOfRange(0, 16)
        val iv2 = iv.copyOfRange(16, 32)
        val tmp = ByteArray(16)
        val aes = AesEcb(key, encrypt = false)
        var offset = 0
        while (offset < n) {
            val src = start + offset
            val dst = destOff + offset
            for (i in 0 until 16) tmp[i] = (data[src + i].toInt() xor iv2[i].toInt()).toByte()
            aes.block(tmp, 0, tmp, 0)
            for (i in 0 until 16) {
                val p = (tmp[i].toInt() xor iv1[i].toInt()).toByte()
                out[dst + i] = p
                iv1[i] = data[src + i]
                iv2[i] = p
            }
            offset += 16
        }
        return out
    }
}