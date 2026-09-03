package iris.kmtproto

import iris.kmtproto.transport.parseIpv4
import iris.kmtproto.transport.socks5Handshake
import iris.kmtproto.transport.writeSocksAddr
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull

class Socks5Test {
    @Test
    fun parseIpv4OnlyDotted() {
        assertContentEquals(byteArrayOf(149.toByte(), 154.toByte(), 167.toByte(), 51), parseIpv4("149.154.167.51"))
        assertNull(parseIpv4("dc2.telegram.org"))
        assertNull(parseIpv4("1.2.3"))
        assertNull(parseIpv4("1.2.3.256"))
    }

    @Test
    fun writeAddrIpv4AndDomain() {
        val ip = ByteArrayOutputStream()
        writeSocksAddr(DataOutputStream(ip), "149.154.167.51")
        assertContentEquals(
            byteArrayOf(1, 149.toByte(), 154.toByte(), 167.toByte(), 51),
            ip.toByteArray(),
        )
        val name = ByteArrayOutputStream()
        writeSocksAddr(DataOutputStream(name), "example.org")
        val nb = "example.org".encodeToByteArray()
        assertEquals(3, name.toByteArray()[0].toInt())
        assertEquals(nb.size, name.toByteArray()[1].toInt() and 0xff)
        assertContentEquals(nb, name.toByteArray().copyOfRange(2, 2 + nb.size))
    }

    @Test
    fun handshakeNoAuthConnectsIpv4() {
        val replies = byteArrayOf(
            5, 0,
            5, 0, 0, 1, 0, 0, 0, 0, 0, 0,
        )
        val written = ByteArrayOutputStream()
        socks5Handshake(
            DataInputStream(ByteArrayInputStream(replies)),
            DataOutputStream(written),
            "149.154.167.51",
            443,
            username = null,
            password = null,
        )
        val out = written.toByteArray()
        assertContentEquals(byteArrayOf(5, 1, 0), out.copyOfRange(0, 3))
        assertContentEquals(
            byteArrayOf(5, 1, 0, 1, 149.toByte(), 154.toByte(), 167.toByte(), 51, 0x01, 0xBB.toByte()),
            out.copyOfRange(3, out.size),
        )
    }

    @Test
    fun handshakeUserPass() {
        val replies = byteArrayOf(
            5, 2,
            1, 0,
            5, 0, 0, 1, 127, 0, 0, 1, 0, 0,
        )
        val written = ByteArrayOutputStream()
        socks5Handshake(
            DataInputStream(ByteArrayInputStream(replies)),
            DataOutputStream(written),
            "10.0.0.1",
            443,
            username = "u",
            password = "p",
        )
        val out = written.toByteArray()
        assertContentEquals(byteArrayOf(5, 2, 0, 2), out.copyOfRange(0, 4))
        assertContentEquals(byteArrayOf(1, 1, 'u'.code.toByte(), 1, 'p'.code.toByte()), out.copyOfRange(4, 9))
    }
}
