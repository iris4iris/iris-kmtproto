package iris.kmtproto.client

import iris.kmtproto.tl.gen.Channel
import iris.kmtproto.tl.gen.ChannelForbidden
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.ChatCtor
import iris.kmtproto.tl.gen.ChatForbidden
import iris.kmtproto.tl.gen.UserCtor

/**
 * Cache the client asks for: access_hash plus User/Chat objects seen on the wire.
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

    fun clearEntities()
}

/** Process-lifetime maps. Dies with the JVM. */
class MemoryStorage : Storage {
    private val hashes = HashMap<Long, Long>()
    private val users = HashMap<Long, UserCtor>()
    private val chats = HashMap<Long, Chat>()

    @Synchronized
    override fun getAccessHash(id: Long): Long = hashes[id] ?: 0L

    @Synchronized
    override fun putAccessHash(id: Long, hash: Long) {
        if (hash != 0L) hashes[id] = hash
    }

    @Synchronized
    override fun getUser(id: Long): UserCtor? = users[id]

    @Synchronized
    override fun getChat(id: Long): Chat? = chats[id]

    @Synchronized
    override fun rememberUser(user: UserCtor) {
        val old = users[user.id]
        if (user.min && old != null && !old.min) return
        users[user.id] = user
    }

    @Synchronized
    override fun rememberChat(chat: Chat) {
        val (id, min) = when (chat) {
            is Channel -> chat.id to chat.min
            is ChannelForbidden -> chat.id to false
            is ChatCtor -> chat.id to false
            is ChatForbidden -> chat.id to false
            else -> return
        }
        val old = chats[id]
        if (min && old is Channel && !old.min) return
        chats[id] = chat
    }

    @Synchronized
    override fun clearEntities() {
        users.clear()
        chats.clear()
    }
}
