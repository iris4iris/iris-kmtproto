package iris.kmtproto.client.storage

import iris.kmtproto.LongIntPair
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageEmpty
import iris.kmtproto.tl.gen.UserCtor

/**
 * Cache the client asks for: access_hash, User/Chat, and messages seen on the wire.
 * Message key is [LongIntPair]: bot-api chat id + message id (user 5 and chat 5 do not collide).
 */
interface Storage {
    /** 0 if unknown. */
    fun getAccessHash(id: Long): Long

    fun putAccessHash(id: Long, hash: Long)

    fun getUser(id: Long): UserCtor?

    fun getChat(id: Long): Chat?

    /** A min-stub does not replace a full user already stored. */
    fun rememberUser(user: UserCtor)

    /** A min channel does not replace a full channel already stored. */
    fun rememberChat(chat: Chat)

    fun getMessage(peerId: Long, messageId: Int): Message? =
        getMessage(LongIntPair(peerId, messageId))

    fun getMessage(key: LongIntPair): Message?

    /** [MessageEmpty] is ignored. Later copy of the same id replaces the previous one. */
    fun rememberMessage(message: Message)

    fun rememberMessages(messages: List<Message>) {
        for (i in messages.indices) rememberMessage(messages[i])
    }

    fun clearEntities()

    /**
     * Raw MTProto channel id → channel pts.
     * Not a bot-api id, and not part of the auth session: a new session of the same account
     * continues [updates.getChannelDifference](https://core.telegram.org/method/updates.getChannelDifference) from here.
     * 0 if this channel has no cursor yet. Not removed by [clearEntities].
     */
    fun getChannelPts(channelId: Long): Int

    fun putChannelPts(channelId: Long, pts: Int)

    fun removeChannelPts(channelId: Long)
}

