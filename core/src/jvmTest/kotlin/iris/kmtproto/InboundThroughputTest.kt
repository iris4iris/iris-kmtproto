package iris.kmtproto

import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.mtproto.EncryptedConnection
import iris.kmtproto.mtproto.MsgIdFactory
import iris.kmtproto.tl.gen.UpdatesCtor
import iris.kmtproto.tl.toBytes
import iris.kmtproto.transport.MtprotoTransport
import kotlinx.coroutines.runBlocking
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

        repeat(2_000) { conn.unwrapFrame(frame) }
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
        if (System.getenv("KMTPROTO_FAKE_DC_INPROCESS") == "1") {
            val key = AuthKey(PlatformCrypto.randomBytes(256))
            val salt = 0x1111_2222_3333_4444L
            val session = 0x5555_6666_7777_8888L
            FakeDc().use { dc ->
                dc.start(key, salt, session, messagesPerFrame, frames)
                val frameBytes = InboundEncoder().payloadBytes(sampleUpdates(messagesPerFrame).toBytes().size)
                runFakeDcClient(
                    label = label,
                    host = "127.0.0.1",
                    port = dc.port,
                    authKey = key,
                    salt = salt,
                    sessionId = session,
                    messagesPerFrame = messagesPerFrame,
                    frames = frames,
                    frameBytes = frameBytes,
                )
            }
            return@runBlocking
        }
        startFakeDcProcess(messagesPerFrame, frames).use { dc ->
            runFakeDcClient(
                label = label,
                host = dc.ready.host,
                port = dc.ready.port,
                authKey = dc.ready.authKey,
                salt = dc.ready.salt,
                sessionId = dc.ready.sessionId,
                messagesPerFrame = dc.ready.messages,
                frames = dc.ready.frames,
                frameBytes = dc.ready.frameBytes,
            )
        }
    }
}

private object NoopTransport : MtprotoTransport {
    override suspend fun send(payload: ByteArray) = Unit
    override suspend fun receive(): ByteArray = error("no socket")
    override suspend fun close() = Unit
}
