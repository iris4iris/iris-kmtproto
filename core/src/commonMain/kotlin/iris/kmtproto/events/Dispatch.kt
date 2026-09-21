package iris.kmtproto.events

import iris.kmtproto.tl.gen.UpdateChannelParticipant
import iris.kmtproto.tl.gen.UpdateChatParticipant
import iris.kmtproto.tl.gen.UpdateChatParticipantAdd
import iris.kmtproto.tl.gen.UpdateChatParticipantDelete

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
