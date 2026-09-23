package iris.kmtproto.client

import iris.kmtproto.LongIntPair
import iris.kmtproto.api.user.UserApi
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageEmpty
import iris.kmtproto.tl.gen.UserCtor
import kotlinx.coroutines.runBlocking

/**
 * Reads [actualStorage] and, on a miss, loads the entity via [userApi] and stores it back.
 * Ids are passed through as given. `contacts.resolve` tells a user, a basic group and a
 * channel apart by the id itself (`> 0`, `< 0`, `<= -1_000_000_000_000`).
 */
class ReadThroughStorage(
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
        return actualStorage.getChat(id) ?: runBlocking {
            userApi.contacts.resolve(id).result?.chats?.firstOrNull()
        }?.also { actualStorage.rememberChat(it) }
    }
}
