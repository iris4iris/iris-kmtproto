package iris.kmtproto.crypto

internal actual class IgeCtx actual constructor(private val encrypt: Boolean) {
    private val aes = AesEcb(ByteArray(32), encrypt)
    private val tmp = ByteArray(16)

    actual fun crypt(
        key: ByteArray,
        iv: ByteArray,
        data: ByteArray,
        start: Int,
        end: Int,
        dest: ByteArray?,
        destOff: Int,
    ): ByteArray {
        val n = end - start
        require(start >= 0 && end <= data.size && n >= 0 && n % 16 == 0) {
            "IGE input must be multiple of 16, got $n"
        }
        require(iv.size == 32)
        val out = dest ?: ByteArray(n)
        require(destOff >= 0 && destOff + n <= out.size) { "IGE dest too small" }
        aes.init(key, encrypt)
        if ((start or destOff) and 7 == 0) {
            if (encrypt) encryptLongs(data, start, out, destOff, n, iv)
            else decryptLongs(data, start, out, destOff, n, iv)
        } else if (encrypt) {
            encryptBytes(data, start, out, destOff, n, iv)
        } else {
            decryptBytes(data, start, out, destOff, n, iv)
        }
        return out
    }

    private fun encryptLongs(data: ByteArray, start: Int, out: ByteArray, destOff: Int, n: Int, iv: ByteArray) {
        var iv1_0 = ByteLongs.get(iv, 0)
        var iv1_1 = ByteLongs.get(iv, 8)
        var iv2_0 = ByteLongs.get(iv, 16)
        var iv2_1 = ByteLongs.get(iv, 24)
        var offset = 0
        while (offset < n) {
            val src = start + offset
            val dst = destOff + offset
            val p0 = ByteLongs.get(data, src)
            val p1 = ByteLongs.get(data, src + 8)
            ByteLongs.set(tmp, 0, p0 xor iv1_0)
            ByteLongs.set(tmp, 8, p1 xor iv1_1)
            aes.block(tmp, 0, out, dst)
            val c0 = ByteLongs.get(out, dst) xor iv2_0
            val c1 = ByteLongs.get(out, dst + 8) xor iv2_1
            ByteLongs.set(out, dst, c0)
            ByteLongs.set(out, dst + 8, c1)
            iv2_0 = p0
            iv2_1 = p1
            iv1_0 = c0
            iv1_1 = c1
            offset += 16
        }
    }

    private fun decryptLongs(data: ByteArray, start: Int, out: ByteArray, destOff: Int, n: Int, iv: ByteArray) {
        var iv1_0 = ByteLongs.get(iv, 0)
        var iv1_1 = ByteLongs.get(iv, 8)
        var iv2_0 = ByteLongs.get(iv, 16)
        var iv2_1 = ByteLongs.get(iv, 24)
        var offset = 0
        while (offset < n) {
            val src = start + offset
            val dst = destOff + offset
            val c0 = ByteLongs.get(data, src)
            val c1 = ByteLongs.get(data, src + 8)
            ByteLongs.set(tmp, 0, c0 xor iv2_0)
            ByteLongs.set(tmp, 8, c1 xor iv2_1)
            aes.block(tmp, 0, out, dst)
            val p0 = ByteLongs.get(out, dst) xor iv1_0
            val p1 = ByteLongs.get(out, dst + 8) xor iv1_1
            ByteLongs.set(out, dst, p0)
            ByteLongs.set(out, dst + 8, p1)
            iv1_0 = c0
            iv1_1 = c1
            iv2_0 = p0
            iv2_1 = p1
            offset += 16
        }
    }

    private fun encryptBytes(data: ByteArray, start: Int, out: ByteArray, destOff: Int, n: Int, iv: ByteArray) {
        val iv1 = iv.copyOfRange(0, 16)
        val iv2 = iv.copyOfRange(16, 32)
        var offset = 0
        while (offset < n) {
            val src = start + offset
            val dst = destOff + offset
            for (i in 0 until 16) tmp[i] = (data[src + i].toInt() xor iv1[i].toInt()).toByte()
            aes.block(tmp, 0, tmp, 0)
            for (i in 0 until 16) {
                val p = data[src + i]
                val c = (tmp[i].toInt() xor iv2[i].toInt()).toByte()
                out[dst + i] = c
                iv2[i] = p
                iv1[i] = c
            }
            offset += 16
        }
    }

    private fun decryptBytes(data: ByteArray, start: Int, out: ByteArray, destOff: Int, n: Int, iv: ByteArray) {
        val iv1 = iv.copyOfRange(0, 16)
        val iv2 = iv.copyOfRange(16, 32)
        var offset = 0
        while (offset < n) {
            val src = start + offset
            val dst = destOff + offset
            for (i in 0 until 16) tmp[i] = (data[src + i].toInt() xor iv2[i].toInt()).toByte()
            aes.block(tmp, 0, tmp, 0)
            for (i in 0 until 16) {
                val c = data[src + i]
                val p = (tmp[i].toInt() xor iv1[i].toInt()).toByte()
                out[dst + i] = p
                iv1[i] = c
                iv2[i] = p
            }
            offset += 16
        }
    }
}
