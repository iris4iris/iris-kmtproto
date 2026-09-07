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
internal expect class IgeCtx(encrypt: Boolean) {
    fun crypt(
        key: ByteArray,
        iv: ByteArray,
        data: ByteArray,
        start: Int = 0,
        end: Int = data.size,
        dest: ByteArray? = null,
        destOff: Int = 0,
    ): ByteArray
}
