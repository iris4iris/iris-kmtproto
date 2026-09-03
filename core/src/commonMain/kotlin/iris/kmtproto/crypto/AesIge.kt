package iris.kmtproto.crypto

internal object AesIge {
    fun encrypt(key: ByteArray, iv: ByteArray, data: ByteArray): ByteArray {
        require(data.size % 16 == 0) { "IGE input must be multiple of 16, got ${data.size}" }
        require(iv.size == 32)
        var iv1 = iv.copyOfRange(0, 16)
        var iv2 = iv.copyOfRange(16, 32)
        val out = ByteArray(data.size)
        var offset = 0
        while (offset < data.size) {
            val plain = data.copyOfRange(offset, offset + 16)
            val xored = ByteArray(16) { (plain[it].toInt() xor iv1[it].toInt()).toByte() }
            val encrypted = PlatformCrypto.aesEcbEncrypt(key, xored)
            val cipher = ByteArray(16) { (encrypted[it].toInt() xor iv2[it].toInt()).toByte() }
            cipher.copyInto(out, offset)
            iv1 = cipher
            iv2 = plain
            offset += 16
        }
        return out
    }

    fun decrypt(key: ByteArray, iv: ByteArray, data: ByteArray): ByteArray {
        require(data.size % 16 == 0) { "IGE input must be multiple of 16, got ${data.size}" }
        require(iv.size == 32)
        var iv1 = iv.copyOfRange(0, 16)
        var iv2 = iv.copyOfRange(16, 32)
        val out = ByteArray(data.size)
        var offset = 0
        while (offset < data.size) {
            val cipher = data.copyOfRange(offset, offset + 16)
            val xored = ByteArray(16) { (cipher[it].toInt() xor iv2[it].toInt()).toByte() }
            val decrypted = PlatformCrypto.aesEcbDecrypt(key, xored)
            val plain = ByteArray(16) { (decrypted[it].toInt() xor iv1[it].toInt()).toByte() }
            plain.copyInto(out, offset)
            iv1 = cipher
            iv2 = plain
            offset += 16
        }
        return out
    }
}
