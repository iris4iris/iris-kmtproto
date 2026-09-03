package iris.kmtproto.mtproto

import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.readIntLe
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.TlObject
import iris.kmtproto.tl.TlReader
import iris.kmtproto.tl.toBytes
import iris.kmtproto.toLeBytes
import iris.kmtproto.transport.MtprotoTransport

internal class MsgIdFactory(private var timeOffsetSec: Int = 0) {
    private var last = 0L

    fun next(): Long {
        val now = PlatformCrypto.currentTimeMillis() + timeOffsetSec * 1000L
        // unix_ms * 2^32 / 1000, overflow-safe. (now * 2^32) does not fit in Long after ~2004.
        val seconds = now / 1000L
        val millis = now - seconds * 1000L
        var id = (seconds shl 32) + (millis shl 32) / 1000L
        id = id and 3L.inv()
        if (id <= last) id = last + 4
        last = id
        return id
    }

    fun syncFromServer(serverTime: Int) {
        val local = (PlatformCrypto.currentTimeMillis() / 1000L).toInt()
        timeOffsetSec = serverTime - local
    }

    val offset: Int get() = timeOffsetSec
}

internal class PlainConnection(
    private val transport: MtprotoTransport,
    val msgIds: MsgIdFactory = MsgIdFactory(),
) {
    suspend fun send(obj: TlObject): TlObject {
        val body = obj.toBytes()
        val msgId = msgIds.next()
        val packet = concatPlain(0L, msgId, body)
        transport.send(packet)
        return receive()
    }

    suspend fun receive(): TlObject {
        val frame = transport.receive()
        require(frame.size >= 20) { "plain frame too short: ${frame.size}" }
        val authKeyId = frame.readLongLe(0)
        check(authKeyId == 0L) { "expected unencrypted (auth_key_id=0), got $authKeyId" }
        val len = frame.readIntLe(16)
        val body = frame.copyOfRange(20, 20 + len)
        return TlReader(body).readObject()
    }

    private fun concatPlain(authKeyId: Long, msgId: Long, body: ByteArray): ByteArray {
        val out = ByteArray(8 + 8 + 4 + body.size)
        authKeyId.toLeBytes().copyInto(out, 0)
        msgId.toLeBytes().copyInto(out, 8)
        body.size.toLeBytes().copyInto(out, 16)
        body.copyInto(out, 20)
        return out
    }
}
