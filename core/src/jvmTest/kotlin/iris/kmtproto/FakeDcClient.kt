package iris.kmtproto

import iris.kmtproto.client.MuxThreads
import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.mtproto.EncryptedConnection
import iris.kmtproto.mtproto.MsgIdFactory
import iris.kmtproto.tl.gen.UpdatesCtor
import iris.kmtproto.transport.Datacenter
import iris.kmtproto.transport.connectObfuscated
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

internal suspend fun runFakeDcClient(
    label: String,
    host: String,
    port: Int,
    authKey: AuthKey,
    salt: Long,
    sessionId: Long,
    messagesPerFrame: Int,
    frames: Int,
    frameBytes: Int,
    warmup: Int = benchWarmup(),
) {
    val warmupUpdates = warmup * messagesPerFrame
    val benchUpdates = frames * messagesPerFrame
    val threads = MuxThreads("dc-bench")
    val warmed = AtomicInteger(0)
    val seen = AtomicInteger(0)
    val t0 = AtomicLong(0)
    val hot = CompletableDeferred<Unit>()
    val done = CompletableDeferred<Unit>()
    try {
        coroutineScope {
            val transport = connectObfuscated(Datacenter(99, host, port))
            val conn = EncryptedConnection(
                transport = transport,
                authKey = authKey,
                salt = salt,
                sessionId = sessionId,
                msgIds = MsgIdFactory(0),
                writeContext = threads.write,
            )
            val job = launch(threads.read) {
                try {
                    conn.runReader { obj ->
                        val box = obj as? UpdatesCtor ?: return@runReader
                        val n = box.updates.size
                        if (warmupUpdates > 0 && warmed.get() < warmupUpdates) {
                            if (warmed.addAndGet(n) >= warmupUpdates) {
                                t0.set(System.nanoTime())
                                hot.complete(Unit)
                            }
                            return@runReader
                        }
                        if (seen.addAndGet(n) >= benchUpdates) {
                            done.complete(Unit)
                        }
                    }
                } catch (e: Throwable) {
                    hot.completeExceptionally(e)
                    done.completeExceptionally(e)
                }
            }
            val start = if (warmupUpdates > 0) {
                withTimeout(180_000) { hot.await() }
                t0.get()
            } else {
                System.nanoTime()
            }
            withTimeout(180_000) { done.await() }
            val sec = (System.nanoTime() - start) / 1e9
            job.cancel()
            runCatching { transport.close() }
            printBench("dc $label", frames, frameBytes, sec, seen.get())
            check(seen.get() == benchUpdates) { "dc $label expected $benchUpdates updates, got ${seen.get()}" }
        }
    } finally {
        threads.close()
    }
}

internal fun printBench(label: String, frames: Int, frameBytes: Int, sec: Double, events: Int) {
    val fps = frames / sec
    val eps = events / sec
    val mbs = (frames.toLong() * frameBytes) / sec / (1024.0 * 1024.0)
    println(
        "kmtproto bench $label: $frames frames × $frameBytes B in ${"%.3f".format(sec)}s → " +
            "${"%.0f".format(fps)} frames/s, ${"%.0f".format(eps)} updates/s, ${"%.1f".format(mbs)} MiB/s",
    )
}
