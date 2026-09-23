package iris.kmtproto.client

import iris.kmtproto.tl.gen.*

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

val Message.peer: Peer?
    get() = when (this) {
        is MessageEmpty -> peerId
        is MessageCtor -> peerId
        is MessageService -> peerId
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
    chatId <= -1_000_000_000_000L -> InputPeerChannel(-(chatId + 1_000_000_000_000L), accessHash)
    chatId < 0L -> InputPeerChat(-chatId)
    else -> error("chat id 0")
}

fun Peer.botApiChatId(): Long = when (this) {
    is PeerUser -> userId
    is PeerChat -> -chatId
    is PeerChannel -> -(channelId + 1_000_000_000_000L)
}

fun Chat.botApiChatId(): Long = when(this) {
    is Channel -> -(id + 1_000_000_000_000L)
    is ChannelForbidden -> -(id + 1_000_000_000_000L)
    is ChatCtor -> -id
    is ChatForbidden -> -id
    else -> 0
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
