package iris.kmtproto

import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.IgeCtx
import iris.kmtproto.crypto.MsgKeys
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UpdatesCtor

internal fun sampleUpdates(count: Int): UpdatesCtor {
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
internal fun encodeInbound(
    authKey: AuthKey,
    salt: Long,
    sessionId: Long,
    msgId: Long,
    seqNo: Int,
    body: ByteArray,
): ByteArray = InboundEncoder().encode(authKey, salt, sessionId, msgId, seqNo, body)

/**
 * Reuses one IgeCtx (AESCrypt bind once). [encodeInbound] one-shot is fine for unwrap;
 * Fake DC must keep an instance — a new AesNi per frame is the packed-dc cliff.
 */
internal class InboundEncoder {
    private val ige = IgeCtx(encrypt = true)
    private val aesKey = ByteArray(32)
    private val aesIv = ByteArray(32)
    private val shaA = ByteArray(32)
    private val shaB = ByteArray(32)
    private val msgKey = ByteArray(16)
    private var inner = ByteArray(512)

    fun payloadBytes(bodySize: Int): Int = 24 + innerLen(bodySize)

    fun encode(
        authKey: AuthKey,
        salt: Long,
        sessionId: Long,
        msgId: Long,
        seqNo: Int,
        body: ByteArray,
    ): ByteArray {
        val n = payloadBytes(body.size)
        val out = ByteArray(n)
        encodeInto(authKey, salt, sessionId, msgId, seqNo, body, out, 0)
        return out
    }

    fun encodeInto(
        authKey: AuthKey,
        salt: Long,
        sessionId: Long,
        msgId: Long,
        seqNo: Int,
        body: ByteArray,
        dest: ByteArray,
        destOff: Int,
    ): Int {
        val innerN = innerLen(body.size)
        if (inner.size < innerN) inner = ByteArray(innerN.coerceAtLeast(inner.size * 2))
        inner.putLongLe(0, salt)
        inner.putLongLe(8, sessionId)
        inner.putLongLe(16, msgId)
        inner.putIntLe(24, seqNo)
        inner.putIntLe(28, body.size)
        body.copyInto(inner, 32)
        PlatformCrypto.randomBytes(innerN - 32 - body.size).copyInto(inner, 32 + body.size)
        MsgKeys.msgKeyInto(authKey.key, inner, innerN, 8, msgKey, 0, shaA)
        MsgKeys.deriveAesInto(authKey.key, msgKey, 0, 8, aesKey, aesIv, shaA, shaB)
        dest.putLongLe(destOff, authKey.keyId)
        msgKey.copyInto(dest, destOff + 8)
        ige.crypt(aesKey, aesIv, inner, 0, innerN, dest, destOff + 24)
        return 24 + innerN
    }

    private fun innerLen(bodySize: Int): Int {
        val header = 32 + bodySize
        var pad = 12
        while ((header + pad) % 16 != 0) pad++
        return header + pad
    }
}

private fun ByteArray.putIntLe(off: Int, v: Int) {
    this[off] = v.toByte()
    this[off + 1] = (v shr 8).toByte()
    this[off + 2] = (v shr 16).toByte()
    this[off + 3] = (v shr 24).toByte()
}

private fun ByteArray.putLongLe(off: Int, v: Long) {
    this[off] = v.toByte()
    this[off + 1] = (v shr 8).toByte()
    this[off + 2] = (v shr 16).toByte()
    this[off + 3] = (v shr 24).toByte()
    this[off + 4] = (v shr 32).toByte()
    this[off + 5] = (v shr 40).toByte()
    this[off + 6] = (v shr 48).toByte()
    this[off + 7] = (v shr 56).toByte()
}

internal fun envInt(name: String, default: Int): Int =
    System.getenv(name)?.toIntOrNull()?.takeIf { it > 0 } ?: default
