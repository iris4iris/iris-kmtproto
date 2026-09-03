package iris.kmtproto

import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.TelegramClient
import kotlin.test.Test
import kotlin.test.assertEquals

class StorageTest {
    @Test
    fun memoryStoresNonZeroHashes() {
        val s = MemoryStorage()
        assertEquals(0L, s.getAccessHash(1))
        s.putAccessHash(1, 0)
        assertEquals(0L, s.getAccessHash(1))
        s.putAccessHash(1, 99)
        assertEquals(99L, s.getAccessHash(1))
        s.putAccessHash(2, 7)
        assertEquals(99L, s.getAccessHash(1))
        assertEquals(7L, s.getAccessHash(2))
    }

    @Test
    fun clientReadsInjectedStorage() {
        val s = MemoryStorage()
        s.putAccessHash(661079614L, 12345L)
        val client = TelegramClient(apiId = 1, apiHash = "x", storage = s)
        assertEquals(12345L, client.accessHash(661079614L))
        assertEquals(0L, client.accessHash(1L))
    }
}
