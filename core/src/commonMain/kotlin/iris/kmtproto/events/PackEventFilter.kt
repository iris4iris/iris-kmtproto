package iris.kmtproto.events

import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateUserStatus

interface PackEventFilter {
    suspend fun filterMessage(messages: List<MessageCtor>): List<MessageCtor> = messages
    suspend fun filterEdit(messages: List<MessageCtor>): List<MessageCtor> = messages
    suspend fun filterChatMember(events: List<ChatMemberEvent>): List<ChatMemberEvent> = events
    suspend fun filterCallback(queries: List<UpdateBotCallbackQuery>): List<UpdateBotCallbackQuery> = queries
    suspend fun filterUserStatus(updates: List<UpdateUserStatus>): List<UpdateUserStatus> = updates
    suspend fun filterUnknown(updates: List<Update>): List<Update> = updates
}

object KeepAllPack : PackEventFilter
