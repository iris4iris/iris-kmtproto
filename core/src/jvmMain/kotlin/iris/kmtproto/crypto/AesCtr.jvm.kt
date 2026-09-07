package iris.kmtproto.crypto

import java.util.concurrent.atomic.AtomicBoolean
import javax.crypto.Cipher
import javax.crypto.ShortBufferException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

internal actual class AesCtr actual constructor(key: ByteArray, iv: ByteArray) {
    private val cipher = Cipher.getInstance("AES/CTR/NoPadding")

    init {
        require(key.size == 32) { "AES-256 key" }
        require(iv.size == 16) { "CTR iv" }
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), IvParameterSpec(iv))
        if (logged.compareAndSet(false, true)) {
            println("kmtproto [ctr] Cipher AES/CTR/NoPadding (HotSpot)")
        }
    }

    actual fun process(data: ByteArray): ByteArray {
        val out = ByteArray(data.size)
        processInto(data, 0, out, 0, data.size)
        return out
    }

    actual fun processInto(src: ByteArray, srcOff: Int, dst: ByteArray, dstOff: Int, len: Int) {
        require(srcOff >= 0 && dstOff >= 0 && len >= 0)
        require(srcOff + len <= src.size && dstOff + len <= dst.size)
        if (len == 0) return
        // CipherCore.update clones the whole input when src===dst. Fake DC CTRs
        // a ~1 GiB tape in place; one clone OOMs a 2g heap. Chunked update keeps
        // the CTR counter and only copies CHUNK bytes.
        var i = 0
        while (i < len) {
            val n = minOf(CHUNK, len - i)
            val wrote = try {
                cipher.update(src, srcOff + i, n, dst, dstOff + i)
            } catch (e: ShortBufferException) {
                throw IllegalStateException(e)
            }
            check(wrote == n) { "AES/CTR buffered $wrote of $n" }
            i += n
        }
    }

    private companion object {
        const val CHUNK = 64 * 1024
        val logged = AtomicBoolean(false)
    }
}
