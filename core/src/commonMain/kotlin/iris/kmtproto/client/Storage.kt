package iris.kmtproto.client

import iris.kmtproto.LongIntPair
import iris.kmtproto.tl.gen.Channel
import iris.kmtproto.tl.gen.ChannelForbidden
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.ChatCtor
import iris.kmtproto.tl.gen.ChatForbidden
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

/** Process-lifetime maps. Dies with the JVM. Each map drops the oldest entry past its capacity. */
class MemoryStorage(
    private val hashCapacity: Int = 16_384,
    private val userCapacity: Int = 16_384,
    private val chatCapacity: Int = 16_384,
    private val messageCapacity: Int = 16_384,
    private val channelPtsCapacity: Int = 16_384,
) : Storage {
    private val hashes = LinkedHashMap<Long, Long>()
    private val users = LinkedHashMap<Long, UserCtor>()
    private val chats = LinkedHashMap<Long, Chat>()
    private val knownMessages = LinkedHashMap<LongIntPair, Message>()
    private val channelPts = LinkedHashMap<Long, Int>()

    @Synchronized
    override fun getAccessHash(id: Long): Long = touch(hashes, id) ?: 0L

    @Synchronized
    override fun putAccessHash(id: Long, hash: Long) {
        if (hash == 0L) return
        putCapped(hashes, id, hash, hashCapacity)
    }

    @Synchronized
    override fun getUser(id: Long): UserCtor? = touch(users, id)

    @Synchronized
    override fun getChat(id: Long): Chat? = touch(chats, id)

    @Synchronized
    override fun rememberUser(user: UserCtor) {
        val old = users[user.id]
        if (user.min && old != null && !old.min) return
        putCapped(users, user.id, user, userCapacity)
    }

    @Synchronized
    override fun rememberChat(chat: Chat) {
        val id = chat.botApiChatId()
        val min= when (chat) {
            is Channel -> chat.min
            is ChannelForbidden, is ChatCtor, is ChatForbidden -> false
            else -> return
        }
        val old = chats[id]
        if (min && old is Channel && !old.min) return
        putCapped(chats, id, chat, chatCapacity)
    }

    @Synchronized
    override fun getMessage(key: LongIntPair): Message? = touch(knownMessages, key)

    @Synchronized
    override fun rememberMessage(message: Message) {
        if (message is MessageEmpty || message.id == 0) return
        val peer = message.peer ?: return
        putCapped(knownMessages, LongIntPair(peer.botApiChatId(), message.id), message, messageCapacity)
    }

    @Synchronized
    override fun clearEntities() {
        users.clear()
        chats.clear()
        knownMessages.clear()
    }

    @Synchronized
    override fun getChannelPts(channelId: Long): Int = touch(channelPts, channelId) ?: 0

    @Synchronized
    override fun putChannelPts(channelId: Long, pts: Int) {
        channelPts.remove(channelId)
        if (pts == 0) return
        putCapped(channelPts, channelId, pts, channelPtsCapacity)
    }

    @Synchronized
    override fun removeChannelPts(channelId: Long) {
        channelPts.remove(channelId)
    }

    private fun <K, V> touch(map: LinkedHashMap<K, V>, key: K): V? {
        val value = map.remove(key) ?: return null
        map[key] = value
        return value
    }

    private fun <K, V> putCapped(map: LinkedHashMap<K, V>, key: K, value: V, capacity: Int) {
        map.remove(key)
        map[key] = value
        while (map.size > capacity) {
            val eldest = map.keys.firstOrNull() ?: break
            map.remove(eldest)
        }
    }
}
