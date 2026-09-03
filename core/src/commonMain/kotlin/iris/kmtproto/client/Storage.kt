package iris.kmtproto.client

/**
 * Durable (or not) cache the client asks for entities.
 * First slice: access_hash by user/channel id. Session and pts come later.
 */
interface Storage {
    /** 0 if unknown. */
    fun getAccessHash(id: Long): Long

    fun putAccessHash(id: Long, hash: Long)
}

/** Process-lifetime maps. Dies with the JVM. */
class MemoryStorage : Storage {
    private val hashes = HashMap<Long, Long>()

    @Synchronized
    override fun getAccessHash(id: Long): Long = hashes[id] ?: 0L

    @Synchronized
    override fun putAccessHash(id: Long, hash: Long) {
        if (hash != 0L) hashes[id] = hash
    }
}
