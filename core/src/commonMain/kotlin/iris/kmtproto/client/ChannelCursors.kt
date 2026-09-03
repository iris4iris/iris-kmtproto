package iris.kmtproto.client

/** Per-channel pts + access_hash. Insertion-order LRU, not a message store. */
internal class ChannelCursors(private val cap: Int = 16_384) {
    class Cursor(var pts: Int, var accessHash: Long = 0L)

    private val map = LinkedHashMap<Long, Cursor>()

    @Synchronized
    fun get(id: Long): Cursor? {
        val cur = map.remove(id) ?: return null
        map[id] = cur
        return cur
    }

    @Synchronized
    fun put(id: Long, pts: Int, hash: Long? = null): Cursor {
        val cur = map.remove(id) ?: Cursor(pts)
        cur.pts = pts
        if (hash != null && hash != 0L) cur.accessHash = hash
        map[id] = cur
        while (map.size > cap) map.remove(map.keys.first())
        return cur
    }

    @Synchronized
    fun rememberHash(id: Long, hash: Long) {
        if (hash == 0L) return
        val cur = map.remove(id) ?: Cursor(0)
        cur.accessHash = hash
        map[id] = cur
        while (map.size > cap) map.remove(map.keys.first())
    }

    @Synchronized
    fun remove(id: Long) {
        map.remove(id)
    }

    @Synchronized
    fun clear() {
        map.clear()
    }
}
