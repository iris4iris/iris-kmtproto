package iris.kmtproto.client

import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.MessageEmpty
import iris.kmtproto.tl.gen.UpdateMessageID
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UpdateShort
import iris.kmtproto.tl.gen.UpdateShortChatMessage
import iris.kmtproto.tl.gen.UpdateShortMessage
import iris.kmtproto.tl.gen.UpdateShortSentMessage
import iris.kmtproto.tl.gen.Updates
import iris.kmtproto.tl.gen.UpdatesCombined
import iris.kmtproto.tl.gen.UpdatesCtor
import iris.kmtproto.tl.gen.UpdatesTooLong

data class SentMessage(val id: Int, val date: Int, val text: String, val raw: Updates) {
    companion object {
        fun from(raw: Updates, text: String): SentMessage {
            when (raw) {
                is UpdateShortSentMessage -> return SentMessage(raw.id, raw.date, text, raw)
                is UpdateShortMessage -> return SentMessage(raw.id, raw.date, raw.message, raw)
                is UpdateShortChatMessage -> return SentMessage(raw.id, raw.date, raw.message, raw)
                is UpdatesCtor -> return fromBox(raw.updates, raw.date, text, raw)
                is UpdatesCombined -> return fromBox(raw.updates, raw.date, text, raw)
                is UpdateShort -> {
                    val msg = textFromUpdate(raw.update) ?: error("sendMessage: no message in $raw")
                    return SentMessage(msg.id, msg.date, msg.message, raw)
                }
                is UpdatesTooLong -> error("updatesTooLong")
            }
        }

        private fun fromBox(
            updates: List<iris.kmtproto.tl.gen.Update>,
            date: Int,
            text: String,
            raw: Updates,
        ): SentMessage {
            val idUpd = updates.filterIsInstance<UpdateMessageID>().firstOrNull()
            val msg = updates.mapNotNull { textFromUpdate(it) }.firstOrNull()
                ?: updates.mapNotNull {
                    when (it) {
                        is UpdateNewMessage -> it.message as? MessageEmpty
                        is UpdateNewChannelMessage -> it.message as? MessageEmpty
                        else -> null
                    }
                }.firstOrNull()
            val id = idUpd?.id ?: msg.let {
                when (it) {
                    is MessageCtor -> it.id
                    is MessageEmpty -> it.id
                    else -> null
                }
            } ?: error("sendMessage: no message id in $raw")
            val body = (msg as? MessageCtor)?.message ?: text
            val whenDate = (msg as? MessageCtor)?.date ?: date
            return SentMessage(id, whenDate, body, raw)
        }
    }
}
