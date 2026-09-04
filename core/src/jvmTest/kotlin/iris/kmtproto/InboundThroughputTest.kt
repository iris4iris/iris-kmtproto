package iris.kmtproto

import iris.kmtproto.crypto.AesIge
import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.MsgKeys
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.mtproto.EncryptedConnection
import iris.kmtproto.mtproto.MsgIdFactory
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UpdatesCtor
import iris.kmtproto.tl.toBytes
import iris.kmtproto.transport.MtprotoTransport
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InboundThroughputTest {
    @Test
    fun unwrapSingleUpdate() {
        val n = envInt("KMTPROTO_BENCH_N", 20_000)
        runBench("1 update", messagesPerFrame = 1, frames = n, minFps = 500)
    }

    @Test
    fun unwrapFatUpdates() {
        val n = envInt("KMTPROTO_BENCH_N_FAT", 2_000)
        runBench("32 updates", messagesPerFrame = 32, frames = n, minFps = 50)
    }

    private fun runBench(label: String, messagesPerFrame: Int, frames: Int, minFps: Int) {
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
        val fps = frames / sec
        val eps = events / sec
        val mbs = (frames.toLong() * frame.size) / sec / (1024.0 * 1024.0)
        println(
            "kmtproto bench $label: $frames frames × ${frame.size} B in ${"%.3f".format(sec)}s → " +
                "${"%.0f".format(fps)} frames/s, ${"%.0f".format(eps)} updates/s, ${"%.1f".format(mbs)} MiB/s",
        )
        assertTrue(fps >= minFps, "$label too slow: ${"%.0f".format(fps)} frames/s < $minFps")
    }
}

private object NoopTransport : MtprotoTransport {
    override suspend fun send(payload: ByteArray) = Unit
    override suspend fun receive(): ByteArray = error("no socket")
    override suspend fun close() = Unit
}

private fun sampleUpdates(count: Int): UpdatesCtor {
    val updates = List(count) { i ->
        UpdateNewMessage(
            message = MessageCtor(
                id = i + 1,
                peerId = PeerUser(42),
                date = 1_700_000_000 + i,
                message = "hello $i",
                fromId = PeerUser(42),
            ),
            pts = i + 1,
            ptsCount = 1,
        )
    }
    return UpdatesCtor(updates = updates, users = emptyList(), chats = emptyList(), date = 1_700_000_000, seq = 0)
}

/** Server → client (x = 8), same layout as EncryptedConnection.encryptPacket. */
private fun encodeInbound(
    authKey: AuthKey,
    salt: Long,
    sessionId: Long,
    msgId: Long,
    seqNo: Int,
    body: ByteArray,
): ByteArray {
    val headerAndBody = concat(
        salt.toLeBytes(),
        sessionId.toLeBytes(),
        msgId.toLeBytes(),
        seqNo.toLeBytes(),
        body.size.toLeBytes(),
        body,
    )
    var pad = 12
    while ((headerAndBody.size + pad) % 16 != 0) pad++
    val inner = concat(headerAndBody, PlatformCrypto.randomBytes(pad))
    val msgKey = MsgKeys.msgKey(authKey.key, inner, x = 8)
    val (aesKey, aesIv) = MsgKeys.deriveAes(authKey.key, msgKey, x = 8)
    return concat(authKey.keyId.toLeBytes(), msgKey, AesIge.encrypt(aesKey, aesIv, inner))
}

private fun envInt(name: String, default: Int): Int =
    System.getenv(name)?.toIntOrNull()?.takeIf { it > 0 } ?: default
