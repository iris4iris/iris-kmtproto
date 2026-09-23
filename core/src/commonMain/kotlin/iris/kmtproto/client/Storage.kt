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
}

/** Process-lifetime maps. Dies with the JVM. */
class MemoryStorage : Storage {
    private val hashes = HashMap<Long, Long>()
    private val users = HashMap<Long, UserCtor>()
    private val chats = HashMap<Long, Chat>()
    private val knownMessages = HashMap<LongIntPair, Message>()

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
        val id = chat.botApiChatId()
        val min= when (chat) {
            is Channel -> chat.min
            is ChannelForbidden, is ChatCtor, is ChatForbidden -> false
            else -> return
        }
        val old = chats[id]
        if (min && old is Channel && !old.min) return
        chats[id] = chat
    }

    @Synchronized
    override fun getMessage(key: LongIntPair): Message? = knownMessages[key]

    @Synchronized
    override fun rememberMessage(message: Message) {
        if (message is MessageEmpty || message.id == 0) return
        val peer = message.peer ?: return
        knownMessages[LongIntPair(peer.botApiChatId(), message.id)] = message
    }

    @Synchronized
    override fun clearEntities() {
        users.clear()
        chats.clear()
        knownMessages.clear()
    }
}
