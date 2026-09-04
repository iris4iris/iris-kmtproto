package iris.kmtproto.api.user

import iris.kmtproto.client.SentMessage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.gen.InputPeer
import iris.kmtproto.tl.gen.InputQuickReplyShortcut
import iris.kmtproto.tl.gen.InputReplyTo
import iris.kmtproto.tl.gen.InputRichMessage
import iris.kmtproto.tl.gen.MessageEntity
import iris.kmtproto.tl.gen.MessagesSendMessage
import iris.kmtproto.tl.gen.ReplyMarkup
import iris.kmtproto.tl.gen.SuggestedPost
import kotlinx.coroutines.Deferred

class Messages(private val client: TelegramClient) {
    fun sendAsync(
        peer: InputPeer,
        text: String = "",
        randomId: Long? = null,
        noWebpage: Boolean = false,
        silent: Boolean = false,
        background: Boolean = false,
        clearDraft: Boolean = false,
        noforwards: Boolean = false,
        updateStickersetsOrder: Boolean = false,
        invertMedia: Boolean = false,
        allowPaidFloodskip: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
        entities: List<MessageEntity>? = null,
        scheduleDate: Int? = null,
        scheduleRepeatPeriod: Int? = null,
        sendAs: InputPeer? = null,
        sendAsId: Long? = null,
        quickReplyShortcut: InputQuickReplyShortcut? = null,
        effect: Long? = null,
        allowPaidStars: Long? = null,
        suggestedPost: SuggestedPost? = null,
        richMessage: InputRichMessage? = null,
    ): Deferred<SentMessage> = client.apiAsync {
        send(
            peer, text, randomId, noWebpage, silent, background, clearDraft, noforwards,
            updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup,
            entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut,
            effect, allowPaidStars, suggestedPost, richMessage,
        )
    }

    fun sendAsync(
        peerId: Long,
        text: String = "",
        randomId: Long? = null,
        noWebpage: Boolean = false,
        silent: Boolean = false,
        background: Boolean = false,
        clearDraft: Boolean = false,
        noforwards: Boolean = false,
        updateStickersetsOrder: Boolean = false,
        invertMedia: Boolean = false,
        allowPaidFloodskip: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
        entities: List<MessageEntity>? = null,
        scheduleDate: Int? = null,
        scheduleRepeatPeriod: Int? = null,
        sendAs: InputPeer? = null,
        sendAsId: Long? = null,
        quickReplyShortcut: InputQuickReplyShortcut? = null,
        effect: Long? = null,
        allowPaidStars: Long? = null,
        suggestedPost: SuggestedPost? = null,
        richMessage: InputRichMessage? = null,
    ): Deferred<SentMessage> = client.apiAsync {
        send(
            peerId, text, randomId, noWebpage, silent, background, clearDraft, noforwards,
            updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup,
            entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut,
            effect, allowPaidStars, suggestedPost, richMessage,
        )
    }

    suspend fun send(
        peerId: Long,
        text: String = "",
        randomId: Long? = null,
        noWebpage: Boolean = false,
        silent: Boolean = false,
        background: Boolean = false,
        clearDraft: Boolean = false,
        noforwards: Boolean = false,
        updateStickersetsOrder: Boolean = false,
        invertMedia: Boolean = false,
        allowPaidFloodskip: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
        entities: List<MessageEntity>? = null,
        scheduleDate: Int? = null,
        scheduleRepeatPeriod: Int? = null,
        sendAs: InputPeer? = null,
        sendAsId: Long? = null,
        quickReplyShortcut: InputQuickReplyShortcut? = null,
        effect: Long? = null,
        allowPaidStars: Long? = null,
        suggestedPost: SuggestedPost? = null,
        richMessage: InputRichMessage? = null,
    ): SentMessage = send(
        peer = client.inputPeerFromId(peerId),
        text = text,
        randomId = randomId,
        noWebpage = noWebpage,
        silent = silent,
        background = background,
        clearDraft = clearDraft,
        noforwards = noforwards,
        updateStickersetsOrder = updateStickersetsOrder,
        invertMedia = invertMedia,
        allowPaidFloodskip = allowPaidFloodskip,
        replyTo = replyTo,
        replyMarkup = replyMarkup,
        entities = entities,
        scheduleDate = scheduleDate,
        scheduleRepeatPeriod = scheduleRepeatPeriod,
        sendAs = sendAs,
        sendAsId = sendAsId,
        quickReplyShortcut = quickReplyShortcut,
        effect = effect,
        allowPaidStars = allowPaidStars,
        suggestedPost = suggestedPost,
        richMessage = richMessage,
    )

    suspend fun send(
        peer: InputPeer,
        text: String = "",
        randomId: Long? = null,
        noWebpage: Boolean = false,
        silent: Boolean = false,
        background: Boolean = false,
        clearDraft: Boolean = false,
        noforwards: Boolean = false,
        updateStickersetsOrder: Boolean = false,
        invertMedia: Boolean = false,
        allowPaidFloodskip: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
        entities: List<MessageEntity>? = null,
        scheduleDate: Int? = null,
        scheduleRepeatPeriod: Int? = null,
        sendAs: InputPeer? = null,
        sendAsId: Long? = null,
        quickReplyShortcut: InputQuickReplyShortcut? = null,
        effect: Long? = null,
        allowPaidStars: Long? = null,
        suggestedPost: SuggestedPost? = null,
        richMessage: InputRichMessage? = null,
    ): SentMessage {
        require(text.isNotEmpty() || richMessage != null) { "empty message" }
        val raw = client.invoke(
            MessagesSendMessage(
                peer = peer,
                message = text,
                randomId = nextRandomId(randomId),
                noWebpage = noWebpage,
                silent = silent,
                background = background,
                clearDraft = clearDraft,
                noforwards = noforwards,
                updateStickersetsOrder = updateStickersetsOrder,
                invertMedia = invertMedia,
                allowPaidFloodskip = allowPaidFloodskip,
                replyTo = replyTo,
                replyMarkup = replyMarkup,
                entities = entities,
                scheduleDate = scheduleDate,
                scheduleRepeatPeriod = scheduleRepeatPeriod,
                sendAs = sendAs ?: sendAsId?.let { client.inputPeerFromId(it) },
                quickReplyShortcut = quickReplyShortcut,
                effect = effect,
                allowPaidStars = allowPaidStars,
                suggestedPost = suggestedPost,
                richMessage = richMessage,
            ),
        )
        return SentMessage.from(raw, text)
    }

    private fun nextRandomId(randomId: Long?): Long {
        if (randomId != null && randomId != 0L) return randomId
        var id = PlatformCrypto.randomBytes(8).readLongLe()
        if (id == 0L) id = 1L
        return id
    }
}
