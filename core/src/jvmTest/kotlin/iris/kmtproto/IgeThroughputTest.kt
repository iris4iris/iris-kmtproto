package iris.kmtproto

import iris.kmtproto.crypto.IgeCtx
import iris.kmtproto.crypto.PlatformCrypto
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

/**
 * Raw AES-256-IGE decrypt — no SHA-256, no TL. Same primitive tgcrypto benches.
 * [IgeCtx.crypt] re-inits the AES key each call (MTProto msg_key → new key per frame).
 */
class IgeThroughputTest {
    @Test
    fun igeDecrypt256() = runIgeDecrypt(256)

    @Test
    fun igeDecrypt4KiB() = runIgeDecrypt(4 * 1024)

    @Test
    fun igeDecrypt64KiB() = runIgeDecrypt(64 * 1024)

    /** Encrypted inner of the 168 B 1-update frame (24 B transport header stripped). */
    @Test
    fun igeDecrypt144() = runIgeDecrypt(144)

    /** Encrypted inner of the 2248 B packed frame. */
    @Test
    fun igeDecrypt2224() = runIgeDecrypt(2224)
}

fun main() {
    IgeThroughputTest().run {
        igeDecrypt144()
        igeDecrypt256()
        igeDecrypt2224()
        igeDecrypt4KiB()
        igeDecrypt64KiB()
    }
}

private fun runIgeDecrypt(size: Int) {
    require(size % 16 == 0)
    val key = PlatformCrypto.randomBytes(32)
    val iv = PlatformCrypto.randomBytes(32)
    val plain = PlatformCrypto.randomBytes(size)
    val cipher = IgeCtx(encrypt = true).crypt(key, iv, plain)
    val probe = IgeCtx(encrypt = false).crypt(key, iv, cipher)
    assertContentEquals(plain, probe)

    val ctx = IgeCtx(encrypt = false)
    val dest = ByteArray(size)
    val n = envInt("KMTPROTO_BENCH_IGE_N", (256 * 1024 * 1024 / size).coerceAtLeast(4_000))
    val warmup = benchWarmup().coerceAtMost(n)
    repeat(warmup) { ctx.crypt(key, iv, cipher, dest = dest) }
    val t0 = System.nanoTime()
    repeat(n) { ctx.crypt(key, iv, cipher, dest = dest) }
    val sec = (System.nanoTime() - t0) / 1e9
    printBench("ige decrypt $size B", n, size, sec, n)
    assertTrue(n / sec * size / (1024.0 * 1024.0) >= 10.0, "ige $size B too slow")
    assertContentEquals(plain, dest)
}
