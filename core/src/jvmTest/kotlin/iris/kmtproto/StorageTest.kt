package iris.kmtproto

import iris.kmtproto.api.user.UserApi
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.storage.MemoryStorage
import iris.kmtproto.client.storage.MultilayerStorage
import iris.kmtproto.client.storage.Storage
import iris.kmtproto.client.storage.TelegramSource
import iris.kmtproto.client.storage.SimpleFileStorage
import iris.kmtproto.tl.gen.Channel
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.ChatCtor
import iris.kmtproto.tl.gen.ChatPhotoEmpty
import iris.kmtproto.tl.gen.InputPeerChannel
import iris.kmtproto.tl.gen.InputPeerChat
import iris.kmtproto.tl.gen.InputPeerUser
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.UserCtor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import java.io.File

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

    @Test
    fun memoryKeepsFullUserOverMinStub() {
        val s = MemoryStorage()
        s.rememberUser(UserCtor(id = 1, firstName = "Ivan", min = false))
        s.rememberUser(UserCtor(id = 1, firstName = "stub", min = true))
        assertEquals("Ivan", s.getUser(1)?.firstName)
        s.clearEntities()
        assertNull(s.getUser(1))
        val client = TelegramClient(apiId = 1, apiHash = "x", storage = s)
        s.rememberUser(UserCtor(id = 7, firstName = "A"))
        assertEquals("A", client.knownUser(7)?.firstName)
        assertNull(client.knownChat(7))
    }

    @Test
    fun messagesKeyedByBotApiChatId() {
        val s = MemoryStorage()
        s.rememberMessage(MessageCtor(id = 1, peerId = PeerUser(5), date = 1, message = "u"))
        s.rememberMessage(MessageCtor(id = 1, peerId = PeerChat(5), date = 1, message = "c"))
        assertEquals("u", (s.getMessage(5, 1) as MessageCtor).message)
        assertEquals("c", (s.getMessage(-5, 1) as MessageCtor).message)
        s.clearEntities()
        assertNull(s.getMessage(5, 1))
    }

    @Test
    fun multilayerFillsFasterLayersAndSkipsTheRest() {
        val memory = MemoryStorage()
        val disk = MemoryStorage()
        disk.rememberUser(UserCtor(id = 5, firstName = "Ivan"))
        disk.rememberChat(ChatCtor(id = 50, title = "Room", photo = ChatPhotoEmpty, participantsCount = 2, date = 1, version = 1))
        disk.rememberMessage(MessageCtor(id = 1, peerId = PeerUser(5), date = 1, message = "u"))
        disk.putAccessHash(5, 11)
        disk.putChannelPts(9, 40)
        val storage = MultilayerStorage(arrayOf(memory, disk, object : Storage by MemoryStorage() {
            override fun getUser(id: Long): UserCtor? = error("telegram")
            override fun getChat(id: Long): Chat? = error("telegram")
            override fun getMessage(key: LongIntPair): Message? = error("telegram")
            override fun getAccessHash(id: Long): Long = error("telegram")
            override fun getChannelPts(channelId: Long): Int = error("telegram")
        }))
        assertEquals("Ivan", storage.getUser(5)?.firstName)
        assertEquals("Room", (storage.getChat(-50) as ChatCtor).title)
        assertEquals("u", (storage.getMessage(5, 1) as MessageCtor).message)
        assertEquals(11L, storage.getAccessHash(5))
        assertEquals(40, storage.getChannelPts(9))
        assertEquals("Ivan", memory.getUser(5)?.firstName)
        assertEquals("Room", (memory.getChat(-50) as ChatCtor).title)
        assertEquals("u", (memory.getMessage(5, 1) as MessageCtor).message)
        assertEquals(11L, memory.getAccessHash(5))
        assertEquals(40, memory.getChannelPts(9))

        val client = TelegramClient(apiId = 1, apiHash = "x", storage = storage)
        assertEquals("Ivan", client.knownUser(5)?.firstName)
        val source = TelegramSource(UserApi(client))
        source.rememberUser(UserCtor(id = 1, firstName = "A"))
        source.putAccessHash(1, 2)
        source.putChannelPts(1, 3)
        assertNull(source.getUser(0))
        assertNull(source.getChat(0))
        assertEquals(0L, source.getAccessHash(1))
        assertEquals(0, source.getChannelPts(1))
    }

    @Test
    fun channelAndBasicChatDoNotShareKey() {
        val s = MemoryStorage()
        s.rememberChat(ChatCtor(id = 5, title = "group", photo = ChatPhotoEmpty, participantsCount = 2, date = 1, version = 1))
        s.rememberChat(Channel(id = 5, title = "chan", photo = ChatPhotoEmpty, date = 1))
        assertEquals("group", (s.getChat(-5) as ChatCtor).title)
        assertEquals("chan", (s.getChat(-(5L + 1_000_000_000_000L)) as Channel).title)
    }

    @Test
    fun channelPtsOutliveEntitiesAndProcess() {
        val s = MemoryStorage()
        assertEquals(0, s.getChannelPts(5))
        s.putChannelPts(5, 40)
        s.putChannelPts(5, 0)
        assertEquals(0, s.getChannelPts(5))
        s.putChannelPts(5, 40)
        s.clearEntities()
        assertEquals(40, s.getChannelPts(5))
        s.removeChannelPts(5)
        assertEquals(0, s.getChannelPts(5))

        val file = File.createTempFile("kmtproto-pts", ".txt")
        file.deleteOnExit()
        val disk = SimpleFileStorage(file)
        disk.putAccessHash(1, 2)
        disk.putChannelPts(9, 100)
        val again = SimpleFileStorage(file)
        assertEquals(2L, again.getAccessHash(1))
        assertEquals(100, again.getChannelPts(9))
        again.clearEntities()
        assertEquals(100, again.getChannelPts(9))
    }

    @Test
    fun eachMapDropsTheOldestPastItsCapacity() {
        val s = MemoryStorage(
            hashCapacity = 2,
            userCapacity = 1,
            chatCapacity = 1,
            messageCapacity = 1,
            channelPtsCapacity = 1,
        )
        s.putAccessHash(1, 10)
        s.putAccessHash(2, 20)
        assertEquals(10L, s.getAccessHash(1))
        s.putAccessHash(3, 30)
        assertEquals(10L, s.getAccessHash(1))
        assertEquals(0L, s.getAccessHash(2))
        assertEquals(30L, s.getAccessHash(3))

        s.rememberUser(UserCtor(id = 1, firstName = "A"))
        s.rememberUser(UserCtor(id = 2, firstName = "B"))
        assertNull(s.getUser(1))
        assertEquals("B", s.getUser(2)?.firstName)

        s.rememberChat(ChatCtor(id = 1, title = "one", photo = ChatPhotoEmpty, participantsCount = 1, date = 1, version = 1))
        s.rememberChat(ChatCtor(id = 2, title = "two", photo = ChatPhotoEmpty, participantsCount = 1, date = 1, version = 1))
        assertNull(s.getChat(-1))
        assertEquals("two", (s.getChat(-2) as ChatCtor).title)

        s.rememberMessage(MessageCtor(id = 1, peerId = PeerUser(1), date = 1, message = "a"))
        s.rememberMessage(MessageCtor(id = 2, peerId = PeerUser(1), date = 1, message = "b"))
        assertNull(s.getMessage(1, 1))
        assertEquals("b", (s.getMessage(1, 2) as MessageCtor).message)

        s.putChannelPts(1, 10)
        s.putChannelPts(2, 20)
        assertEquals(0, s.getChannelPts(1))
        assertEquals(20, s.getChannelPts(2))
    }

    @Test
    fun disabledCapacityStoresNothingAndUnlimitedKeepsAll() {
        val off = MemoryStorage(
            hashCapacity = MemoryStorage.DISABLED,
            userCapacity = MemoryStorage.DISABLED,
            chatCapacity = MemoryStorage.DISABLED,
            messageCapacity = MemoryStorage.DISABLED,
            channelPtsCapacity = MemoryStorage.DISABLED,
        )
        off.putAccessHash(1, 10)
        off.rememberUser(UserCtor(id = 1, firstName = "A"))
        off.rememberChat(ChatCtor(id = 1, title = "one", photo = ChatPhotoEmpty, participantsCount = 1, date = 1, version = 1))
        off.rememberMessage(MessageCtor(id = 1, peerId = PeerUser(1), date = 1, message = "a"))
        off.putChannelPts(1, 40)
        assertEquals(0L, off.getAccessHash(1))
        assertNull(off.getUser(1))
        assertNull(off.getChat(-1))
        assertNull(off.getMessage(1, 1))
        assertEquals(0, off.getChannelPts(1))

        val all = MemoryStorage(
            hashCapacity = MemoryStorage.UNLIMITED,
            userCapacity = MemoryStorage.UNLIMITED,
            chatCapacity = MemoryStorage.UNLIMITED,
            messageCapacity = MemoryStorage.UNLIMITED,
            channelPtsCapacity = MemoryStorage.UNLIMITED,
        )
        all.putAccessHash(1, 10)
        all.putAccessHash(2, 20)
        all.putAccessHash(3, 30)
        all.rememberUser(UserCtor(id = 1, firstName = "A"))
        all.rememberUser(UserCtor(id = 2, firstName = "B"))
        all.rememberChat(ChatCtor(id = 1, title = "one", photo = ChatPhotoEmpty, participantsCount = 1, date = 1, version = 1))
        all.rememberChat(ChatCtor(id = 2, title = "two", photo = ChatPhotoEmpty, participantsCount = 1, date = 1, version = 1))
        all.rememberMessage(MessageCtor(id = 1, peerId = PeerUser(1), date = 1, message = "a"))
        all.rememberMessage(MessageCtor(id = 2, peerId = PeerUser(1), date = 1, message = "b"))
        all.putChannelPts(1, 10)
        all.putChannelPts(2, 20)
        assertEquals(10L, all.getAccessHash(1))
        assertEquals(20L, all.getAccessHash(2))
        assertEquals(30L, all.getAccessHash(3))
        assertEquals("A", all.getUser(1)?.firstName)
        assertEquals("B", all.getUser(2)?.firstName)
        assertEquals("one", (all.getChat(-1) as ChatCtor).title)
        assertEquals("two", (all.getChat(-2) as ChatCtor).title)
        assertEquals("a", (all.getMessage(1, 1) as MessageCtor).message)
        assertEquals("b", (all.getMessage(1, 2) as MessageCtor).message)
        assertEquals(10, all.getChannelPts(1))
        assertEquals(20, all.getChannelPts(2))
    }
}
