package iris.kmtproto.api.user

import iris.kmtproto.client.RpcResponse
import iris.kmtproto.client.SentMessage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.asInputChannel
import iris.kmtproto.client.id
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.io.ByteArrayByteSource
import iris.kmtproto.io.ByteSource
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.gen.BoolTrue
import iris.kmtproto.tl.gen.ChannelsDeleteMessages
import iris.kmtproto.tl.gen.ChannelsGetMessages
import iris.kmtproto.tl.gen.ChannelsReadHistory
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.Dialog
import iris.kmtproto.tl.gen.DocumentAttribute
import iris.kmtproto.tl.gen.DocumentAttributeAnimated
import iris.kmtproto.tl.gen.DocumentAttributeAudio
import iris.kmtproto.tl.gen.DocumentAttributeFilename
import iris.kmtproto.tl.gen.DocumentAttributeVideo
import iris.kmtproto.tl.gen.InputMedia
import iris.kmtproto.tl.gen.InputMediaUploadedDocument
import iris.kmtproto.tl.gen.InputMediaUploadedPhoto
import iris.kmtproto.tl.gen.InputMessage
import iris.kmtproto.tl.gen.InputMessageID
import iris.kmtproto.tl.gen.InputPeer
import iris.kmtproto.tl.gen.InputPeerEmpty
import iris.kmtproto.tl.gen.InputQuickReplyShortcut
import iris.kmtproto.tl.gen.InputReplyTo
import iris.kmtproto.tl.gen.InputRichMessage
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageEntity
import iris.kmtproto.tl.gen.MessagesAffectedMessages
import iris.kmtproto.tl.gen.MessagesChannelMessages
import iris.kmtproto.tl.gen.MessagesDeleteMessages
import iris.kmtproto.tl.gen.MessagesDialogs
import iris.kmtproto.tl.gen.MessagesDialogsCtor
import iris.kmtproto.tl.gen.MessagesDialogsNotModified
import iris.kmtproto.tl.gen.MessagesDialogsSlice
import iris.kmtproto.tl.gen.MessagesEditMessage
import iris.kmtproto.tl.gen.MessagesForwardMessages
import iris.kmtproto.tl.gen.MessagesGetDialogs
import iris.kmtproto.tl.gen.MessagesGetHistory
import iris.kmtproto.tl.gen.MessagesGetMessages
import iris.kmtproto.tl.gen.MessagesMessages
import iris.kmtproto.tl.gen.MessagesMessagesCtor
import iris.kmtproto.tl.gen.MessagesMessagesNotModified
import iris.kmtproto.tl.gen.MessagesMessagesSlice
import iris.kmtproto.tl.gen.MessagesReadHistory
import iris.kmtproto.tl.gen.MessagesSendMedia
import iris.kmtproto.tl.gen.MessagesSendMessage
import iris.kmtproto.tl.gen.ReplyMarkup
import iris.kmtproto.tl.gen.SuggestedPost
import iris.kmtproto.tl.gen.Updates
import iris.kmtproto.tl.gen.User
import kotlinx.coroutines.Deferred

private const val HISTORY_PAGE = 100
private const val HISTORY_CAP = 10_000

class Messages(
    private val client: TelegramClient,
) {
    fun sendAsync(peer: InputPeer, text: String = "", randomId: Long = 0, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { send(peer, text, randomId, noWebpage, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost, richMessage) }

    fun sendAsync(peerId: Long, text: String = "", randomId: Long = 0, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { send(client.inputPeerFromId(peerId), text, randomId, noWebpage, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost, richMessage) }

    suspend fun send(peerId: Long, text: String = "", randomId: Long = 0, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): RpcResponse<SentMessage> =
        send(client.inputPeerFromId(peerId), text, randomId, noWebpage, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost, richMessage)

    suspend fun send(peer: InputPeer, text: String = "", randomId: Long = 0, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): RpcResponse<SentMessage> {
        require(text.isNotEmpty() || richMessage != null) { "empty message" }
        val raw = client.invoke(MessagesSendMessage(peer = peer, message = text, randomId = nextRandomId(randomId), noWebpage = noWebpage, silent = silent, background = background, clearDraft = clearDraft, noforwards = noforwards, updateStickersetsOrder = updateStickersetsOrder, invertMedia = invertMedia, allowPaidFloodskip = allowPaidFloodskip, replyTo = replyTo, replyMarkup = replyMarkup, entities = entities, scheduleDate = scheduleDate, scheduleRepeatPeriod = scheduleRepeatPeriod, sendAs = sendAs ?: sendAsId.takeIf { it != 0L }?.let { client.inputPeerFromId(it) }, quickReplyShortcut = quickReplyShortcut, effect = effect, allowPaidStars = allowPaidStars, suggestedPost = suggestedPost, richMessage = richMessage))
        return raw.map { SentMessage.from(it, text) }
    }

    fun sendPhotoAsync(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int = 0, randomId: Long = 0, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendPhoto(peer, source, caption, fileName, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost) }

    fun sendPhotoAsync(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int = 0, randomId: Long = 0, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendPhoto(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost) }

    suspend fun sendPhoto(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int = 0, randomId: Long = 0, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null): RpcResponse<SentMessage> =
        sendPhoto(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost)

    suspend fun sendPhoto(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int = 0, randomId: Long = 0, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null): RpcResponse<SentMessage> {
        val file = Upload.saveFile(client, source, fileName)
        val err = file.error
        if (err != null) return RpcResponse(null, err)
        return sendMedia(peer, InputMediaUploadedPhoto(file = file.result!!, spoiler = spoiler, ttlSeconds = ttlSeconds), caption, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost)
    }

    fun sendVideoAsync(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "video.mp4", mimeType: String? = null, duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteSource? = null, supportsStreaming: Boolean = true, roundMessage: Boolean = false, nosound: Boolean = false, spoiler: Boolean = false, ttlSeconds: Int = 0, randomId: Long = 0, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendVideo(peer, source, caption, fileName, mimeType, duration, width, height, thumb, supportsStreaming, roundMessage, nosound, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost) }

    fun sendVideoAsync(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "video.mp4", mimeType: String? = null, duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteArray? = null, supportsStreaming: Boolean = true, roundMessage: Boolean = false, nosound: Boolean = false, spoiler: Boolean = false, ttlSeconds: Int = 0, randomId: Long = 0, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendVideo(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, mimeType, duration, width, height, thumb?.let { ByteArrayByteSource(it) }, supportsStreaming, roundMessage, nosound, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost) }

    suspend fun sendVideo(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "video.mp4", mimeType: String? = null, duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteArray? = null, supportsStreaming: Boolean = true, roundMessage: Boolean = false, nosound: Boolean = false, spoiler: Boolean = false, ttlSeconds: Int = 0, randomId: Long = 0, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null): RpcResponse<SentMessage> =
        sendVideo(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, mimeType, duration, width, height, thumb?.let { ByteArrayByteSource(it) }, supportsStreaming, roundMessage, nosound, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost)

    suspend fun sendVideo(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "video.mp4", mimeType: String? = null, duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteSource? = null, supportsStreaming: Boolean = true, roundMessage: Boolean = false, nosound: Boolean = false, spoiler: Boolean = false, ttlSeconds: Int = 0, randomId: Long = 0, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int = 0, scheduleRepeatPeriod: Int = 0, sendAs: InputPeer? = null, sendAsId: Long = 0, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long = 0, allowPaidStars: Long = 0, suggestedPost: SuggestedPost? = null): RpcResponse<SentMessage> =
        sendUploadedDocument(peer = peer, source = source, fileName = fileName, mimeType = mimeType ?: mimeFromName(fileName), attributes = listOf(DocumentAttributeVideo(duration = duration, w = width, h = height, roundMessage = roundMessage, supportsStreaming = supportsStreaming, nosound = nosound), DocumentAttributeFilename(fileName)), caption = caption, thumb = thumb, forceFile = false, spoiler = spoiler, ttlSeconds = ttlSeconds, randomId = randomId, silent = silent, background = background, clearDraft = clearDraft, noforwards = noforwards, updateStickersetsOrder = updateStickersetsOrder, invertMedia = invertMedia, allowPaidFloodskip = allowPaidFloodskip, replyTo = replyTo, replyMarkup = replyMarkup, entities = entities, scheduleDate = scheduleDate, scheduleRepeatPeriod = scheduleRepeatPeriod, sendAs = sendAs, sendAsId = sendAsId, quickReplyShortcut = quickReplyShortcut, effect = effect, allowPaidStars = allowPaidStars, suggestedPost = suggestedPost)

    fun sendVoiceAsync(peer: InputPeer, source: ByteSource, duration: Int = 0, fileName: String = "voice.ogg", waveform: ByteArray? = null, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendVoice(peer, source, duration, fileName, waveform, caption, silent, replyTo, replyMarkup) }

    fun sendVoiceAsync(peerId: Long, bytes: ByteArray, duration: Int = 0, fileName: String = "voice.ogg", waveform: ByteArray? = null, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendVoice(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), duration, fileName, waveform, caption, silent, replyTo, replyMarkup) }

    suspend fun sendVoice(peerId: Long, bytes: ByteArray, duration: Int = 0, fileName: String = "voice.ogg", waveform: ByteArray? = null, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendVoice(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), duration, fileName, waveform, caption, silent, replyTo, replyMarkup)

    suspend fun sendVoice(peer: InputPeer, source: ByteSource, duration: Int = 0, fileName: String = "voice.ogg", waveform: ByteArray? = null, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendUploadedDocument(peer = peer, source = source, fileName = fileName, mimeType = "audio/ogg", attributes = listOf(DocumentAttributeAudio(duration = duration, voice = true, waveform = waveform)), caption = caption, thumb = null, forceFile = false, spoiler = false, ttlSeconds = 0, randomId = 0, silent = silent, background = false, clearDraft = false, noforwards = false, updateStickersetsOrder = false, invertMedia = false, allowPaidFloodskip = false, replyTo = replyTo, replyMarkup = replyMarkup, entities = null, scheduleDate = 0, scheduleRepeatPeriod = 0, sendAs = null, sendAsId = 0, quickReplyShortcut = null, effect = 0, allowPaidStars = 0, suggestedPost = null)

    fun sendVideoNoteAsync(peer: InputPeer, source: ByteSource, duration: Double = 0.0, length: Int = 384, fileName: String = "video_note.mp4", thumb: ByteSource? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendVideoNote(peer, source, duration, length, fileName, thumb, silent, replyTo, replyMarkup) }

    fun sendVideoNoteAsync(peerId: Long, bytes: ByteArray, duration: Double = 0.0, length: Int = 384, fileName: String = "video_note.mp4", thumb: ByteArray? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendVideoNote(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), duration, length, fileName, thumb?.let { ByteArrayByteSource(it) }, silent, replyTo, replyMarkup) }

    suspend fun sendVideoNote(peerId: Long, bytes: ByteArray, duration: Double = 0.0, length: Int = 384, fileName: String = "video_note.mp4", thumb: ByteArray? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendVideoNote(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), duration, length, fileName, thumb?.let { ByteArrayByteSource(it) }, silent, replyTo, replyMarkup)

    suspend fun sendVideoNote(peer: InputPeer, source: ByteSource, duration: Double = 0.0, length: Int = 384, fileName: String = "video_note.mp4", thumb: ByteSource? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendVideo(peer = peer, source = source, fileName = fileName, duration = duration, width = length, height = length, thumb = thumb, supportsStreaming = true, roundMessage = true, silent = silent, replyTo = replyTo, replyMarkup = replyMarkup)

    fun sendDocumentAsync(peer: InputPeer, source: ByteSource, fileName: String, caption: String = "", mimeType: String? = null, thumb: ByteSource? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendDocument(peer, source, fileName, caption, mimeType, thumb, silent, replyTo, replyMarkup) }

    fun sendDocumentAsync(peerId: Long, bytes: ByteArray, fileName: String, caption: String = "", mimeType: String? = null, thumb: ByteArray? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendDocument(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), fileName, caption, mimeType, thumb?.let { ByteArrayByteSource(it) }, silent, replyTo, replyMarkup) }

    suspend fun sendDocument(peerId: Long, bytes: ByteArray, fileName: String, caption: String = "", mimeType: String? = null, thumb: ByteArray? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendDocument(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), fileName, caption, mimeType, thumb?.let { ByteArrayByteSource(it) }, silent, replyTo, replyMarkup)

    suspend fun sendDocument(peer: InputPeer, source: ByteSource, fileName: String, caption: String = "", mimeType: String? = null, thumb: ByteSource? = null, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendUploadedDocument(peer = peer, source = source, fileName = fileName, mimeType = mimeType ?: mimeFromName(fileName), attributes = listOf(DocumentAttributeFilename(fileName)), caption = caption, thumb = thumb, forceFile = true, spoiler = false, ttlSeconds = 0, randomId = 0, silent = silent, background = false, clearDraft = false, noforwards = false, updateStickersetsOrder = false, invertMedia = false, allowPaidFloodskip = false, replyTo = replyTo, replyMarkup = replyMarkup, entities = null, scheduleDate = 0, scheduleRepeatPeriod = 0, sendAs = null, sendAsId = 0, quickReplyShortcut = null, effect = 0, allowPaidStars = 0, suggestedPost = null)

    fun sendGifAsync(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteSource? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendGif(peer, source, caption, fileName, duration, width, height, thumb, spoiler, silent, replyTo, replyMarkup) }

    fun sendGifAsync(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteArray? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendGif(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, duration, width, height, thumb?.let { ByteArrayByteSource(it) }, spoiler, silent, replyTo, replyMarkup) }

    suspend fun sendGif(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteArray? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendGif(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, duration, width, height, thumb?.let { ByteArrayByteSource(it) }, spoiler, silent, replyTo, replyMarkup)

    suspend fun sendGif(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteSource? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendUploadedDocument(peer = peer, source = source, fileName = fileName, mimeType = if (fileName.endsWith(".gif", ignoreCase = true)) "image/gif" else "video/mp4", attributes = listOf(DocumentAttributeAnimated, DocumentAttributeVideo(duration = duration, w = width, h = height, supportsStreaming = true), DocumentAttributeFilename(fileName)), caption = caption, thumb = thumb, forceFile = false, spoiler = spoiler, ttlSeconds = 0, randomId = 0, silent = silent, background = false, clearDraft = false, noforwards = false, updateStickersetsOrder = false, invertMedia = false, allowPaidFloodskip = false, replyTo = replyTo, replyMarkup = replyMarkup, entities = null, scheduleDate = 0, scheduleRepeatPeriod = 0, sendAs = null, sendAsId = 0, quickReplyShortcut = null, effect = 0, allowPaidStars = 0, suggestedPost = null)

    fun historyAsync(peer: InputPeer, limit: Int = 100, offsetId: Int = 0, offsetDate: Int = 0, addOffset: Int = 0, maxId: Int = 0, minId: Int = 0): Deferred<RpcResponse<List<Message>>> =
        client.apiAsync { history(peer, limit, offsetId, offsetDate, addOffset, maxId, minId) }

    fun historyAsync(peerId: Long, limit: Int = 100, offsetId: Int = 0, offsetDate: Int = 0, addOffset: Int = 0, maxId: Int = 0, minId: Int = 0): Deferred<RpcResponse<List<Message>>> =
        client.apiAsync { history(client.inputPeerFromId(peerId), limit, offsetId, offsetDate, addOffset, maxId, minId) }

    suspend fun history(peerId: Long, limit: Int = 100, offsetId: Int = 0, offsetDate: Int = 0, addOffset: Int = 0, maxId: Int = 0, minId: Int = 0): RpcResponse<List<Message>> =
        history(client.inputPeerFromId(peerId), limit, offsetId, offsetDate, addOffset, maxId, minId)

    suspend fun history(peer: InputPeer, limit: Int = 100, offsetId: Int = 0, offsetDate: Int = 0, addOffset: Int = 0, maxId: Int = 0, minId: Int = 0): RpcResponse<List<Message>> {
        val want = minOf(limit.coerceAtLeast(0), HISTORY_CAP)
        if (want == 0) return RpcResponse(emptyList(), null)
        val out = ArrayList<Message>(minOf(want, HISTORY_PAGE))
        var nextOffset = offsetId
        var first = true
        while (out.size < want) {
            val batch = minOf(HISTORY_PAGE, want - out.size)
            val raw = client.invoke(
                MessagesGetHistory(
                    peer = peer,
                    offsetId = nextOffset,
                    offsetDate = if (first) offsetDate else 0,
                    addOffset = if (first) addOffset else 0,
                    limit = batch,
                    maxId = maxId,
                    minId = minId,
                    hash = 0L,
                ),
            )
            val err = raw.error
            if (err != null) return if (out.isEmpty()) RpcResponse(null, err) else RpcResponse(out, null)
            val pack = raw.result!!.unpack()
            client.rememberUsers(pack.users)
            client.rememberChats(pack.chats)
            if (pack.messages.isEmpty()) break
            out += pack.messages
            val lastId = pack.messages.last().id
            if (lastId == 0 || lastId == nextOffset) break
            nextOffset = lastId
            first = false
            if (pack.messages.size < batch) break
        }
        return RpcResponse(if (out.size > want) out.subList(0, want) else out, null)
    }

    fun getAsync(peer: InputPeer, vararg ids: Int): Deferred<RpcResponse<List<Message>>> = client.apiAsync { get(peer, *ids) }

    fun getAsync(peerId: Long, vararg ids: Int): Deferred<RpcResponse<List<Message>>> = client.apiAsync { get(client.inputPeerFromId(peerId), *ids) }

    suspend fun get(peerId: Long, vararg ids: Int): RpcResponse<List<Message>> = get(client.inputPeerFromId(peerId), *ids)

    suspend fun get(peer: InputPeer, vararg ids: Int): RpcResponse<List<Message>> {
        if (ids.isEmpty()) return RpcResponse(emptyList(), null)
        val input = ids.map { InputMessageID(it) as InputMessage }
        val channel = peer.asInputChannel()
        val raw = if (channel != null) {
            client.invoke(ChannelsGetMessages(channel, input))
        } else {
            client.invoke(MessagesGetMessages(input))
        }
        raw.result?.let {
            val pack = it.unpack()
            client.rememberUsers(pack.users)
            client.rememberChats(pack.chats)
        }
        return raw.map { it.unpack().messages }
    }

    fun editAsync(peer: InputPeer, id: Int, text: String, noWebpage: Boolean = false, invertMedia: Boolean = false, entities: List<MessageEntity>? = null, replyMarkup: ReplyMarkup? = null, scheduleDate: Int = 0): Deferred<RpcResponse<Updates>> =
        client.apiAsync { edit(peer, id, text, noWebpage, invertMedia, entities, replyMarkup, scheduleDate) }

    fun editAsync(peerId: Long, id: Int, text: String, noWebpage: Boolean = false, invertMedia: Boolean = false, entities: List<MessageEntity>? = null, replyMarkup: ReplyMarkup? = null, scheduleDate: Int = 0): Deferred<RpcResponse<Updates>> =
        client.apiAsync { edit(client.inputPeerFromId(peerId), id, text, noWebpage, invertMedia, entities, replyMarkup, scheduleDate) }

    suspend fun edit(peerId: Long, id: Int, text: String, noWebpage: Boolean = false, invertMedia: Boolean = false, entities: List<MessageEntity>? = null, replyMarkup: ReplyMarkup? = null, scheduleDate: Int = 0): RpcResponse<Updates> =
        edit(client.inputPeerFromId(peerId), id, text, noWebpage, invertMedia, entities, replyMarkup, scheduleDate)

    suspend fun edit(peer: InputPeer, id: Int, text: String, noWebpage: Boolean = false, invertMedia: Boolean = false, entities: List<MessageEntity>? = null, replyMarkup: ReplyMarkup? = null, scheduleDate: Int = 0): RpcResponse<Updates> {
        require(text.isNotEmpty()) { "empty message" }
        return client.invoke(
            MessagesEditMessage(
                peer = peer,
                id = id,
                noWebpage = noWebpage,
                invertMedia = invertMedia,
                message = text,
                entities = entities,
                replyMarkup = replyMarkup,
                scheduleDate = scheduleDate,
            ),
        )
    }

    fun deleteAsync(peer: InputPeer, ids: IntArray, revoke: Boolean = true): Deferred<RpcResponse<MessagesAffectedMessages>> =
        client.apiAsync { delete(peer, ids, revoke) }

    fun deleteAsync(peerId: Long, ids: IntArray, revoke: Boolean = true): Deferred<RpcResponse<MessagesAffectedMessages>> =
        client.apiAsync { delete(client.inputPeerFromId(peerId), ids, revoke) }

    suspend fun delete(peerId: Long, ids: IntArray, revoke: Boolean = true): RpcResponse<MessagesAffectedMessages> =
        delete(client.inputPeerFromId(peerId), ids, revoke)

    suspend fun delete(peer: InputPeer, ids: IntArray, revoke: Boolean = true): RpcResponse<MessagesAffectedMessages> {
        require(ids.isNotEmpty()) { "empty ids" }
        val channel = peer.asInputChannel()
        return if (channel != null) {
            client.invoke(ChannelsDeleteMessages(channel, ids))
        } else {
            client.invoke(MessagesDeleteMessages(ids, revoke))
        }
    }

    fun forwardAsync(
        to: InputPeer,
        from: InputPeer,
        ids: IntArray,
        silent: Boolean = false,
        dropAuthor: Boolean = false,
        dropMediaCaptions: Boolean = false,
        noforwards: Boolean = false,
        topMsgId: Int = 0,
        scheduleDate: Int = 0,
        sendAs: InputPeer? = null,
    ): Deferred<RpcResponse<Updates>> =
        client.apiAsync { forward(to, from, ids, silent, dropAuthor, dropMediaCaptions, noforwards, topMsgId, scheduleDate, sendAs) }

    fun forwardAsync(
        toId: Long,
        fromId: Long,
        ids: IntArray,
        silent: Boolean = false,
        dropAuthor: Boolean = false,
        dropMediaCaptions: Boolean = false,
        noforwards: Boolean = false,
        topMsgId: Int = 0,
        scheduleDate: Int = 0,
        sendAs: InputPeer? = null,
    ): Deferred<RpcResponse<Updates>> =
        client.apiAsync { forward(client.inputPeerFromId(toId), client.inputPeerFromId(fromId), ids, silent, dropAuthor, dropMediaCaptions, noforwards, topMsgId, scheduleDate, sendAs) }

    suspend fun forward(
        toId: Long,
        fromId: Long,
        ids: IntArray,
        silent: Boolean = false,
        dropAuthor: Boolean = false,
        dropMediaCaptions: Boolean = false,
        noforwards: Boolean = false,
        topMsgId: Int = 0,
        scheduleDate: Int = 0,
        sendAs: InputPeer? = null,
    ): RpcResponse<Updates> =
        forward(client.inputPeerFromId(toId), client.inputPeerFromId(fromId), ids, silent, dropAuthor, dropMediaCaptions, noforwards, topMsgId, scheduleDate, sendAs)

    suspend fun forward(
        to: InputPeer,
        from: InputPeer,
        ids: IntArray,
        silent: Boolean = false,
        dropAuthor: Boolean = false,
        dropMediaCaptions: Boolean = false,
        noforwards: Boolean = false,
        topMsgId: Int = 0,
        scheduleDate: Int = 0,
        sendAs: InputPeer? = null,
    ): RpcResponse<Updates> {
        require(ids.isNotEmpty()) { "empty ids" }
        return client.invoke(
            MessagesForwardMessages(
                fromPeer = from,
                id = ids,
                randomId = nextRandomIds(ids.size),
                toPeer = to,
                silent = silent,
                dropAuthor = dropAuthor,
                dropMediaCaptions = dropMediaCaptions,
                noforwards = noforwards,
                topMsgId = topMsgId,
                scheduleDate = scheduleDate,
                sendAs = sendAs,
            ),
        )
    }

    fun dialogsAsync(limit: Int = 100, offsetDate: Int = 0, offsetId: Int = 0, offsetPeer: InputPeer = InputPeerEmpty, excludePinned: Boolean = false, folderId: Int = 0): Deferred<RpcResponse<List<Dialog>>> =
        client.apiAsync { dialogs(limit, offsetDate, offsetId, offsetPeer, excludePinned, folderId) }

    suspend fun dialogs(limit: Int = 100, offsetDate: Int = 0, offsetId: Int = 0, offsetPeer: InputPeer = InputPeerEmpty, excludePinned: Boolean = false, folderId: Int = 0): RpcResponse<List<Dialog>> {
        val page = limit.coerceIn(1, HISTORY_PAGE)
        val raw = client.invoke(
            MessagesGetDialogs(
                offsetDate = offsetDate,
                offsetId = offsetId,
                offsetPeer = offsetPeer,
                limit = page,
                hash = 0L,
                excludePinned = excludePinned,
                folderId = folderId,
            ),
        )
        raw.result?.let {
            client.rememberUsers(it.users())
            client.rememberChats(it.chats())
        }
        return raw.map { it.dialogs() }
    }

    fun readAsync(peer: InputPeer, maxId: Int = 0): Deferred<RpcResponse<Boolean>> = client.apiAsync { read(peer, maxId) }

    fun readAsync(peerId: Long, maxId: Int = 0): Deferred<RpcResponse<Boolean>> = client.apiAsync { read(client.inputPeerFromId(peerId), maxId) }

    suspend fun read(peerId: Long, maxId: Int = 0): RpcResponse<Boolean> = read(client.inputPeerFromId(peerId), maxId)

    suspend fun read(peer: InputPeer, maxId: Int = 0): RpcResponse<Boolean> {
        val channel = peer.asInputChannel()
        return if (channel != null) {
            client.invoke(ChannelsReadHistory(channel, maxId)).map { it === BoolTrue }
        } else {
            client.invoke(MessagesReadHistory(peer, maxId)).map { true }
        }
    }

    private suspend fun sendMedia(peer: InputPeer, media: InputMedia, caption: String, randomId: Long, silent: Boolean, background: Boolean, clearDraft: Boolean, noforwards: Boolean, updateStickersetsOrder: Boolean, invertMedia: Boolean, allowPaidFloodskip: Boolean, replyTo: InputReplyTo?, replyMarkup: ReplyMarkup?, entities: List<MessageEntity>?, scheduleDate: Int, scheduleRepeatPeriod: Int, sendAs: InputPeer?, sendAsId: Long, quickReplyShortcut: InputQuickReplyShortcut?, effect: Long, allowPaidStars: Long, suggestedPost: SuggestedPost?): RpcResponse<SentMessage> {
        val raw = client.invoke(MessagesSendMedia(peer = peer, media = media, message = caption, randomId = nextRandomId(randomId), silent = silent, background = background, clearDraft = clearDraft, noforwards = noforwards, updateStickersetsOrder = updateStickersetsOrder, invertMedia = invertMedia, allowPaidFloodskip = allowPaidFloodskip, replyTo = replyTo, replyMarkup = replyMarkup, entities = entities, scheduleDate = scheduleDate, scheduleRepeatPeriod = scheduleRepeatPeriod, sendAs = sendAs ?: sendAsId.takeIf { it != 0L }?.let { client.inputPeerFromId(it) }, quickReplyShortcut = quickReplyShortcut, effect = effect, allowPaidStars = allowPaidStars, suggestedPost = suggestedPost))
        return raw.map { SentMessage.from(it, caption) }
    }

    private suspend fun sendUploadedDocument(peer: InputPeer, source: ByteSource, fileName: String, mimeType: String, attributes: List<DocumentAttribute>, caption: String, thumb: ByteSource?, forceFile: Boolean, spoiler: Boolean, ttlSeconds: Int, randomId: Long, silent: Boolean, background: Boolean, clearDraft: Boolean, noforwards: Boolean, updateStickersetsOrder: Boolean, invertMedia: Boolean, allowPaidFloodskip: Boolean, replyTo: InputReplyTo?, replyMarkup: ReplyMarkup?, entities: List<MessageEntity>?, scheduleDate: Int, scheduleRepeatPeriod: Int, sendAs: InputPeer?, sendAsId: Long, quickReplyShortcut: InputQuickReplyShortcut?, effect: Long, allowPaidStars: Long, suggestedPost: SuggestedPost?): RpcResponse<SentMessage> {
        val file = Upload.saveFile(client, source, fileName)
        if (file.error != null) return RpcResponse(null, file.error)
        val thumbFile = thumb?.let { Upload.saveFile(client, it, "thumb.jpg") }
        if (thumbFile?.error != null) return RpcResponse(null, thumbFile.error)
        return sendMedia(peer, InputMediaUploadedDocument(file = file.result!!, mimeType = mimeType, attributes = attributes, forceFile = forceFile, spoiler = spoiler, thumb = thumbFile?.result, ttlSeconds = ttlSeconds), caption, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost)
    }

    private fun mimeFromName(name: String): String = when (name.substringAfterLast('.', "").lowercase()) {
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        "gif" -> "image/gif"
        "webp" -> "image/webp"
        "webm" -> "video/webm"
        "mov" -> "video/quicktime"
        "mkv" -> "video/x-matroska"
        "3gp" -> "video/3gpp"
        "mp4", "m4v" -> "video/mp4"
        "ogg", "opus" -> "audio/ogg"
        "mp3" -> "audio/mpeg"
        "m4a" -> "audio/mp4"
        "wav" -> "audio/wav"
        "pdf" -> "application/pdf"
        "zip" -> "application/zip"
        "json" -> "application/json"
        "txt" -> "text/plain"
        else -> "application/octet-stream"
    }

    private fun nextRandomId(randomId: Long): Long {
        if (randomId != 0L) return randomId
        var id = PlatformCrypto.randomBytes(8).readLongLe()
        if (id == 0L) id = 1L
        return id
    }

    private fun nextRandomIds(n: Int): LongArray = LongArray(n) { nextRandomId(0) }
}

private class MsgPack(val messages: List<Message>, val users: List<User>, val chats: List<Chat>)

private fun MessagesMessages.unpack(): MsgPack = when (this) {
    is MessagesMessagesCtor -> MsgPack(messages, users, chats)
    is MessagesMessagesSlice -> MsgPack(messages, users, chats)
    is MessagesChannelMessages -> MsgPack(messages, users, chats)
    is MessagesMessagesNotModified -> MsgPack(emptyList(), emptyList(), emptyList())
}

private fun MessagesDialogs.dialogs(): List<Dialog> = when (this) {
    is MessagesDialogsCtor -> dialogs
    is MessagesDialogsSlice -> dialogs
    is MessagesDialogsNotModified -> emptyList()
}

private fun MessagesDialogs.users(): List<User> = when (this) {
    is MessagesDialogsCtor -> users
    is MessagesDialogsSlice -> users
    is MessagesDialogsNotModified -> emptyList()
}

private fun MessagesDialogs.chats(): List<Chat> = when (this) {
    is MessagesDialogsCtor -> chats
    is MessagesDialogsSlice -> chats
    is MessagesDialogsNotModified -> emptyList()
}
