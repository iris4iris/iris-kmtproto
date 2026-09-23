package iris.kmtproto.client.storage

import iris.kmtproto.LongIntPair
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.UserCtor

/**
 * Walks [storages] from front to back. A hit is copied into the faster layers
 * in front of it (`i - 1` down to `0`), not back into the layer that already had it.
 * Writes go to every layer. Put the fast cache first and [TelegramSource] last.
 */
class MultilayerStorage(
    private val storages: Array<out Storage>,
) : Storage {

    override fun getAccessHash(id: Long): Long = find(
        { it.getAccessHash(id).takeIf { hash -> hash != 0L } },
        { layer, hash -> layer.putAccessHash(id, hash) },
    ) ?: 0L

    override fun putAccessHash(id: Long, hash: Long) {
        for (i in storages.indices) storages[i].putAccessHash(id, hash)
    }

    override fun getUser(id: Long): UserCtor? = find(
        { it.getUser(id) },
        { layer, user -> layer.rememberUser(user) },
    )

    override fun getChat(id: Long): Chat? = find(
        { it.getChat(id) },
        { layer, chat -> layer.rememberChat(chat) },
    )

    override fun rememberUser(user: UserCtor) {
        for (i in storages.indices) storages[i].rememberUser(user)
    }

    override fun rememberChat(chat: Chat) {
        for (i in storages.indices) storages[i].rememberChat(chat)
    }

    override fun getMessage(key: LongIntPair): Message? = find(
        { it.getMessage(key) },
        { layer, message -> layer.rememberMessage(message) },
    )

    override fun rememberMessage(message: Message) {
        for (i in storages.indices) storages[i].rememberMessage(message)
    }

    override fun clearEntities() {
        for (i in storages.indices) storages[i].clearEntities()
    }

    override fun getChannelPts(channelId: Long): Int = find(
        { it.getChannelPts(channelId).takeIf { pts -> pts != 0 } },
        { layer, pts -> layer.putChannelPts(channelId, pts) },
    ) ?: 0

    override fun putChannelPts(channelId: Long, pts: Int) {
        for (i in storages.indices) storages[i].putChannelPts(channelId, pts)
    }

    override fun removeChannelPts(channelId: Long) {
        for (i in storages.indices) storages[i].removeChannelPts(channelId)
    }

    private inline fun <T> find(read: (Storage) -> T?, write: (Storage, T) -> Unit): T? {
        for (i in storages.indices) {
            val found = read(storages[i]) ?: continue
            if (i > 0)
                for (j in i-1 downTo 0) write(storages[j], found)
            return found
        }
        return null
    }
}
