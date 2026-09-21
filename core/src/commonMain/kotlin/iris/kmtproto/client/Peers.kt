package iris.kmtproto.client

import iris.kmtproto.tl.gen.InputChannel
import iris.kmtproto.tl.gen.InputChannelCtor
import iris.kmtproto.tl.gen.InputPeer
import iris.kmtproto.tl.gen.InputPeerChannel
import iris.kmtproto.tl.gen.InputPeerChat
import iris.kmtproto.tl.gen.InputPeerUser
import iris.kmtproto.tl.gen.InputUser
import iris.kmtproto.tl.gen.InputUserCtor
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.MessageEmpty
import iris.kmtproto.tl.gen.MessageService
import iris.kmtproto.tl.gen.Peer
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.User
import iris.kmtproto.tl.gen.UserCtor
import iris.kmtproto.tl.gen.UserEmpty

val Peer.id: Long
    get() = when (this) {
        is PeerUser -> userId
        is PeerChat -> chatId
        is PeerChannel -> channelId
    }

val User.id: Long
    get() = when (this) {
        is UserEmpty -> id
        is UserCtor -> id
    }

val User.accessHash: Long
    get() = (this as? UserCtor)?.accessHash ?: 0L

val Message.id: Int
    get() = when (this) {
        is MessageEmpty -> id
        is MessageCtor -> id
        is MessageService -> id
    }

fun InputPeer.asInputChannel(): InputChannel? = when (this) {
    is InputPeerChannel -> InputChannelCtor(channelId, accessHash)
    else -> null
}

fun InputPeer.asInputUser(): InputUser? = when (this) {
    is InputPeerUser -> InputUserCtor(userId, accessHash)
    else -> null
}

fun inputPeerFromBotApiId(chatId: Long, accessHash: Long = 0L): InputPeer = when {
    chatId > 0L -> InputPeerUser(chatId, accessHash)
    chatId <= -1_000_000_000_000L -> InputPeerChannel(-chatId - 1_000_000_000_000L, accessHash)
    chatId < 0L -> InputPeerChat(-chatId)
    else -> error("chat id 0")
}

fun Peer.botApiChatId(): Long = when (this) {
    is PeerUser -> userId
    is PeerChat -> -chatId
    is PeerChannel -> -1_000_000_000_000L - channelId
}

fun inputPeerFrom(peer: Peer, accessHash: Long = 0L): InputPeer = when (peer) {
    is PeerUser -> InputPeerUser(peer.userId, accessHash)
    is PeerChat -> InputPeerChat(peer.chatId)
    is PeerChannel -> InputPeerChannel(peer.channelId, accessHash)
}

fun Message.asText(): MessageCtor? = this as? MessageCtor

fun textFromUpdate(u: iris.kmtproto.tl.TlObject): MessageCtor? = when (u) {
    is UpdateNewMessage -> u.message.asText()
    is UpdateNewChannelMessage -> u.message.asText()
    else -> null
}
