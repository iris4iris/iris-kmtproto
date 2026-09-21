package iris.kmtproto.events

import iris.kmtproto.client.asText
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateChannelParticipant
import iris.kmtproto.tl.gen.UpdateChatParticipant
import iris.kmtproto.tl.gen.UpdateChatParticipantAdd
import iris.kmtproto.tl.gen.UpdateChatParticipantDelete
import iris.kmtproto.tl.gen.UpdateEditChannelMessage
import iris.kmtproto.tl.gen.UpdateEditMessage
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UpdateUserStatus

fun interface SingleEventDispatcher {
    suspend fun dispatch(update: Update)
}

open class BasicSingleEventDispatcher(
    private val handler: SingleEventHandler,
) : SingleEventDispatcher {
    override suspend fun dispatch(update: Update) {
        when (update) {
            is UpdateNewMessage -> {
                val m = update.message.asText()
                if (m != null) handler.handleMessage(m) else handler.handleUnknown(update)
            }
            is UpdateNewChannelMessage -> {
                val m = update.message.asText()
                if (m != null) handler.handleMessage(m) else handler.handleUnknown(update)
            }
            is UpdateEditMessage -> {
                val m = update.message.asText()
                if (m != null) handler.handleEdit(m) else handler.handleUnknown(update)
            }
            is UpdateEditChannelMessage -> {
                val m = update.message.asText()
                if (m != null) handler.handleEdit(m) else handler.handleUnknown(update)
            }
            is UpdateBotCallbackQuery -> handler.handleCallback(update)
            is UpdateUserStatus -> handler.handleUserStatus(update)
            is UpdateChatParticipant -> handler.handleChatMember(update.toEvent())
            is UpdateChannelParticipant -> handler.handleChatMember(update.toEvent())
            is UpdateChatParticipantAdd -> handler.handleChatMember(update.toEvent())
            is UpdateChatParticipantDelete -> handler.handleChatMember(update.toEvent())
            else -> handler.handleUnknown(update)
        }
    }
}
