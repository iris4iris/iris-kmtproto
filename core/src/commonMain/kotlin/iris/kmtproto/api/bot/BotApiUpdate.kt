package iris.kmtproto.api.bot

import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.Storage
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.tl.gen.BoolTrue
import iris.kmtproto.tl.gen.Boost
import iris.kmtproto.tl.gen.BotAppCtor
import iris.kmtproto.tl.gen.Channel
import iris.kmtproto.tl.gen.ChannelForbidden
import iris.kmtproto.tl.gen.ChannelParticipant
import iris.kmtproto.tl.gen.ChannelParticipantAdmin
import iris.kmtproto.tl.gen.ChannelParticipantBanned
import iris.kmtproto.tl.gen.ChannelParticipantCtor
import iris.kmtproto.tl.gen.ChannelParticipantCreator
import iris.kmtproto.tl.gen.ChannelParticipantLeft
import iris.kmtproto.tl.gen.ChannelParticipantSelf
import iris.kmtproto.tl.gen.ChatAdminRights
import iris.kmtproto.tl.gen.ChatBannedRights
import iris.kmtproto.tl.gen.ChatCtor
import iris.kmtproto.tl.gen.ChatForbidden
import iris.kmtproto.tl.gen.ChatParticipant
import iris.kmtproto.tl.gen.ChatParticipantAdmin
import iris.kmtproto.tl.gen.ChatParticipantCtor
import iris.kmtproto.tl.gen.ChatParticipantCreator
import iris.kmtproto.tl.gen.DocumentAttributeAnimated
import iris.kmtproto.tl.gen.DocumentAttributeAudio
import iris.kmtproto.tl.gen.DocumentAttributeCustomEmoji
import iris.kmtproto.tl.gen.DocumentAttributeFilename
import iris.kmtproto.tl.gen.DocumentAttributeSticker
import iris.kmtproto.tl.gen.DocumentAttributeVideo
import iris.kmtproto.tl.gen.DocumentCtor
import iris.kmtproto.tl.gen.GeoPoint
import iris.kmtproto.tl.gen.GeoPointCtor
import iris.kmtproto.tl.gen.InlineButtonTypeBuy
import iris.kmtproto.tl.gen.InlineButtonTypeCallback
import iris.kmtproto.tl.gen.InlineButtonTypeCopy
import iris.kmtproto.tl.gen.InlineButtonTypeGame
import iris.kmtproto.tl.gen.InlineButtonTypeSwitchInline
import iris.kmtproto.tl.gen.InlineButtonTypeUrl
import iris.kmtproto.tl.gen.InlineButtonTypeUrlAuth
import iris.kmtproto.tl.gen.InlineButtonTypeUserProfile
import iris.kmtproto.tl.gen.InlineButtonTypeWebView
import iris.kmtproto.tl.gen.InlineQueryPeerTypeBotPM
import iris.kmtproto.tl.gen.InlineQueryPeerTypeBroadcast
import iris.kmtproto.tl.gen.InlineQueryPeerTypeChat
import iris.kmtproto.tl.gen.InlineQueryPeerTypeMegagroup
import iris.kmtproto.tl.gen.InlineQueryPeerTypePM
import iris.kmtproto.tl.gen.InlineQueryPeerTypeSameBotPM
import iris.kmtproto.tl.gen.InputBotInlineMessageID
import iris.kmtproto.tl.gen.InputBotInlineMessageID64
import iris.kmtproto.tl.gen.InputBotInlineMessageIDCtor
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageActionBoostApply
import iris.kmtproto.tl.gen.MessageActionBotAllowed
import iris.kmtproto.tl.gen.MessageActionChangeCommunity
import iris.kmtproto.tl.gen.MessageActionChangeCreator
import iris.kmtproto.tl.gen.MessageActionChannelCreate
import iris.kmtproto.tl.gen.MessageActionChannelMigrateFrom
import iris.kmtproto.tl.gen.MessageActionChatAddUser
import iris.kmtproto.tl.gen.MessageActionChatCreate
import iris.kmtproto.tl.gen.MessageActionChatDeletePhoto
import iris.kmtproto.tl.gen.MessageActionChatDeleteUser
import iris.kmtproto.tl.gen.MessageActionChatEditPhoto
import iris.kmtproto.tl.gen.MessageActionChatEditTitle
import iris.kmtproto.tl.gen.MessageActionChatJoinedByLink
import iris.kmtproto.tl.gen.MessageActionChatJoinedByRequest
import iris.kmtproto.tl.gen.MessageActionChatJoinedViaCommunity
import iris.kmtproto.tl.gen.MessageActionChatMigrateTo
import iris.kmtproto.tl.gen.MessageActionGeoProximityReached
import iris.kmtproto.tl.gen.MessageActionGiveawayLaunch
import iris.kmtproto.tl.gen.MessageActionGiveawayResults
import iris.kmtproto.tl.gen.MessageActionGroupCall
import iris.kmtproto.tl.gen.MessageActionGroupCallScheduled
import iris.kmtproto.tl.gen.MessageActionInviteToGroupCall
import iris.kmtproto.tl.gen.MessageActionManagedBotCreated
import iris.kmtproto.tl.gen.MessageActionNewCreatorPending
import iris.kmtproto.tl.gen.MessageActionPaidMessagesPrice
import iris.kmtproto.tl.gen.MessageActionPaymentRefunded
import iris.kmtproto.tl.gen.MessageActionPaymentSentMe
import iris.kmtproto.tl.gen.MessageActionPinMessage
import iris.kmtproto.tl.gen.MessageActionPollAppendAnswer
import iris.kmtproto.tl.gen.MessageActionPollDeleteAnswer
import iris.kmtproto.tl.gen.MessageActionRequestedPeerSentMe
import iris.kmtproto.tl.gen.MessageActionSetMessagesTTL
import iris.kmtproto.tl.gen.MessageActionSuggestedPostApproval
import iris.kmtproto.tl.gen.MessageActionSuggestedPostRefund
import iris.kmtproto.tl.gen.MessageActionSuggestedPostSuccess
import iris.kmtproto.tl.gen.MessageActionTodoAppendTasks
import iris.kmtproto.tl.gen.MessageActionTodoCompletions
import iris.kmtproto.tl.gen.MessageActionTopicCreate
import iris.kmtproto.tl.gen.MessageActionTopicEdit
import iris.kmtproto.tl.gen.MessageActionWebViewDataSentMe
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.MessageEntity
import iris.kmtproto.tl.gen.MessageEntityBlockquote
import iris.kmtproto.tl.gen.MessageEntityBold
import iris.kmtproto.tl.gen.MessageEntityBotCommand
import iris.kmtproto.tl.gen.MessageEntityCashtag
import iris.kmtproto.tl.gen.MessageEntityCode
import iris.kmtproto.tl.gen.MessageEntityCustomEmoji
import iris.kmtproto.tl.gen.MessageEntityEmail
import iris.kmtproto.tl.gen.MessageEntityHashtag
import iris.kmtproto.tl.gen.MessageEntityItalic
import iris.kmtproto.tl.gen.MessageEntityMention
import iris.kmtproto.tl.gen.MessageEntityMentionName
import iris.kmtproto.tl.gen.MessageEntityPhone
import iris.kmtproto.tl.gen.MessageEntityPre
import iris.kmtproto.tl.gen.MessageEntitySpoiler
import iris.kmtproto.tl.gen.MessageEntityStrike
import iris.kmtproto.tl.gen.MessageEntityTextUrl
import iris.kmtproto.tl.gen.MessageEntityUnderline
import iris.kmtproto.tl.gen.MessageEntityUrl
import iris.kmtproto.tl.gen.MessageExtendedMediaCtor
import iris.kmtproto.tl.gen.MessageExtendedMediaPreview
import iris.kmtproto.tl.gen.MessageFwdHeader
import iris.kmtproto.tl.gen.MessageMediaContact
import iris.kmtproto.tl.gen.MessageMediaDice
import iris.kmtproto.tl.gen.MessageMediaDocument
import iris.kmtproto.tl.gen.MessageMediaGame
import iris.kmtproto.tl.gen.MessageMediaGeo
import iris.kmtproto.tl.gen.MessageMediaGeoLive
import iris.kmtproto.tl.gen.MessageMediaGiveaway
import iris.kmtproto.tl.gen.MessageMediaGiveawayResults
import iris.kmtproto.tl.gen.MessageMediaInvoice
import iris.kmtproto.tl.gen.MessageMediaPaidMedia
import iris.kmtproto.tl.gen.MessageMediaPhoto
import iris.kmtproto.tl.gen.MessageMediaPoll
import iris.kmtproto.tl.gen.MessageMediaStory
import iris.kmtproto.tl.gen.MessageMediaToDo
import iris.kmtproto.tl.gen.MessageMediaVenue
import iris.kmtproto.tl.gen.MessageReplyHeaderCtor
import iris.kmtproto.tl.gen.MessageService
import iris.kmtproto.tl.gen.Peer
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.PhotoCachedSize
import iris.kmtproto.tl.gen.PhotoCtor
import iris.kmtproto.tl.gen.PhotoSize
import iris.kmtproto.tl.gen.PhotoSizeCtor
import iris.kmtproto.tl.gen.PhotoSizeProgressive
import iris.kmtproto.tl.gen.Poll
import iris.kmtproto.tl.gen.PollAnswerCtor
import iris.kmtproto.tl.gen.PollResults
import iris.kmtproto.tl.gen.PostAddress
import iris.kmtproto.tl.gen.Reaction
import iris.kmtproto.tl.gen.ReactionCustomEmoji
import iris.kmtproto.tl.gen.ReactionEmoji
import iris.kmtproto.tl.gen.ReactionPaid
import iris.kmtproto.tl.gen.ReplyInlineMarkup
import iris.kmtproto.tl.gen.ReplyKeyboardForceReply
import iris.kmtproto.tl.gen.ReplyKeyboardMarkup
import iris.kmtproto.tl.gen.ReplyMarkup
import iris.kmtproto.tl.gen.RequestedPeerChannel
import iris.kmtproto.tl.gen.RequestedPeerChat
import iris.kmtproto.tl.gen.RequestedPeerUser
import iris.kmtproto.tl.gen.StarsAmount
import iris.kmtproto.tl.gen.StarsAmountCtor
import iris.kmtproto.tl.gen.StarsTonAmount
import iris.kmtproto.tl.gen.TextWithEntities
import iris.kmtproto.tl.gen.TodoCompletion
import iris.kmtproto.tl.gen.TodoItem
import iris.kmtproto.tl.gen.TodoList
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateBotBusinessConnect
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateBotChatBoost
import iris.kmtproto.tl.gen.UpdateBotChatInviteRequester
import iris.kmtproto.tl.gen.UpdateBotDeleteBusinessMessage
import iris.kmtproto.tl.gen.UpdateBotEditBusinessMessage
import iris.kmtproto.tl.gen.UpdateBotGuestChatQuery
import iris.kmtproto.tl.gen.UpdateBotInlineQuery
import iris.kmtproto.tl.gen.UpdateBotInlineSend
import iris.kmtproto.tl.gen.UpdateBotMessageReaction
import iris.kmtproto.tl.gen.UpdateBotMessageReactions
import iris.kmtproto.tl.gen.UpdateBotNewBusinessMessage
import iris.kmtproto.tl.gen.UpdateBotPrecheckoutQuery
import iris.kmtproto.tl.gen.UpdateBotPurchasedPaidMedia
import iris.kmtproto.tl.gen.UpdateBotShippingQuery
import iris.kmtproto.tl.gen.UpdateBotStarsSubscription
import iris.kmtproto.tl.gen.UpdateBotStopped
import iris.kmtproto.tl.gen.UpdateBotWebhookJSON
import iris.kmtproto.tl.gen.UpdateBotWebhookJSONQuery
import iris.kmtproto.tl.gen.UpdateBusinessBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateChannelParticipant
import iris.kmtproto.tl.gen.UpdateChatParticipant
import iris.kmtproto.tl.gen.UpdateChatParticipantAdd
import iris.kmtproto.tl.gen.UpdateChatParticipantAdmin
import iris.kmtproto.tl.gen.UpdateChatParticipantDelete
import iris.kmtproto.tl.gen.UpdateEditChannelMessage
import iris.kmtproto.tl.gen.UpdateEditMessage
import iris.kmtproto.tl.gen.UpdateInlineBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateManagedBot
import iris.kmtproto.tl.gen.UpdateMessageID
import iris.kmtproto.tl.gen.UpdateMessagePoll
import iris.kmtproto.tl.gen.UpdateMessagePollVote
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UserCtor
import iris.kmtproto.tl.gen.Username

suspend fun Update.toBotApiMap(
    w: BotApiWriter,
): MutableMap<String, Any?>? {
    val body = w.updateBody(this) ?: return null
    val out = w.maps.create()
    out["update_id"] = w.nextUpdateId()
    out.putAll(body)
    return out
}

class BotApiWriter(
    val selfId: Long,
    val storage: Storage = MemoryStorage(),
    val maps: BotApiMapFactory = BotApiMapFactory { HashMap() },
) {

    private var updateIdSeq = 0

    @Synchronized
    fun nextUpdateId(): Int = ++updateIdSeq

    private fun me(): Long = selfId

    fun map(): MutableMap<String, Any?> = maps.create()

    fun MutableMap<String, Any?>.opt(key: String, value: Any?) {
        when (value) {
            null, false, "" -> return
            0, 0L -> return
            is Collection<*> -> if (value.isEmpty()) return else put(key, value)
            is Map<*, *> -> if (value.isEmpty()) return else put(key, value)
            else -> put(key, value)
        }
    }

    suspend fun updateBody(u: Update): MutableMap<String, Any?>? {
        return when (u) {
            is UpdateNewMessage -> messageKey(u.message, edited = false)
            is UpdateNewChannelMessage -> messageKey(u.message, edited = false)
            is UpdateEditMessage -> messageKey(u.message, edited = true)
            is UpdateEditChannelMessage -> messageKey(u.message, edited = true)
            is UpdateBotNewBusinessMessage -> {
                val m = message(u.message, u.replyToMessage) ?: return null
                m.opt("business_connection_id", u.connectionId)
                map().also { it["business_message"] = m }
            }

            is UpdateBotEditBusinessMessage -> {
                val m = message(u.message, u.replyToMessage) ?: return null
                m.opt("business_connection_id", u.connectionId)
                map().also { it["edited_business_message"] = m }
            }

            is UpdateBotDeleteBusinessMessage -> map().also {
                it["deleted_business_messages"] = map().apply {
                    put("business_connection_id", u.connectionId)
                    put("chat", chat(u.peer))
                    put("message_ids", u.messages.toList())
                }
            }

            is UpdateBotBusinessConnect -> {
                val c = u.connection
                map().also {
                    it["business_connection"] = map().apply {
                        put("id", c.connectionId)
                        put("user", user(c.userId))
                        put("user_chat_id", c.userId)
                        put("date", c.date)
                        put("can_reply", !c.disabled)
                        put("is_enabled", !c.disabled)
                    }
                }
            }

            is UpdateBotCallbackQuery -> map().also { it["callback_query"] = callback(u) }
            is UpdateInlineBotCallbackQuery -> map().also { it["callback_query"] = inlineCallback(u) }
            is UpdateBotInlineQuery -> map().also { it["inline_query"] = inlineQuery(u) }
            is UpdateBotInlineSend -> map().also { it["chosen_inline_result"] = chosenInline(u) }
            is UpdateBotShippingQuery -> map().also { it["shipping_query"] = shipping(u) }
            is UpdateBotPrecheckoutQuery -> map().also { it["pre_checkout_query"] = preCheckout(u) }
            is UpdateMessagePoll -> {
                val poll = u.poll ?: return null
                map().also { it["poll"] = poll(poll, u.results) }
            }

            is UpdateMessagePollVote -> map().also { it["poll_answer"] = pollAnswer(u) }
            is UpdateBotChatInviteRequester -> map().also { it["chat_join_request"] = joinRequest(u) }
            is UpdateBotPurchasedPaidMedia -> map().also {
                it["purchased_paid_media"] = map().apply {
                    put("from", user(u.userId))
                    put("paid_media_payload", u.payload)
                }
            }

            is UpdateBotStopped -> map().also { it["my_chat_member"] = botStopped(u) }
            is UpdateChannelParticipant -> map().also {
                it[memberKey(u.userId, u.prevParticipant.isSelf() || u.newParticipant.isSelf())] = chatMemberUpdated(
                    chatPeer = PeerChannel(u.channelId),
                    actorId = u.actorId,
                    date = u.date,
                    oldMember = channelMember(u.userId, u.prevParticipant),
                    newMember = channelMember(u.userId, u.newParticipant),
                    viaChatlist = u.viaChatlist,
                )
            }
            is UpdateChatParticipant -> map().also {
                it[memberKey(u.userId)] = chatMemberUpdated(
                    chatPeer = PeerChat(u.chatId),
                    actorId = u.actorId,
                    date = u.date,
                    oldMember = chatMember(u.userId, u.prevParticipant),
                    newMember = chatMember(u.userId, u.newParticipant),
                )
            }
            is UpdateChatParticipantAdd -> map().also {
                it[memberKey(u.userId)] = chatMemberUpdated(
                    chatPeer = PeerChat(u.chatId),
                    actorId = u.inviterId,
                    date = u.date,
                    oldMember = memberStatus(user(u.userId), "left"),
                    newMember = memberStatus(user(u.userId), "member"),
                )
            }
            is UpdateChatParticipantDelete -> map().also {
                it[memberKey(u.userId)] = chatMemberUpdated(
                    chatPeer = PeerChat(u.chatId),
                    actorId = u.userId,
                    date = 0,
                    oldMember = memberStatus(user(u.userId), "member"),
                    newMember = memberStatus(user(u.userId), "left"),
                )
            }
            is UpdateBotMessageReactions -> map().also { it["message_reaction_count"] = reactionCountUpdated(u) }
            is UpdateBotMessageReaction -> map().also { it["message_reaction"] = reactionUpdated(u) }
            is UpdateBotChatBoost -> map().also { chatBoostUpdate(it, u) }
            is UpdateManagedBot -> map().also {
                it["managed_bot"] = map().apply {
                    put("user", user(u.userId))
                    put("bot", user(u.botId, isBot = true))
                }
            }
            is UpdateBotStarsSubscription -> map().also {
                it["subscription"] = map().apply {
                    put("user", user(u.userId))
                    put("invoice_payload", u.payload.utf8().orEmpty())
                    put(
                        "state",
                        when {
                            u.canceled -> "canceled"
                            u.paymentFailed -> "failed"
                            else -> "active"
                        },
                    )
                }
            }
            is UpdateBotGuestChatQuery -> {
                val replyId = ((u.message as? MessageCtor)?.replyTo ?: (u.message as? MessageService)?.replyTo)
                    .let { it as? MessageReplyHeaderCtor }?.replyToMsgId ?: 0
                val reply = u.referenceMessages?.firstOrNull { ref ->
                    when (ref) {
                        is MessageCtor -> ref.id == replyId
                        is MessageService -> ref.id == replyId
                        else -> false
                    }
                }
                val m = message(u.message, reply) ?: return null
                m["guest_query_id"] = u.queryId.toString()
                map().also { it["guest_message"] = m }
            }
            is UpdateBusinessBotCallbackQuery -> map().also { it["callback_query"] = businessCallback(u) }
            is UpdateBotWebhookJSON -> map().also { it["custom_event"] = u.data.data }
            is UpdateBotWebhookJSONQuery -> map().also {
                it["custom_query"] = map().apply {
                    put("id", u.queryId.toString())
                    put("data", u.data.data)
                    put("timeout", u.timeout)
                }
            }
            is UpdateChatParticipantAdmin -> {
                val admin = u.isAdmin is BoolTrue
                map().also {
                    it[memberKey(u.userId)] = chatMemberUpdated(
                        chatPeer = PeerChat(u.chatId),
                        actorId = 0L,
                        date = 0,
                        oldMember = memberStatus(user(u.userId), if (admin) "member" else "administrator"),
                        newMember = memberStatus(user(u.userId), if (admin) "administrator" else "member"),
                    )
                }
            }
            is UpdateMessageID -> null
            else -> null
        }
    }

    private suspend fun messageKey(raw: Message, edited: Boolean): MutableMap<String, Any?>? {
        val m = message(raw) ?: return null
        val post = when (raw) {
            is MessageCtor -> raw.post
            is MessageService -> raw.post
            else -> false
        }
        val key = when {
            edited && post -> "edited_channel_post"
            edited -> "edited_message"
            post -> "channel_post"
            else -> "message"
        }
        return map().also { it[key] = m }
    }

    suspend fun message(raw: Message, replyTo: Message? = null, withReply: Boolean = true): MutableMap<String, Any?>? = when (raw) {
        is MessageCtor -> regularMessage(raw, replyTo, withReply)
        is MessageService -> serviceMessage(raw, replyTo, withReply)
        else -> null
    }

    private suspend fun regularMessage(m: MessageCtor, replyTo: Message?, withReply: Boolean): MutableMap<String, Any?> {
        val out = map()
        out["message_id"] = m.id
        fillHeader(out, m.peerId, m.fromId, m.date, m.post, m.out, m.replyTo, replyTo, withReply)
        if (m.fromBoostsApplied != 0) out["sender_boost_count"] = m.fromBoostsApplied
        out.opt("author_signature", m.postAuthor)
        if (m.editDate != 0) out["edit_date"] = m.editDate
        if (m.noforwards) out["has_protected_content"] = true
        if (m.offline) out["is_from_offline"] = true
        if (m.groupedId != 0L) out["media_group_id"] = m.groupedId.toString()
        if (m.effect != 0L) out["effect_id"] = m.effect.toString()
        if (m.paidMessageStars != 0L) out["paid_star_count"] = m.paidMessageStars.toInt()
        m.fwdFrom?.let { out.opt("forward_origin", forwardOrigin(it)) }
        if (m.viaBotId != 0L) out["via_bot"] = user(m.viaBotId, isBot = true)
        fillMedia(out, m.media, m.message, m.entities, m.invertMedia)
        if (m.message.isNotEmpty() && m.media == null) {
            out["text"] = m.message
            out.opt("entities", entities(m.entities))
        }
        out.opt("reply_markup", replyMarkup(m.replyMarkup))
        return out
    }

    private suspend fun serviceMessage(m: MessageService, replyTo: Message?, withReply: Boolean): MutableMap<String, Any?> {
        val out = map()
        out["message_id"] = m.id
        fillHeader(out, m.peerId, m.fromId, m.date, m.post, m.out, m.replyTo, replyTo, withReply)
        when (val a = m.action) {
            is MessageActionChatAddUser -> out["new_chat_members"] = a.users.map { user(it) }
            is MessageActionChatJoinedByLink, is MessageActionChatJoinedByRequest ->
                out["new_chat_members"] = listOf(user((m.fromId as? PeerUser)?.userId ?: 0L))
            is MessageActionChatJoinedViaCommunity -> {
                out["new_chat_members"] = listOf(user((m.fromId as? PeerUser)?.userId ?: 0L))
                out["community_chat_joined"] = map().apply {
                    put("community", map().apply { put("id", a.communityId) })
                }
            }
            is MessageActionChatDeleteUser -> out["left_chat_member"] = user(a.userId)
            is MessageActionChatEditTitle -> out["new_chat_title"] = a.title
            is MessageActionChatEditPhoto ->
                (a.photo as? PhotoCtor)?.let { out.opt("new_chat_photo", photoSizes(it)) }
            is MessageActionChatDeletePhoto -> out["delete_chat_photo"] = true
            is MessageActionChatCreate -> out["group_chat_created"] = true
            is MessageActionChannelCreate -> {
                if (m.post) out["channel_chat_created"] = true
                else out["supergroup_chat_created"] = true
            }
            is MessageActionChatMigrateTo ->
                out["migrate_to_chat_id"] = PeerChannel(a.channelId).botApiChatId()
            is MessageActionChannelMigrateFrom ->
                out["migrate_from_chat_id"] = PeerChat(a.chatId).botApiChatId()
            is MessageActionPinMessage -> {
                val id = (m.replyTo as? MessageReplyHeaderCtor)?.replyToMsgId ?: 0
                out["pinned_message"] = map().apply {
                    put("message_id", id)
                    put("date", m.date)
                    put("chat", chat(m.peerId, m.post))
                }
            }
            is MessageActionPaymentSentMe -> out["successful_payment"] = successfulPayment(a)
            is MessageActionPaymentRefunded -> out["refunded_payment"] = map().apply {
                put("currency", a.currency)
                put("total_amount", a.totalAmount)
                opt("invoice_payload", a.payload.utf8())
                put("telegram_payment_charge_id", a.charge.id)
                opt("provider_payment_charge_id", a.charge.providerChargeId)
            }
            is MessageActionWebViewDataSentMe -> out["web_app_data"] = map().apply {
                put("button_text", a.text)
                put("data", a.data)
            }
            is MessageActionBotAllowed -> {
                if (!a.domain.isNullOrEmpty()) out["connected_website"] = a.domain
                else out["write_access_allowed"] = map().apply {
                    if (a.fromRequest) put("from_request", true)
                    if (a.attachMenu) put("from_attachment_menu", true)
                    (a.app as? BotAppCtor)?.let { opt("web_app_name", it.shortName) }
                }
            }
            is MessageActionRequestedPeerSentMe -> requestedPeers(out, a)
            is MessageActionTopicCreate -> out["forum_topic_created"] = map().apply {
                put("name", a.title)
                put("icon_color", a.iconColor)
                if (a.iconEmojiId != 0L) put("icon_custom_emoji_id", a.iconEmojiId.toString())
                if (a.titleMissing) put("is_name_implicit", true)
            }
            is MessageActionTopicEdit -> topicEdit(out, a)
            is MessageActionGroupCallScheduled -> {
                val scheduled = map().apply { put("start_date", a.scheduleDate) }
                out["video_chat_scheduled"] = scheduled
                out["voice_chat_scheduled"] = scheduled
            }
            is MessageActionGroupCall -> {
                if (a.duration != 0) {
                    val ended = map().apply { put("duration", a.duration) }
                    out["video_chat_ended"] = ended
                    out["voice_chat_ended"] = ended
                } else {
                    val started = map()
                    out["video_chat_started"] = started
                    out["voice_chat_started"] = started
                }
            }
            is MessageActionInviteToGroupCall -> {
                val invited = map().apply { put("users", a.users.map { user(it) }) }
                out["video_chat_participants_invited"] = invited
                out["voice_chat_participants_invited"] = invited
            }
            is MessageActionGeoProximityReached -> out["proximity_alert_triggered"] = map().apply {
                put("traveler", senderMap(a.fromId))
                put("watcher", senderMap(a.toId))
                put("distance", a.distance)
            }
            is MessageActionSetMessagesTTL -> out["message_auto_delete_timer_changed"] = map().apply {
                put("message_auto_delete_time", a.period)
            }
            is MessageActionGiveawayLaunch -> out["giveaway_created"] = map().apply {
                if (a.stars != 0L) put("prize_star_count", a.stars.toInt())
            }
            is MessageActionGiveawayResults -> out["giveaway_completed"] = map().apply {
                put("winner_count", a.winnersCount)
                if (a.unclaimedCount != 0) put("unclaimed_prize_count", a.unclaimedCount)
                if (a.stars) put("is_star_giveaway", true)
            }
            is MessageActionBoostApply -> out["boost_added"] = map().apply {
                put("boost_count", a.boosts)
            }
            is MessageActionPaidMessagesPrice -> {
                if (a.broadcastMessagesAllowed) {
                    out["direct_message_price_changed"] = map().apply {
                        put("are_direct_messages_enabled", true)
                        if (a.stars != 0L) put("direct_message_star_count", a.stars.toInt())
                    }
                } else {
                    out["paid_message_price_changed"] = map().apply {
                        put("paid_message_star_count", a.stars.toInt())
                    }
                }
            }
            is MessageActionTodoCompletions -> out["checklist_tasks_done"] = map().apply {
                if (a.completed.isNotEmpty()) put("marked_as_done_task_ids", a.completed.toList())
                if (a.incompleted.isNotEmpty()) put("marked_as_not_done_task_ids", a.incompleted.toList())
            }
            is MessageActionTodoAppendTasks -> out["checklist_tasks_added"] = map().apply {
                put("tasks", a.list.map { checklistTask(it) })
            }
            is MessageActionSuggestedPostApproval -> suggestedPostApproval(out, a)
            is MessageActionSuggestedPostSuccess -> out["suggested_post_paid"] = starsPrice(a.price)
            is MessageActionSuggestedPostRefund -> out["suggested_post_refunded"] = map().apply {
                put("reason", if (a.payerInitiated) "payment_refunded" else "post_deleted")
            }
            is MessageActionNewCreatorPending -> out["chat_owner_left"] = map().apply {
                put("new_owner", user(a.newCreatorId))
            }
            is MessageActionChangeCreator -> out["chat_owner_changed"] = map().apply {
                put("new_owner", user(a.newCreatorId))
            }
            is MessageActionPollAppendAnswer -> out["poll_option_added"] = pollOptionChange(a.answer)
            is MessageActionPollDeleteAnswer -> out["poll_option_deleted"] = pollOptionChange(a.answer)
            is MessageActionManagedBotCreated -> out["managed_bot_created"] = map().apply {
                put("bot", user(a.botId, isBot = true))
            }
            is MessageActionChangeCommunity -> {
                if (a.communityId != 0L) {
                    out["community_chat_added"] = map().apply {
                        put("community", map().apply { put("id", a.communityId) })
                    }
                } else {
                    out["community_chat_removed"] = map()
                }
            }
            else -> Unit
        }
        return out
    }

    private fun successfulPayment(a: MessageActionPaymentSentMe): MutableMap<String, Any?> = map().apply {
        put("currency", a.currency)
        put("total_amount", a.totalAmount)
        put("invoice_payload", a.payload.utf8().orEmpty())
        opt("shipping_option_id", a.shippingOptionId)
        a.info?.let { info ->
            put(
                "order_info",
                map().apply {
                    opt("name", info.name)
                    opt("phone_number", info.phone)
                    opt("email", info.email)
                    info.shippingAddress?.let { put("shipping_address", address(it)) }
                },
            )
        }
        put("telegram_payment_charge_id", a.charge.id)
        put("provider_payment_charge_id", a.charge.providerChargeId)
        if (a.recurringUsed) put("is_recurring", true)
        if (a.recurringInit) put("is_first_recurring", true)
        if (a.subscriptionUntilDate != 0) put("subscription_expiration_date", a.subscriptionUntilDate)
    }

    private fun requestedPeers(out: MutableMap<String, Any?>, a: MessageActionRequestedPeerSentMe) {
        val users = a.peers.filterIsInstance<RequestedPeerUser>()
        val chats = a.peers.mapNotNull { p ->
            when (p) {
                is RequestedPeerChat, is RequestedPeerChannel -> p
                else -> null
            }
        }
        if (users.size == 1 && chats.isEmpty()) {
            out["user_shared"] = map().apply {
                put("user_id", users[0].userId)
                put("request_id", a.buttonId)
            }
        }
        if (users.isNotEmpty()) {
            out["users_shared"] = map().apply {
                put("request_id", a.buttonId)
                put("user_ids", users.map { it.userId })
                put(
                    "users",
                    users.map { u ->
                        map().apply {
                            put("user_id", u.userId)
                            opt("first_name", u.firstName)
                            opt("last_name", u.lastName)
                            opt("username", u.username)
                        }
                    },
                )
            }
        }
        val chat = chats.firstOrNull()
        if (chat != null) {
            out["chat_shared"] = map().apply {
                put("request_id", a.buttonId)
                when (chat) {
                    is RequestedPeerChat -> {
                        put("chat_id", PeerChat(chat.chatId).botApiChatId())
                        opt("title", chat.title)
                    }
                    is RequestedPeerChannel -> {
                        put("chat_id", PeerChannel(chat.channelId).botApiChatId())
                        opt("title", chat.title)
                        opt("username", chat.username)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun topicEdit(out: MutableMap<String, Any?>, a: MessageActionTopicEdit) {
        when {
            a.hidden != null -> {
                val empty = map()
                if (a.hidden is BoolTrue) out["general_forum_topic_hidden"] = empty
                else out["general_forum_topic_unhidden"] = empty
            }
            a.closed != null && a.title == null && a.iconEmojiId == 0L -> {
                val empty = map()
                if (a.closed is BoolTrue) out["forum_topic_closed"] = empty
                else out["forum_topic_reopened"] = empty
            }
            else -> out["forum_topic_edited"] = map().apply {
                opt("name", a.title)
                if (a.iconEmojiId != 0L) put("icon_custom_emoji_id", a.iconEmojiId.toString())
            }
        }
    }

    private fun suggestedPostApproval(out: MutableMap<String, Any?>, a: MessageActionSuggestedPostApproval) {
        when {
            a.rejected -> out["suggested_post_declined"] = map().apply {
                opt("comment", a.rejectComment)
            }
            a.balanceTooLow -> out["suggested_post_approval_failed"] = map().apply {
                a.price?.let { put("price", starsPrice(it)) }
            }
            else -> out["suggested_post_approved"] = map().apply {
                a.price?.let { put("price", starsPrice(it)) }
                if (a.scheduleDate != 0) put("send_date", a.scheduleDate)
            }
        }
    }

    private fun starsPrice(price: StarsAmount): MutableMap<String, Any?> = map().apply {
        when (price) {
            is StarsAmountCtor -> {
                put("currency", "XTR")
                put("amount", price.amount)
            }
            is StarsTonAmount -> {
                put("currency", "TON")
                put("amount", price.amount)
            }
        }
    }

    private fun pollOptionChange(answer: iris.kmtproto.tl.gen.PollAnswer): MutableMap<String, Any?> = map().apply {
        val a = answer as? PollAnswerCtor ?: return@apply
        put("option_persistent_id", a.option.decodeToString())
        put("option_text", a.text.text)
        opt("option_entities", entities(a.text.entities))
    }

    private fun MutableMap<String, Any?>.putSender(peer: Peer, userKey: String, chatKey: String) {
        when (peer) {
            is PeerUser -> put(userKey, user(peer.userId))
            else -> put(chatKey, chat(peer))
        }
    }

    private fun senderMap(peer: Peer): MutableMap<String, Any?> = when (peer) {
        is PeerUser -> user(peer.userId)
        else -> chat(peer)
    }

    private fun checklistTask(item: TodoItem, done: TodoCompletion? = null): MutableMap<String, Any?> = map().apply {
        put("id", item.id)
        put("text", item.title.text)
        opt("text_entities", entities(item.title.entities))
        if (done != null) {
            putSender(done.completedBy, "completed_by_user", "completed_by_chat")
            put("completion_date", done.date)
        }
    }

    private fun checklist(todo: TodoList, completions: List<TodoCompletion>?): MutableMap<String, Any?> = map().apply {
        put("title", todo.title.text)
        opt("title_entities", entities(todo.title.entities))
        put(
            "tasks",
            todo.list.map { item ->
                checklistTask(item, completions?.firstOrNull { it.id == item.id })
            },
        )
        if (todo.othersCanAppend) put("others_can_add_tasks", true)
        if (todo.othersCanComplete) put("others_can_mark_tasks_as_done", true)
    }

    private fun paidMediaItem(m: iris.kmtproto.tl.gen.MessageExtendedMedia): MutableMap<String, Any?> = when (m) {
        is MessageExtendedMediaPreview -> map().apply {
            put("type", "preview")
            if (m.w != 0) put("width", m.w)
            if (m.h != 0) put("height", m.h)
            if (m.videoDuration != 0) put("duration", m.videoDuration)
        }
        is MessageExtendedMediaCtor -> when (val inner = m.media) {
            is MessageMediaPhoto -> map().apply {
                put("type", "photo")
                (inner.photo as? PhotoCtor)?.let { opt("photo", photoSizes(it)) }
            }
            is MessageMediaDocument -> map().apply { put("type", "video") }
            else -> map().apply { put("type", "other") }
        }
        else -> map().apply { put("type", "other") }
    }

    private suspend fun fillHeader(
        out: MutableMap<String, Any?>,
        peer: Peer,
        fromId: Peer?,
        date: Int,
        post: Boolean,
        outgoing: Boolean,
        replyTo: iris.kmtproto.tl.gen.MessageReplyHeader?,
        nestedReply: Message?,
        withReply: Boolean,
    ) {
        out["date"] = date
        out["chat"] = chat(peer, post)
        when {
            post -> out["sender_chat"] = out["chat"]
            fromId is PeerChannel || fromId is PeerChat ->
                out["sender_chat"] = chat(fromId, post = fromId is PeerChannel)
            fromId is PeerUser -> out["from"] = user(fromId.userId)
            outgoing -> {
                val meUser = if (selfId != 0L) storage.getUser(selfId) else null
                when {
                    meUser != null -> out["from"] = user(meUser)
                    else -> {
                        val id = me()
                        if (id != 0L) out["from"] = user(id, isBot = true)
                    }
                }
            }
            !outgoing && peer is PeerUser -> out["from"] = user(peer.userId)
        }
        val rh = replyTo as? MessageReplyHeaderCtor
        if (rh != null) {
            if (rh.forumTopic && rh.replyToTopId != 0) {
                out["message_thread_id"] = rh.replyToTopId
                out["is_topic_message"] = true
            }
            if (withReply) {
                val replyMsg = nestedReply?.let { message(it, withReply = false) }
                    ?: rh.replyToMsgId.takeIf { it != 0 }?.let { id ->
                        val replyPeer = rh.replyToPeerId ?: peer
                        storage.getMessage(replyPeer.botApiChatId(), id)?.let { message(it, withReply = false) }
                    }
                if (replyMsg != null) {
                    out["reply_to_message"] = replyMsg
                } else if (rh.replyToMsgId != 0) {
                    out["reply_to_message"] = replyStub(rh, peer, post, date)
                }
            }
            if (rh.quote && !rh.quoteText.isNullOrEmpty()) {
                out["quote"] = map().apply {
                    put("text", rh.quoteText)
                    opt("entities", entities(rh.quoteEntities))
                    if (rh.quoteOffset != 0) put("position", rh.quoteOffset)
                    put("is_manual", true)
                }
            }
        }
    }

    private fun replyStub(
        rh: MessageReplyHeaderCtor,
        peer: Peer,
        post: Boolean,
        fallbackDate: Int,
    ): MutableMap<String, Any?> {
        val out = map()
        out["message_id"] = rh.replyToMsgId
        val replyPeer = rh.replyToPeerId ?: peer
        val fwd = rh.replyFrom
        out["date"] = fwd?.date?.takeIf { it != 0 } ?: fallbackDate
        out["chat"] = chat(replyPeer, post)
        when (val from = fwd?.fromId) {
            is PeerUser -> out["from"] = user(from.userId)
            is PeerChannel -> out["sender_chat"] = chat(from, post = true)
            is PeerChat -> out["sender_chat"] = chat(from)
            else -> Unit
        }
        out.opt("author_signature", fwd?.postAuthor)
        rh.replyMedia?.let { fillMedia(out, it, caption = "", ents = null, invert = false) }
        return out
    }

    private fun fillMedia(
        out: MutableMap<String, Any?>,
        media: iris.kmtproto.tl.gen.MessageMedia?,
        caption: String,
        ents: List<MessageEntity>?,
        invert: Boolean,
    ) {
        when (media) {
            is MessageMediaPhoto -> {
                val photo = media.photo as? PhotoCtor ?: return
                out.opt("photo", photoSizes(photo))
                if (media.spoiler) out["has_media_spoiler"] = true
                captionOf(out, caption, ents, invert)
            }
            is MessageMediaDocument -> {
                val doc = media.document as? DocumentCtor ?: return
                putDocument(out, doc, media)
                if (media.spoiler) out["has_media_spoiler"] = true
                captionOf(out, caption, ents, invert)
            }
            is MessageMediaContact -> out["contact"] = map().apply {
                put("phone_number", media.phoneNumber)
                put("first_name", media.firstName)
                opt("last_name", media.lastName)
                opt("vcard", media.vcard)
                if (media.userId != 0L) put("user_id", media.userId)
            }
            is MessageMediaGeo -> out.opt("location", location(media.geo))
            is MessageMediaGeoLive -> {
                out.opt("location", location(media.geo)?.apply {
                    put("live_period", media.period)
                    if (media.heading != 0) put("heading", media.heading)
                    if (media.proximityNotificationRadius != 0) {
                        put("proximity_alert_radius", media.proximityNotificationRadius)
                    }
                })
            }
            is MessageMediaVenue -> {
                out.opt("location", location(media.geo))
                out["venue"] = map().apply {
                    opt("location", location(media.geo))
                    put("title", media.title)
                    put("address", media.address)
                    opt("foursquare_id", media.venueId.takeIf { media.provider == "foursquare" })
                    opt("foursquare_type", media.venueType.takeIf { media.provider == "foursquare" })
                    opt("google_place_id", media.venueId.takeIf { media.provider == "gplaces" })
                    opt("google_place_type", media.venueType.takeIf { media.provider == "gplaces" })
                }
            }
            is MessageMediaDice -> out["dice"] = map().apply {
                put("emoji", media.emoticon)
                put("value", media.value)
            }
            is MessageMediaPoll -> out["poll"] = poll(media.poll, media.results)
            is MessageMediaInvoice -> out["invoice"] = map().apply {
                put("title", media.title)
                put("description", media.description)
                put("start_parameter", media.startParam)
                put("currency", media.currency)
                put("total_amount", media.totalAmount)
            }
            is MessageMediaGame -> out["game"] = map().apply {
                put("title", media.game.title)
                put("description", media.game.description)
                (media.game.photo as? PhotoCtor)?.let { opt("photo", photoSizes(it)) }
            }
            is MessageMediaPaidMedia -> out["paid_media"] = map().apply {
                put("star_count", media.starsAmount.toInt())
                put("paid_media", media.extendedMedia.map { paidMediaItem(it) })
            }
            is MessageMediaGiveaway -> out["giveaway"] = map().apply {
                put("chats", media.channels.map { chat(PeerChannel(it), post = true) })
                put("winners_selection_date", media.untilDate)
                put("winner_count", media.quantity)
                if (media.onlyNewSubscribers) put("only_new_members", true)
                if (media.winnersAreVisible) put("has_public_winners", true)
                media.countriesIso2?.takeIf { it.isNotEmpty() }?.let { put("country_codes", it) }
                opt("prize_description", media.prizeDescription)
                if (media.months != 0) put("premium_subscription_month_count", media.months)
                if (media.stars != 0L) put("prize_star_count", media.stars.toInt())
            }
            is MessageMediaGiveawayResults -> out["giveaway_winners"] = map().apply {
                put("chat", chat(PeerChannel(media.channelId), post = true))
                put("giveaway_message_id", media.launchMsgId)
                put("winners_selection_date", media.untilDate)
                put("winner_count", media.winnersCount)
                put("winners", media.winners.map { user(it) })
                if (media.additionalPeersCount != 0) put("additional_chat_count", media.additionalPeersCount)
                if (media.onlyNewSubscribers) put("only_new_members", true)
                if (media.refunded) put("was_refunded", true)
                if (media.months != 0) put("premium_subscription_month_count", media.months)
                if (media.stars != 0L) put("prize_star_count", media.stars.toInt())
                opt("prize_description", media.prizeDescription)
                if (media.unclaimedCount != 0) put("unclaimed_prize_count", media.unclaimedCount)
            }
            is MessageMediaStory -> out["story"] = map().apply {
                put("chat", chat(media.peer))
                put("id", media.id)
            }
            is MessageMediaToDo -> out["checklist"] = checklist(media.todo, media.completions)
            else -> {
                if (caption.isNotEmpty()) {
                    out["text"] = caption
                    out.opt("entities", entities(ents))
                }
            }
        }
    }

    private fun captionOf(
        out: MutableMap<String, Any?>,
        caption: String,
        ents: List<MessageEntity>?,
        invert: Boolean,
    ) {
        if (caption.isEmpty()) return
        out["caption"] = caption
        out.opt("caption_entities", entities(ents))
        if (invert) out["show_caption_above_media"] = true
    }

    private fun putDocument(out: MutableMap<String, Any?>, doc: DocumentCtor, media: MessageMediaDocument) {
        val attrs = doc.attributes
        val fileName = attrs.filterIsInstance<DocumentAttributeFilename>().firstOrNull()?.fileName
        val audio = attrs.filterIsInstance<DocumentAttributeAudio>().firstOrNull()
        val video = attrs.filterIsInstance<DocumentAttributeVideo>().firstOrNull()
        val sticker = attrs.filterIsInstance<DocumentAttributeSticker>().firstOrNull()
        val animated = attrs.any { it is DocumentAttributeAnimated }
        val customEmoji = attrs.any { it is DocumentAttributeCustomEmoji }
        val thumb = photoSizes(doc.thumbs).firstOrNull()
        when {
            media.voice || audio?.voice == true -> out["voice"] = map().apply {
                fileFields(doc)
                opt("duration", audio?.duration)
                opt("mime_type", doc.mimeType)
            }
            media.round || video?.roundMessage == true -> out["video_note"] = map().apply {
                fileFields(doc)
                opt("length", video?.w)
                opt("duration", video?.duration?.toInt())
                opt("thumbnail", thumb)
            }
            sticker != null || customEmoji -> out["sticker"] = map().apply {
                fileFields(doc)
                opt("emoji", sticker?.alt)
                opt("width", video?.w)
                opt("height", video?.h)
                opt("thumbnail", thumb)
                put("is_animated", animated && video == null)
                put("is_video", video != null)
                if (customEmoji) put("type", "custom_emoji") else put("type", if (sticker?.mask == true) "mask" else "regular")
            }
            animated && video != null -> out["animation"] = map().apply {
                fileFields(doc)
                opt("width", video.w)
                opt("height", video.h)
                opt("duration", video.duration.toInt())
                opt("file_name", fileName)
                opt("mime_type", doc.mimeType)
                opt("thumbnail", thumb)
            }
            audio != null -> out["audio"] = map().apply {
                fileFields(doc)
                opt("duration", audio.duration)
                opt("performer", audio.performer)
                opt("title", audio.title)
                opt("file_name", fileName)
                opt("mime_type", doc.mimeType)
                opt("thumbnail", thumb)
            }
            video != null || media.video -> out["video"] = map().apply {
                fileFields(doc)
                opt("width", video?.w)
                opt("height", video?.h)
                opt("duration", video?.duration?.toInt())
                opt("file_name", fileName)
                opt("mime_type", doc.mimeType)
                opt("thumbnail", thumb)
            }
            else -> out["document"] = map().apply {
                fileFields(doc)
                opt("file_name", fileName)
                opt("mime_type", doc.mimeType)
                opt("thumbnail", thumb)
            }
        }
    }

    private fun MutableMap<String, Any?>.fileFields(doc: DocumentCtor) {
        put("file_id", fileId("doc", doc.dcId, doc.id, doc.accessHash))
        put("file_unique_id", uniqueId("doc", doc.id))
        if (doc.size > 0L) put("file_size", doc.size.coerceAtMost(Int.MAX_VALUE.toLong()).toInt())
    }

    private fun photoSizes(photo: PhotoCtor): List<Map<String, Any?>> = photoSizes(photo.sizes, photo)

    private fun photoSizes(sizes: List<PhotoSize>?, photo: PhotoCtor? = null): List<Map<String, Any?>> {
        if (sizes.isNullOrEmpty()) return emptyList()
        val id = photo?.id ?: 0L
        val hash = photo?.accessHash ?: 0L
        val dc = photo?.dcId ?: 0
        return sizes.mapNotNull { size ->
            val (w, h, bytes) = when (size) {
                is PhotoSizeCtor -> Triple(size.w, size.h, size.size)
                is PhotoCachedSize -> Triple(size.w, size.h, size.bytes.size)
                is PhotoSizeProgressive -> Triple(size.w, size.h, size.sizes.maxOrNull() ?: 0)
                else -> return@mapNotNull null
            }
            map().apply {
                put("file_id", fileId("photo", dc, id, hash) + ":${w}x$h")
                put("file_unique_id", uniqueId("photo", id) + ":${w}x$h")
                put("width", w)
                put("height", h)
                if (bytes > 0) put("file_size", bytes)
            }
        }
    }

    private fun photoSizes(sizes: List<PhotoSize>?): List<Map<String, Any?>> = photoSizes(sizes, null)

    fun chat(peer: Peer, post: Boolean = false): MutableMap<String, Any?> = map().apply {
        put("id", peer.botApiChatId())
        when (peer) {
            is PeerUser -> {
                put("type", "private")
                val u = storage.getUser(peer.botApiChatId())
                if (u != null) {
                    put("first_name", u.firstName.orEmpty())
                    opt("last_name", u.lastName)
                    opt("username", usernameOf(u.username, u.usernames))
                }
            }
            is PeerChat -> {
                put("type", "group")
                when (val c = storage.getChat(peer.botApiChatId())) {
                    is ChatCtor -> put("title", c.title)
                    is ChatForbidden -> put("title", c.title)
                    else -> Unit
                }
            }
            is PeerChannel -> {
                val c = storage.getChat(peer.botApiChatId())
                val channel = c as? Channel
                val forbidden = c as? ChannelForbidden
                put(
                    "type",
                    when {
                        post || channel?.broadcast == true || forbidden?.broadcast == true -> "channel"
                        else -> "supergroup"
                    },
                )
                when {
                    channel != null -> {
                        put("title", channel.title)
                        opt("username", usernameOf(channel.username, channel.usernames))
                        if (channel.forum) put("is_forum", true)
                    }
                    forbidden != null -> put("title", forbidden.title)
                }
            }
            else -> put("type", "private")
        }
    }

    fun user(id: Long, isBot: Boolean = false, firstName: String = ""): MutableMap<String, Any?> {
        storage.getUser(id)?.let { return user(it) }
        return map().apply {
            put("id", id)
            put("is_bot", isBot)
            put("first_name", firstName)
        }
    }

    fun user(u: UserCtor): MutableMap<String, Any?> = map().apply {
        put("id", u.id)
        put("is_bot", u.bot)
        put("first_name", u.firstName.orEmpty())
        opt("last_name", u.lastName)
        opt("username", usernameOf(u.username, u.usernames))
        opt("language_code", u.langCode)
        if (u.premium) put("is_premium", true)
        if (u.bot && u.botAttachMenu) put("added_to_attachment_menu", true)
        if (u.bot && u.botHasMainApp) put("has_main_web_app", true)
        if (u.fake) put("is_fake", true)
        if (u.scam) put("is_scam", true)
        if (u.verified) put("is_verified", true)
    }

    private fun usernameOf(primary: String?, extras: List<Username>?): String? {
        if (!primary.isNullOrEmpty()) return primary
        return extras?.firstOrNull { it.active }?.username ?: extras?.firstOrNull()?.username
    }

    private fun entities(list: List<MessageEntity>?): List<Map<String, Any?>> {
        if (list.isNullOrEmpty()) return emptyList()
        return list.mapNotNull { e ->
            val type = when (e) {
                is MessageEntityMention -> "mention"
                is MessageEntityHashtag -> "hashtag"
                is MessageEntityBotCommand -> "bot_command"
                is MessageEntityUrl -> "url"
                is MessageEntityEmail -> "email"
                is MessageEntityBold -> "bold"
                is MessageEntityItalic -> "italic"
                is MessageEntityCode -> "code"
                is MessageEntityPre -> "pre"
                is MessageEntityTextUrl -> "text_link"
                is MessageEntityMentionName -> "text_mention"
                is MessageEntityPhone -> "phone_number"
                is MessageEntityCashtag -> "cashtag"
                is MessageEntityUnderline -> "underline"
                is MessageEntityStrike -> "strikethrough"
                is MessageEntityBlockquote -> "blockquote"
                is MessageEntitySpoiler -> "spoiler"
                is MessageEntityCustomEmoji -> "custom_emoji"
                else -> return@mapNotNull null
            }
            map().apply {
                put("type", type)
                put("offset", e.offset())
                put("length", e.length())
                when (e) {
                    is MessageEntityTextUrl -> put("url", e.url)
                    is MessageEntityPre -> opt("language", e.language)
                    is MessageEntityMentionName -> put("user", user(e.userId))
                    is MessageEntityCustomEmoji -> put("custom_emoji_id", e.documentId.toString())
                    else -> Unit
                }
            }
        }
    }

    private fun MessageEntity.offset(): Int = when (this) {
        is MessageEntityMention -> offset
        is MessageEntityHashtag -> offset
        is MessageEntityBotCommand -> offset
        is MessageEntityUrl -> offset
        is MessageEntityEmail -> offset
        is MessageEntityBold -> offset
        is MessageEntityItalic -> offset
        is MessageEntityCode -> offset
        is MessageEntityPre -> offset
        is MessageEntityTextUrl -> offset
        is MessageEntityMentionName -> offset
        is MessageEntityPhone -> offset
        is MessageEntityCashtag -> offset
        is MessageEntityUnderline -> offset
        is MessageEntityStrike -> offset
        is MessageEntityBlockquote -> offset
        is MessageEntitySpoiler -> offset
        is MessageEntityCustomEmoji -> offset
        else -> 0
    }

    private fun MessageEntity.length(): Int = when (this) {
        is MessageEntityMention -> length
        is MessageEntityHashtag -> length
        is MessageEntityBotCommand -> length
        is MessageEntityUrl -> length
        is MessageEntityEmail -> length
        is MessageEntityBold -> length
        is MessageEntityItalic -> length
        is MessageEntityCode -> length
        is MessageEntityPre -> length
        is MessageEntityTextUrl -> length
        is MessageEntityMentionName -> length
        is MessageEntityPhone -> length
        is MessageEntityCashtag -> length
        is MessageEntityUnderline -> length
        is MessageEntityStrike -> length
        is MessageEntityBlockquote -> length
        is MessageEntitySpoiler -> length
        is MessageEntityCustomEmoji -> length
        else -> 0
    }

    private fun forwardOrigin(h: MessageFwdHeader): MutableMap<String, Any?>? {
        val out = map()
        out["date"] = h.date
        when (val from = h.fromId) {
            is PeerUser -> {
                out["type"] = "user"
                out["sender_user"] = user(from.userId)
            }
            is PeerChannel -> {
                out["type"] = "channel"
                out["chat"] = chat(from, post = true)
                if (h.channelPost != 0) out["message_id"] = h.channelPost
                out.opt("author_signature", h.postAuthor)
            }
            is PeerChat -> {
                out["type"] = "chat"
                out["sender_chat"] = chat(from)
                out.opt("author_signature", h.postAuthor)
            }
            else -> {
                if (h.fromName.isNullOrEmpty()) return null
                out["type"] = "hidden_user"
                out["sender_user_name"] = h.fromName
            }
        }
        return out
    }

    private fun location(geo: GeoPoint?): MutableMap<String, Any?>? {
        val g = geo as? GeoPointCtor ?: return null
        return map().apply {
            put("longitude", g.`long`)
            put("latitude", g.lat)
            if (g.accuracyRadius != 0) put("horizontal_accuracy", g.accuracyRadius.toDouble())
        }
    }

    private fun poll(p: Poll, results: PollResults?): MutableMap<String, Any?> = map().apply {
        put("id", p.id.toString())
        put("question", p.question.text)
        opt("question_entities", entities(p.question.entities))
        put(
            "options",
            p.answers.map { ans ->
                val a = ans as PollAnswerCtor
                val voters = results?.results?.firstOrNull { it.option.contentEquals(a.option) }
                map().apply {
                    put("text", a.text.text)
                    opt("text_entities", entities(a.text.entities))
                    put("voter_count", voters?.voters ?: 0)
                }
            },
        )
        put("total_voter_count", results?.totalVoters ?: 0)
        put("is_closed", p.closed)
        put("is_anonymous", !p.publicVoters)
        put(
            "type",
            when {
                p.quiz -> "quiz"
                else -> "regular"
            },
        )
        put("allows_multiple_answers", p.multipleChoice)
        results?.results?.firstOrNull { it.correct }?.let { c ->
            val idx = p.answers.indexOfFirst { (it as PollAnswerCtor).option.contentEquals(c.option) }
            if (idx >= 0) put("correct_option_id", idx)
        }
        opt("explanation", results?.solution)
        opt("explanation_entities", entities(results?.solutionEntities))
        if (p.closePeriod != 0) put("open_period", p.closePeriod)
        if (p.closeDate != 0) put("close_date", p.closeDate)
    }

    private fun pollAnswer(u: UpdateMessagePollVote): MutableMap<String, Any?> = map().apply {
        put("poll_id", u.pollId.toString())
        when (val p = u.peer) {
            is PeerUser -> put("user", user(p.userId))
            else -> put("voter_chat", chat(p))
        }
        put("option_ids", u.positions.toList())
    }

    private fun callback(u: UpdateBotCallbackQuery): MutableMap<String, Any?> = map().apply {
        put("id", u.queryId.toString())
        put("from", user(u.userId))
        put("chat_instance", u.chatInstance.toString())
        put(
            "message",
            map().apply {
                put("message_id", u.msgId)
                put("date", 0)
                put("chat", chat(u.peer))
            },
        )
        opt("data", u.data.utf8())
        opt("game_short_name", u.gameShortName)
    }

    private fun inlineCallback(u: UpdateInlineBotCallbackQuery): MutableMap<String, Any?> = map().apply {
        put("id", u.queryId.toString())
        put("from", user(u.userId))
        put("chat_instance", u.chatInstance.toString())
        put("inline_message_id", inlineMessageId(u.msgId))
        opt("data", u.data.utf8())
        opt("game_short_name", u.gameShortName)
    }

    private fun inlineQuery(u: UpdateBotInlineQuery): MutableMap<String, Any?> = map().apply {
        put("id", u.queryId.toString())
        put("from", user(u.userId))
        put("query", u.query)
        put("offset", u.offset)
        when (u.peerType) {
            is InlineQueryPeerTypePM, is InlineQueryPeerTypeSameBotPM -> put("chat_type", "private")
            is InlineQueryPeerTypeBotPM -> put("chat_type", "sender")
            is InlineQueryPeerTypeChat -> put("chat_type", "group")
            is InlineQueryPeerTypeMegagroup -> put("chat_type", "supergroup")
            is InlineQueryPeerTypeBroadcast -> put("chat_type", "channel")
            else -> Unit
        }
        opt("location", location(u.geo))
    }

    private fun chosenInline(u: UpdateBotInlineSend): MutableMap<String, Any?> = map().apply {
        put("from", user(u.userId))
        put("query", u.query)
        put("result_id", u.id)
        opt("location", location(u.geo))
        u.msgId?.let { put("inline_message_id", inlineMessageId(it)) }
    }

    private fun shipping(u: UpdateBotShippingQuery): MutableMap<String, Any?> = map().apply {
        put("id", u.queryId.toString())
        put("from", user(u.userId))
        put("invoice_payload", u.payload.utf8())
        put("shipping_address", address(u.shippingAddress))
    }

    private fun preCheckout(u: UpdateBotPrecheckoutQuery): MutableMap<String, Any?> = map().apply {
        put("id", u.queryId.toString())
        put("from", user(u.userId))
        put("currency", u.currency)
        put("total_amount", u.totalAmount)
        put("invoice_payload", u.payload.utf8())
        opt("shipping_option_id", u.shippingOptionId)
        u.info?.let { info ->
            put(
                "order_info",
                map().apply {
                    opt("name", info.name)
                    opt("phone_number", info.phone)
                    opt("email", info.email)
                    info.shippingAddress?.let { put("shipping_address", address(it)) }
                },
            )
        }
    }

    private fun address(a: PostAddress): MutableMap<String, Any?> = map().apply {
        put("country_code", a.countryIso2)
        put("state", a.state)
        put("city", a.city)
        put("street_line1", a.streetLine1)
        put("street_line2", a.streetLine2)
        put("post_code", a.postCode)
    }

    private fun joinRequest(u: UpdateBotChatInviteRequester): MutableMap<String, Any?> = map().apply {
        put("chat", chat(u.peer))
        put("from", user(u.userId))
        put("user_chat_id", u.userId)
        put("date", u.date)
        opt("bio", u.about)
    }

    private fun botStopped(u: UpdateBotStopped): MutableMap<String, Any?> {
        val stopped = u.stopped is BoolTrue
        val from = user(u.userId)
        val chat = chat(PeerUser(u.userId))
        return map().apply {
            put("chat", chat)
            put("from", from)
            put("date", u.date)
            put(
                "old_chat_member",
                map().apply {
                    put("user", from)
                    put("status", if (stopped) "member" else "kicked")
                },
            )
            put(
                "new_chat_member",
                map().apply {
                    put("user", from)
                    put("status", if (stopped) "kicked" else "member")
                    if (stopped) put("until_date", 0)
                },
            )
        }
    }

    private fun memberKey(userId: Long, selfParticipant: Boolean = false): String =
        if (selfParticipant || (me() != 0L && userId == me())) "my_chat_member" else "chat_member"

    private fun ChannelParticipant?.isSelf(): Boolean = when (this) {
        is ChannelParticipantSelf -> true
        is ChannelParticipantAdmin -> self
        else -> false
    }

    private fun memberStatus(u: MutableMap<String, Any?>, status: String): MutableMap<String, Any?> = map().apply {
        put("user", u)
        put("status", status)
    }

    private fun chatMemberUpdated(
        chatPeer: Peer,
        actorId: Long,
        date: Int,
        oldMember: MutableMap<String, Any?>,
        newMember: MutableMap<String, Any?>,
        viaChatlist: Boolean = false,
    ): MutableMap<String, Any?> = map().apply {
        put("chat", chat(chatPeer))
        put("from", user(actorId))
        put("date", date)
        put("old_chat_member", oldMember)
        put("new_chat_member", newMember)
        if (viaChatlist) put("via_chat_folder_invite_link", true)
    }

    private fun channelMember(userId: Long, p: ChannelParticipant?): MutableMap<String, Any?> {
        val u = user(userId)
        return when (p) {
            null -> memberStatus(u, "left")
            is ChannelParticipantCtor -> memberStatus(u, "member").also {
                if (p.subscriptionUntilDate != 0) it["until_date"] = p.subscriptionUntilDate
            }
            is ChannelParticipantSelf -> memberStatus(u, "member").also {
                if (p.subscriptionUntilDate != 0) it["until_date"] = p.subscriptionUntilDate
            }
            is ChannelParticipantCreator -> map().apply {
                put("user", u)
                put("status", "creator")
                put("is_anonymous", p.adminRights.anonymous)
                opt("custom_title", p.rank)
            }
            is ChannelParticipantAdmin -> map().apply {
                put("user", u)
                put("status", "administrator")
                put("can_be_edited", p.canEdit)
                putAdminRights(p.adminRights)
                opt("custom_title", p.rank)
            }
            is ChannelParticipantBanned -> {
                val r = p.bannedRights
                map().apply {
                    put("user", u)
                    if (r.viewMessages) {
                        put("status", "kicked")
                        put("until_date", r.untilDate)
                    } else {
                        put("status", "restricted")
                        put("is_member", !p.left)
                        put("until_date", r.untilDate)
                        putRestricted(r)
                    }
                }
            }
            is ChannelParticipantLeft -> memberStatus(u, "left")
        }
    }

    private fun chatMember(userId: Long, p: ChatParticipant?): MutableMap<String, Any?> {
        val u = user(userId)
        return when (p) {
            null -> memberStatus(u, "left")
            is ChatParticipantCreator -> map().apply {
                put("user", u)
                put("status", "creator")
                put("is_anonymous", false)
                opt("custom_title", p.rank)
            }
            is ChatParticipantAdmin -> map().apply {
                put("user", u)
                put("status", "administrator")
                put("can_be_edited", false)
                opt("custom_title", p.rank)
            }
            is ChatParticipantCtor -> memberStatus(u, "member")
        }
    }

    private fun MutableMap<String, Any?>.putAdminRights(r: ChatAdminRights) {
        put("is_anonymous", r.anonymous)
        put("can_manage_chat", r.other)
        put("can_delete_messages", r.deleteMessages)
        put("can_manage_video_chats", r.manageCall)
        put("can_restrict_members", r.banUsers)
        put("can_promote_members", r.addAdmins)
        put("can_change_info", r.changeInfo)
        put("can_invite_users", r.inviteUsers)
        put("can_post_stories", r.postStories)
        put("can_edit_stories", r.editStories)
        put("can_delete_stories", r.deleteStories)
        if (r.postMessages) put("can_post_messages", true)
        if (r.editMessages) put("can_edit_messages", true)
        if (r.pinMessages) put("can_pin_messages", true)
        if (r.manageTopics) put("can_manage_topics", true)
        if (r.manageDirectMessages) put("can_manage_direct_messages", true)
    }

    private fun MutableMap<String, Any?>.putRestricted(r: ChatBannedRights) {
        put("can_send_messages", !r.sendMessages)
        put("can_send_audios", !r.sendAudios && !r.sendMedia)
        put("can_send_documents", !r.sendDocs && !r.sendMedia)
        put("can_send_photos", !r.sendPhotos && !r.sendMedia)
        put("can_send_videos", !r.sendVideos && !r.sendMedia)
        put("can_send_video_notes", !r.sendRoundvideos && !r.sendMedia)
        put("can_send_voice_notes", !r.sendVoices && !r.sendMedia)
        put("can_send_polls", !r.sendPolls)
        put("can_send_other_messages", !r.sendStickers && !r.sendGifs && !r.sendGames && !r.sendInline)
        put("can_add_web_page_previews", !r.embedLinks)
        put("can_change_info", !r.changeInfo)
        put("can_invite_users", !r.inviteUsers)
        put("can_pin_messages", !r.pinMessages)
        put("can_manage_topics", !r.manageTopics)
    }

    private fun reactionType(r: Reaction): MutableMap<String, Any?>? = when (r) {
        is ReactionEmoji -> map().apply {
            put("type", "emoji")
            put("emoji", r.emoticon)
        }
        is ReactionCustomEmoji -> map().apply {
            put("type", "custom_emoji")
            put("custom_emoji_id", r.documentId.toString())
        }
        is ReactionPaid -> map().apply { put("type", "paid") }
        else -> null
    }

    private fun reactionCountUpdated(u: UpdateBotMessageReactions): MutableMap<String, Any?> = map().apply {
        put("chat", chat(u.peer))
        put("message_id", u.msgId)
        put("date", u.date)
        put(
            "reactions",
            u.reactions.mapNotNull { c ->
                val t = reactionType(c.reaction) ?: return@mapNotNull null
                map().apply {
                    put("type", t)
                    put("total_count", c.count)
                }
            },
        )
    }

    private fun reactionUpdated(u: UpdateBotMessageReaction): MutableMap<String, Any?> = map().apply {
        put("chat", chat(u.peer))
        put("message_id", u.msgId)
        put("date", u.date)
        when (val a = u.actor) {
            is PeerUser -> put("user", user(a.userId))
            else -> put("actor_chat", chat(a))
        }
        put("old_reaction", u.oldReactions.mapNotNull { reactionType(it) })
        put("new_reaction", u.newReactions.mapNotNull { reactionType(it) })
    }

    private fun chatBoostUpdate(out: MutableMap<String, Any?>, u: UpdateBotChatBoost) {
        val b = u.boost
        if (b.expires == 0) {
            out["removed_chat_boost"] = map().apply {
                put("chat", chat(u.peer))
                put("boost_id", b.id)
                put("remove_date", b.date)
                put("source", boostSource(b))
            }
        } else {
            out["chat_boost"] = map().apply {
                put("chat", chat(u.peer))
                put("boost", map().apply {
                    put("boost_id", b.id)
                    put("add_date", b.date)
                    put("expiration_date", b.expires)
                    put("source", boostSource(b))
                })
            }
        }
    }

    private fun boostSource(b: Boost): MutableMap<String, Any?> = map().apply {
        when {
            b.giveaway -> {
                put("source", "giveaway")
                if (b.giveawayMsgId != 0) put("giveaway_message_id", b.giveawayMsgId)
                if (b.stars > 0L) put("prize_star_count", b.stars.toInt())
                if (b.userId != 0L) put("user", user(b.userId))
                else if (b.unclaimed) put("is_unclaimed", true)
            }
            b.gift -> {
                put("source", "gift_code")
                if (b.userId != 0L) put("user", user(b.userId))
            }
            else -> {
                put("source", "premium")
                if (b.userId != 0L) put("user", user(b.userId))
            }
        }
    }

    private suspend fun businessCallback(u: UpdateBusinessBotCallbackQuery): MutableMap<String, Any?> = map().apply {
        put("id", u.queryId.toString())
        put("from", user(u.userId))
        put("chat_instance", u.chatInstance.toString())
        put("business_connection_id", u.connectionId)
        message(u.message, u.replyToMessage)?.let { put("message", it) }
        opt("data", u.data.utf8())
    }

    private fun replyMarkup(raw: ReplyMarkup?): MutableMap<String, Any?>? = when (raw) {
        is ReplyInlineMarkup -> map().apply {
            put(
                "inline_keyboard",
                raw.rows.map { row ->
                    row.buttons.map { btn ->
                        map().apply {
                            put("text", btn.text)
                            when (val t = btn.type) {
                                is InlineButtonTypeUrl -> put("url", t.url)
                                is InlineButtonTypeCallback -> put("callback_data", t.data.utf8())
                                is InlineButtonTypeSwitchInline -> {
                                    if (t.samePeer) put("switch_inline_query_current_chat", t.query)
                                    else put("switch_inline_query", t.query)
                                }
                                is InlineButtonTypeWebView -> put("web_app", map().apply { put("url", t.url) })
                                is InlineButtonTypeUrlAuth -> put("url", t.url)
                                is InlineButtonTypeUserProfile -> put("url", "tg://user?id=${t.userId}")
                                is InlineButtonTypeBuy -> put("pay", true)
                                is InlineButtonTypeGame -> put("callback_game", map())
                                is InlineButtonTypeCopy -> put("copy_text", map().apply { put("text", t.copyText) })
                                else -> Unit
                            }
                        }
                    }
                },
            )
        }
        is ReplyKeyboardMarkup -> map().apply {
            put(
                "keyboard",
                raw.rows.map { row ->
                    row.buttons.map { btn ->
                        map().apply { put("text", btn.text) }
                    }
                },
            )
            if (raw.resize) put("resize_keyboard", true)
            if (raw.singleUse) put("one_time_keyboard", true)
            if (raw.selective) put("selective", true)
            if (raw.persistent) put("is_persistent", true)
            opt("input_field_placeholder", raw.placeholder)
        }
        is ReplyKeyboardForceReply -> map().apply {
            put("force_reply", true)
            if (raw.selective) put("selective", true)
            opt("input_field_placeholder", raw.placeholder)
        }
        else -> null
    }
}

private fun inlineMessageId(id: InputBotInlineMessageID): String = when (id) {
    is InputBotInlineMessageID64 -> "${id.dcId}:${id.ownerId}:${id.id}:${id.accessHash}"
    is InputBotInlineMessageIDCtor -> "${id.dcId}:${id.id}:${id.accessHash}"
    else -> id.toString()
}

private fun fileId(kind: String, dc: Int, id: Long, accessHash: Long): String =
    "$kind:$dc:$id:$accessHash"

private fun uniqueId(kind: String, id: Long): String = "$kind:$id"

private fun ByteArray?.utf8(): String? {
    if (this == null || isEmpty()) return null
    return decodeToString()
}
