package iris.kmtproto

import iris.kmtproto.LongIntPair
import iris.kmtproto.api.bot.BotApiMapFactory
import iris.kmtproto.api.bot.BotApiWriter
import iris.kmtproto.api.bot.toBotApiMap
import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.Storage
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.tl.gen.Boost
import iris.kmtproto.tl.gen.Channel
import iris.kmtproto.tl.gen.ChannelParticipantCtor
import iris.kmtproto.tl.gen.ChannelParticipantSelf
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.ChatCtor
import iris.kmtproto.tl.gen.ChatPhotoEmpty
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.MessageEntityBold
import iris.kmtproto.tl.gen.MessageFwdHeader
import iris.kmtproto.tl.gen.MessageReplyHeaderCtor
import iris.kmtproto.tl.gen.Peer
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.ReactionCount
import iris.kmtproto.tl.gen.ReactionEmoji
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateBotChatBoost
import iris.kmtproto.tl.gen.UpdateBotGuestChatQuery
import iris.kmtproto.tl.gen.UpdateBotMessageReaction
import iris.kmtproto.tl.gen.UpdateBotMessageReactions
import iris.kmtproto.tl.gen.UpdateBotStarsSubscription
import iris.kmtproto.tl.gen.UpdateBotWebhookJSON
import iris.kmtproto.tl.gen.UpdateBotWebhookJSONQuery
import iris.kmtproto.tl.gen.UpdateBusinessBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateChannel
import iris.kmtproto.tl.gen.UpdateChannelParticipant
import iris.kmtproto.tl.gen.UpdateManagedBot
import iris.kmtproto.tl.gen.UpdateMessageID
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UpdateUserStatus
import iris.kmtproto.tl.gen.UserCtor
import iris.kmtproto.tl.gen.UserStatusOffline
import iris.kmtproto.tl.gen.DataJSON
import iris.kmtproto.tl.gen.MessageActionBotAllowed
import iris.kmtproto.tl.gen.MessageActionPaymentSentMe
import iris.kmtproto.tl.gen.MessageActionTopicCreate
import iris.kmtproto.tl.gen.MessageActionWebViewDataSentMe
import iris.kmtproto.tl.gen.MessageMediaInvoice
import iris.kmtproto.tl.gen.MessageService
import iris.kmtproto.tl.gen.PaymentCharge
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

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
            PeerChat(50).botApiChatId() to ChatCtor(
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
        val chats = mapOf(PeerChannel(99).botApiChatId() to Channel(id = 99, title = "News", photo = ChatPhotoEmpty, date = 1, broadcast = true, username = "news"))
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
    fun channelParticipantSelfTypeIsMyChatMemberWithoutSelfId() {
        val raw = UpdateChannelParticipant(
            channelId = 5,
            date = 1,
            actorId = 9,
            userId = 42,
            qts = 1,
            newParticipant = ChannelParticipantSelf(userId = 42, inviterId = 9, date = 1),
        )
        val u = raw.toBotApiMap(maps, 4)!!
        assertTrue("my_chat_member" in u)
        assertTrue("chat_member" !in u)
    }

    @Test
    fun channelParticipantMatchesSelfId() {
        val raw = UpdateChannelParticipant(
            channelId = 5,
            date = 1,
            actorId = 9,
            userId = 42,
            qts = 1,
            newParticipant = ChannelParticipantCtor(userId = 42, date = 1),
        )
        val u = raw.toBotApiMap(maps, 4, selfId = 42)!!
        assertTrue("my_chat_member" in u)
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

    @Test
    fun updateMessageIdHasNoBotApiShape() {
        assertNull(UpdateMessageID(id = 10, randomId = 99L).toBotApiMap(maps, 1))
    }

    @Test
    fun chatBoostAndRemoved() {
        val added = UpdateBotChatBoost(
            peer = PeerChannel(9),
            boost = Boost(id = "b1", date = 10, expires = 20, userId = 7),
            qts = 1,
        )
        val u = added.toBotApiMap(maps, 1)!!
        val boost = (u["chat_boost"] as Map<*, *>)["boost"] as Map<*, *>
        assertEquals("b1", boost["boost_id"])
        assertEquals("premium", (boost["source"] as Map<*, *>)["source"])
        val gone = UpdateBotChatBoost(
            peer = PeerChannel(9),
            boost = Boost(id = "b1", date = 11, expires = 0, userId = 7),
            qts = 2,
        ).toBotApiMap(maps, 2)!!
        assertEquals("b1", (gone["removed_chat_boost"] as Map<*, *>)["boost_id"])
    }

    @Test
    fun managedBotAndSubscription() {
        val mb = UpdateManagedBot(userId = 1, botId = 2, qts = 1).toBotApiMap(maps, 1)!!
        val body = mb["managed_bot"] as Map<*, *>
        assertEquals(1L, (body["user"] as Map<*, *>)["id"])
        assertEquals(true, (body["bot"] as Map<*, *>)["is_bot"])
        val sub = UpdateBotStarsSubscription(
            userId = 3,
            payload = "pay".encodeToByteArray(),
            qts = 1,
            canceled = true,
        ).toBotApiMap(maps, 2)!!
        val s = sub["subscription"] as Map<*, *>
        assertEquals("canceled", s["state"])
        assertEquals("pay", s["invoice_payload"])
    }

    @Test
    fun guestMessageAndBusinessCallback() {
        val guest = UpdateBotGuestChatQuery(
            queryId = 88L,
            message = MessageCtor(id = 1, peerId = PeerUser(7), date = 1, message = "hi", fromId = PeerUser(7)),
            qts = 1,
        ).toBotApiMap(maps, 1)!!
        val gm = guest["guest_message"] as Map<*, *>
        assertEquals("88", gm["guest_query_id"])
        assertEquals("hi", gm["text"])
        val cb = UpdateBusinessBotCallbackQuery(
            queryId = 5L,
            userId = 7L,
            connectionId = "c1",
            message = MessageCtor(id = 2, peerId = PeerUser(7), date = 1, message = "x", fromId = PeerUser(7)),
            chatInstance = 9L,
            data = "ok".encodeToByteArray(),
        ).toBotApiMap(maps, 2)!!
        val q = cb["callback_query"] as Map<*, *>
        assertEquals("c1", q["business_connection_id"])
        assertEquals("ok", q["data"])
    }

    @Test
    fun customEventAndQuery() {
        val event = UpdateBotWebhookJSON(data = DataJSON("""{"foo":1}""")).toBotApiMap(maps, 1)!!
        assertEquals("""{"foo":1}""", event["custom_event"])
        val query = UpdateBotWebhookJSONQuery(
            queryId = 9L,
            data = DataJSON("""{"bar":2}"""),
            timeout = 30,
        ).toBotApiMap(maps, 2)!!
        val body = query["custom_query"] as Map<*, *>
        assertEquals("9", body["id"])
        assertEquals("""{"bar":2}""", body["data"])
        assertEquals(30, body["timeout"])
    }

    @Test
    fun servicePaymentAndWebApp() {
        val pay = UpdateNewMessage(
            message = MessageService(
                id = 1,
                peerId = PeerUser(7),
                date = 2,
                action = MessageActionPaymentSentMe(
                    currency = "USD",
                    totalAmount = 199,
                    payload = "sku".encodeToByteArray(),
                    charge = PaymentCharge(id = "tg", providerChargeId = "stripe"),
                    recurringInit = true,
                ),
                fromId = PeerUser(7),
            ),
            pts = 1,
            ptsCount = 1,
        ).toBotApiMap(maps, 1)!!
        val payment = (pay["message"] as Map<*, *>)["successful_payment"] as Map<*, *>
        assertEquals("USD", payment["currency"])
        assertEquals(199L, payment["total_amount"])
        assertEquals("sku", payment["invoice_payload"])
        assertEquals("tg", payment["telegram_payment_charge_id"])
        assertEquals(true, payment["is_first_recurring"])
        val web = UpdateNewMessage(
            message = MessageService(
                id = 2,
                peerId = PeerUser(7),
                date = 3,
                action = MessageActionWebViewDataSentMe(text = "Open", data = "ok"),
                fromId = PeerUser(7),
            ),
            pts = 1,
            ptsCount = 1,
        ).toBotApiMap(maps, 2)!!
        val wad = (web["message"] as Map<*, *>)["web_app_data"] as Map<*, *>
        assertEquals("Open", wad["button_text"])
        assertEquals("ok", wad["data"])
    }

    @Test
    fun forumTopicAndWriteAccessAndInvoice() {
        val topic = UpdateNewMessage(
            message = MessageService(
                id = 1,
                peerId = PeerChannel(9),
                date = 1,
                action = MessageActionTopicCreate(title = "General", iconColor = 0x6FB9F0),
                fromId = PeerUser(7),
            ),
            pts = 1,
            ptsCount = 1,
        ).toBotApiMap(maps, 1)!!
        val created = (topic["message"] as Map<*, *>)["forum_topic_created"] as Map<*, *>
        assertEquals("General", created["name"])
        assertEquals(0x6FB9F0, created["icon_color"])
        val access = UpdateNewMessage(
            message = MessageService(
                id = 2,
                peerId = PeerUser(7),
                date = 1,
                action = MessageActionBotAllowed(fromRequest = true),
                fromId = PeerUser(7),
            ),
            pts = 1,
            ptsCount = 1,
        ).toBotApiMap(maps, 2)!!
        val allowed = (access["message"] as Map<*, *>)["write_access_allowed"] as Map<*, *>
        assertEquals(true, allowed["from_request"])
        val invoice = UpdateNewMessage(
            message = MessageCtor(
                id = 3,
                peerId = PeerUser(7),
                date = 1,
                message = "",
                fromId = PeerUser(7),
                media = MessageMediaInvoice(
                    title = "Item",
                    description = "Desc",
                    currency = "XTR",
                    totalAmount = 50,
                    startParam = "pay",
                ),
            ),
            pts = 1,
            ptsCount = 1,
        ).toBotApiMap(maps, 3)!!
        val inv = (invoice["message"] as Map<*, *>)["invoice"] as Map<*, *>
        assertEquals("Item", inv["title"])
        assertEquals("XTR", inv["currency"])
        assertEquals(50L, inv["total_amount"])
        assertEquals("pay", inv["start_parameter"])
    }

    @Test
    fun replyToMessageUsesReplyFromForFrom() {
        val users = mapOf(
            661079614L to UserCtor(id = 661079614L, firstName = "Ivan", username = "airi_gf"),
        )
        val raw = UpdateNewMessage(
            message = MessageCtor(
                id = 114,
                peerId = PeerUser(661079614),
                date = 2_000,
                message = "пинг",
                replyTo = MessageReplyHeaderCtor(
                    replyToMsgId = 113,
                    replyFrom = MessageFwdHeader(date = 1_000, fromId = PeerUser(661079614)),
                ),
            ),
            pts = 1,
            ptsCount = 1,
        )
        val u = raw.toBotApiMap(maps, 1, users = { users[it] })!!
        val reply = (u["message"] as Map<*, *>)["reply_to_message"] as Map<*, *>
        assertEquals(113, reply["message_id"])
        assertEquals(1_000, reply["date"])
        val from = reply["from"] as Map<*, *>
        assertEquals(661079614L, from["id"])
        assertEquals("Ivan", from["first_name"])
        assertEquals("airi_gf", from["username"])
    }

    @Test
    fun replyToMessageUsesFetchedOriginal() {
        val original = MessageCtor(
            id = 113,
            peerId = PeerUser(661079614),
            date = 1_000,
            message = "привет",
            fromId = PeerUser(661079614),
            replyTo = MessageReplyHeaderCtor(replyToMsgId = 100),
        )
        val users = mapOf(
            661079614L to UserCtor(id = 661079614L, firstName = "Ivan", username = "airi_gf"),
        )
        val raw = UpdateNewMessage(
            message = MessageCtor(
                id = 114,
                peerId = PeerUser(661079614),
                date = 2_000,
                message = "пинг",
                replyTo = MessageReplyHeaderCtor(replyToMsgId = 113),
            ),
            pts = 1,
            ptsCount = 1,
        )
        val u = raw.toBotApiMap(
            maps,
            1,
            users = { users[it] },
            messages = { _, id -> original.takeIf { id == 113 } },
        )!!
        val reply = (u["message"] as Map<*, *>)["reply_to_message"] as Map<*, *>
        assertEquals(113, reply["message_id"])
        assertEquals("привет", reply["text"])
        val from = reply["from"] as Map<*, *>
        assertEquals(661079614L, from["id"])
        assertEquals("Ivan", from["first_name"])
        assertTrue("reply_to_message" !in reply)
    }

    @Test
    fun replyToMessageNestedWithoutFromIdInfersPeer() {
        val original = MessageCtor(
            id = 113,
            peerId = PeerUser(661079614),
            date = 1_000,
            message = "hello",
        )
        val users = mapOf(
            661079614L to UserCtor(id = 661079614L, firstName = "Ivan"),
        )
        val raw = UpdateNewMessage(
            message = MessageCtor(
                id = 114,
                peerId = PeerUser(661079614),
                date = 2_000,
                message = "пинг",
                replyTo = MessageReplyHeaderCtor(replyToMsgId = 113),
            ),
            pts = 1,
            ptsCount = 1,
        )
        val u = raw.toBotApiMap(
            maps,
            1,
            users = { users[it] },
            messages = { _, id -> original.takeIf { id == 113 } },
        )!!
        val from = ((u["message"] as Map<*, *>)["reply_to_message"] as Map<*, *>)["from"] as Map<*, *>
        assertEquals(661079614L, from["id"])
        assertEquals("Ivan", from["first_name"])
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
        channelParticipantSelfTypeIsMyChatMemberWithoutSelfId()
        channelParticipantMatchesSelfId()
        botMessageReactionsAreReactionCount()
        botMessageReactionIsMessageReaction()
        updateChannelHasNoBotApiShape()
        updateMessageIdHasNoBotApiShape()
        chatBoostAndRemoved()
        managedBotAndSubscription()
        guestMessageAndBusinessCallback()
        customEventAndQuery()
        servicePaymentAndWebApp()
        forumTopicAndWriteAccessAndInvoice()
        replyToMessageUsesReplyFromForFrom()
        replyToMessageUsesFetchedOriginal()
        replyToMessageNestedWithoutFromIdInfersPeer()
    }
    println("BotApiUpdateTest ok")
}

private fun Update.toBotApiMap(
    maps: BotApiMapFactory,
    updateId: Int,
    users: (Long) -> UserCtor? = { null },
    chats: (Long) -> Chat? = { null },
    self: UserCtor? = null,
    selfId: Long = 0L,
    messages: (Peer, Int) -> Message? = { _, _ -> null },
): MutableMap<String, Any?>? {
    val base = MemoryStorage()
    if (self != null) base.rememberUser(self)
    val storage = object : Storage by base {
        override fun getUser(id: Long): UserCtor? = users(id) ?: base.getUser(id)
        override fun getChat(id: Long): Chat? = chats(id) ?: base.getChat(id)
        override fun getMessage(peerId: Long, messageId: Int): Message? =
            getMessage(LongIntPair(peerId, messageId))
        override fun getMessage(key: LongIntPair): Message? {
            val peer = when {
                key.first > 0L -> PeerUser(key.first)
                key.first <= -1_000_000_000_000L -> PeerChannel(-(key.first + 1_000_000_000_000L))
                else -> PeerChat(-key.first)
            }
            return messages(peer, key.second) ?: base.getMessage(key)
        }
    }
    val id = if (selfId != 0L) selfId else self?.id ?: 0L
    val writer = BotApiWriter(selfId = id, storage = storage, maps = maps)
    repeat((updateId - 1).coerceAtLeast(0)) { writer.nextUpdateId() }
    return runBlocking { toBotApiMap(writer) }
}
