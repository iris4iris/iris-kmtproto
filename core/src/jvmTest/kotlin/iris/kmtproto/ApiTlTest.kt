package iris.kmtproto

import iris.kmtproto.api.user.stripInviteHash
import iris.kmtproto.api.user.stripNftSlug
import iris.kmtproto.api.user.stripUsername
import iris.kmtproto.client.asInputChannel
import iris.kmtproto.client.asInputUser
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.client.fileMigrateDc
import iris.kmtproto.client.id
import iris.kmtproto.client.inputPeerFrom
import iris.kmtproto.client.inputPeerFromBotApiId
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tl.InitConnection
import iris.kmtproto.tl.InvokeWithLayer
import iris.kmtproto.tl.InvokeWithoutUpdates
import iris.kmtproto.tl.TlIds
import iris.kmtproto.tl.TlReader
import iris.kmtproto.tl.gen.HelpGetNearestDc
import iris.kmtproto.tl.gen.InputChannelCtor
import iris.kmtproto.tl.gen.InputPeerChannel
import iris.kmtproto.tl.gen.InputPeerChat
import iris.kmtproto.tl.gen.InputPeerUser
import iris.kmtproto.tl.gen.InputUserCtor
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.MessageEmpty
import iris.kmtproto.tl.gen.NearestDc
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.toBytes
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ApiTlTest {
    @Test
    fun vendoredApiTlLayerMatchesDefault() {
        val file = findVendoredApiTl() ?: error("schema/api.tl not found")
        val text = file.readText()
        val layer = Regex("""// LAYER (\d+)""").findAll(text).lastOrNull()?.groupValues?.get(1)?.toInt()
            ?: error("no // LAYER in ${file.absolutePath}")
        assertEquals(API_LAYER, layer, "${file.absolutePath} LAYER=$layer, API_LAYER=$API_LAYER")
    }

    @Test
    fun invokeWithLayerWrapsNearestDc() {
        val wrapped = InvokeWithLayer(
            layer = API_LAYER,
            query = InitConnection(
                apiId = 1,
                deviceModel = "Iris kMTProto",
                systemVersion = "JVM",
                appVersion = "0.1.0",
                systemLangCode = "en",
                langPack = "",
                langCode = "en",
                query = HelpGetNearestDc,
            ),
        )
        val bytes = wrapped.toBytes()
        val r = TlReader(bytes)
        assertEquals(TlIds.INVOKE_WITH_LAYER, r.readInt())
        assertEquals(API_LAYER, r.readInt())
        assertEquals(TlIds.INIT_CONNECTION, r.readInt())
        assertEquals(0, r.readInt())
        assertEquals(1, r.readInt())
        assertEquals("Iris kMTProto", r.readString())
        assertEquals("JVM", r.readString())
        assertEquals("0.1.0", r.readString())
        assertEquals("en", r.readString())
        assertEquals("", r.readString())
        assertEquals("en", r.readString())
        assertEquals(TlIds.HELP_GET_NEAREST_DC, r.readInt())
        assertEquals(0, r.remaining)
    }

    @Test
    fun invokeWithoutUpdatesWrapsQuery() {
        val wrapped = InvokeWithoutUpdates(query = HelpGetNearestDc)
        val bytes = wrapped.toBytes()
        val r = TlReader(bytes)
        assertEquals(TlIds.INVOKE_WITHOUT_UPDATES, r.readInt())
        assertEquals(TlIds.HELP_GET_NEAREST_DC, r.readInt())
        assertEquals(0, r.remaining)
    }

    @Test
    fun nearestDcRoundtrip() {
        val dc = NearestDc("NL", 2, 2)
        val back = TlReader(dc.toBytes()).readObject() as NearestDc
        assertEquals("NL", back.country)
        assertEquals(2, back.thisDc)
        assertEquals(2, back.nearestDc)
    }

    @Test
    fun botApiChatIdMapping() {
        assertTrue(inputPeerFromBotApiId(1001L) is InputPeerUser)
        assertTrue(inputPeerFromBotApiId(-42L) is InputPeerChat)
        val ch = inputPeerFromBotApiId(-1001234567890L) as InputPeerChannel
        assertEquals(1234567890L, ch.channelId)
        val mapped = inputPeerFrom(PeerUser(7L), 11L) as InputPeerUser
        assertEquals(7L, mapped.userId)
        assertEquals(11L, mapped.accessHash)
        assertEquals(1001L, PeerUser(1001L).botApiChatId())
        assertEquals(-42L, PeerChat(42L).botApiChatId())
        assertEquals(-1001234567890L, PeerChannel(1234567890L).botApiChatId())
    }

    @Test
    fun messageIdAndAsInputChannel() {
        val empty: Message = MessageEmpty(42)
        assertEquals(42, empty.id)
        val ctor: Message = MessageCtor(id = 7, peerId = PeerUser(1L), date = 0, message = "hi")
        assertEquals(7, ctor.id)
        val ch = InputPeerChannel(9L, 11L).asInputChannel() as InputChannelCtor
        assertEquals(9L, ch.channelId)
        assertEquals(11L, ch.accessHash)
        assertNull(InputPeerUser(1L, 2L).asInputChannel())
        assertNull(InputPeerChat(3L).asInputChannel())
        val u = InputPeerUser(1L, 2L).asInputUser() as InputUserCtor
        assertEquals(1L, u.userId)
        assertEquals(2L, u.accessHash)
        assertNull(InputPeerChat(3L).asInputUser())
        assertNull(InputPeerChannel(9L, 11L).asInputUser())
    }

    @Test
    fun stripUsernameAcceptsLinks() {
        assertEquals("durov", stripUsername("@durov"))
        assertEquals("durov", stripUsername("https://t.me/durov"))
        assertEquals("durov", stripUsername("t.me/durov/123"))
        assertEquals("durov", stripUsername("https://telegram.me/durov?start=1"))
    }

    @Test
    fun stripInviteHashAcceptsLinks() {
        assertEquals("AbCdEf", stripInviteHash("https://t.me/+AbCdEf"))
        assertEquals("AbCdEf", stripInviteHash("t.me/joinchat/AbCdEf"))
        assertEquals("AbCdEf", stripInviteHash("+AbCdEf"))
        assertEquals("AbCdEf", stripInviteHash("tg://join?invite=AbCdEf"))
        assertEquals("", stripInviteHash("@durov"))
        assertEquals("", stripInviteHash("+79991234567"))
    }

    @Test
    fun fileMigrateDcParses() {
        assertEquals(4, fileMigrateDc("FILE_MIGRATE_4"))
        assertEquals(0, fileMigrateDc("PHONE_MIGRATE_2"))
        assertEquals(0, fileMigrateDc("FLOOD_WAIT_3"))
    }

    @Test
    fun stripNftSlugAcceptsLinks() {
        assertEquals("PlushPepe-42", stripNftSlug("PlushPepe-42"))
        assertEquals("PlushPepe-42", stripNftSlug("https://t.me/nft/PlushPepe-42"))
        assertEquals("PlushPepe-42", stripNftSlug("t.me/nft/PlushPepe-42?start=1"))
    }
}

private fun findVendoredApiTl(): File? {
    val names = listOf(
        "schema/api.tl",
        "tl/schema/api.tl",
        "kmtproto/schema/api.tl",
        "kmtproto/tl/schema/api.tl",
    )
    val roots = listOf(File("."), File(".."), File(System.getProperty("user.dir")))
    for (root in roots) {
        for (name in names) {
            val f = File(root, name)
            if (f.isFile) return f.canonicalFile
        }
    }
    return null
}
