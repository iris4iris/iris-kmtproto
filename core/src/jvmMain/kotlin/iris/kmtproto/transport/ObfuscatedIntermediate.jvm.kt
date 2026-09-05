@file:Suppress("BlockingMethodInNonBlockingContext")

package iris.kmtproto.transport

import iris.kmtproto.crypto.AesCtr
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.readIntLe
import iris.kmtproto.toHex
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

private const val STREAM_BUF = 64 * 1024

private class ObfuscatedIntermediate(
    private val socket: Socket,
    private val input: DataInputStream,
    private val output: DataOutputStream,
    private val encryptor: AesCtr,
    private val decryptor: AesCtr,
) : MtprotoTransport {
    private val sendLen = ByteArray(4)
    private var sendBuf = ByteArray(2048)
    private val lenBuf = ByteArray(4)
    private var payloadBuf = ByteArray(2048)

    override suspend fun send(payload: ByteArray) {
        val n = 4 + payload.size
        if (sendBuf.size < n) sendBuf = ByteArray(n.coerceAtLeast(sendBuf.size * 2))
        val size = payload.size
        sendLen[0] = size.toByte()
        sendLen[1] = (size shr 8).toByte()
        sendLen[2] = (size shr 16).toByte()
        sendLen[3] = (size shr 24).toByte()
        sendLen.copyInto(sendBuf, 0)
        payload.copyInto(sendBuf, 4)
        encryptor.processInto(sendBuf, 0, sendBuf, 0, n)
        output.write(sendBuf, 0, n)
        output.flush()
    }

    override suspend fun receive(): ByteArray {
        while (true) {
            input.readFully(lenBuf)
            decryptor.processInto(lenBuf, 0, lenBuf, 0, 4)
            val len = lenBuf.readIntLe() and 0x7fffffff
            require(len in 0..2_000_000) {
                "implausible frame length $len (head=${lenBuf.toHex()})"
            }
            if (payloadBuf.size != len) payloadBuf = ByteArray(len)
            input.readFully(payloadBuf, 0, len)
            decryptor.processInto(payloadBuf, 0, payloadBuf, 0, len)
            if (len >= 20) return payloadBuf
        }
    }

    override suspend fun close() {
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
    private val lenBuf = ByteArray(4)
    private var payloadBuf = ByteArray(2048)

    override suspend fun send(payload: ByteArray) {
        val size = payload.size
        lenBuf[0] = size.toByte()
        lenBuf[1] = (size shr 8).toByte()
        lenBuf[2] = (size shr 16).toByte()
        lenBuf[3] = (size shr 24).toByte()
        output.write(lenBuf)
        output.write(payload)
        output.flush()
    }

    override suspend fun receive(): ByteArray {
        while (true) {
            input.readFully(lenBuf)
            val len = lenBuf.readIntLe() and 0x7fffffff
            require(len in 0..2_000_000) { "implausible frame length $len" }
            if (payloadBuf.size != len) payloadBuf = ByteArray(len)
            input.readFully(payloadBuf, 0, len)
            if (len >= 20) return payloadBuf
        }
    }

    override suspend fun close() {
        runCatching { socket.close() }
        Unit
    }

    override fun setReadTimeoutMs(ms: Int) {
        socket.soTimeout = ms
    }
}

actual suspend fun connectObfuscated(dc: Datacenter, proxy: Proxy?): MtprotoTransport =
    connectObfuscatedAt(dc.host, dc.port, proxy)

private fun connectObfuscatedAt(host: String, port: Int, proxy: Proxy?): MtprotoTransport {
    val socket = openTcp(host, port, proxy)
    val input = DataInputStream(BufferedInputStream(socket.getInputStream(), STREAM_BUF))
    val output = DataOutputStream(BufferedOutputStream(socket.getOutputStream(), STREAM_BUF))

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
