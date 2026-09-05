package iris.kmtproto

import iris.kmtproto.crypto.AesIge
import iris.kmtproto.crypto.AuthKey
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

internal fun envInt(name: String, default: Int): Int =
    System.getenv(name)?.toIntOrNull()?.takeIf { it > 0 } ?: default
