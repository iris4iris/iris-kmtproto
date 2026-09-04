package iris.kmtproto.crypto

internal object AesIge {
    fun encrypt(
        key: ByteArray,
        iv: ByteArray,
        data: ByteArray,
        start: Int = 0,
        end: Int = data.size,
    ): ByteArray {
        val n = end - start
        require(start >= 0 && end <= data.size && n >= 0 && n % 16 == 0) {
            "IGE input must be multiple of 16, got $n"
        }
        require(iv.size == 32)
        var iv1 = iv.copyOfRange(0, 16)
        var iv2 = iv.copyOfRange(16, 32)
        val out = ByteArray(n)
        var offset = 0
        while (offset < n) {
            val src = start + offset
            val xored = ByteArray(16) { i ->
                (data[src + i].toInt() xor iv1[i].toInt()).toByte()
            }
            val encrypted = PlatformCrypto.aesEcbEncrypt(key, xored)
            val cipher = ByteArray(16) { (encrypted[it].toInt() xor iv2[it].toInt()).toByte() }
            cipher.copyInto(out, offset)
            iv1 = cipher
            iv2 = data.copyOfRange(src, src + 16)
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
    ): ByteArray {
        val n = end - start
        require(start >= 0 && end <= data.size && n >= 0 && n % 16 == 0) {
            "IGE input must be multiple of 16, got $n"
        }
        require(iv.size == 32)
        var iv1 = iv.copyOfRange(0, 16)
        var iv2 = iv.copyOfRange(16, 32)
        val out = ByteArray(n)
        var offset = 0
        while (offset < n) {
            val src = start + offset
            val xored = ByteArray(16) { i ->
                (data[src + i].toInt() xor iv2[i].toInt()).toByte()
            }
            val decrypted = PlatformCrypto.aesEcbDecrypt(key, xored)
            val plain = ByteArray(16) { (decrypted[it].toInt() xor iv1[it].toInt()).toByte() }
            plain.copyInto(out, offset)
            iv1 = data.copyOfRange(src, src + 16)
            iv2 = plain
            offset += 16
        }
        return out
    }
}
