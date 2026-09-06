package iris.kmtproto.events

import iris.kmtproto.client.asText
import iris.kmtproto.tl.gen.MessageCtor
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

internal suspend fun dispatchSingle(update: Update, handler: SingleEventHandler) {
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

internal suspend fun dispatchPack(batch: List<Update>, handler: PackEventHandler) {
    var messages: ArrayList<MessageCtor>? = null
    var edits: ArrayList<MessageCtor>? = null
    var members: ArrayList<ChatMemberEvent>? = null
    var callbacks: ArrayList<UpdateBotCallbackQuery>? = null
    var statuses: ArrayList<UpdateUserStatus>? = null
    var unknown: ArrayList<Update>? = null

    fun <T> add(cur: ArrayList<T>?, value: T): ArrayList<T> {
        val list = cur ?: ArrayList()
        list.add(value)
        return list
    }

    for (i in batch.indices) {
        when (val update = batch[i]) {
            is UpdateNewMessage -> {
                val m = update.message.asText()
                if (m != null) messages = add(messages, m) else unknown = add(unknown, update)
            }
            is UpdateNewChannelMessage -> {
                val m = update.message.asText()
                if (m != null) messages = add(messages, m) else unknown = add(unknown, update)
            }
            is UpdateEditMessage -> {
                val m = update.message.asText()
                if (m != null) edits = add(edits, m) else unknown = add(unknown, update)
            }
            is UpdateEditChannelMessage -> {
                val m = update.message.asText()
                if (m != null) edits = add(edits, m) else unknown = add(unknown, update)
            }
            is UpdateBotCallbackQuery -> callbacks = add(callbacks, update)
            is UpdateUserStatus -> statuses = add(statuses, update)
            is UpdateChatParticipant -> members = add(members, update.toEvent())
            is UpdateChannelParticipant -> members = add(members, update.toEvent())
            is UpdateChatParticipantAdd -> members = add(members, update.toEvent())
            is UpdateChatParticipantDelete -> members = add(members, update.toEvent())
            else -> unknown = add(unknown, update)
        }
    }

    supervisorScope {
        messages?.let { launch { guard("pack-message") { handler.handleMessage(it) } } }
        edits?.let { launch { guard("pack-edit") { handler.handleEdit(it) } } }
        members?.let { launch { guard("pack-member") { handler.handleChatMember(it) } } }
        callbacks?.let { launch { guard("pack-callback") { handler.handleCallback(it) } } }
        statuses?.let { launch { guard("pack-status") { handler.handleUserStatus(it) } } }
        unknown?.let { launch { guard("pack-unknown") { handler.handleUnknown(it) } } }
    }
}

internal fun UpdateChatParticipant.toEvent() = ChatMemberEvent(
    chatId = chatId,
    userId = userId,
    actorId = actorId,
    date = date,
    joined = prevParticipant == null && newParticipant != null,
    left = prevParticipant != null && newParticipant == null,
    raw = this,
)

internal fun UpdateChannelParticipant.toEvent() = ChatMemberEvent(
    chatId = channelId,
    userId = userId,
    actorId = actorId,
    date = date,
    joined = prevParticipant == null && newParticipant != null,
    left = prevParticipant != null && newParticipant == null,
    raw = this,
)

internal fun UpdateChatParticipantAdd.toEvent() = ChatMemberEvent(
    chatId = chatId,
    userId = userId,
    actorId = inviterId,
    date = date,
    joined = true,
    left = false,
    raw = this,
)

internal fun UpdateChatParticipantDelete.toEvent() = ChatMemberEvent(
    chatId = chatId,
    userId = userId,
    actorId = 0L,
    date = 0,
    joined = false,
    left = true,
    raw = this,
)
