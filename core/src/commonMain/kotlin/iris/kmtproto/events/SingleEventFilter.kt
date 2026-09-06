package iris.kmtproto.events

import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateUserStatus

interface SingleEventFilter {
    suspend fun filterMessage(message: MessageCtor): Boolean = true
    suspend fun filterEdit(message: MessageCtor): Boolean = true
    suspend fun filterChatMember(event: ChatMemberEvent): Boolean = true
    suspend fun filterCallback(query: UpdateBotCallbackQuery): Boolean = true
    suspend fun filterUserStatus(update: UpdateUserStatus): Boolean = true
    suspend fun filterUnknown(update: Update): Boolean = true
}

object AcceptAllSingle : SingleEventFilter
