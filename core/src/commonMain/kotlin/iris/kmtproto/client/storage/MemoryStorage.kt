package iris.kmtproto.client.storage

import iris.kmtproto.LongIntPair
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.client.id
import iris.kmtproto.client.peer
import iris.kmtproto.tl.gen.*

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