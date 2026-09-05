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
) {
    val expected = frames * messagesPerFrame
    val threads = MuxThreads("dc-bench")
    val seen = AtomicInteger(0)
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
                        if (seen.addAndGet(box.updates.size) >= expected) {
                            done.complete(Unit)
                        }
                    }
                } catch (e: Throwable) {
                    done.completeExceptionally(e)
                }
            }
            val t0 = System.nanoTime()
            withTimeout(180_000) { done.await() }
            val sec = (System.nanoTime() - t0) / 1e9
            job.cancel()
            runCatching { transport.close() }
            printBench("dc $label", frames, frameBytes, sec, seen.get())
            check(seen.get() == expected) { "dc $label expected $expected updates, got ${seen.get()}" }
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
