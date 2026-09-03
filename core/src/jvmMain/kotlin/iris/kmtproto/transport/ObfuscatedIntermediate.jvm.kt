package iris.kmtproto.transport

import iris.kmtproto.crypto.AesCtr
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.readIntLe
import iris.kmtproto.toHex
import iris.kmtproto.toLeBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket

private class ObfuscatedIntermediate(
    private val socket: Socket,
    private val input: DataInputStream,
    private val output: DataOutputStream,
    private val encryptor: AesCtr,
    private val decryptor: AesCtr,
) : MtprotoTransport {
    override suspend fun send(payload: ByteArray) = withContext(Dispatchers.IO) {
        val framed = payload.size.toLeBytes() + payload
        val encrypted = encryptor.process(framed)
        output.write(encrypted)
        output.flush()
    }

    override suspend fun receive(): ByteArray = withContext(Dispatchers.IO) {
        while (true) {
            val lenEnc = ByteArray(4)
            input.readFully(lenEnc)
            val lenDec = decryptor.process(lenEnc)
            val len = lenDec.readIntLe() and 0x7fffffff
            require(len in 0..2_000_000) {
                "implausible frame length $len (head=${lenDec.toHex()})"
            }
            val payloadEnc = ByteArray(len)
            input.readFully(payloadEnc)
            val payload = decryptor.process(payloadEnc)
            if (payload.size >= 20) return@withContext payload
        }
        error("unreachable")
    }

    override suspend fun close() = withContext(Dispatchers.IO) {
        runCatching { socket.close() }
        Unit
    }

    override fun setReadTimeoutMs(ms: Int) {
        socket.soTimeout = ms
    }
}

private class PlainIntermediate(
    private val socket: Socket,
    private val input: DataInputStream,
    private val output: DataOutputStream,
) : MtprotoTransport {
    override suspend fun send(payload: ByteArray) = withContext(Dispatchers.IO) {
        output.write(payload.size.toLeBytes())
        output.write(payload)
        output.flush()
    }

    override suspend fun receive(): ByteArray = withContext(Dispatchers.IO) {
        while (true) {
            val lenBuf = ByteArray(4)
            input.readFully(lenBuf)
            val len = lenBuf.readIntLe() and 0x7fffffff
            require(len in 0..2_000_000) { "implausible frame length $len" }
            val payload = ByteArray(len)
            input.readFully(payload)
            if (payload.size >= 20) return@withContext payload
        }
        error("unreachable")
    }

    override suspend fun close() = withContext(Dispatchers.IO) {
        runCatching { socket.close() }
        Unit
    }

    override fun setReadTimeoutMs(ms: Int) {
        socket.soTimeout = ms
    }
}

actual suspend fun connectObfuscated(dc: Datacenter): MtprotoTransport = withContext(Dispatchers.IO) {
    connectObfuscatedAt(dc.host, dc.port)
}

private fun connectObfuscatedAt(host: String, port: Int): MtprotoTransport {
    val socket = openSocket(host, port)
    val input = DataInputStream(socket.getInputStream())
    val output = DataOutputStream(socket.getOutputStream())

    val init = ByteArray(64)
    while (true) {
        PlatformCrypto.randomBytes(64).copyInto(init)
        val b0 = init[0].toInt() and 0xff
        val first4 = init.readIntLe(0)
        val second4 = init.readIntLe(4)
        val forbidden = first4 == 0x44414548 || first4 == 0x54534f50 ||
            first4 == 0x20544547 || first4 == 0x4954504f ||
            first4 == 0xeeeeeeee.toInt() || first4 == 0xdddddddd.toInt() ||
            first4 == 0x00000000
        if (b0 != 0xef && second4 != 0 && !forbidden) break
    }
    init[56] = 0xee.toByte()
    init[57] = 0xee.toByte()
    init[58] = 0xee.toByte()
    init[59] = 0xee.toByte()

    val encryptKey = init.copyOfRange(8, 40)
    val encryptIv = init.copyOfRange(40, 56)
    val reversed = init.reversedArray()
    val decryptKey = reversed.copyOfRange(8, 40)
    val decryptIv = reversed.copyOfRange(40, 56)

    val encryptor = AesCtr(encryptKey, encryptIv)
    val decryptor = AesCtr(decryptKey, decryptIv)

    val encryptedInit = encryptor.process(init)
    encryptedInit.copyInto(init, 56, 56, 64)
    output.write(init)
    output.flush()

    return ObfuscatedIntermediate(socket, input, output, encryptor, decryptor)
}

private fun openSocket(host: String, port: Int): Socket {
    val socket = Socket()
    socket.tcpNoDelay = true
    socket.connect(InetSocketAddress(host, port), 8_000)
    socket.soTimeout = 12_000
    return socket
}
