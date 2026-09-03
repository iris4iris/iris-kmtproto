package iris.kmtproto

import iris.kmtproto.client.inputPeerFrom
import iris.kmtproto.client.inputPeerFromBotApiId
import iris.kmtproto.example.replyPeer
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tl.InitConnection
import iris.kmtproto.tl.InvokeWithLayer
import iris.kmtproto.tl.TlIds
import iris.kmtproto.tl.TlReader
import iris.kmtproto.tl.gen.HelpGetNearestDc
import iris.kmtproto.tl.gen.InputPeerChannel
import iris.kmtproto.tl.gen.InputPeerChat
import iris.kmtproto.tl.gen.InputPeerUser
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.NearestDc
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.toBytes
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
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
    }

    @Test
    fun echoRepliesToUserNotSelf() {
        val incoming = MessageCtor(
            id = 1,
            fromId = PeerUser(99L),
            peerId = PeerUser(99L),
            date = 1,
            message = "hi",
        )
        val peer = replyPeer(incoming, me = 1L)
        assertEquals(99L, (peer as PeerUser).userId)
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
