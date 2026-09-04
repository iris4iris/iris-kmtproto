package iris.kmtproto

import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.InputPeerChannel
import iris.kmtproto.tl.gen.InputPeerChat
import iris.kmtproto.tl.gen.InputPeerUser
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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

    @Test
    fun inputPeerFromIdUsesStoredHash() {
        val s = MemoryStorage()
        s.putAccessHash(1001L, 11L)
        s.putAccessHash(-1001234567890L, 99L)
        val client = TelegramClient(apiId = 1, apiHash = "x", storage = s)
        val user = client.inputPeerFromId(1001L) as InputPeerUser
        assertEquals(1001L, user.userId)
        assertEquals(11L, user.accessHash)
        assertTrue(client.inputPeerFromId(-42L) is InputPeerChat)
        val ch = client.inputPeerFromId(-1001234567890L) as InputPeerChannel
        assertEquals(1234567890L, ch.channelId)
        assertEquals(99L, ch.accessHash)
    }
}
