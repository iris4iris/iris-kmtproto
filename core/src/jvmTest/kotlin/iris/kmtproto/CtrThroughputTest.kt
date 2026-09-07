package iris.kmtproto

import iris.kmtproto.crypto.AesCtr
import iris.kmtproto.crypto.PlatformCrypto
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

class CtrThroughputTest {
    @Test
    fun ctr16() = runCtr(16)

    @Test
    fun ctr512() = runCtr(512)

    @Test
    fun ctr4KiB() = runCtr(4 * 1024)

    @Test
    fun ctr64KiB() = runCtr(64 * 1024)
}

fun main() {
    CtrThroughputTest().run {
        ctr16()
        ctr512()
        ctr4KiB()
        ctr64KiB()
    }
    runCtr(1024 * 1024)
    val huge = envInt("KMTPROTO_BENCH_CTR_HUGE", 0)
    if (huge > 0) runCtr(huge)
}

private fun runCtr(size: Int) {
    val key = PlatformCrypto.randomBytes(32)
    val iv = PlatformCrypto.randomBytes(16)
    val plain = PlatformCrypto.randomBytes(size)
    val probe = AesCtr(key, iv.copyOf()).process(plain)
    val back = AesCtr(key, iv.copyOf()).process(probe)
    assertContentEquals(plain, back)

    val ctx = AesCtr(key, iv.copyOf())
    val dest = ByteArray(size)
    val n = envInt("KMTPROTO_BENCH_CTR_N", (256 * 1024 * 1024 / size).coerceAtLeast(200))
    val warmup = benchWarmup().coerceAtMost(n)
    repeat(warmup) { ctx.processInto(plain, 0, dest, 0, size) }
    val t0 = System.nanoTime()
    repeat(n) { ctx.processInto(plain, 0, dest, 0, size) }
    val sec = (System.nanoTime() - t0) / 1e9
    printBench("ctr crypt $size B", n, size, sec, n)
    assertTrue(n / sec * size / (1024.0 * 1024.0) >= 10.0, "ctr $size B too slow")
}
