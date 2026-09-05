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
    ): ByteArray = IgeCtx(encrypt = true).crypt(key, iv, data, start, end, dest, destOff)

    fun decrypt(
        key: ByteArray,
        iv: ByteArray,
        data: ByteArray,
        start: Int = 0,
        end: Int = data.size,
        dest: ByteArray? = null,
        destOff: Int = 0,
    ): ByteArray = IgeCtx(encrypt = false).crypt(key, iv, data, start, end, dest, destOff)
}

/** Reusable IGE over one AES-ECB (new msg_key → [init] per frame, no getInstance). */
internal class IgeCtx(private val encrypt: Boolean) {
    private val aes = AesEcb(ByteArray(32), encrypt)
    private val iv1 = ByteArray(16)
    private val iv2 = ByteArray(16)
    private val tmpIn = ByteArray(16)
    private val tmpOut = ByteArray(16)

    fun crypt(
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
        aes.init(key, encrypt)
        iv.copyInto(iv1, 0, 0, 16)
        iv.copyInto(iv2, 0, 16, 32)
        igeCryptLoop(aes, encrypt, iv1, iv2, tmpIn, tmpOut, data, start, n, out, destOff)
        return out
    }
}
