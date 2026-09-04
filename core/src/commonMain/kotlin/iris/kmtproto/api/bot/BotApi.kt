package iris.kmtproto.api.bot

import iris.kmtproto.api.user.UserApi
import iris.kmtproto.client.ClientInfo
import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.SentMessage
import iris.kmtproto.client.Storage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.io.ByteArrayByteSource
import iris.kmtproto.io.ByteSource
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tl.gen.ContactsResolvedPeer
import iris.kmtproto.tl.gen.PaymentsStarGifts
import iris.kmtproto.tl.gen.InputPeer
import iris.kmtproto.tl.gen.InputQuickReplyShortcut
import iris.kmtproto.tl.gen.InputReplyTo
import iris.kmtproto.tl.gen.InputRichMessage
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.MessageEntity
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.ReplyMarkup
import iris.kmtproto.tl.gen.SuggestedPost
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.User
import iris.kmtproto.transport.Datacenter
import iris.kmtproto.transport.Proxy
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

data class BotMessage(
    val messageId: Int,
    val chatId: Long,
    val fromId: Long?,
    val text: String,
    val date: Int,
    val out: Boolean,
)

/**
 * Bot-API-shaped adapter over MTProto. Not HTTP getUpdates.
 */
class BotApi(val client: TelegramClient) {
    val user = UserApi(client)

    constructor(
        apiId: Int,
        apiHash: String,
        dc: Datacenter = Datacenter.DC2,
        storage: Storage = MemoryStorage(),
        proxy: Proxy? = null,
        info: ClientInfo = ClientInfo(),
        layer: Int = API_LAYER,
    ) : this(
        TelegramClient(
            apiId = apiId,
            apiHash = apiHash,
            dc = dc,
            info = info,
            layer = layer,
            storage = storage,
            proxy = proxy,
        ),
    )

    fun loginAsync(token: String): Deferred<User> = client.apiAsync { login(token) }

    suspend fun login(token: String): User {
        val me = user.auth.importBotAuthorization(token)
        client.getState()
        return me
    }

    fun sendMessageAsync(peer: InputPeer, text: String = "", randomId: Long? = null, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): Deferred<SentMessage> =
        client.apiAsync { sendMessage(peer, text, randomId, noWebpage, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost, richMessage) }

    fun sendMessageAsync(chatId: Long, text: String = "", randomId: Long? = null, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): Deferred<SentMessage> =
        client.apiAsync { sendMessage(client.inputPeerFromId(chatId), text, randomId, noWebpage, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost, richMessage) }

    suspend fun sendMessage(chatId: Long, text: String = "", randomId: Long? = null, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): SentMessage =
        sendMessage(client.inputPeerFromId(chatId), text, randomId, noWebpage, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost, richMessage)

    suspend fun sendMessage(peer: InputPeer, text: String = "", randomId: Long? = null, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): SentMessage =
        user.messages.send(peer, text, randomId, noWebpage, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost, richMessage)

    fun sendPhotoAsync(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendPhoto(peer, source, caption, fileName, spoiler, ttlSeconds, silent, replyTo, replyMarkup) }

    fun sendPhotoAsync(chatId: Long, bytes: ByteArray, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendPhoto(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), caption, fileName, spoiler, ttlSeconds, silent, replyTo, replyMarkup) }

    suspend fun sendPhoto(chatId: Long, bytes: ByteArray, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        sendPhoto(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), caption, fileName, spoiler, ttlSeconds, silent, replyTo, replyMarkup)

    suspend fun sendPhoto(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        user.messages.sendPhoto(peer, source, caption, fileName, spoiler, ttlSeconds, silent = silent, replyTo = replyTo, replyMarkup = replyMarkup)

    fun sendVideoAsync(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "video.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteSource? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendVideo(peer, source, caption, fileName, duration, width, height, thumb, spoiler, silent, replyTo, replyMarkup) }

    fun sendVideoAsync(chatId: Long, bytes: ByteArray, caption: String = "", fileName: String = "video.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteArray? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendVideo(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), caption, fileName, duration, width, height, thumb?.let { ByteArrayByteSource(it) }, spoiler, silent, replyTo, replyMarkup) }

    suspend fun sendVideo(chatId: Long, bytes: ByteArray, caption: String = "", fileName: String = "video.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteArray? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        sendVideo(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), caption, fileName, duration, width, height, thumb?.let { ByteArrayByteSource(it) }, spoiler, silent, replyTo, replyMarkup)

    suspend fun sendVideo(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "video.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteSource? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        user.messages.sendVideo(peer, source, caption, fileName, duration = duration, width = width, height = height, thumb = thumb, spoiler = spoiler, silent = silent, replyTo = replyTo, replyMarkup = replyMarkup)

    fun sendVoiceAsync(peer: InputPeer, source: ByteSource, duration: Int = 0, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendVoice(peer, source, duration, caption, silent, replyTo, replyMarkup) }

    fun sendVoiceAsync(chatId: Long, bytes: ByteArray, duration: Int = 0, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendVoice(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), duration, caption, silent, replyTo, replyMarkup) }

    suspend fun sendVoice(chatId: Long, bytes: ByteArray, duration: Int = 0, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        sendVoice(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), duration, caption, silent, replyTo, replyMarkup)

    suspend fun sendVoice(peer: InputPeer, source: ByteSource, duration: Int = 0, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        user.messages.sendVoice(peer, source, duration, caption = caption, silent = silent, replyTo = replyTo, replyMarkup = replyMarkup)

    fun sendVideoNoteAsync(peer: InputPeer, source: ByteSource, duration: Double = 0.0, length: Int = 384, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendVideoNote(peer, source, duration, length, silent, replyTo, replyMarkup) }

    fun sendVideoNoteAsync(chatId: Long, bytes: ByteArray, duration: Double = 0.0, length: Int = 384, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendVideoNote(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), duration, length, silent, replyTo, replyMarkup) }

    suspend fun sendVideoNote(chatId: Long, bytes: ByteArray, duration: Double = 0.0, length: Int = 384, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        sendVideoNote(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), duration, length, silent, replyTo, replyMarkup)

    suspend fun sendVideoNote(peer: InputPeer, source: ByteSource, duration: Double = 0.0, length: Int = 384, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        user.messages.sendVideoNote(peer, source, duration, length, silent = silent, replyTo = replyTo, replyMarkup = replyMarkup)

    fun sendDocumentAsync(peer: InputPeer, source: ByteSource, fileName: String, caption: String = "", mimeType: String? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendDocument(peer, source, fileName, caption, mimeType, silent, replyTo, replyMarkup) }

    fun sendDocumentAsync(chatId: Long, bytes: ByteArray, fileName: String, caption: String = "", mimeType: String? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendDocument(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), fileName, caption, mimeType, silent, replyTo, replyMarkup) }

    suspend fun sendDocument(chatId: Long, bytes: ByteArray, fileName: String, caption: String = "", mimeType: String? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        sendDocument(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), fileName, caption, mimeType, silent, replyTo, replyMarkup)

    suspend fun sendDocument(peer: InputPeer, source: ByteSource, fileName: String, caption: String = "", mimeType: String? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        user.messages.sendDocument(peer, source, fileName, caption, mimeType, silent = silent, replyTo = replyTo, replyMarkup = replyMarkup)

    fun sendGifAsync(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendGif(peer, source, caption, fileName, duration, width, height, spoiler, silent, replyTo, replyMarkup) }

    fun sendGifAsync(chatId: Long, bytes: ByteArray, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<SentMessage> =
        client.apiAsync { sendGif(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), caption, fileName, duration, width, height, spoiler, silent, replyTo, replyMarkup) }

    suspend fun sendGif(chatId: Long, bytes: ByteArray, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        sendGif(client.inputPeerFromId(chatId), ByteArrayByteSource(bytes), caption, fileName, duration, width, height, spoiler, silent, replyTo, replyMarkup)

    suspend fun sendGif(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): SentMessage =
        user.messages.sendGif(peer, source, caption, fileName, duration, width, height, spoiler = spoiler, silent = silent, replyTo = replyTo, replyMarkup = replyMarkup)

    fun incomingMessages(): Flow<BotMessage> =
        client.incomingMessages().filter { !it.out }.map { it.toBotMessage() }

    fun incomingUpdates(): Flow<Update> = client.incomingUpdates()

    fun getStarGiftsAsync(hash: Int = 0): Deferred<PaymentsStarGifts> = user.payments.getStarGiftsAsync(hash)

    suspend fun getStarGifts(hash: Int = 0): PaymentsStarGifts = user.payments.getStarGifts(hash)

    fun getChatAsync(username: String): Deferred<ContactsResolvedPeer> = user.contacts.resolveUsernameAsync(username)

    suspend fun getChat(username: String): ContactsResolvedPeer = user.contacts.resolveUsername(username)

    fun getChatAsync(chatId: Long): Deferred<ContactsResolvedPeer> = user.contacts.resolveAsync(chatId)

    suspend fun getChat(chatId: Long): ContactsResolvedPeer = user.contacts.resolve(chatId)
}

fun MessageCtor.toBotMessage(): BotMessage = BotMessage(
    messageId = id,
    chatId = peerId.botApiChatId(),
    fromId = when (val from = fromId) {
        is PeerUser -> from.userId
        is PeerChat -> from.chatId
        is PeerChannel -> from.channelId
        null -> null
        else -> null
    },
    text = message,
    date = date,
    out = out,
)
