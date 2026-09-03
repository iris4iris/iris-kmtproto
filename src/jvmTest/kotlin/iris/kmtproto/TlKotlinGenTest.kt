package iris.kmtproto

import iris.kmtproto.tl.TlReader
import iris.kmtproto.tl.gen.MessageFwdHeader
import iris.kmtproto.tl.gen.MessageReplies
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.readMessageFwdHeader
import iris.kmtproto.tl.gen.readMessageReplies
import iris.kmtproto.tl.gen.readPeer
import iris.kmtproto.tl.toBytes
import iris.kmtproto.tlgen.TlKotlinGen
import iris.kmtproto.tlgen.TlParser
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TlKotlinGenTest {
    @Test
    fun generatesApiLayerFiles() {
        val schema = TlParser.parse(findSchema("api.tl").readText())
        val files = TlKotlinGen.generateFiles(schema)
        assertTrue(files.containsKey("Register.kt"))
        assertTrue(files.keys.any { it.startsWith("T_") })
        assertTrue(files.keys.any { it.startsWith("Fn_") })
        val peerFile = files.entries.first { it.value.contains("sealed interface Peer") }.value
        assertTrue(peerFile.contains("data class PeerUser("), peerFile.take(400))
    }

    @Test
    fun peerFwdRepliesRoundtrip() {
        val peer = PeerUser(userId = 42L)
        val back = readPeer(TlReader(peer.toBytes())) as PeerUser
        assertEquals(42L, back.userId)

        val fwd = MessageFwdHeader(
            date = 100,
            imported = true,
            fromId = PeerUser(7L),
            fromName = "Ann",
        )
        val fwdBack = readMessageFwdHeader(TlReader(fwd.toBytes()))
        assertEquals(100, fwdBack.date)
        assertTrue(fwdBack.imported)
        assertEquals(7L, (fwdBack.fromId as PeerUser).userId)
        assertEquals("Ann", fwdBack.fromName)

        val replies = MessageReplies(
            replies = 3,
            repliesPts = 9,
            comments = true,
            recentRepliers = listOf(PeerUser(1L), PeerUser(2L)),
            channelId = 99L,
        )
        val rBack = readMessageReplies(TlReader(replies.toBytes()))
        assertEquals(3, rBack.replies)
        assertTrue(rBack.comments)
        assertEquals(99L, rBack.channelId)
        assertEquals(listOf(1L, 2L), rBack.recentRepliers!!.map { (it as PeerUser).userId })
    }
}

private fun findSchema(name: String): File {
    val paths = listOf("schema/$name", "kmtproto/schema/$name")
    val roots = listOf(File("."), File(".."), File(System.getProperty("user.dir")))
    for (root in roots) {
        for (p in paths) {
            val f = File(root, p)
            if (f.isFile) return f.canonicalFile
        }
    }
    error("schema/$name not found")
}