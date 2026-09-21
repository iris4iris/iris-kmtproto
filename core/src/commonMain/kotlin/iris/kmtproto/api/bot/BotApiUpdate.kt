package iris.kmtproto.api.bot

import iris.kmtproto.client.botApiChatId
import iris.kmtproto.tl.gen.BoolTrue
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
import iris.kmtproto.tl.gen.MessageActionChannelCreate
import iris.kmtproto.tl.gen.MessageActionChannelMigrateFrom
import iris.kmtproto.tl.gen.MessageActionChatAddUser
import iris.kmtproto.tl.gen.MessageActionChatCreate
import iris.kmtproto.tl.gen.MessageActionChatDeletePhoto
import iris.kmtproto.tl.gen.MessageActionChatDeleteUser
import iris.kmtproto.tl.gen.MessageActionChatEditTitle
import iris.kmtproto.tl.gen.MessageActionChatJoinedByLink
import iris.kmtproto.tl.gen.MessageActionChatJoinedByRequest
import iris.kmtproto.tl.gen.MessageActionChatMigrateTo
import iris.kmtproto.tl.gen.MessageActionPinMessage
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
import iris.kmtproto.tl.gen.MessageFwdHeader
import iris.kmtproto.tl.gen.MessageMediaContact
import iris.kmtproto.tl.gen.MessageMediaDice
import iris.kmtproto.tl.gen.MessageMediaDocument
import iris.kmtproto.tl.gen.MessageMediaGeo
import iris.kmtproto.tl.gen.MessageMediaGeoLive
import iris.kmtproto.tl.gen.MessageMediaPhoto
import iris.kmtproto.tl.gen.MessageMediaPoll
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
import iris.kmtproto.tl.gen.ReplyInlineMarkup
import iris.kmtproto.tl.gen.ReplyKeyboardForceReply
import iris.kmtproto.tl.gen.ReplyKeyboardMarkup
import iris.kmtproto.tl.gen.ReplyMarkup
import iris.kmtproto.tl.gen.TextWithEntities
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateBotBusinessConnect
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateBotChatInviteRequester
import iris.kmtproto.tl.gen.UpdateBotDeleteBusinessMessage
import iris.kmtproto.tl.gen.UpdateBotEditBusinessMessage
import iris.kmtproto.tl.gen.UpdateBotInlineQuery
import iris.kmtproto.tl.gen.UpdateBotInlineSend
import iris.kmtproto.tl.gen.UpdateBotNewBusinessMessage
import iris.kmtproto.tl.gen.UpdateBotPrecheckoutQuery
import iris.kmtproto.tl.gen.UpdateBotPurchasedPaidMedia
import iris.kmtproto.tl.gen.UpdateBotShippingQuery
import iris.kmtproto.tl.gen.UpdateBotStopped
import iris.kmtproto.tl.gen.UpdateEditChannelMessage
import iris.kmtproto.tl.gen.UpdateEditMessage
import iris.kmtproto.tl.gen.UpdateInlineBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateMessagePoll
import iris.kmtproto.tl.gen.UpdateMessagePollVote
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UserCtor

/**
 * One [core.telegram.org/bots/api#update] object as a map, or null if this MTProto
 * update has no Bot API counterpart (typing, pts-only, …).
 */
fun Update.toBotApiMap(
    maps: BotApiMapFactory,
    updateId: Int,
): MutableMap<String, Any?>? {
    val w = BotApiWriter(maps)
    val body = w.updateBody(this) ?: return null
    val out = maps.create()
    out["update_id"] = updateId
    out.putAll(body)
    return out
}

internal class BotApiWriter(val maps: BotApiMapFactory) {
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

    fun updateBody(u: Update): MutableMap<String, Any?>? = when (u) {
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
        else -> null
    }

    private fun messageKey(raw: Message, edited: Boolean): MutableMap<String, Any?>? {
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

    fun message(raw: Message, replyTo: Message? = null): MutableMap<String, Any?>? = when (raw) {
        is MessageCtor -> regularMessage(raw, replyTo)
        is MessageService -> serviceMessage(raw, replyTo)
        else -> null
    }

    private fun regularMessage(m: MessageCtor, replyTo: Message?): MutableMap<String, Any?> {
        val out = map()
        out["message_id"] = m.id
        fillHeader(out, m.peerId, m.fromId, m.date, m.post, m.replyTo, replyTo)
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

    private fun serviceMessage(m: MessageService, replyTo: Message?): MutableMap<String, Any?> {
        val out = map()
        out["message_id"] = m.id
        fillHeader(out, m.peerId, m.fromId, m.date, m.post, m.replyTo, replyTo)
        when (val a = m.action) {
            is MessageActionChatAddUser -> out["new_chat_members"] = a.users.map { user(it) }
            is MessageActionChatJoinedByLink, is MessageActionChatJoinedByRequest ->
                out["new_chat_members"] = listOf(user((m.fromId as? PeerUser)?.userId ?: 0L))
            is MessageActionChatDeleteUser -> out["left_chat_member"] = user(a.userId)
            is MessageActionChatEditTitle -> out["new_chat_title"] = a.title
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
            else -> Unit
        }
        return out
    }

    private fun fillHeader(
        out: MutableMap<String, Any?>,
        peer: Peer,
        fromId: Peer?,
        date: Int,
        post: Boolean,
        replyTo: iris.kmtproto.tl.gen.MessageReplyHeader?,
        nestedReply: Message?,
    ) {
        out["date"] = date
        out["chat"] = chat(peer, post)
        when {
            post -> {
                out["sender_chat"] = out["chat"]
                out.opt("author_signature", null)
            }
            fromId is PeerChannel || fromId is PeerChat -> out["sender_chat"] = chat(fromId, post = fromId is PeerChannel)
            fromId is PeerUser -> out["from"] = user(fromId.userId)
        }
        val rh = replyTo as? MessageReplyHeaderCtor
        if (rh != null) {
            if (rh.forumTopic && rh.replyToTopId != 0) {
                out["message_thread_id"] = rh.replyToTopId
                out["is_topic_message"] = true
            }
            val replyMsg = nestedReply?.let { message(it) }
            if (replyMsg != null) {
                out["reply_to_message"] = replyMsg
            } else if (rh.replyToMsgId != 0) {
                out["reply_to_message"] = map().apply {
                    put("message_id", rh.replyToMsgId)
                    put("date", date)
                    put("chat", rh.replyToPeerId?.let { chat(it, post) } ?: out["chat"])
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
        put(
            "type",
            when (peer) {
                is PeerUser -> "private"
                is PeerChat -> "group"
                is PeerChannel -> if (post) "channel" else "supergroup"
                else -> "private"
            },
        )
    }

    fun user(id: Long, isBot: Boolean = false, firstName: String = ""): MutableMap<String, Any?> = map().apply {
        put("id", id)
        put("is_bot", isBot)
        put("first_name", firstName)
    }

    fun user(u: UserCtor): MutableMap<String, Any?> = map().apply {
        put("id", u.id)
        put("is_bot", u.bot)
        put("first_name", u.firstName)
        opt("last_name", u.lastName)
        opt("username", u.username)
        opt("language_code", u.langCode)
        if (u.premium) put("is_premium", true)
        if (u.bot && u.botAttachMenu) put("added_to_attachment_menu", true)
        if (u.bot && u.botHasMainApp) put("has_main_web_app", true)
        if (u.fake) put("is_fake", true)
        if (u.scam) put("is_scam", true)
        if (u.verified) put("is_verified", true)
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
