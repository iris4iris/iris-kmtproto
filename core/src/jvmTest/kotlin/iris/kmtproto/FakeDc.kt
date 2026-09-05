package iris.kmtproto

import iris.kmtproto.crypto.AesCtr
import iris.kmtproto.readIntLe
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * Local obfuscated-intermediate DC. Shared auth_key/salt/session with the client;
 * no DH. Wire payload is a prebuilt [FakeDcTape] (IGE already done). After the
 * handshake the whole blob is CTR'd once, then written — no per-frame encrypt.
 *
 * Bench runs this in a **separate JVM** ([FakeDcMain]) so encrypt/GC do not share
 * the client heap and compiler.
 */
internal class FakeDc(
    host: String = "127.0.0.1",
    port: Int = 0,
) : AutoCloseable {
    private val server = ServerSocket().apply {
        reuseAddress = true
        bind(InetSocketAddress(host, port))
    }
    private val keepOpen = CountDownLatch(1)
    val port: Int get() = server.localPort
    @Volatile var frameBytes: Int = 0
        private set

    fun start(tape: FakeDcTape): Thread = thread(name = "fake-dc") {
        server.soTimeout = 20_000
        val socket = server.accept()
        socket.tcpNoDelay = true
        socket.soTimeout = 0
        try {
            val input = DataInputStream(BufferedInputStream(socket.getInputStream(), 64 * 1024))
            val output = DataOutputStream(BufferedOutputStream(socket.getOutputStream(), 1024 * 1024))
            val (toClient, fromClient) = handshake(input)
            val drain = thread(name = "fake-dc-drain", isDaemon = true) {
                try {
                    drainClient(input, fromClient)
                } finally {
                    keepOpen.countDown()
                }
            }
            frameBytes = tape.frameBytes
            toClient.processInto(tape.blob, 0, tape.blob, 0, tape.blob.size)
            var off = 0
            val blob = tape.blob
            while (off < blob.size) {
                val n = minOf(1 shl 20, blob.size - off)
                output.write(blob, off, n)
                off += n
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
    val toClient = AesCtr(
        ByteArray(32) { wire[55 - it] },
        ByteArray(16) { wire[23 - it] },
    )
    fromClient.processInto(wire, 0, wire, 0, 64)
    return toClient to fromClient
}

private fun drainClient(input: DataInputStream, fromClient: AesCtr) {
    try {
        val lenEnc = ByteArray(4)
        var payload = ByteArray(256)
        while (true) {
            input.readFully(lenEnc)
            fromClient.processInto(lenEnc, 0, lenEnc, 0, 4)
            val len = lenEnc.readIntLe() and 0x7fffffff
            if (len !in 0..2_000_000) return
            if (payload.size < len) payload = ByteArray(len)
            input.readFully(payload, 0, len)
            fromClient.processInto(payload, 0, payload, 0, len)
        }
    } catch (_: Exception) {
    }
}
