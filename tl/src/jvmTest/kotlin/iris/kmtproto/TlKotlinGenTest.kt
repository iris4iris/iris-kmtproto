package iris.kmtproto

import iris.kmtproto.tl.TlReader
import iris.kmtproto.tl.gen.MessageFwdHeader
import iris.kmtproto.tl.gen.MessageReplies
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.UpdateDeleteMessages
import iris.kmtproto.tl.gen.UpdateDeleteScheduledMessages
import iris.kmtproto.tl.gen.readMessageFwdHeader
import iris.kmtproto.tl.gen.readMessageReplies
import iris.kmtproto.tl.gen.readPeer
import iris.kmtproto.tl.gen.readUpdate
import iris.kmtproto.tl.toBytes
import iris.kmtproto.tlgen.TlKotlinGen
import iris.kmtproto.tlgen.TlParser
import java.io.File
import kotlin.test.Test
import kotlin.test.assertContentEquals
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
        assertTrue(peerFile.contains("class PeerUser("), peerFile.take(400))
        assertTrue(peerFile.contains("override fun serialize(w: TlWriter)"), peerFile.take(600))
        for ((name, src) in files) {
            assertTrue("Int? = null" !in src, "$name still has Int? = null")
            assertTrue("Long? = null" !in src, "$name still has Long? = null")
            assertTrue("List<Int>" !in src, "$name still has List<Int>")
            assertTrue("List<Long>" !in src, "$name still has List<Long>")
        }
        val fwdSrc = files.entries.first { it.value.contains("class MessageFwdHeader(") }.value
        assertTrue(fwdSrc.contains("val channelPost: Int = 0"), fwdSrc)
        assertTrue(fwdSrc.contains("if (channelPost != 0)"), fwdSrc)
        val userSrc = files.entries.first { it.value.contains("class UserCtor(") }.value
        assertTrue(userSrc.contains("val accessHash: Long = 0L"), userSrc)
        assertTrue(userSrc.contains("if (accessHash != 0L)"), userSrc)
        assertTrue(userSrc.contains("val self: Boolean = false"), userSrc)
        val delSrc = files.entries.first { it.value.contains("class UpdateDeleteMessages(") }.value
        assertTrue(delSrc.contains("val messages: IntArray"), delSrc)
        assertTrue(delSrc.contains("w.writeVectorInt(messages)"), delSrc)
        val schedSrc = files.entries.first { it.value.contains("class UpdateDeleteScheduledMessages(") }.value
        assertTrue(schedSrc.contains("val sentMessages: IntArray = intArrayOf()"), schedSrc)
        assertTrue(schedSrc.contains("if (sentMessages.isNotEmpty())"), schedSrc)
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
        assertEquals(0, fwdBack.channelPost)

        val withPost = MessageFwdHeader(date = 100, channelPost = 7)
        val postBack = readMessageFwdHeader(TlReader(withPost.toBytes()))
        assertEquals(7, postBack.channelPost)
        assertTrue(withPost.toBytes().size > MessageFwdHeader(date = 100).toBytes().size)

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

    @Test
    fun vectorIntLongRoundtrip() {
        val del = UpdateDeleteMessages(messages = intArrayOf(1, 2, 3), pts = 10, ptsCount = 3)
        val delBack = readUpdate(TlReader(del.toBytes())) as UpdateDeleteMessages
        assertContentEquals(intArrayOf(1, 2, 3), delBack.messages)
        assertEquals(10, delBack.pts)

        val absent = UpdateDeleteScheduledMessages(peer = PeerUser(1L), messages = intArrayOf(5))
        val present = UpdateDeleteScheduledMessages(
            peer = PeerUser(1L),
            messages = intArrayOf(5),
            sentMessages = intArrayOf(9, 10),
        )
        assertTrue(present.toBytes().size > absent.toBytes().size)
        val presentBack = readUpdate(TlReader(present.toBytes())) as UpdateDeleteScheduledMessages
        assertContentEquals(intArrayOf(9, 10), presentBack.sentMessages)
        val absentBack = readUpdate(TlReader(absent.toBytes())) as UpdateDeleteScheduledMessages
        assertContentEquals(intArrayOf(), absentBack.sentMessages)
    }
}

private fun findSchema(name: String): File {
    val paths = listOf(
        "schema/$name",
        "tl/schema/$name",
        "kmtproto/schema/$name",
        "kmtproto/tl/schema/$name",
    )
    val roots = listOf(File("."), File(".."), File(System.getProperty("user.dir")))
    for (root in roots) {
        for (p in paths) {
            val f = File(root, p)
            if (f.isFile) return f.canonicalFile
        }
    }
    error("schema/$name not found")
}