package iris.kmtproto

import iris.kmtproto.api.bot.BotApiMapFactory
import iris.kmtproto.api.bot.toBotApiMap
import iris.kmtproto.tl.gen.Channel
import iris.kmtproto.tl.gen.ChannelParticipantCtor
import iris.kmtproto.tl.gen.ChatCtor
import iris.kmtproto.tl.gen.ChatPhotoEmpty
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.MessageEntityBold
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.ReactionCount
import iris.kmtproto.tl.gen.ReactionEmoji
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateBotMessageReaction
import iris.kmtproto.tl.gen.UpdateBotMessageReactions
import iris.kmtproto.tl.gen.UpdateChannel
import iris.kmtproto.tl.gen.UpdateChannelParticipant
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UpdateUserStatus
import iris.kmtproto.tl.gen.UserCtor
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

    @Test
    fun privateMessageWithoutFromIdUsesPeerAndCachedUser() {
        val raw = UpdateNewMessage(
            message = MessageCtor(
                id = 114,
                peerId = PeerUser(661079614),
                date = 1_789_997_041,
                message = "пинг",
            ),
            pts = 1,
            ptsCount = 1,
        )
        val users = mapOf(
            661079614L to UserCtor(
                id = 661079614L,
                firstName = "Ivan",
                lastName = "I",
                username = "airi_gf",
                langCode = "ru",
            ),
        )
        val u = raw.toBotApiMap(maps, 1, users = { users[it] })!!
        val msg = u["message"] as Map<*, *>
        val from = msg["from"] as Map<*, *>
        assertEquals(661079614L, from["id"])
        assertEquals(false, from["is_bot"])
        assertEquals("Ivan", from["first_name"])
        assertEquals("I", from["last_name"])
        assertEquals("airi_gf", from["username"])
        assertEquals("ru", from["language_code"])
        val chat = msg["chat"] as Map<*, *>
        assertEquals("private", chat["type"])
        assertEquals("Ivan", chat["first_name"])
        assertEquals("airi_gf", chat["username"])
    }

    @Test
    fun groupChatGetsTitleFromCache() {
        val raw = UpdateNewMessage(
            message = MessageCtor(
                id = 1,
                peerId = PeerChat(50),
                date = 1,
                message = "hi",
                fromId = PeerUser(7),
            ),
            pts = 1,
            ptsCount = 1,
        )
        val chats = mapOf(
            50L to ChatCtor(
                id = 50,
                title = "Room",
                photo = ChatPhotoEmpty,
                participantsCount = 2,
                date = 1,
                version = 1,
            ),
        )
        val u = raw.toBotApiMap(maps, 1, chats = { chats[it] })!!
        val chat = (u["message"] as Map<*, *>)["chat"] as Map<*, *>
        assertEquals(-50L, chat["id"])
        assertEquals("group", chat["type"])
        assertEquals("Room", chat["title"])
    }

    @Test
    fun channelChatGetsTitleAndUsername() {
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
        val chats = mapOf(99L to Channel(id = 99, title = "News", photo = ChatPhotoEmpty, date = 1, broadcast = true, username = "news"))
        val u = raw.toBotApiMap(maps, 1, chats = { chats[it] })!!
        val chat = (u["channel_post"] as Map<*, *>)["chat"] as Map<*, *>
        assertEquals("channel", chat["type"])
        assertEquals("News", chat["title"])
        assertEquals("news", chat["username"])
    }

    @Test
    fun channelParticipantJoinIsChatMember() {
        val raw = UpdateChannelParticipant(
            channelId = 99,
            date = 10,
            actorId = 1,
            userId = 7,
            qts = 1,
            prevParticipant = null,
            newParticipant = ChannelParticipantCtor(userId = 7, date = 10),
        )
        val u = raw.toBotApiMap(maps, 3)!!
        val ev = u["chat_member"] as Map<*, *>
        assertEquals(10, ev["date"])
        val old = ev["old_chat_member"] as Map<*, *>
        val neu = ev["new_chat_member"] as Map<*, *>
        assertEquals("left", old["status"])
        assertEquals("member", neu["status"])
        assertEquals(7L, (neu["user"] as Map<*, *>)["id"])
        assertEquals(-1_000_000_000_000L - 99L, (ev["chat"] as Map<*, *>)["id"])
    }

    @Test
    fun channelParticipantSelfIsMyChatMember() {
        val me = UserCtor(id = 42, bot = true, firstName = "Bot")
        val raw = UpdateChannelParticipant(
            channelId = 5,
            date = 1,
            actorId = 9,
            userId = 42,
            qts = 1,
            newParticipant = ChannelParticipantCtor(userId = 42, date = 1),
        )
        val u = raw.toBotApiMap(maps, 4, self = me)!!
        assertTrue("my_chat_member" in u)
        assertTrue("chat_member" !in u)
    }

    @Test
    fun botMessageReactionsAreReactionCount() {
        val raw = UpdateBotMessageReactions(
            peer = PeerChannel(99),
            msgId = 8,
            date = 3,
            reactions = listOf(ReactionCount(reaction = ReactionEmoji("👍"), count = 4)),
            qts = 1,
        )
        val u = raw.toBotApiMap(maps, 5)!!
        val body = u["message_reaction_count"] as Map<*, *>
        assertEquals(8, body["message_id"])
        val reactions = body["reactions"] as List<*>
        val first = reactions[0] as Map<*, *>
        assertEquals(4, first["total_count"])
        assertEquals("emoji", (first["type"] as Map<*, *>)["type"])
        assertEquals("👍", (first["type"] as Map<*, *>)["emoji"])
    }

    @Test
    fun botMessageReactionIsMessageReaction() {
        val raw = UpdateBotMessageReaction(
            peer = PeerUser(7),
            msgId = 2,
            date = 4,
            actor = PeerUser(7),
            oldReactions = emptyList(),
            newReactions = listOf(ReactionEmoji("❤")),
            qts = 1,
        )
        val u = raw.toBotApiMap(maps, 6)!!
        val body = u["message_reaction"] as Map<*, *>
        assertEquals(2, body["message_id"])
        assertEquals(7L, (body["user"] as Map<*, *>)["id"])
        val neu = body["new_reaction"] as List<*>
        assertEquals("❤", (neu[0] as Map<*, *>)["emoji"])
    }

    @Test
    fun updateChannelHasNoBotApiShape() {
        assertNull(UpdateChannel(channelId = 1).toBotApiMap(maps, 1))
    }
}

fun main() {
    BotApiUpdateTest().run {
        textMessageMatchesBotApiShape()
        channelPostUsesChannelPostKey()
        factoryBuildsEveryNestedMap()
        callbackQuery()
        unmappedUpdateDropped()
        privateMessageWithoutFromIdUsesPeerAndCachedUser()
        groupChatGetsTitleFromCache()
        channelChatGetsTitleAndUsername()
        channelParticipantJoinIsChatMember()
        channelParticipantSelfIsMyChatMember()
        botMessageReactionsAreReactionCount()
        botMessageReactionIsMessageReaction()
        updateChannelHasNoBotApiShape()
    }
    println("BotApiUpdateTest ok")
}
