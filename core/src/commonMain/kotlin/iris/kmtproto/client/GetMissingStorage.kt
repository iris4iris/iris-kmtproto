package iris.kmtproto.client

import iris.kmtproto.LongIntPair
import iris.kmtproto.api.user.UserApi
import iris.kmtproto.tl.gen.Channel
import iris.kmtproto.tl.gen.ChannelForbidden
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.ChatCtor
import iris.kmtproto.tl.gen.ChatForbidden
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageEmpty
import iris.kmtproto.tl.gen.UserCtor
import kotlinx.coroutines.runBlocking

/**
 * Reads [actualStorage] and, on a miss, loads the entity via [userApi] and stores it back.
 * Message key is bot-api chat id + message id. [getUser] takes a user id.
 * [getChat] accepts a raw chat/channel id or a bot-api id (negative).
 */
class GetMissingStorage(
    private val userApi: UserApi,
    private val actualStorage: Storage,
) : Storage by actualStorage {

    override fun getMessage(peerId: Long, messageId: Int): Message? =
        getMessage(LongIntPair(peerId, messageId))

    override fun getMessage(key: LongIntPair): Message? {
        return actualStorage.getMessage(key) ?: runBlocking {
            userApi.messages.get(key.first, key.second).result?.firstOrNull { it !is MessageEmpty }
        }?.also { actualStorage.rememberMessage(it) }
    }

    override fun getUser(id: Long): UserCtor? {
        return actualStorage.getUser(id) ?: runBlocking {
            userApi.contacts.resolve(id).result?.users?.firstOrNull { it.id == id } as? UserCtor
        }?.also { actualStorage.rememberUser(it) }
    }

    override fun getChat(id: Long): Chat? {
        if (id == 0L) return null
        cachedChat(id)?.let { return it }
        val raw = rawChatId(id)
        return runBlocking {
            userApi.contacts.resolve(resolveChatId(id)).result?.chats?.firstOrNull { it.rawId() == raw }
        }?.also { actualStorage.rememberChat(it) }
    }

    private fun cachedChat(id: Long): Chat? {
        actualStorage.getChat(id)?.let { return it }
        val raw = rawChatId(id)
        if (raw == id) return null
        return actualStorage.getChat(raw)
    }

    /** Positive ids stay raw. Negative ids are bot-api and decode to the raw chat/channel id. */
    private fun rawChatId(id: Long): Long = when {
        id <= -1_000_000_000_000L -> -id - 1_000_000_000_000L
        id < 0L -> -id
        else -> id
    }

    /**
     * [iris.kmtproto.api.user.Contacts.resolve] wants a bot-api id.
     * A positive id is a channel when its access hash is already stored, otherwise a basic group.
     */
    private fun resolveChatId(id: Long): Long = when {
        id < 0L -> id
        actualStorage.getAccessHash(-1_000_000_000_000L - id) != 0L -> -1_000_000_000_000L - id
        else -> -id
    }

    private fun Chat.rawId(): Long? = when (this) {
        is Channel -> id
        is ChannelForbidden -> id
        is ChatCtor -> id
        is ChatForbidden -> id
        else -> null
    }
}
