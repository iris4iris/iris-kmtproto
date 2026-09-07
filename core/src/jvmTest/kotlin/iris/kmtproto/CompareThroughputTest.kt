package iris.kmtproto

import iris.kmtproto.crypto.AesCtr
import iris.kmtproto.crypto.IgeCtx
import iris.kmtproto.crypto.PlatformCrypto
import kotlin.math.max

/**
 * Same sizes / units / min-of-N as nccrypto `bench_nccrypto.py`.
 * MB/s is SI (1e6), matching their table — not MiB/s.
 */
fun main() {
    println("kmtproto | " + System.getProperty("java.vm.name") + " " + System.getProperty("java.version"))
    val key = PlatformCrypto.randomBytes(32)
    val iv32 = PlatformCrypto.randomBytes(32)
    val iv16 = PlatformCrypto.randomBytes(16)
    println()
    println("%-38s %22s".format("benchmark", "kmtproto"))
    println("-".repeat(72))

    for (size in intArrayOf(16, 512, 4096, 65536)) {
        val label = if (size < 1024) "${size}B" else "${size / 1024}KB"
        val data = PlatformCrypto.randomBytes(size)
        val ige = IgeCtx(encrypt = true)
        val dest = ByteArray(size)
        row("IGE enc $label per call (alloc)", size) {
            ige.crypt(key, iv32, data, dest = dest)
        }
    }

    val data64 = PlatformCrypto.randomBytes(65536)
    row("IGE enc 64KB per call (new ctx each)", 65536) {
        IgeCtx(encrypt = true).crypt(key, iv32, data64)
    }

    for (size in intArrayOf(16, 512, 4096)) {
        val label = if (size < 1024) "${size}B" else "${size / 1024}KB"
        val data = PlatformCrypto.randomBytes(size)
        val ctr = AesCtr(key, iv16.copyOf())
        val dest = ByteArray(size)
        row("CTR $label per packet (stream)", size) {
            ctr.processInto(data, 0, dest, 0, size)
        }
    }

    run {
        val data = PlatformCrypto.randomBytes(4096)
        val ctr = AesCtr(key, iv16.copyOf())
        val buf = data.copyOf()
        row("CTR 4KB per packet in-place (stream)", 4096) {
            ctr.processInto(buf, 0, buf, 0, buf.size)
        }
    }

    run {
        val big = PlatformCrypto.randomBytes(16 * 1024 * 1024)
        val ctr = AesCtr(key, iv16.copyOf())
        val dest = ByteArray(big.size)
        row("CTR 16MB single stream", big.size) {
            ctr.processInto(big, 0, dest, 0, big.size)
        }
    }

    run {
        val big = PlatformCrypto.randomBytes(16 * 1024 * 1024)
        val ctr = AesCtr(key, iv16.copyOf())
        row("CTR 16MB single in-place", big.size) {
            ctr.processInto(big, 0, big, 0, big.size)
        }
    }

    println("-".repeat(72))
    println("wrapper overhead (CTR 16B, crypto negligible):")
    val tiny = ByteArray(16) { it.toByte() }
    val ctr4 = AesCtr(key, iv16.copyOf())
    val dest16 = ByteArray(16)
    row("CTR 16B call overhead", 16) { ctr4.process(tiny) }
    row("CTR 16B call + ctx alloc", 16) { AesCtr(key, iv16.copyOf()).process(tiny) }
    row("CTR 16B in-place overhead", 16) {
        ctr4.processInto(dest16, 0, dest16, 0, 16)
    }
}

private fun benchNs(fn: () -> Unit, reps: Int = 5, budgetS: Double = 1.0): Double {
    repeat(3) { fn() }
    var best = Double.POSITIVE_INFINITY
    val t0 = System.nanoTime()
    var done = 0
    while (true) {
        val t1 = System.nanoTime()
        fn()
        val dt = (System.nanoTime() - t1).toDouble()
        if (dt < best) best = dt
        done++
        val elapsed = (System.nanoTime() - t0) / 1e9
        if (elapsed > budgetS && done >= reps) break
        if (done >= max(reps, 100_000)) break
    }
    return best
}

private fun row(name: String, nbytes: Int, fn: () -> Unit) {
    val ns = benchNs(fn)
    val mbs = if (ns > 0) nbytes / ns * 1_000.0 else 0.0
    val mibs = mbs / 1.048576
    println("%-38s %10.0f ns %7.0f MB/s (%5.1f MiB/s)".format(name, ns, mbs, mibs))
}
