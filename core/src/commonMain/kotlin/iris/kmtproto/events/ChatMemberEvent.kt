package iris.kmtproto.events

import iris.kmtproto.tl.gen.Update

/**
 * Join / leave / role change, folded from several TL participant updates.
 * [chatId] is the raw Telegram id (user chat or channel), not Bot API.
 */
class ChatMemberEvent(
    val chatId: Long,
    val userId: Long,
    val actorId: Long,
    val date: Int,
    val joined: Boolean,
    val left: Boolean,
    val raw: Update,
)
