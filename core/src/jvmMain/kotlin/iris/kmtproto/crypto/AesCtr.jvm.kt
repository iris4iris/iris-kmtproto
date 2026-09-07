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
        val n = try {
            cipher.update(src, srcOff, len, dst, dstOff)
        } catch (e: ShortBufferException) {
            throw IllegalStateException(e)
        }
        check(n == len) { "AES/CTR buffered $n of $len" }
    }

    private companion object {
        val logged = AtomicBoolean(false)
    }
}
