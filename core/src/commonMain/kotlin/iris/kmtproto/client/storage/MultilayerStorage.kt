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
    constructor(storage: Storage): this(arrayOf(storage))
    constructor(storage1: Storage, storage2: Storage): this(arrayOf(storage1, storage2))
    constructor(storage1: Storage, storage2: Storage, storage3: Storage): this(arrayOf(storage1, storage2, storage3))
    constructor(storage1: Storage, storage2: Storage, storage3: Storage, vararg otherStorages: Storage) :
        this(arrayOf(storage1, storage2, storage3) + otherStorages)

    override fun getAccessHash(id: Long): Long = find(
        { it.getAccessHash(id).takeIf { hash -> hash != 0L } },
        { layer, hash -> layer.putAccessHash(id, hash) },
    ) ?: 0L

    override fun putAccessHash(id: Long, hash: Long) {
        for (storage in storages) storage.putAccessHash(id, hash)
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
        for (storage in storages) storage.rememberUser(user)
    }

    override fun rememberChat(chat: Chat) {
        for (storage in storages) storage.rememberChat(chat)
    }

    override fun getMessage(key: LongIntPair): Message? = find(
        { it.getMessage(key) },
        { layer, message -> layer.rememberMessage(message) },
    )

    override fun getReplyMessage(peerId: Long, messageId: Int): Message? = find(
        { it.getReplyMessage(peerId, messageId) },
        { layer, message -> layer.rememberMessage(message) },
    )

    override fun rememberMessage(message: Message) {
        for (storage in storages) storage.rememberMessage(message)
    }

    override fun clearEntities() {
        for (storage in storages) storage.clearEntities()
    }

    override fun getChannelPts(channelId: Long): Int = find(
        { it.getChannelPts(channelId).takeIf { pts -> pts != 0 } },
        { layer, pts -> layer.putChannelPts(channelId, pts) },
    ) ?: 0

    override fun putChannelPts(channelId: Long, pts: Int) {
        for (storage in storages) storage.putChannelPts(channelId, pts)
    }

    override fun removeChannelPts(channelId: Long) {
        for (storage in storages) storage.removeChannelPts(channelId)
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
