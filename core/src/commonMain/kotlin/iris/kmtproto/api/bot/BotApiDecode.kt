package iris.kmtproto.api.bot

import iris.kmtproto.bot.Animation
import iris.kmtproto.bot.Audio
import iris.kmtproto.bot.CallbackGame
import iris.kmtproto.bot.ChatOwnerChanged
import iris.kmtproto.bot.ChatOwnerLeft
import iris.kmtproto.bot.ChatShared
import iris.kmtproto.bot.Checklist
import iris.kmtproto.bot.ChecklistTask
import iris.kmtproto.bot.ChecklistTasksAdded
import iris.kmtproto.bot.ChecklistTasksDone
import iris.kmtproto.bot.Community
import iris.kmtproto.bot.CommunityChatAdded
import iris.kmtproto.bot.CommunityChatJoined
import iris.kmtproto.bot.CommunityChatRemoved
import iris.kmtproto.bot.Contact
import iris.kmtproto.bot.CopyTextButton
import iris.kmtproto.bot.Dice
import iris.kmtproto.bot.DirectMessagePriceChanged
import iris.kmtproto.bot.Document as BotDocument
import iris.kmtproto.bot.ForumTopicClosed
import iris.kmtproto.bot.ForumTopicEdited
import iris.kmtproto.bot.ForumTopicReopened
import iris.kmtproto.bot.Game
import iris.kmtproto.bot.GeneralForumTopicHidden
import iris.kmtproto.bot.GeneralForumTopicUnhidden
import iris.kmtproto.bot.Giveaway
import iris.kmtproto.bot.GiveawayCompleted
import iris.kmtproto.bot.GiveawayWinners
import iris.kmtproto.bot.InlineKeyboardButton
import iris.kmtproto.bot.InlineKeyboardMarkup
import iris.kmtproto.bot.Invoice
import iris.kmtproto.bot.ManagedBotCreated
import iris.kmtproto.bot.PaidMedia
import iris.kmtproto.bot.PaidMediaInfo
import iris.kmtproto.bot.PaidMediaPhoto
import iris.kmtproto.bot.PaidMediaPreview
import iris.kmtproto.bot.PaidMediaVideo
import iris.kmtproto.bot.PaidMessagePriceChanged
import iris.kmtproto.bot.PollOptionAdded
import iris.kmtproto.bot.PollOptionDeleted
import iris.kmtproto.bot.ProximityAlertTriggered
import iris.kmtproto.bot.SharedUser
import iris.kmtproto.bot.Sticker
import iris.kmtproto.bot.Story
import iris.kmtproto.bot.SuggestedPostApprovalFailed
import iris.kmtproto.bot.SuggestedPostApproved
import iris.kmtproto.bot.SuggestedPostDeclined
import iris.kmtproto.bot.SuggestedPostPaid
import iris.kmtproto.bot.SuggestedPostPrice
import iris.kmtproto.bot.SuggestedPostRefunded
import iris.kmtproto.bot.UsersShared
import iris.kmtproto.bot.Venue
import iris.kmtproto.bot.Video
import iris.kmtproto.bot.VideoNote
import iris.kmtproto.bot.Voice
import iris.kmtproto.bot.WebAppInfo
import iris.kmtproto.bot.BotSubscriptionUpdated
import iris.kmtproto.bot.BusinessBotRights
import iris.kmtproto.bot.BusinessConnection
import iris.kmtproto.bot.BusinessMessagesDeleted
import iris.kmtproto.bot.CallbackQuery
import iris.kmtproto.bot.Chat as BotChat
import iris.kmtproto.bot.ChatBoost
import iris.kmtproto.bot.ChatBoostAdded
import iris.kmtproto.bot.ChatBoostRemoved
import iris.kmtproto.bot.ChatBoostSource
import iris.kmtproto.bot.ChatBoostSourceGiftCode
import iris.kmtproto.bot.ChatBoostSourceGiveaway
import iris.kmtproto.bot.ChatBoostSourcePremium
import iris.kmtproto.bot.ChatBoostUpdated
import iris.kmtproto.bot.ChatJoinRequest
import iris.kmtproto.bot.ChatMember
import iris.kmtproto.bot.ChatMemberAdministrator
import iris.kmtproto.bot.ChatMemberBanned
import iris.kmtproto.bot.ChatMemberLeft
import iris.kmtproto.bot.ChatMemberMember
import iris.kmtproto.bot.ChatMemberOwner
import iris.kmtproto.bot.ChatMemberRestricted
import iris.kmtproto.bot.ChatMemberUpdated
import iris.kmtproto.bot.ChosenInlineResult
import iris.kmtproto.bot.ForumTopicCreated
import iris.kmtproto.bot.GiveawayCreated
import iris.kmtproto.bot.InlineQuery
import iris.kmtproto.bot.Location
import iris.kmtproto.bot.ManagedBotUpdated
import iris.kmtproto.bot.Message as BotMessage
import iris.kmtproto.bot.MessageAutoDeleteTimerChanged
import iris.kmtproto.bot.MessageEntity as BotEntity
import iris.kmtproto.bot.MessageOrigin
import iris.kmtproto.bot.MessageOriginChannel
import iris.kmtproto.bot.MessageOriginChat
import iris.kmtproto.bot.MessageOriginHiddenUser
import iris.kmtproto.bot.MessageOriginUser
import iris.kmtproto.bot.MessageReactionCountUpdated
import iris.kmtproto.bot.MessageReactionUpdated
import iris.kmtproto.bot.OrderInfo
import iris.kmtproto.bot.PaidMediaPurchased
import iris.kmtproto.bot.PhotoSize as BotPhotoSize
import iris.kmtproto.bot.Poll as BotPoll
import iris.kmtproto.bot.PollAnswer as BotPollAnswer
import iris.kmtproto.bot.PollOption
import iris.kmtproto.bot.PreCheckoutQuery
import iris.kmtproto.bot.ReactionCount as BotReactionCount
import iris.kmtproto.bot.ReactionType
import iris.kmtproto.bot.ReactionTypeCustomEmoji
import iris.kmtproto.bot.ReactionTypeEmoji
import iris.kmtproto.bot.ReactionTypePaid
import iris.kmtproto.bot.RefundedPayment
import iris.kmtproto.bot.ShippingAddress
import iris.kmtproto.bot.ShippingQuery
import iris.kmtproto.bot.SuccessfulPayment
import iris.kmtproto.bot.TextQuote
import iris.kmtproto.bot.Update as BotUpdate
import iris.kmtproto.bot.User as BotUser
import iris.kmtproto.bot.VideoChatEnded
import iris.kmtproto.bot.VideoChatParticipantsInvited
import iris.kmtproto.bot.VideoChatScheduled
import iris.kmtproto.bot.VideoChatStarted
import iris.kmtproto.bot.WebAppData
import iris.kmtproto.bot.WriteAccessAllowed
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.tl.gen.BoolTrue
import iris.kmtproto.tl.gen.Channel
import iris.kmtproto.tl.gen.ChannelForbidden
import iris.kmtproto.tl.gen.ChannelParticipant
import iris.kmtproto.tl.gen.ChannelParticipantAdmin
import iris.kmtproto.tl.gen.ChannelParticipantBanned
import iris.kmtproto.tl.gen.ChannelParticipantCreator
import iris.kmtproto.tl.gen.ChannelParticipantCtor
import iris.kmtproto.tl.gen.ChannelParticipantLeft
import iris.kmtproto.tl.gen.ChannelParticipantSelf
import iris.kmtproto.tl.gen.ChatAdminRights
import iris.kmtproto.tl.gen.ChatBannedRights
import iris.kmtproto.tl.gen.ChatCtor
import iris.kmtproto.tl.gen.ChatForbidden
import iris.kmtproto.tl.gen.ChatParticipant
import iris.kmtproto.tl.gen.ChatParticipantAdmin
import iris.kmtproto.tl.gen.ChatParticipantCreator
import iris.kmtproto.tl.gen.ChatParticipantCtor
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
import iris.kmtproto.tl.gen.Message as TlMessage
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
import iris.kmtproto.tl.gen.MessageExtendedMedia
import iris.kmtproto.tl.gen.MessageExtendedMediaCtor
import iris.kmtproto.tl.gen.MessageExtendedMediaPreview
import iris.kmtproto.tl.gen.MessageFwdHeader
import iris.kmtproto.tl.gen.MessageMedia
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
import iris.kmtproto.tl.gen.PollAnswer
import iris.kmtproto.tl.gen.PollAnswerCtor
import iris.kmtproto.tl.gen.PollResults
import iris.kmtproto.tl.gen.ReplyInlineMarkup
import iris.kmtproto.tl.gen.ReplyMarkup
import iris.kmtproto.tl.gen.RequestedPeerChannel
import iris.kmtproto.tl.gen.RequestedPeerChat
import iris.kmtproto.tl.gen.RequestedPeerUser
import iris.kmtproto.tl.gen.StarsAmount
import iris.kmtproto.tl.gen.StarsAmountCtor
import iris.kmtproto.tl.gen.StarsTonAmount
import iris.kmtproto.tl.gen.TodoCompletion
import iris.kmtproto.tl.gen.TodoItem
import iris.kmtproto.tl.gen.TodoList
import iris.kmtproto.tl.gen.PostAddress
import iris.kmtproto.tl.gen.Reaction
import iris.kmtproto.tl.gen.ReactionCustomEmoji
import iris.kmtproto.tl.gen.ReactionEmoji
import iris.kmtproto.tl.gen.ReactionPaid
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
import iris.kmtproto.tl.gen.UpdateMessagePoll
import iris.kmtproto.tl.gen.UpdateMessagePollVote
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UserCtor
import iris.kmtproto.tl.gen.Username

/**
 * MTProto [Update] → Bot API [BotUpdate], without an intermediate map.
 */
suspend fun Update.toBotUpdate(w: BotApiWriter): BotUpdate? {
    val id = w.nextUpdateId()
    return when (this) {
        is UpdateNewMessage -> w.keyedMessage(id, message, edited = false)
        is UpdateNewChannelMessage -> w.keyedMessage(id, message, edited = false)
        is UpdateEditMessage -> w.keyedMessage(id, message, edited = true)
        is UpdateEditChannelMessage -> w.keyedMessage(id, message, edited = true)
        is UpdateBotNewBusinessMessage -> w.botMessage(message, replyToMessage, businessConnectionId = connectionId)?.let {
            BotUpdate(updateId = id, businessMessage = it)
        }
        is UpdateBotEditBusinessMessage -> w.botMessage(message, replyToMessage, businessConnectionId = connectionId)?.let {
            BotUpdate(updateId = id, editedBusinessMessage = it)
        }
        is UpdateBotDeleteBusinessMessage -> BotUpdate(
            updateId = id,
            deletedBusinessMessages = BusinessMessagesDeleted(
                businessConnectionId = connectionId,
                chat = w.botChat(peer),
                messageIds = messages.map { it.toInt() },
            ),
        )
        is UpdateBotBusinessConnect -> BotUpdate(
            updateId = id,
            businessConnection = BusinessConnection(
                id = connection.connectionId,
                user = w.botUser(connection.userId),
                userChatId = connection.userId,
                date = connection.date.toInt(),
                rights = BusinessBotRights(canReply = !connection.disabled),
                isEnabled = !connection.disabled,
            ),
        )
        is UpdateBotCallbackQuery -> BotUpdate(updateId = id, callbackQuery = w.callback(this))
        is UpdateInlineBotCallbackQuery -> BotUpdate(updateId = id, callbackQuery = w.inlineCallback(this))
        is UpdateBotInlineQuery -> BotUpdate(updateId = id, inlineQuery = w.inlineQuery(this))
        is UpdateBotInlineSend -> BotUpdate(updateId = id, chosenInlineResult = w.chosenInline(this))
        is UpdateBotShippingQuery -> BotUpdate(updateId = id, shippingQuery = w.shipping(this))
        is UpdateBotPrecheckoutQuery -> BotUpdate(updateId = id, preCheckoutQuery = w.preCheckout(this))
        is UpdateMessagePoll -> poll?.let { BotUpdate(updateId = id, poll = w.botPoll(it, results)) }
        is UpdateMessagePollVote -> BotUpdate(updateId = id, pollAnswer = w.pollAnswer(this))
        is UpdateBotChatInviteRequester -> BotUpdate(updateId = id, chatJoinRequest = w.joinRequest(this))
        is UpdateBotPurchasedPaidMedia -> BotUpdate(
            updateId = id,
            purchasedPaidMedia = PaidMediaPurchased(from = w.botUser(userId), paidMediaPayload = payload),
        )
        is UpdateBotStopped -> BotUpdate(updateId = id, myChatMember = w.botStopped(this))
        is UpdateChannelParticipant -> {
            val member = w.memberUpdate(
                chatPeer = PeerChannel(channelId),
                actorId = actorId,
                date = date,
                oldMember = w.channelMember(userId, prevParticipant),
                newMember = w.channelMember(userId, newParticipant),
                viaChatlist = viaChatlist,
            )
            if (w.isMyMember(userId, prevParticipant.isSelf() || newParticipant.isSelf())) {
                BotUpdate(updateId = id, myChatMember = member)
            } else {
                BotUpdate(updateId = id, chatMember = member)
            }
        }
        is UpdateChatParticipant -> w.placedMember(
            id, userId, w.memberUpdate(
                chatPeer = PeerChat(chatId),
                actorId = actorId,
                date = date,
                oldMember = w.basicMember(userId, prevParticipant),
                newMember = w.basicMember(userId, newParticipant),
            ),
        )
        is UpdateChatParticipantAdd -> w.placedMember(
            id, userId, w.memberUpdate(
                chatPeer = PeerChat(chatId),
                actorId = inviterId,
                date = date,
                oldMember = w.botUser(userId).asStatus("left"),
                newMember = w.botUser(userId).asStatus("member"),
            ),
        )
        is UpdateChatParticipantDelete -> w.placedMember(
            id, userId, w.memberUpdate(
                chatPeer = PeerChat(chatId),
                actorId = userId,
                date = 0,
                oldMember = w.botUser(userId).asStatus("member"),
                newMember = w.botUser(userId).asStatus("left"),
            ),
        )
        is UpdateChatParticipantAdmin -> {
            val admin = isAdmin is BoolTrue
            w.placedMember(
                id, userId, w.memberUpdate(
                    chatPeer = PeerChat(chatId),
                    actorId = 0L,
                    date = 0,
                    oldMember = w.botUser(userId).asStatus(if (admin) "member" else "administrator"),
                    newMember = w.botUser(userId).asStatus(if (admin) "administrator" else "member"),
                ),
            )
        }
        is UpdateBotMessageReactions -> BotUpdate(updateId = id, messageReactionCount = w.reactionCount(this))
        is UpdateBotMessageReaction -> BotUpdate(updateId = id, messageReaction = w.reaction(this))
        is UpdateBotChatBoost -> w.chatBoost(id, this)
        is UpdateManagedBot -> BotUpdate(
            updateId = id,
            managedBot = ManagedBotUpdated(user = w.botUser(userId), bot = w.botUser(botId, isBot = true)),
        )
        is UpdateBotStarsSubscription -> BotUpdate(
            updateId = id,
            subscription = BotSubscriptionUpdated(
                user = w.botUser(userId),
                invoicePayload = payload.utf8().orEmpty(),
                state = when {
                    canceled -> "canceled"
                    paymentFailed -> "failed"
                    else -> "active"
                },
            ),
        )
        is UpdateBotGuestChatQuery -> {
            val replyId = ((message as? MessageCtor)?.replyTo ?: (message as? MessageService)?.replyTo)
                .let { it as? MessageReplyHeaderCtor }?.replyToMsgId ?: 0
            val reply = referenceMessages?.firstOrNull { ref ->
                when (ref) {
                    is MessageCtor -> ref.id == replyId
                    is MessageService -> ref.id == replyId
                    else -> false
                }
            }
            w.botMessage(message, reply, guestQueryId = queryId.toString())?.let {
                BotUpdate(updateId = id, guestMessage = it)
            }
        }
        is UpdateBusinessBotCallbackQuery -> BotUpdate(updateId = id, callbackQuery = w.businessCallback(this))
        else -> null
    }
}

private suspend fun BotApiWriter.keyedMessage(updateId: Int, raw: TlMessage, edited: Boolean): BotUpdate? {
    val m = botMessage(raw) ?: return null
    val post = when (raw) {
        is MessageCtor -> raw.post
        is MessageService -> raw.post
        else -> false
    }
    return when {
        edited && post -> BotUpdate(updateId = updateId, editedChannelPost = m)
        edited -> BotUpdate(updateId = updateId, editedMessage = m)
        post -> BotUpdate(updateId = updateId, channelPost = m)
        else -> BotUpdate(updateId = updateId, message = m)
    }
}

private fun BotApiWriter.placedMember(updateId: Int, userId: Long, member: ChatMemberUpdated): BotUpdate =
    if (isMyMember(userId)) BotUpdate(updateId = updateId, myChatMember = member)
    else BotUpdate(updateId = updateId, chatMember = member)

private fun BotApiWriter.isMyMember(userId: Long, selfParticipant: Boolean = false): Boolean =
    selfParticipant || (selfId != 0L && userId == selfId)

private suspend fun BotApiWriter.botMessage(
    raw: TlMessage,
    replyTo: TlMessage? = null,
    withReply: Boolean = true,
    businessConnectionId: String? = null,
    guestQueryId: String? = null,
): BotMessage? = when (raw) {
    is MessageCtor -> regular(raw, replyTo, withReply, businessConnectionId, guestQueryId)
    is MessageService -> service(raw, replyTo, withReply, businessConnectionId, guestQueryId)
    else -> null
}

private class Head(
    val date: Int,
    val chat: BotChat,
    val from: BotUser?,
    val senderChat: BotChat?,
    val messageThreadId: Int?,
    val isTopicMessage: Boolean?,
    val replyToMessage: BotMessage?,
    val quote: TextQuote?,
)

private suspend fun BotApiWriter.head(
    peer: Peer,
    fromId: Peer?,
    date: Int,
    post: Boolean,
    outgoing: Boolean,
    replyTo: iris.kmtproto.tl.gen.MessageReplyHeader?,
    nestedReply: TlMessage?,
    withReply: Boolean,
): Head {
    val chat = botChat(peer, post)
    val senderChat = when {
        post -> chat
        fromId is PeerChannel || fromId is PeerChat -> botChat(fromId, post = fromId is PeerChannel)
        else -> null
    }
    val from = when {
        post || senderChat != null -> null
        fromId is PeerUser -> botUser(fromId.userId)
        outgoing -> {
            val meUser = if (selfId != 0L) storage.getUser(selfId) else null
            when {
                meUser != null -> botUser(meUser)
                selfId != 0L -> botUser(selfId, isBot = true)
                else -> null
            }
        }
        peer is PeerUser -> botUser(peer.userId)
        else -> null
    }
    val rh = replyTo as? MessageReplyHeaderCtor
    var thread: Int? = null
    var topic: Boolean? = null
    var reply: BotMessage? = null
    var quote: TextQuote? = null
    if (rh != null) {
        if (rh.forumTopic && rh.replyToTopId != 0) {
            thread = rh.replyToTopId
            topic = true
        }
        if (withReply) {
            reply = nestedReply?.let { botMessage(it, withReply = false) }
                ?: rh.replyToMsgId.takeIf { it != 0 }?.let { id ->
                    val replyPeer = rh.replyToPeerId ?: peer
                    storage.getMessage(replyPeer.botApiChatId(), id)?.let { botMessage(it, withReply = false) }
                }
                ?: if (rh.replyToMsgId != 0) replyStub(rh, peer, post, date) else null
        }
        if (rh.quote) {
            val quoted = rh.quoteText
            if (!quoted.isNullOrEmpty()) {
                quote = TextQuote(
                    text = quoted,
                    entities = botEntities(rh.quoteEntities).ifEmpty { null },
                    position = rh.quoteOffset.toInt(),
                    isManual = true,
                )
            }
        }
    }
    return Head(date, chat, from, senderChat, thread, topic, reply, quote)
}

private fun BotApiWriter.replyStub(rh: MessageReplyHeaderCtor, peer: Peer, post: Boolean, fallbackDate: Int): BotMessage {
    val replyPeer = rh.replyToPeerId ?: peer
    val fwd = rh.replyFrom
    val from = (fwd?.fromId as? PeerUser)?.let { botUser(it.userId) }
    val sender = when (val id = fwd?.fromId) {
        is PeerChannel -> botChat(id, post = true)
        is PeerChat -> botChat(id)
        else -> null
    }
    val media = readMedia(rh.replyMedia, "", null, false)
    return BotMessage(
        messageId = rh.replyToMsgId.toInt(),
        date = (fwd?.date?.takeIf { it != 0 } ?: fallbackDate).toInt(),
        chat = botChat(replyPeer, post),
        from = from,
        senderChat = sender,
        authorSignature = fwd?.postAuthor?.takeIf { it.isNotEmpty() },
        text = media.text,
        entities = media.textEntities,
        animation = media.animation,
        audio = media.audio,
        document = media.document,
        paidMedia = media.paidMedia,
        photo = media.photo,
        sticker = media.sticker,
        story = media.story,
        video = media.video,
        videoNote = media.videoNote,
        voice = media.voice,
        caption = media.caption,
        captionEntities = media.captionEntities,
        showCaptionAboveMedia = media.showCaptionAboveMedia,
        hasMediaSpoiler = media.hasMediaSpoiler,
        checklist = media.checklist,
        contact = media.contact,
        dice = media.dice,
        game = media.game,
        poll = media.poll,
        venue = media.venue,
        location = media.location,
        giveaway = media.giveaway,
        giveawayWinners = media.giveawayWinners,
        invoice = media.invoice,
    )
}

private suspend fun BotApiWriter.regular(
    m: MessageCtor,
    replyTo: TlMessage?,
    withReply: Boolean,
    businessConnectionId: String?,
    guestQueryId: String?,
): BotMessage {
    val h = head(m.peerId, m.fromId, m.date, m.post, m.out, m.replyTo, replyTo, withReply)
    val media = readMedia(m.media, m.message, m.entities, m.invertMedia)
    val plain = m.media == null && m.message.isNotEmpty()
    return BotMessage(
        messageId = m.id.toInt(),
        messageThreadId = h.messageThreadId,
        from = h.from,
        senderChat = h.senderChat,
        senderBoostCount = m.fromBoostsApplied.takeIf { it != 0 }?.toInt(),
        date = h.date,
        guestQueryId = guestQueryId,
        businessConnectionId = businessConnectionId,
        chat = h.chat,
        forwardOrigin = m.fwdFrom?.let { origin(it) },
        isTopicMessage = h.isTopicMessage,
        replyToMessage = h.replyToMessage,
        quote = h.quote,
        viaBot = m.viaBotId.takeIf { it != 0L }?.let { botUser(it, isBot = true) },
        editDate = m.editDate.takeIf { it != 0 }?.toInt(),
        hasProtectedContent = m.noforwards.takeIf { it },
        isFromOffline = m.offline.takeIf { it },
        mediaGroupId = m.groupedId.takeIf { it != 0L }?.toString(),
        authorSignature = m.postAuthor?.takeIf { it.isNotEmpty() },
        paidStarCount = m.paidMessageStars.takeIf { it != 0L }?.toInt(),
        text = if (plain) m.message else media.text,
        entities = if (plain) botEntities(m.entities).ifEmpty { null } else media.textEntities,
        effectId = m.effect.takeIf { it != 0L }?.toString(),
        animation = media.animation,
        audio = media.audio,
        document = media.document,
        paidMedia = media.paidMedia,
        photo = media.photo,
        sticker = media.sticker,
        story = media.story,
        video = media.video,
        videoNote = media.videoNote,
        voice = media.voice,
        caption = media.caption,
        captionEntities = media.captionEntities,
        showCaptionAboveMedia = media.showCaptionAboveMedia,
        hasMediaSpoiler = media.hasMediaSpoiler,
        checklist = media.checklist,
        contact = media.contact,
        dice = media.dice,
        game = media.game,
        poll = media.poll,
        venue = media.venue,
        location = media.location,
        giveaway = media.giveaway,
        giveawayWinners = media.giveawayWinners,
        invoice = media.invoice,
        replyMarkup = inlineMarkup(m.replyMarkup),
    )
}

private suspend fun BotApiWriter.service(
    m: MessageService,
    replyTo: TlMessage?,
    withReply: Boolean,
    businessConnectionId: String?,
    guestQueryId: String?,
): BotMessage {
    val h = head(m.peerId, m.fromId, m.date, m.post, m.out, m.replyTo, replyTo, withReply)
    val s = ServiceFields()
    when (val a = m.action) {
        is MessageActionChatAddUser -> s.newMembers = a.users.map { botUser(it) }
        is MessageActionChatJoinedByLink, is MessageActionChatJoinedByRequest ->
            s.newMembers = listOf(botUser((m.fromId as? PeerUser)?.userId ?: 0L))
        is MessageActionChatJoinedViaCommunity -> {
            s.newMembers = listOf(botUser((m.fromId as? PeerUser)?.userId ?: 0L))
            s.communityJoined = CommunityChatJoined(community = Community(id = a.communityId))
        }
        is MessageActionChatDeleteUser -> s.left = botUser(a.userId)
        is MessageActionChatEditTitle -> s.title = a.title
        is MessageActionChatEditPhoto -> s.newPhoto = (a.photo as? PhotoCtor)?.let { photoSizes(it) }
        is MessageActionChatDeletePhoto -> s.deletePhoto = true
        is MessageActionChatCreate -> s.groupCreated = true
        is MessageActionChannelCreate -> if (m.post) s.channelCreated = true else s.superCreated = true
        is MessageActionChatMigrateTo -> s.migrateTo = PeerChannel(a.channelId).botApiChatId()
        is MessageActionChannelMigrateFrom -> s.migrateFrom = PeerChat(a.chatId).botApiChatId()
        is MessageActionPinMessage -> {
            val id = (m.replyTo as? MessageReplyHeaderCtor)?.replyToMsgId ?: 0
            s.pinned = BotMessage(messageId = id.toInt(), date = m.date.toInt(), chat = botChat(m.peerId, m.post))
        }
        is MessageActionPaymentSentMe -> s.paid = SuccessfulPayment(
            currency = a.currency,
            totalAmount = a.totalAmount.toInt(),
            invoicePayload = a.payload.utf8().orEmpty(),
            subscriptionExpirationDate = a.subscriptionUntilDate.takeIf { it != 0 }?.toInt(),
            isRecurring = a.recurringUsed.takeIf { it },
            isFirstRecurring = a.recurringInit.takeIf { it },
            shippingOptionId = a.shippingOptionId?.takeIf { it.isNotEmpty() },
            orderInfo = a.info?.let { info ->
                OrderInfo(
                    name = info.name?.takeIf { it.isNotEmpty() },
                    phoneNumber = info.phone?.takeIf { it.isNotEmpty() },
                    email = info.email?.takeIf { it.isNotEmpty() },
                    shippingAddress = info.shippingAddress?.let { address(it) },
                )
            },
            telegramPaymentChargeId = a.charge.id,
            providerPaymentChargeId = a.charge.providerChargeId,
        )
        is MessageActionPaymentRefunded -> s.refunded = RefundedPayment(
            currency = a.currency,
            totalAmount = a.totalAmount.toInt(),
            invoicePayload = a.payload.utf8().orEmpty(),
            telegramPaymentChargeId = a.charge.id,
            providerPaymentChargeId = a.charge.providerChargeId.takeIf { it.isNotEmpty() },
        )
        is MessageActionWebViewDataSentMe -> s.web = WebAppData(data = a.data, buttonText = a.text)
        is MessageActionBotAllowed -> if (!a.domain.isNullOrEmpty()) s.site = a.domain else s.write = WriteAccessAllowed(
            fromRequest = a.fromRequest.takeIf { it },
            fromAttachmentMenu = a.attachMenu.takeIf { it },
            webAppName = (a.app as? iris.kmtproto.tl.gen.BotAppCtor)?.shortName?.takeIf { it.isNotEmpty() },
        )
        is MessageActionRequestedPeerSentMe -> sharedPeers(s, a)
        is MessageActionTopicCreate -> s.topic = ForumTopicCreated(
            name = a.title,
            iconColor = a.iconColor.toInt(),
            iconCustomEmojiId = a.iconEmojiId.takeIf { it != 0L }?.toString(),
            isNameImplicit = a.titleMissing.takeIf { it },
        )
        is MessageActionTopicEdit -> topicEdit(s, a)
        is MessageActionGroupCallScheduled -> s.scheduled = VideoChatScheduled(startDate = a.scheduleDate.toInt())
        is MessageActionGroupCall -> if (a.duration != 0) s.ended = VideoChatEnded(a.duration.toInt()) else s.started = VideoChatStarted
        is MessageActionInviteToGroupCall -> s.invited = VideoChatParticipantsInvited(a.users.map { botUser(it) })
        is MessageActionGeoProximityReached -> s.proximity = ProximityAlertTriggered(
            traveler = (a.fromId as? PeerUser)?.let { botUser(it.userId) },
            watcher = (a.toId as? PeerUser)?.let { botUser(it.userId) },
            distance = a.distance.toInt(),
        )
        is MessageActionSetMessagesTTL -> s.ttl = MessageAutoDeleteTimerChanged(a.period.toInt())
        is MessageActionGiveawayLaunch -> s.giveawayCreated = GiveawayCreated(prizeStarCount = a.stars.takeIf { it != 0L }?.toInt())
        is MessageActionGiveawayResults -> s.giveawayCompleted = GiveawayCompleted(
            winnerCount = a.winnersCount.toInt(),
            unclaimedPrizeCount = a.unclaimedCount.takeIf { it != 0 }?.toInt(),
            isStarGiveaway = a.stars.takeIf { it },
        )
        is MessageActionBoostApply -> s.boost = ChatBoostAdded(a.boosts.toInt())
        is MessageActionPaidMessagesPrice -> if (a.broadcastMessagesAllowed) {
            s.directPrice = DirectMessagePriceChanged(
                areDirectMessagesEnabled = true,
                directMessageStarCount = a.stars.takeIf { it != 0L }?.toInt(),
            )
        } else {
            s.paidPrice = PaidMessagePriceChanged(paidMessageStarCount = a.stars.toInt())
        }
        is MessageActionTodoCompletions -> s.tasksDone = ChecklistTasksDone(
            markedAsDoneTaskIds = a.completed.takeIf { it.isNotEmpty() }?.map { it.toInt() },
            markedAsNotDoneTaskIds = a.incompleted.takeIf { it.isNotEmpty() }?.map { it.toInt() },
        )
        is MessageActionTodoAppendTasks -> s.tasksAdded = ChecklistTasksAdded(tasks = a.list.map { checklistTask(it) })
        is MessageActionSuggestedPostApproval -> suggestedApproval(s, a)
        is MessageActionSuggestedPostSuccess -> {
            val price = starsPrice(a.price)
            s.suggestedPaid = SuggestedPostPaid(currency = price.currency, amount = price.amount)
        }
        is MessageActionSuggestedPostRefund -> s.suggestedRefunded = SuggestedPostRefunded(
            reason = if (a.payerInitiated) "payment_refunded" else "post_deleted",
        )
        is MessageActionNewCreatorPending -> s.ownerLeft = ChatOwnerLeft(newOwner = botUser(a.newCreatorId))
        is MessageActionChangeCreator -> s.ownerChanged = ChatOwnerChanged(newOwner = botUser(a.newCreatorId))
        is MessageActionPollAppendAnswer -> pollChange(a.answer)?.let { (id, text, ents) ->
            s.pollAdded = PollOptionAdded(optionPersistentId = id, optionText = text, optionTextEntities = ents)
        }
        is MessageActionPollDeleteAnswer -> pollChange(a.answer)?.let { (id, text, ents) ->
            s.pollDeleted = PollOptionDeleted(optionPersistentId = id, optionText = text, optionTextEntities = ents)
        }
        is MessageActionManagedBotCreated -> s.managedBot = ManagedBotCreated(bot = botUser(a.botId, isBot = true))
        is MessageActionChangeCommunity -> if (a.communityId != 0L) {
            s.communityAdded = CommunityChatAdded(community = Community(id = a.communityId))
        } else {
            s.communityRemoved = CommunityChatRemoved
        }
        else -> Unit
    }
    return BotMessage(
        messageId = m.id.toInt(),
        messageThreadId = h.messageThreadId,
        from = h.from,
        senderChat = h.senderChat,
        date = h.date,
        guestQueryId = guestQueryId,
        businessConnectionId = businessConnectionId,
        chat = h.chat,
        isTopicMessage = h.isTopicMessage,
        replyToMessage = h.replyToMessage,
        quote = h.quote,
        newChatMembers = s.newMembers,
        leftChatMember = s.left,
        chatOwnerLeft = s.ownerLeft,
        chatOwnerChanged = s.ownerChanged,
        newChatTitle = s.title,
        newChatPhoto = s.newPhoto,
        deleteChatPhoto = s.deletePhoto,
        groupChatCreated = s.groupCreated,
        supergroupChatCreated = s.superCreated,
        channelChatCreated = s.channelCreated,
        messageAutoDeleteTimerChanged = s.ttl,
        migrateToChatId = s.migrateTo,
        migrateFromChatId = s.migrateFrom,
        pinnedMessage = s.pinned,
        successfulPayment = s.paid,
        refundedPayment = s.refunded,
        usersShared = s.usersShared,
        chatShared = s.chatShared,
        connectedWebsite = s.site,
        writeAccessAllowed = s.write,
        proximityAlertTriggered = s.proximity,
        boostAdded = s.boost,
        checklistTasksDone = s.tasksDone,
        checklistTasksAdded = s.tasksAdded,
        communityChatAdded = s.communityAdded,
        communityChatJoined = s.communityJoined,
        communityChatRemoved = s.communityRemoved,
        directMessagePriceChanged = s.directPrice,
        forumTopicCreated = s.topic,
        forumTopicEdited = s.topicEdited,
        forumTopicClosed = s.topicClosed,
        forumTopicReopened = s.topicReopened,
        generalForumTopicHidden = s.generalHidden,
        generalForumTopicUnhidden = s.generalUnhidden,
        giveawayCreated = s.giveawayCreated,
        giveawayCompleted = s.giveawayCompleted,
        managedBotCreated = s.managedBot,
        paidMessagePriceChanged = s.paidPrice,
        pollOptionAdded = s.pollAdded,
        pollOptionDeleted = s.pollDeleted,
        suggestedPostApproved = s.suggestedApproved,
        suggestedPostApprovalFailed = s.suggestedFailed,
        suggestedPostDeclined = s.suggestedDeclined,
        suggestedPostPaid = s.suggestedPaid,
        suggestedPostRefunded = s.suggestedRefunded,
        videoChatScheduled = s.scheduled,
        videoChatStarted = s.started,
        videoChatEnded = s.ended,
        videoChatParticipantsInvited = s.invited,
        webAppData = s.web,
    )
}

private class ServiceFields {
    var newMembers: List<BotUser>? = null
    var left: BotUser? = null
    var title: String? = null
    var newPhoto: List<BotPhotoSize>? = null
    var deletePhoto: Boolean? = null
    var groupCreated: Boolean? = null
    var superCreated: Boolean? = null
    var channelCreated: Boolean? = null
    var ttl: MessageAutoDeleteTimerChanged? = null
    var migrateTo: Long? = null
    var migrateFrom: Long? = null
    var pinned: BotMessage? = null
    var paid: SuccessfulPayment? = null
    var refunded: RefundedPayment? = null
    var usersShared: UsersShared? = null
    var chatShared: ChatShared? = null
    var site: String? = null
    var write: WriteAccessAllowed? = null
    var proximity: ProximityAlertTriggered? = null
    var boost: ChatBoostAdded? = null
    var tasksDone: ChecklistTasksDone? = null
    var tasksAdded: ChecklistTasksAdded? = null
    var communityAdded: CommunityChatAdded? = null
    var communityJoined: CommunityChatJoined? = null
    var communityRemoved: CommunityChatRemoved? = null
    var directPrice: DirectMessagePriceChanged? = null
    var topic: ForumTopicCreated? = null
    var topicEdited: ForumTopicEdited? = null
    var topicClosed: ForumTopicClosed? = null
    var topicReopened: ForumTopicReopened? = null
    var generalHidden: GeneralForumTopicHidden? = null
    var generalUnhidden: GeneralForumTopicUnhidden? = null
    var giveawayCreated: GiveawayCreated? = null
    var giveawayCompleted: GiveawayCompleted? = null
    var managedBot: ManagedBotCreated? = null
    var paidPrice: PaidMessagePriceChanged? = null
    var pollAdded: PollOptionAdded? = null
    var pollDeleted: PollOptionDeleted? = null
    var suggestedApproved: SuggestedPostApproved? = null
    var suggestedFailed: SuggestedPostApprovalFailed? = null
    var suggestedDeclined: SuggestedPostDeclined? = null
    var suggestedPaid: SuggestedPostPaid? = null
    var suggestedRefunded: SuggestedPostRefunded? = null
    var scheduled: VideoChatScheduled? = null
    var started: VideoChatStarted? = null
    var ended: VideoChatEnded? = null
    var invited: VideoChatParticipantsInvited? = null
    var web: WebAppData? = null
    var ownerLeft: ChatOwnerLeft? = null
    var ownerChanged: ChatOwnerChanged? = null
}

private fun BotApiWriter.botChat(peer: Peer, post: Boolean = false): BotChat {
    val id = peer.botApiChatId()
    return when (peer) {
        is PeerUser -> {
            val u = storage.getUser(id)
            BotChat(
                id = id,
                type = "private",
                firstName = u?.firstName.orEmpty().ifEmpty { null },
                lastName = u?.lastName?.takeIf { it.isNotEmpty() },
                username = u?.let { usernameOf(it.username, it.usernames) },
            )
        }
        is PeerChat -> {
            val title = when (val c = storage.getChat(id)) {
                is ChatCtor -> c.title
                is ChatForbidden -> c.title
                else -> null
            }
            BotChat(id = id, type = "group", title = title)
        }
        is PeerChannel -> {
            val c = storage.getChat(id)
            val channel = c as? Channel
            val forbidden = c as? ChannelForbidden
            BotChat(
                id = id,
                type = when {
                    post || channel?.broadcast == true || forbidden?.broadcast == true -> "channel"
                    else -> "supergroup"
                },
                title = channel?.title ?: forbidden?.title,
                username = channel?.let { usernameOf(it.username, it.usernames) },
                isForum = channel?.forum?.takeIf { it },
            )
        }
        else -> BotChat(id = id, type = "private")
    }
}

private fun BotApiWriter.botUser(id: Long, isBot: Boolean = false, firstName: String = ""): BotUser =
    storage.getUser(id)?.let { botUser(it) } ?: BotUser(id = id, isBot = isBot, firstName = firstName)

private fun botUser(u: UserCtor): BotUser = BotUser(
    id = u.id,
    isBot = u.bot,
    firstName = u.firstName.orEmpty(),
    lastName = u.lastName?.takeIf { it.isNotEmpty() },
    username = usernameOf(u.username, u.usernames),
    languageCode = u.langCode?.takeIf { it.isNotEmpty() },
    isPremium = u.premium.takeIf { it },
    addedToAttachmentMenu = (u.bot && u.botAttachMenu).takeIf { it },
    hasMainWebApp = (u.bot && u.botHasMainApp).takeIf { it },
)

private fun usernameOf(primary: String?, extras: List<Username>?): String? {
    if (!primary.isNullOrEmpty()) return primary
    return extras?.firstOrNull { it.active }?.username ?: extras?.firstOrNull()?.username
}

private fun BotApiWriter.botEntities(list: List<MessageEntity>?): List<BotEntity> {
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
        BotEntity(
            type = type,
            offset = e.entityOffset().toInt(),
            length = e.entityLength().toInt(),
            url = (e as? MessageEntityTextUrl)?.url,
            user = (e as? MessageEntityMentionName)?.let { botUser(it.userId) },
            language = (e as? MessageEntityPre)?.language?.takeIf { it.isNotEmpty() },
            customEmojiId = (e as? MessageEntityCustomEmoji)?.documentId?.toString(),
        )
    }
}

private fun MessageEntity.entityOffset(): Int = when (this) {
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

private fun MessageEntity.entityLength(): Int = when (this) {
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

private fun BotApiWriter.origin(h: MessageFwdHeader): MessageOrigin? {
    val date = h.date
    return when (val from = h.fromId) {
        is PeerUser -> MessageOriginUser(type = "user", date = date, senderUser = botUser(from.userId))
        is PeerChannel -> MessageOriginChannel(
            type = "channel",
            date = date,
            chat = botChat(from, post = true),
            messageId = h.channelPost.toInt(),
            authorSignature = h.postAuthor?.takeIf { it.isNotEmpty() },
        )
        is PeerChat -> MessageOriginChat(
            type = "chat",
            date = date,
            senderChat = botChat(from),
            authorSignature = h.postAuthor?.takeIf { it.isNotEmpty() },
        )
        else -> {
            val name = h.fromName
            if (name.isNullOrEmpty()) null
            else MessageOriginHiddenUser(type = "hidden_user", date = date, senderUserName = name)
        }
    }
}

private class MediaFields {
    var animation: Animation? = null
    var audio: Audio? = null
    var document: BotDocument? = null
    var paidMedia: PaidMediaInfo? = null
    var photo: List<BotPhotoSize>? = null
    var sticker: Sticker? = null
    var story: Story? = null
    var video: Video? = null
    var videoNote: VideoNote? = null
    var voice: Voice? = null
    var caption: String? = null
    var captionEntities: List<BotEntity>? = null
    var showCaptionAboveMedia: Boolean? = null
    var hasMediaSpoiler: Boolean? = null
    var checklist: Checklist? = null
    var contact: Contact? = null
    var dice: Dice? = null
    var game: Game? = null
    var poll: BotPoll? = null
    var venue: Venue? = null
    var location: Location? = null
    var giveaway: Giveaway? = null
    var giveawayWinners: GiveawayWinners? = null
    var invoice: Invoice? = null
    var text: String? = null
    var textEntities: List<BotEntity>? = null
}

private fun BotApiWriter.readMedia(
    media: MessageMedia?,
    caption: String,
    ents: List<MessageEntity>?,
    invert: Boolean,
): MediaFields {
    val out = MediaFields()
    when (media) {
        is MessageMediaPhoto -> {
            val photo = media.photo as? PhotoCtor ?: return out
            out.photo = photoSizes(photo).ifEmpty { null }
            if (media.spoiler) out.hasMediaSpoiler = true
            applyCaption(out, caption, ents, invert)
        }
        is MessageMediaDocument -> {
            val doc = media.document as? DocumentCtor ?: return out
            putDocument(out, doc, media)
            if (media.spoiler) out.hasMediaSpoiler = true
            applyCaption(out, caption, ents, invert)
        }
        is MessageMediaContact -> out.contact = Contact(
            phoneNumber = media.phoneNumber,
            firstName = media.firstName,
            lastName = media.lastName.takeIf { it.isNotEmpty() },
            userId = media.userId.takeIf { it != 0L },
            vcard = media.vcard.takeIf { it.isNotEmpty() },
        )
        is MessageMediaGeo -> out.location = point(media.geo)
        is MessageMediaGeoLive -> out.location = point(
            media.geo,
            livePeriod = media.period,
            heading = media.heading,
            proximity = media.proximityNotificationRadius,
        )
        is MessageMediaVenue -> {
            val loc = point(media.geo)
            out.location = loc
            out.venue = Venue(
                location = loc,
                title = media.title,
                address = media.address,
                foursquareId = media.venueId.takeIf { media.provider == "foursquare" && it.isNotEmpty() },
                foursquareType = media.venueType.takeIf { media.provider == "foursquare" && it.isNotEmpty() },
                googlePlaceId = media.venueId.takeIf { media.provider == "gplaces" && it.isNotEmpty() },
                googlePlaceType = media.venueType.takeIf { media.provider == "gplaces" && it.isNotEmpty() },
            )
        }
        is MessageMediaDice -> out.dice = Dice(emoji = media.emoticon, value = media.value.toInt())
        is MessageMediaPoll -> out.poll = botPoll(media.poll, media.results)
        is MessageMediaInvoice -> out.invoice = Invoice(
            title = media.title,
            description = media.description,
            startParameter = media.startParam,
            currency = media.currency,
            totalAmount = media.totalAmount.toInt(),
        )
        is MessageMediaGame -> out.game = Game(
            title = media.game.title,
            description = media.game.description,
            photo = (media.game.photo as? PhotoCtor)?.let { photoSizes(it) }.orEmpty(),
        )
        is MessageMediaPaidMedia -> out.paidMedia = PaidMediaInfo(
            starCount = media.starsAmount.toInt(),
            paidMedia = media.extendedMedia.map { paidItem(it) },
        )
        is MessageMediaGiveaway -> out.giveaway = Giveaway(
            chats = media.channels.map { botChat(PeerChannel(it), post = true) },
            winnersSelectionDate = media.untilDate.toInt(),
            winnerCount = media.quantity.toInt(),
            onlyNewMembers = media.onlyNewSubscribers.takeIf { it },
            hasPublicWinners = media.winnersAreVisible.takeIf { it },
            prizeDescription = media.prizeDescription?.takeIf { it.isNotEmpty() },
            countryCodes = media.countriesIso2?.takeIf { it.isNotEmpty() },
            prizeStarCount = media.stars.takeIf { it != 0L }?.toInt(),
            premiumSubscriptionMonthCount = media.months.takeIf { it != 0 }?.toInt(),
        )
        is MessageMediaGiveawayResults -> out.giveawayWinners = GiveawayWinners(
            chat = botChat(PeerChannel(media.channelId), post = true),
            giveawayMessageId = media.launchMsgId.toInt(),
            winnersSelectionDate = media.untilDate.toInt(),
            winnerCount = media.winnersCount.toInt(),
            winners = media.winners.map { botUser(it) },
            additionalChatCount = media.additionalPeersCount.takeIf { it != 0 }?.toInt(),
            prizeStarCount = media.stars.takeIf { it != 0L }?.toInt(),
            premiumSubscriptionMonthCount = media.months.takeIf { it != 0 }?.toInt(),
            unclaimedPrizeCount = media.unclaimedCount.takeIf { it != 0 }?.toInt(),
            onlyNewMembers = media.onlyNewSubscribers.takeIf { it },
            wasRefunded = media.refunded.takeIf { it },
            prizeDescription = media.prizeDescription?.takeIf { it.isNotEmpty() },
        )
        is MessageMediaStory -> out.story = Story(chat = botChat(media.peer), id = media.id.toInt())
        is MessageMediaToDo -> out.checklist = checklist(media.todo, media.completions)
        else -> if (caption.isNotEmpty()) {
            out.text = caption
            out.textEntities = botEntities(ents).ifEmpty { null }
        }
    }
    return out
}

private fun BotApiWriter.applyCaption(out: MediaFields, caption: String, ents: List<MessageEntity>?, invert: Boolean) {
    if (caption.isEmpty()) return
    out.caption = caption
    out.captionEntities = botEntities(ents).ifEmpty { null }
    if (invert) out.showCaptionAboveMedia = true
}

private fun BotApiWriter.putDocument(out: MediaFields, doc: DocumentCtor, media: MessageMediaDocument) {
    val attrs = doc.attributes
    val fileName = attrs.filterIsInstance<DocumentAttributeFilename>().firstOrNull()?.fileName?.takeIf { it.isNotEmpty() }
    val audio = attrs.filterIsInstance<DocumentAttributeAudio>().firstOrNull()
    val video = attrs.filterIsInstance<DocumentAttributeVideo>().firstOrNull()
    val sticker = attrs.filterIsInstance<DocumentAttributeSticker>().firstOrNull()
    val animated = attrs.any { it is DocumentAttributeAnimated }
    val customEmoji = attrs.any { it is DocumentAttributeCustomEmoji }
    val thumb = docThumb(doc)
    val fileId = "doc:${doc.dcId}:${doc.id}:${doc.accessHash}"
    val unique = "doc:${doc.id}"
    val size = doc.size.takeIf { it > 0L }
    val mime = doc.mimeType.takeIf { it.isNotEmpty() }
    when {
        media.voice || audio?.voice == true -> out.voice = Voice(
            fileId = fileId,
            fileUniqueId = unique,
            duration = (audio?.duration ?: 0).toInt(),
            mimeType = mime,
            fileSize = size,
        )
        media.round || video?.roundMessage == true -> out.videoNote = VideoNote(
            fileId = fileId,
            fileUniqueId = unique,
            length = (video?.w ?: 0).toInt(),
            duration = video?.duration?.toInt() ?: 0,
            thumbnail = thumb,
            fileSize = size?.toInt(),
        )
        sticker != null || customEmoji -> out.sticker = Sticker(
            fileId = fileId,
            fileUniqueId = unique,
            type = when {
                customEmoji -> "custom_emoji"
                sticker?.mask == true -> "mask"
                else -> "regular"
            },
            width = (video?.w ?: 0).toInt(),
            height = (video?.h ?: 0).toInt(),
            isAnimated = animated && video == null,
            isVideo = video != null,
            thumbnail = thumb,
            emoji = sticker?.alt?.takeIf { it.isNotEmpty() },
            fileSize = size?.toInt(),
        )
        animated && video != null -> out.animation = Animation(
            fileId = fileId,
            fileUniqueId = unique,
            width = video.w.toInt(),
            height = video.h.toInt(),
            duration = video.duration.toInt(),
            thumbnail = thumb,
            fileName = fileName,
            mimeType = mime,
            fileSize = size,
        )
        audio != null -> out.audio = Audio(
            fileId = fileId,
            fileUniqueId = unique,
            duration = audio.duration.toInt(),
            performer = audio.performer?.takeIf { it.isNotEmpty() },
            title = audio.title?.takeIf { it.isNotEmpty() },
            fileName = fileName,
            mimeType = mime,
            fileSize = size,
            thumbnail = thumb,
        )
        video != null || media.video -> out.video = Video(
            fileId = fileId,
            fileUniqueId = unique,
            width = (video?.w ?: 0).toInt(),
            height = (video?.h ?: 0).toInt(),
            duration = video?.duration?.toInt() ?: 0,
            thumbnail = thumb,
            fileName = fileName,
            mimeType = mime,
            fileSize = size,
        )
        else -> out.document = BotDocument(
            fileId = fileId,
            fileUniqueId = unique,
            thumbnail = thumb,
            fileName = fileName,
            mimeType = mime,
            fileSize = size,
        )
    }
}

private fun docThumb(doc: DocumentCtor): BotPhotoSize? = thumbSizes(doc.thumbs).firstOrNull()

private fun thumbSizes(sizes: List<PhotoSize>?): List<BotPhotoSize> {
    if (sizes.isNullOrEmpty()) return emptyList()
    return sizes.mapNotNull { size ->
        val (w, h, bytes) = when (size) {
            is PhotoSizeCtor -> Triple(size.w, size.h, size.size)
            is PhotoCachedSize -> Triple(size.w, size.h, size.bytes.size)
            is PhotoSizeProgressive -> Triple(size.w, size.h, size.sizes.maxOrNull() ?: 0)
            else -> return@mapNotNull null
        }
        BotPhotoSize(
            fileId = "photo:0:0:0:${w}x$h",
            fileUniqueId = "photo:0:${w}x$h",
            width = w.toInt(),
            height = h.toInt(),
            fileSize = bytes.takeIf { it > 0 }?.toInt(),
        )
    }
}

private fun point(geo: GeoPoint?, livePeriod: Int = 0, heading: Int = 0, proximity: Int = 0): Location? {
    val g = geo as? GeoPointCtor ?: return null
    return Location(
        latitude = g.lat,
        longitude = g.`long`,
        horizontalAccuracy = g.accuracyRadius.takeIf { it != 0 }?.toDouble(),
        livePeriod = livePeriod.takeIf { it != 0 }?.toInt(),
        heading = heading.takeIf { it != 0 }?.toInt(),
        proximityAlertRadius = proximity.takeIf { it != 0 }?.toInt(),
    )
}

private fun BotApiWriter.paidItem(m: MessageExtendedMedia): PaidMedia = when (m) {
    is MessageExtendedMediaPreview -> PaidMediaPreview(
        type = "preview",
        width = m.w.takeIf { it != 0 }?.toInt(),
        height = m.h.takeIf { it != 0 }?.toInt(),
        duration = m.videoDuration.takeIf { it != 0 }?.toInt(),
    )
    is MessageExtendedMediaCtor -> when (val inner = m.media) {
        is MessageMediaPhoto -> PaidMediaPhoto(
            type = "photo",
            photo = (inner.photo as? PhotoCtor)?.let { photoSizes(it) }.orEmpty(),
        )
        is MessageMediaDocument -> {
            val fields = MediaFields()
            (inner.document as? DocumentCtor)?.let { putDocument(fields, it, inner) }
            PaidMediaVideo(type = "video", video = fields.video)
        }
        else -> PaidMediaPreview(type = "other")
    }
    else -> PaidMediaPreview(type = "other")
}

private fun BotApiWriter.checklist(todo: TodoList, completions: List<TodoCompletion>?): Checklist = Checklist(
    title = todo.title.text,
    titleEntities = botEntities(todo.title.entities).ifEmpty { null },
    tasks = todo.list.map { checklistTask(it, completions?.firstOrNull { done -> done.id == it.id }) },
    othersCanAddTasks = todo.othersCanAppend.takeIf { it },
    othersCanMarkTasksAsDone = todo.othersCanComplete.takeIf { it },
)

private fun BotApiWriter.checklistTask(item: TodoItem, done: TodoCompletion? = null): ChecklistTask {
    val by = done?.completedBy
    return ChecklistTask(
        id = item.id.toInt(),
        text = item.title.text,
        textEntities = botEntities(item.title.entities).ifEmpty { null },
        completedByUser = (by as? PeerUser)?.let { botUser(it.userId) },
        completedByChat = when (by) {
            is PeerChannel, is PeerChat -> botChat(by)
            else -> null
        },
        completionDate = done?.date?.takeIf { it != 0 }?.toInt(),
    )
}

private fun BotApiWriter.sharedPeers(out: ServiceFields, a: MessageActionRequestedPeerSentMe) {
    val users = a.peers.filterIsInstance<RequestedPeerUser>()
    val chat = a.peers.firstOrNull { it is RequestedPeerChat || it is RequestedPeerChannel }
    if (users.isNotEmpty()) {
        out.usersShared = UsersShared(
            requestId = a.buttonId.toInt(),
            users = users.map { u ->
                SharedUser(
                    userId = u.userId,
                    firstName = u.firstName?.takeIf { it.isNotEmpty() },
                    lastName = u.lastName?.takeIf { it.isNotEmpty() },
                    username = u.username?.takeIf { it.isNotEmpty() },
                )
            },
        )
    }
    when (chat) {
        is RequestedPeerChat -> out.chatShared = ChatShared(
            requestId = a.buttonId.toInt(),
            chatId = PeerChat(chat.chatId).botApiChatId(),
            title = chat.title?.takeIf { it.isNotEmpty() },
        )
        is RequestedPeerChannel -> out.chatShared = ChatShared(
            requestId = a.buttonId.toInt(),
            chatId = PeerChannel(chat.channelId).botApiChatId(),
            title = chat.title?.takeIf { it.isNotEmpty() },
            username = chat.username?.takeIf { it.isNotEmpty() },
        )
        else -> Unit
    }
}

private fun topicEdit(out: ServiceFields, a: MessageActionTopicEdit) {
    when {
        a.hidden != null -> if (a.hidden is BoolTrue) out.generalHidden = GeneralForumTopicHidden
        else out.generalUnhidden = GeneralForumTopicUnhidden
        a.closed != null && a.title == null && a.iconEmojiId == 0L ->
            if (a.closed is BoolTrue) out.topicClosed = ForumTopicClosed else out.topicReopened = ForumTopicReopened
        else -> out.topicEdited = ForumTopicEdited(
            name = a.title?.takeIf { it.isNotEmpty() },
            iconCustomEmojiId = a.iconEmojiId.takeIf { it != 0L }?.toString(),
        )
    }
}

private fun BotApiWriter.suggestedApproval(out: ServiceFields, a: MessageActionSuggestedPostApproval) {
    when {
        a.rejected -> out.suggestedDeclined = SuggestedPostDeclined(comment = a.rejectComment?.takeIf { it.isNotEmpty() })
        a.balanceTooLow -> out.suggestedFailed = SuggestedPostApprovalFailed(price = a.price?.let { starsPrice(it) })
        else -> out.suggestedApproved = SuggestedPostApproved(
            price = a.price?.let { starsPrice(it) },
            sendDate = a.scheduleDate.toInt(),
        )
    }
}

private fun starsPrice(price: StarsAmount): SuggestedPostPrice = when (price) {
    is StarsAmountCtor -> SuggestedPostPrice(currency = "XTR", amount = price.amount.toInt())
    is StarsTonAmount -> SuggestedPostPrice(currency = "TON", amount = price.amount.toInt())
    else -> SuggestedPostPrice()
}

private fun BotApiWriter.pollChange(answer: PollAnswer): Triple<String, String, List<BotEntity>?>? {
    val a = answer as? PollAnswerCtor ?: return null
    return Triple(a.option.decodeToString(), a.text.text, botEntities(a.text.entities).ifEmpty { null })
}

private fun BotApiWriter.inlineMarkup(raw: ReplyMarkup?): InlineKeyboardMarkup? {
    val markup = raw as? ReplyInlineMarkup ?: return null
    return InlineKeyboardMarkup(
        inlineKeyboard = markup.rows.map { row ->
            row.buttons.map { btn ->
                var url: String? = null
                var callbackData: String? = null
                var switchInline: String? = null
                var switchCurrent: String? = null
                var webApp: WebAppInfo? = null
                var copy: CopyTextButton? = null
                var game: CallbackGame? = null
                var pay: Boolean? = null
                when (val t = btn.type) {
                    is InlineButtonTypeUrl -> url = t.url
                    is InlineButtonTypeCallback -> callbackData = t.data.utf8()
                    is InlineButtonTypeSwitchInline -> if (t.samePeer) switchCurrent = t.query else switchInline = t.query
                    is InlineButtonTypeWebView -> webApp = WebAppInfo(url = t.url)
                    is InlineButtonTypeUrlAuth -> url = t.url
                    is InlineButtonTypeUserProfile -> url = "tg://user?id=${t.userId}"
                    is InlineButtonTypeBuy -> pay = true
                    is InlineButtonTypeGame -> game = CallbackGame
                    is InlineButtonTypeCopy -> copy = CopyTextButton(text = t.copyText)
                    else -> Unit
                }
                InlineKeyboardButton(
                    text = btn.text,
                    url = url,
                    callbackData = callbackData,
                    webApp = webApp,
                    switchInlineQuery = switchInline,
                    switchInlineQueryCurrentChat = switchCurrent,
                    copyText = copy,
                    callbackGame = game,
                    pay = pay,
                )
            }
        },
        forceReply = markup.forceReply.takeIf { it },
    )
}

private fun photoSizes(photo: PhotoCtor): List<BotPhotoSize> {
    val id = photo.id
    val hash = photo.accessHash
    val dc = photo.dcId
    return photo.sizes.mapNotNull { size ->
        val (w, h, bytes) = when (size) {
            is PhotoSizeCtor -> Triple(size.w, size.h, size.size)
            is PhotoCachedSize -> Triple(size.w, size.h, size.bytes.size)
            is PhotoSizeProgressive -> Triple(size.w, size.h, size.sizes.maxOrNull() ?: 0)
            else -> return@mapNotNull null
        }
        BotPhotoSize(
            fileId = "photo:$dc:$id:$hash:${w}x$h",
            fileUniqueId = "photo:$id:${w}x$h",
            width = w.toInt(),
            height = h.toInt(),
            fileSize = bytes.takeIf { it > 0 }?.toInt(),
        )
    }
}

private fun BotApiWriter.callback(u: UpdateBotCallbackQuery) = CallbackQuery(
    id = u.queryId.toString(),
    from = botUser(u.userId),
    message = BotMessage(messageId = u.msgId.toInt(), date = 0, chat = botChat(u.peer)),
    chatInstance = u.chatInstance.toString(),
    data = u.data.utf8(),
    gameShortName = u.gameShortName?.takeIf { it.isNotEmpty() },
)

private fun BotApiWriter.inlineCallback(u: UpdateInlineBotCallbackQuery) = CallbackQuery(
    id = u.queryId.toString(),
    from = botUser(u.userId),
    inlineMessageId = inlineMessageId(u.msgId),
    chatInstance = u.chatInstance.toString(),
    data = u.data.utf8(),
    gameShortName = u.gameShortName?.takeIf { it.isNotEmpty() },
)

private suspend fun BotApiWriter.businessCallback(u: UpdateBusinessBotCallbackQuery) = CallbackQuery(
    id = u.queryId.toString(),
    from = botUser(u.userId),
    message = botMessage(u.message, u.replyToMessage),
    chatInstance = u.chatInstance.toString(),
    data = u.data.utf8(),
)

private fun BotApiWriter.inlineQuery(u: UpdateBotInlineQuery) = InlineQuery(
    id = u.queryId.toString(),
    from = botUser(u.userId),
    query = u.query,
    offset = u.offset,
    chatType = when (u.peerType) {
        is InlineQueryPeerTypePM, is InlineQueryPeerTypeSameBotPM -> "private"
        is InlineQueryPeerTypeBotPM -> "sender"
        is InlineQueryPeerTypeChat -> "group"
        is InlineQueryPeerTypeMegagroup -> "supergroup"
        is InlineQueryPeerTypeBroadcast -> "channel"
        else -> null
    },
    location = (u.geo as? GeoPointCtor)?.let {
        Location(latitude = it.lat, longitude = it.`long`, horizontalAccuracy = it.accuracyRadius.takeIf { r -> r != 0 }?.toDouble())
    },
)

private fun BotApiWriter.chosenInline(u: UpdateBotInlineSend) = ChosenInlineResult(
    resultId = u.id,
    from = botUser(u.userId),
    location = (u.geo as? GeoPointCtor)?.let { Location(latitude = it.lat, longitude = it.`long`) },
    inlineMessageId = u.msgId?.let { inlineMessageId(it) },
    query = u.query,
)

private fun BotApiWriter.shipping(u: UpdateBotShippingQuery) = ShippingQuery(
    id = u.queryId.toString(),
    from = botUser(u.userId),
    invoicePayload = u.payload.utf8().orEmpty(),
    shippingAddress = address(u.shippingAddress),
)

private fun BotApiWriter.preCheckout(u: UpdateBotPrecheckoutQuery) = PreCheckoutQuery(
    id = u.queryId.toString(),
    from = botUser(u.userId),
    currency = u.currency,
    totalAmount = u.totalAmount.toInt(),
    invoicePayload = u.payload.utf8().orEmpty(),
    shippingOptionId = u.shippingOptionId?.takeIf { it.isNotEmpty() },
    orderInfo = u.info?.let { info ->
        OrderInfo(
            name = info.name?.takeIf { it.isNotEmpty() },
            phoneNumber = info.phone?.takeIf { it.isNotEmpty() },
            email = info.email?.takeIf { it.isNotEmpty() },
            shippingAddress = info.shippingAddress?.let { address(it) },
        )
    },
)

private fun address(a: PostAddress) = ShippingAddress(
    countryCode = a.countryIso2,
    state = a.state,
    city = a.city,
    streetLine1 = a.streetLine1,
    streetLine2 = a.streetLine2,
    postCode = a.postCode,
)

private fun BotApiWriter.joinRequest(u: UpdateBotChatInviteRequester) = ChatJoinRequest(
    chat = botChat(u.peer),
    from = botUser(u.userId),
    userChatId = u.userId,
    date = u.date.toInt(),
    bio = u.about?.takeIf { it.isNotEmpty() },
)

private fun BotApiWriter.botPoll(p: Poll, results: PollResults?): BotPoll {
    val correct = results?.results?.firstOrNull { it.correct }?.let { c ->
        p.answers.indexOfFirst { (it as PollAnswerCtor).option.contentEquals(c.option) }
    }?.takeIf { it >= 0 }
    return BotPoll(
        id = p.id.toString(),
        question = p.question.text,
        questionEntities = botEntities(p.question.entities).ifEmpty { null },
        options = p.answers.map { ans ->
            val a = ans as PollAnswerCtor
            val voters = results?.results?.firstOrNull { it.option.contentEquals(a.option) }
            PollOption(
                text = a.text.text,
                textEntities = botEntities(a.text.entities).ifEmpty { null },
                voterCount = (voters?.voters ?: 0).toInt(),
            )
        },
        totalVoterCount = (results?.totalVoters ?: 0).toInt(),
        isClosed = p.closed,
        isAnonymous = !p.publicVoters,
        type = if (p.quiz) "quiz" else "regular",
        allowsMultipleAnswers = p.multipleChoice,
        correctOptionIds = correct?.let { listOf(it.toInt()) },
        explanation = results?.solution?.takeIf { it.isNotEmpty() },
        explanationEntities = botEntities(results?.solutionEntities).ifEmpty { null },
        openPeriod = p.closePeriod.takeIf { it != 0 }?.toInt(),
        closeDate = p.closeDate.takeIf { it != 0 }?.toInt(),
    )
}

private fun BotApiWriter.pollAnswer(u: UpdateMessagePollVote) = BotPollAnswer(
    pollId = u.pollId.toString(),
    user = (u.peer as? PeerUser)?.let { botUser(it.userId) },
    voterChat = if (u.peer is PeerUser) null else botChat(u.peer),
    optionIds = u.positions.map { it.toInt() },
)

private fun BotApiWriter.botStopped(u: UpdateBotStopped): ChatMemberUpdated {
    val stopped = u.stopped is BoolTrue
    val from = botUser(u.userId)
    return ChatMemberUpdated(
        chat = botChat(PeerUser(u.userId)),
        from = from,
        date = u.date.toInt(),
        oldChatMember = from.asStatus(if (stopped) "member" else "kicked"),
        newChatMember = from.asStatus(if (stopped) "kicked" else "member"),
    )
}

private fun BotApiWriter.memberUpdate(
    chatPeer: Peer,
    actorId: Long,
    date: Int,
    oldMember: ChatMember,
    newMember: ChatMember,
    viaChatlist: Boolean = false,
) = ChatMemberUpdated(
    chat = botChat(chatPeer),
    from = botUser(actorId),
    date = date.toInt(),
    oldChatMember = oldMember,
    newChatMember = newMember,
    viaChatFolderInviteLink = viaChatlist.takeIf { it },
)

private fun BotUser.asStatus(status: String): ChatMember = when (status) {
    "kicked" -> ChatMemberBanned(status = "kicked", user = this, untilDate = 0)
    "administrator" -> ChatMemberAdministrator(status = "administrator", user = this)
    "left" -> ChatMemberLeft(status = "left", user = this)
    else -> ChatMemberMember(status = "member", user = this)
}

private fun BotApiWriter.channelMember(userId: Long, p: ChannelParticipant?): ChatMember {
    val u = botUser(userId)
    return when (p) {
        null, is ChannelParticipantLeft -> ChatMemberLeft(status = "left", user = u)
        is ChannelParticipantCtor -> ChatMemberMember(
            status = "member",
            user = u,
            untilDate = p.subscriptionUntilDate.takeIf { it != 0 }?.toInt(),
        )
        is ChannelParticipantSelf -> ChatMemberMember(
            status = "member",
            user = u,
            untilDate = p.subscriptionUntilDate.takeIf { it != 0 }?.toInt(),
        )
        is ChannelParticipantCreator -> ChatMemberOwner(
            status = "creator",
            user = u,
            isAnonymous = p.adminRights.anonymous,
            customTitle = p.rank?.takeIf { it.isNotEmpty() },
        )
        is ChannelParticipantAdmin -> adminMember(u, p.adminRights, p.canEdit, p.rank)
        is ChannelParticipantBanned -> restricted(u, p.bannedRights, !p.left)
    }
}

private fun BotApiWriter.basicMember(userId: Long, p: ChatParticipant?): ChatMember {
    val u = botUser(userId)
    return when (p) {
        null -> ChatMemberLeft(status = "left", user = u)
        is ChatParticipantCreator -> ChatMemberOwner(status = "creator", user = u, customTitle = p.rank?.takeIf { it.isNotEmpty() })
        is ChatParticipantAdmin -> ChatMemberAdministrator(status = "administrator", user = u, customTitle = p.rank?.takeIf { it.isNotEmpty() })
        is ChatParticipantCtor -> ChatMemberMember(status = "member", user = u)
    }
}

private fun adminMember(u: BotUser, r: ChatAdminRights, canEdit: Boolean, rank: String?) = ChatMemberAdministrator(
    status = "administrator",
    user = u,
    canBeEdited = canEdit,
    isAnonymous = r.anonymous,
    canManageChat = r.other,
    canDeleteMessages = r.deleteMessages,
    canManageVideoChats = r.manageCall,
    canRestrictMembers = r.banUsers,
    canPromoteMembers = r.addAdmins,
    canChangeInfo = r.changeInfo,
    canInviteUsers = r.inviteUsers,
    canPostStories = r.postStories,
    canEditStories = r.editStories,
    canDeleteStories = r.deleteStories,
    canPostMessages = r.postMessages.takeIf { it },
    canEditMessages = r.editMessages.takeIf { it },
    canPinMessages = r.pinMessages.takeIf { it },
    canManageTopics = r.manageTopics.takeIf { it },
    canManageDirectMessages = r.manageDirectMessages.takeIf { it },
    customTitle = rank?.takeIf { it.isNotEmpty() },
)

private fun restricted(u: BotUser, r: ChatBannedRights, member: Boolean): ChatMember =
    if (r.viewMessages) {
        ChatMemberBanned(status = "kicked", user = u, untilDate = r.untilDate.toInt())
    } else {
        ChatMemberRestricted(
            status = "restricted",
            user = u,
            isMember = member,
            canSendMessages = !r.sendMessages,
            canSendAudios = !r.sendAudios && !r.sendMedia,
            canSendDocuments = !r.sendDocs && !r.sendMedia,
            canSendPhotos = !r.sendPhotos && !r.sendMedia,
            canSendVideos = !r.sendVideos && !r.sendMedia,
            canSendVideoNotes = !r.sendRoundvideos && !r.sendMedia,
            canSendVoiceNotes = !r.sendVoices && !r.sendMedia,
            canSendPolls = !r.sendPolls,
            canSendOtherMessages = !r.sendStickers && !r.sendGifs && !r.sendGames && !r.sendInline,
            canAddWebPagePreviews = !r.embedLinks,
            canChangeInfo = !r.changeInfo,
            canInviteUsers = !r.inviteUsers,
            canPinMessages = !r.pinMessages,
            canManageTopics = !r.manageTopics,
            untilDate = r.untilDate.toInt(),
        )
    }

private fun ChannelParticipant?.isSelf(): Boolean = when (this) {
    is ChannelParticipantSelf -> true
    is ChannelParticipantAdmin -> self
    else -> false
}

private fun BotApiWriter.reactionType(r: Reaction): ReactionType? = when (r) {
    is ReactionEmoji -> ReactionTypeEmoji(type = "emoji", emoji = r.emoticon)
    is ReactionCustomEmoji -> ReactionTypeCustomEmoji(type = "custom_emoji", customEmojiId = r.documentId.toString())
    is ReactionPaid -> ReactionTypePaid(type = "paid")
    else -> null
}

private fun BotApiWriter.reactionCount(u: UpdateBotMessageReactions) = MessageReactionCountUpdated(
    chat = botChat(u.peer),
    messageId = u.msgId.toInt(),
    date = u.date.toInt(),
    reactions = u.reactions.mapNotNull { c ->
        reactionType(c.reaction)?.let { BotReactionCount(type = it, totalCount = c.count.toInt()) }
    },
)

private fun BotApiWriter.reaction(u: UpdateBotMessageReaction) = MessageReactionUpdated(
    chat = botChat(u.peer),
    messageId = u.msgId.toInt(),
    user = (u.actor as? PeerUser)?.let { botUser(it.userId) },
    actorChat = if (u.actor is PeerUser) null else botChat(u.actor),
    date = u.date.toInt(),
    oldReaction = u.oldReactions.mapNotNull { reactionType(it) },
    newReaction = u.newReactions.mapNotNull { reactionType(it) },
)

private fun BotApiWriter.chatBoost(updateId: Int, u: UpdateBotChatBoost): BotUpdate {
    val b = u.boost
    val source = boostSource(b)
    val chat = botChat(u.peer)
    return if (b.expires == 0) {
        BotUpdate(
            updateId = updateId,
            removedChatBoost = ChatBoostRemoved(
                chat = chat,
                boostId = b.id,
                removeDate = b.date.toInt(),
                source = source,
            ),
        )
    } else {
        BotUpdate(
            updateId = updateId,
            chatBoost = ChatBoostUpdated(
                chat = chat,
                boost = ChatBoost(
                    boostId = b.id,
                    addDate = b.date.toInt(),
                    expirationDate = b.expires.toInt(),
                    source = source,
                ),
            ),
        )
    }
}

private fun BotApiWriter.boostSource(b: iris.kmtproto.tl.gen.Boost): ChatBoostSource = when {
    b.giveaway -> ChatBoostSourceGiveaway(
        source = "giveaway",
        giveawayMessageId = b.giveawayMsgId.toInt(),
        user = b.userId.takeIf { it != 0L }?.let { botUser(it) },
        prizeStarCount = b.stars.takeIf { it > 0L }?.toInt(),
        isUnclaimed = (b.userId == 0L && b.unclaimed).takeIf { it },
    )
    b.gift -> ChatBoostSourceGiftCode(source = "gift_code", user = b.userId.takeIf { it != 0L }?.let { botUser(it) })
    else -> ChatBoostSourcePremium(source = "premium", user = b.userId.takeIf { it != 0L }?.let { botUser(it) })
}

private fun inlineMessageId(id: InputBotInlineMessageID): String = when (id) {
    is InputBotInlineMessageID64 -> "${id.dcId}:${id.ownerId}:${id.id}:${id.accessHash}"
    is InputBotInlineMessageIDCtor -> "${id.dcId}:${id.id}:${id.accessHash}"
    else -> id.toString()
}

private fun ByteArray?.utf8(): String? {
    if (this == null || isEmpty()) return null
    return decodeToString()
}
