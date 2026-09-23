package iris.kmtproto.client.storage

import iris.kmtproto.LongIntPair
import iris.kmtproto.api.user.UserApi
import iris.kmtproto.client.id
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageEmpty
import iris.kmtproto.tl.gen.UserCtor
import kotlinx.coroutines.runBlocking

/**
 * Loads a missing user, chat, or message from Telegram and stores nothing.
 * Ids are passed through as given. `contacts.resolve` tells a user, a basic group and a
 * channel apart by the id itself (`> 0`, `< 0`, `<= -1_000_000_000_000`).
 * Access hashes and channel cursors are not asked here: a hash arrives with the entity,
 * and a cursor is local.
 *
 * Put this last in [MultilayerStorage], and assign that stack to [iris.kmtproto.client.TelegramClient.storage].
 * `resolve` / `messages.get` also write into the client storage on their own.
 */
class TelegramSource(private val userApi: UserApi) : Storage {
    override fun getAccessHash(id: Long): Long = 0L

    override fun putAccessHash(id: Long, hash: Long) = Unit

    override fun getUser(id: Long): UserCtor? {
        if (id <= 0L) return null
        return runBlocking {
            userApi.contacts.resolve(id).result?.users?.firstOrNull { it.id == id } as? UserCtor
        }
    }

    override fun getChat(id: Long): Chat? {
        if (id >= 0L) return null
        return runBlocking {
            userApi.contacts.resolve(id).result?.chats?.firstOrNull()
        }
    }

    override fun rememberUser(user: UserCtor) = Unit

    override fun rememberChat(chat: Chat) = Unit

    override fun getMessage(key: LongIntPair): Message? = runBlocking {
        userApi.messages.get(key.first, key.second).result?.firstOrNull { it !is MessageEmpty }
    }

    override fun rememberMessage(message: Message) = Unit

    override fun clearEntities() = Unit

    override fun getChannelPts(channelId: Long): Int = 0

    override fun putChannelPts(channelId: Long, pts: Int) = Unit

    override fun removeChannelPts(channelId: Long) = Unit
}
