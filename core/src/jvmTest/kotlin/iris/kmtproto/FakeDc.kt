package iris.kmtproto

import iris.kmtproto.crypto.AesCtr
import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.readIntLe
import iris.kmtproto.tl.toBytes
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * Local obfuscated-intermediate DC. Shared auth_key/salt/session with the client;
 * no DH. Each frame is [sampleUpdates] + [InboundEncoder], same bodies as unwrap benches.
 */
internal class FakeDc : AutoCloseable {
    private val server = ServerSocket().apply {
        reuseAddress = true
        bind(InetSocketAddress("127.0.0.1", 0))
    }
    private val keepOpen = CountDownLatch(1)
    val port: Int get() = server.localPort
    @Volatile var frameBytes: Int = 0
        private set

    fun start(
        authKey: AuthKey,
        salt: Long,
        sessionId: Long,
        messagesPerFrame: Int,
        frames: Int,
    ): Thread = thread(name = "fake-dc") {
        server.soTimeout = 20_000
        val socket = server.accept()
        socket.tcpNoDelay = true
        socket.soTimeout = 0
        try {
            val input = DataInputStream(socket.getInputStream())
            val output = DataOutputStream(socket.getOutputStream())
            val (toClient, fromClient) = handshake(input)
            val drain = thread(name = "fake-dc-drain", isDaemon = true) {
                drainClient(input, fromClient)
            }
            val body = sampleUpdates(messagesPerFrame).toBytes()
            val encoder = InboundEncoder()
            val payloadLen = encoder.payloadBytes(body.size)
            val wire = ByteArray(4 + payloadLen)
            wire.putIntLe(0, payloadLen)
            var msgId = 8L
            frameBytes = payloadLen
            repeat(frames) { i ->
                encoder.encodeInto(authKey, salt, sessionId, msgId, seqNo = 1, body, wire, 4)
                msgId += 2
                output.write(toClient.process(wire))
                if (i and 127 == 127) output.flush()
            }
            output.flush()
            keepOpen.await(3, TimeUnit.MINUTES)
            drain.interrupt()
        } finally {
            runCatching { socket.close() }
        }
    }

    override fun close() {
        keepOpen.countDown()
        runCatching { server.close() }
    }
}

/**
 * Client encrypts with init[8:40]; that CTR already consumed 64 bytes of init.
 * Client decrypts with reversed init[8:56], counter at 0.
 */
private fun handshake(input: DataInputStream): Pair<AesCtr, AesCtr> {
    val wire = ByteArray(64)
    input.readFully(wire)
    val fromClient = AesCtr(wire.copyOfRange(8, 40), wire.copyOfRange(40, 56))
    fromClient.process(wire)
    val toClient = AesCtr(
        ByteArray(32) { wire[55 - it] },
        ByteArray(16) { wire[23 - it] },
    )
    return toClient to fromClient
}

private fun drainClient(input: DataInputStream, fromClient: AesCtr) {
    try {
        while (true) {
            val lenEnc = ByteArray(4)
            input.readFully(lenEnc)
            val len = fromClient.process(lenEnc).readIntLe() and 0x7fffffff
            if (len !in 0..2_000_000) return
            val payload = ByteArray(len)
            input.readFully(payload)
            fromClient.process(payload)
        }
    } catch (_: Exception) {
    }
}

private fun ByteArray.putIntLe(off: Int, v: Int) {
    this[off] = v.toByte()
    this[off + 1] = (v shr 8).toByte()
    this[off + 2] = (v shr 16).toByte()
    this[off + 3] = (v shr 24).toByte()
}
