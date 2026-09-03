package iris.kmtproto.client

/** Per-channel pts. Insertion-order LRU. Hashes live in [Storage]. */
internal class ChannelCursors(private val cap: Int = 16_384) {
    class Cursor(var pts: Int)

    private val map = LinkedHashMap<Long, Cursor>()

    @Synchronized
    fun get(id: Long): Cursor? {
        val cur = map.remove(id) ?: return null
        map[id] = cur
        return cur
    }

    @Synchronized
    fun put(id: Long, pts: Int): Cursor {
        val cur = map.remove(id) ?: Cursor(pts)
        cur.pts = pts
        map[id] = cur
        while (map.size > cap) map.remove(map.keys.first())
        return cur
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
