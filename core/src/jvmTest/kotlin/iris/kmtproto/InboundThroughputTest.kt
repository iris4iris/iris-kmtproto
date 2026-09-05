package iris.kmtproto

import iris.kmtproto.client.MuxThreads
import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.mtproto.EncryptedConnection
import iris.kmtproto.mtproto.MsgIdFactory
import iris.kmtproto.tl.gen.UpdatesCtor
import iris.kmtproto.tl.toBytes
import iris.kmtproto.transport.Datacenter
import iris.kmtproto.transport.MtprotoTransport
import iris.kmtproto.transport.connectObfuscated
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InboundThroughputTest {
    @Test
    fun unwrapSingleUpdate() {
        val n = envInt("KMTPROTO_BENCH_N", 20_000)
        runUnwrapBench("1 update", messagesPerFrame = 1, frames = n, minFps = 500)
    }

    @Test
    fun unwrapFatUpdates() {
        val n = envInt("KMTPROTO_BENCH_N_FAT", 2_000)
        runUnwrapBench("32 updates", messagesPerFrame = 32, frames = n, minFps = 50)
    }

    @Test
    fun fakeDcSingleUpdate() {
        val n = envInt("KMTPROTO_BENCH_DC_N", 8_000)
        runFakeDcBench("1 update", messagesPerFrame = 1, frames = n)
    }

    @Test
    fun fakeDcPackedUpdates() {
        val n = envInt("KMTPROTO_BENCH_DC_N_FAT", 1_000)
        runFakeDcBench("32 updates", messagesPerFrame = 32, frames = n)
    }

    private fun runUnwrapBench(label: String, messagesPerFrame: Int, frames: Int, minFps: Int) {
        val key = AuthKey(PlatformCrypto.randomBytes(256))
        val salt = 0x1111_2222_3333_4444L
        val session = 0x5555_6666_7777_8888L
        val conn = EncryptedConnection(
            transport = NoopTransport,
            authKey = key,
            salt = salt,
            sessionId = session,
            msgIds = MsgIdFactory(0),
            writeContext = EmptyCoroutineContext,
        )
        val body = sampleUpdates(messagesPerFrame).toBytes()
        val frame = encodeInbound(key, salt, session, msgId = 8L, seqNo = 1, body = body)
        val probe = conn.unwrapFrame(frame)
        assertEquals(1, probe.second.size)
        assertTrue(probe.second[0] is UpdatesCtor)
        assertEquals(messagesPerFrame, (probe.second[0] as UpdatesCtor).updates.size)

        repeat(200) { conn.unwrapFrame(frame) }
        val t0 = System.nanoTime()
        var events = 0
        repeat(frames) {
            val (_, objs) = conn.unwrapFrame(frame)
            events += (objs[0] as UpdatesCtor).updates.size
        }
        val sec = (System.nanoTime() - t0) / 1e9
        printBench("unwrap $label", frames, frame.size, sec, events)
        assertTrue(frames / sec >= minFps, "$label too slow: ${"%.0f".format(frames / sec)} frames/s < $minFps")
    }

    private fun runFakeDcBench(label: String, messagesPerFrame: Int, frames: Int) = runBlocking {
        val key = AuthKey(PlatformCrypto.randomBytes(256))
        val salt = 0x1111_2222_3333_4444L
        val session = 0x5555_6666_7777_8888L
        val expected = frames * messagesPerFrame
        val dc = FakeDc()
        val threads = MuxThreads("dc-bench")
        val seen = AtomicInteger(0)
        val done = CompletableDeferred<Unit>()
        try {
            dc.start(key, salt, session, messagesPerFrame, frames)
            val transport = connectObfuscated(Datacenter(99, "127.0.0.1", dc.port))
            val conn = EncryptedConnection(
                transport = transport,
                authKey = key,
                salt = salt,
                sessionId = session,
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
            withTimeout(120_000) { done.await() }
            val sec = (System.nanoTime() - t0) / 1e9
            job.cancel()
            runCatching { transport.close() }
            printBench("dc $label", frames, dc.frameBytes, sec, seen.get())
            assertEquals(expected, seen.get())
        } finally {
            dc.close()
            threads.close()
        }
    }
}

private fun printBench(label: String, frames: Int, frameBytes: Int, sec: Double, events: Int) {
    val fps = frames / sec
    val eps = events / sec
    val mbs = (frames.toLong() * frameBytes) / sec / (1024.0 * 1024.0)
    println(
        "kmtproto bench $label: $frames frames × $frameBytes B in ${"%.3f".format(sec)}s → " +
            "${"%.0f".format(fps)} frames/s, ${"%.0f".format(eps)} updates/s, ${"%.1f".format(mbs)} MiB/s",
    )
}

private object NoopTransport : MtprotoTransport {
    override suspend fun send(payload: ByteArray) = Unit
    override suspend fun receive(): ByteArray = error("no socket")
    override suspend fun close() = Unit
}
