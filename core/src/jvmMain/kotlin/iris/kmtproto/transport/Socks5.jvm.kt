package iris.kmtproto.transport

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket

internal fun openTcp(destHost: String, destPort: Int, proxy: Proxy?): Socket {
    val socket = Socket()
    socket.tcpNoDelay = true
    when (proxy) {
        null -> {
            socket.connect(InetSocketAddress(destHost, destPort), 8_000)
        }
        is Proxy.Socks5 -> {
            socket.connect(InetSocketAddress(proxy.host, proxy.port), 8_000)
            socket.soTimeout = 12_000
            socks5Handshake(
                DataInputStream(socket.getInputStream()),
                DataOutputStream(socket.getOutputStream()),
                destHost,
                destPort,
                proxy.username,
                proxy.password,
            )
        }
    }
    socket.soTimeout = 12_000
    return socket
}

internal fun socks5Handshake(
    input: DataInputStream,
    output: DataOutputStream,
    destHost: String,
    destPort: Int,
    username: String?,
    password: String?,
) {
    val auth = !username.isNullOrEmpty()
    if (auth) {
        output.write(byteArrayOf(5, 2, 0, 2))
    } else {
        output.write(byteArrayOf(5, 1, 0))
    }
    output.flush()
    val ver = input.readUnsignedByte()
    val method = input.readUnsignedByte()
    check(ver == 5) { "SOCKS version $ver" }
    when (method) {
        0 -> Unit
        2 -> {
            check(auth) { "SOCKS5 server demands username/password" }
            val user = username!!.encodeToByteArray()
            val pass = (password ?: "").encodeToByteArray()
            require(user.size in 1..255 && pass.size <= 255) { "SOCKS5 credentials too long" }
            output.writeByte(1)
            output.writeByte(user.size)
            output.write(user)
            output.writeByte(pass.size)
            output.write(pass)
            output.flush()
            val authVer = input.readUnsignedByte()
            val status = input.readUnsignedByte()
            check(authVer == 1 && status == 0) { "SOCKS5 auth failed status=$status" }
        }
        0xff -> error("SOCKS5: no acceptable auth method")
        else -> error("SOCKS5: unsupported method $method")
    }

    output.writeByte(5)
    output.writeByte(1)
    output.writeByte(0)
    writeSocksAddr(output, destHost)
    output.writeShort(destPort)
    output.flush()

    val rver = input.readUnsignedByte()
    val rep = input.readUnsignedByte()
    val rsv = input.readUnsignedByte()
    val atyp = input.readUnsignedByte()
    check(rver == 5 && rsv == 0) { "SOCKS5 bad reply ver=$rver rsv=$rsv" }
    skipSocksAddr(input, atyp)
    if (rep != 0) error("SOCKS5 CONNECT failed: ${socksRep(rep)}")
}

internal fun writeSocksAddr(output: DataOutputStream, host: String) {
    val ipv4 = parseIpv4(host)
    if (ipv4 != null) {
        output.writeByte(1)
        output.write(ipv4)
        return
    }
    val name = host.encodeToByteArray()
    require(name.size in 1..255) { "SOCKS5 domain length ${name.size}" }
    output.writeByte(3)
    output.writeByte(name.size)
    output.write(name)
}

private fun skipSocksAddr(input: DataInputStream, atyp: Int) {
    when (atyp) {
        1 -> input.skipBytes(4 + 2)
        3 -> {
            val n = input.readUnsignedByte()
            input.skipBytes(n + 2)
        }
        4 -> input.skipBytes(16 + 2)
        else -> error("SOCKS5 unknown ATYP $atyp")
    }
}

internal fun parseIpv4(host: String): ByteArray? {
    val parts = host.split('.')
    if (parts.size != 4) return null
    val out = ByteArray(4)
    for (i in 0..3) {
        val n = parts[i].toIntOrNull() ?: return null
        if (n !in 0..255) return null
        out[i] = n.toByte()
    }
    return out
}

private fun socksRep(code: Int): String = when (code) {
    1 -> "general failure"
    2 -> "not allowed"
    3 -> "network unreachable"
    4 -> "host unreachable"
    5 -> "connection refused"
    6 -> "TTL expired"
    7 -> "command not supported"
    8 -> "address type not supported"
    else -> "code $code"
}
