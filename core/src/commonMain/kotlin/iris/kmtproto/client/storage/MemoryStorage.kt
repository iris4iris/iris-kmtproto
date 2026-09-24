package iris.kmtproto.client.storage

import iris.kmtproto.LongIntPair
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.client.id
import iris.kmtproto.client.peer
import iris.kmtproto.tl.gen.*

/**
 * Process-lifetime maps. Dies with the JVM.
 * A positive capacity drops the oldest entry past that size.
 * [UNLIMITED] keeps every entry. [DISABLED] neither stores nor returns.
 */
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
    override fun getAccessHash(id: Long): Long = touch(hashes, id, hashCapacity) ?: 0L

    @Synchronized
    override fun putAccessHash(id: Long, hash: Long) {
        if (hash == 0L) return
        putCapped(hashes, id, hash, hashCapacity)
    }

    @Synchronized
    override fun getUser(id: Long): UserCtor? = touch(users, id, userCapacity)

    @Synchronized
    override fun getChat(id: Long): Chat? = touch(chats, id, chatCapacity)

    @Synchronized
    override fun rememberUser(user: UserCtor) {
        if (userCapacity == DISABLED) return
        val old = users[user.id]
        if (user.min && old != null && !old.min) return
        putCapped(users, user.id, user, userCapacity)
    }

    @Synchronized
    override fun rememberChat(chat: Chat) {
        if (chatCapacity == DISABLED) return
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
    override fun getMessage(key: LongIntPair): Message? = touch(knownMessages, key, messageCapacity)

    @Synchronized
    override fun rememberMessage(message: Message) {
        if (messageCapacity == DISABLED) return
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
    override fun getChannelPts(channelId: Long): Int = touch(channelPts, channelId, channelPtsCapacity) ?: 0

    @Synchronized
    override fun putChannelPts(channelId: Long, pts: Int) {
        if (channelPtsCapacity == DISABLED) return
        channelPts.remove(channelId)
        if (pts == 0) return
        putCapped(channelPts, channelId, pts, channelPtsCapacity)
    }

    @Synchronized
    override fun removeChannelPts(channelId: Long) {
        channelPts.remove(channelId)
    }

    private fun <K, V> touch(map: LinkedHashMap<K, V>, key: K, capacity: Int): V? {
        if (capacity == DISABLED) return null
        val value = map.remove(key) ?: return null
        map[key] = value
        return value
    }

    private fun <K, V> putCapped(map: LinkedHashMap<K, V>, key: K, value: V, capacity: Int) {
        if (capacity == DISABLED) return
        map.remove(key)
        map[key] = value
        if (capacity == UNLIMITED) return
        while (map.size > capacity) {
            val eldest = map.keys.firstOrNull() ?: break
            map.remove(eldest)
        }
    }

    companion object {
        /** Never evict. */
        const val UNLIMITED = 0

        /** Neither store nor return. */
        const val DISABLED = -1
    }
}
