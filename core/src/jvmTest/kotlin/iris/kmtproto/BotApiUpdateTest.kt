package iris.kmtproto

import iris.kmtproto.api.bot.BotApiMapFactory
import iris.kmtproto.api.bot.toBotApiMap
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.MessageEntityBold
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UpdateUserStatus
import iris.kmtproto.tl.gen.UserStatusOffline
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MyBotMap : LinkedHashMap<String, Any?>()

class BotApiUpdateTest {
    private val maps = BotApiMapFactory { LinkedHashMap() }

    @Test
    fun textMessageMatchesBotApiShape() {
        val raw = UpdateNewMessage(
            message = MessageCtor(
                id = 10,
                peerId = PeerUser(42),
                date = 1_700_000_000,
                message = "hello",
                fromId = PeerUser(42),
                entities = listOf(MessageEntityBold(offset = 0, length = 5)),
            ),
            pts = 2,
            ptsCount = 1,
        )
        val u = raw.toBotApiMap(maps, 7)!!
        assertEquals(7, u["update_id"])
        val msg = u["message"] as Map<*, *>
        assertEquals(10, msg["message_id"])
        assertEquals(1_700_000_000, msg["date"])
        assertEquals("hello", msg["text"])
        val chat = msg["chat"] as Map<*, *>
        assertEquals(42L, chat["id"])
        assertEquals("private", chat["type"])
        val from = msg["from"] as Map<*, *>
        assertEquals(42L, from["id"])
        assertEquals(false, from["is_bot"])
        val ents = msg["entities"] as List<*>
        val bold = ents[0] as Map<*, *>
        assertEquals("bold", bold["type"])
        assertEquals(0, bold["offset"])
        assertEquals(5, bold["length"])
    }

    @Test
    fun channelPostUsesChannelPostKey() {
        val raw = UpdateNewChannelMessage(
            message = MessageCtor(
                id = 3,
                peerId = PeerChannel(99),
                date = 2,
                message = "post",
                post = true,
            ),
            pts = 1,
            ptsCount = 1,
        )
        val u = raw.toBotApiMap(maps, 1)!!
        assertTrue("channel_post" in u)
        assertTrue("message" !in u)
        val post = u["channel_post"] as Map<*, *>
        val chat = post["chat"] as Map<*, *>
        assertEquals(-1_000_000_000_000L - 99L, chat["id"])
        assertEquals("channel", chat["type"])
    }

    @Test
    fun factoryBuildsEveryNestedMap() {
        val factory = BotApiMapFactory { MyBotMap() }
        val raw = UpdateNewMessage(
            message = MessageCtor(
                id = 1,
                peerId = PeerUser(7),
                date = 1,
                message = "x",
                fromId = PeerUser(7),
            ),
            pts = 1,
            ptsCount = 1,
        )
        val u = raw.toBotApiMap(factory, 1)!!
        assertTrue(u is MyBotMap)
        assertTrue(u["message"] is MyBotMap)
        val msg = u["message"] as MyBotMap
        assertTrue(msg["chat"] is MyBotMap)
        assertTrue(msg["from"] is MyBotMap)
    }

    @Test
    fun callbackQuery() {
        val raw = UpdateBotCallbackQuery(
            queryId = 99L,
            userId = 5L,
            peer = PeerUser(5),
            msgId = 10,
            chatInstance = 123L,
            data = "ok".encodeToByteArray(),
        )
        val u = raw.toBotApiMap(maps, 2)!!
        val q = u["callback_query"] as Map<*, *>
        assertEquals("99", q["id"])
        assertEquals("ok", q["data"])
        assertEquals("123", q["chat_instance"])
        val from = q["from"] as Map<*, *>
        assertEquals(5L, from["id"])
    }

    @Test
    fun unmappedUpdateDropped() {
        val raw = UpdateUserStatus(userId = 1, status = UserStatusOffline(wasOnline = 0))
        assertNull(raw.toBotApiMap(maps, 1))
    }
}

fun main() {
    BotApiUpdateTest().run {
        textMessageMatchesBotApiShape()
        channelPostUsesChannelPostKey()
        factoryBuildsEveryNestedMap()
        callbackQuery()
        unmappedUpdateDropped()
    }
    println("BotApiUpdateTest ok")
}
