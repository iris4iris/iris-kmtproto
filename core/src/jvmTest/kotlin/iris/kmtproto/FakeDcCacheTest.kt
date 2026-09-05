package iris.kmtproto

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class FakeDcCacheTest {
    @Test
    fun loadOrBuildReusesFile() {
        val first = FakeDcCache.loadOrBuild(1, 8)
        assertEquals(8, first.frames)
        assertEquals(1, first.messages)
        assertEquals(8 * (4 + first.frameBytes), first.blob.size)
        val second = FakeDcCache.loadOrBuild(1, 8)
        assertContentEquals(first.authKey.key, second.authKey.key)
        assertEquals(first.salt, second.salt)
        assertEquals(first.blob.size, second.blob.size)
        assertContentEquals(first.blob.copyOfRange(0, 32), second.blob.copyOfRange(0, 32))
    }
}
