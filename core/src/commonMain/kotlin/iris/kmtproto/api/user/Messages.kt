package iris.kmtproto.api.user

import iris.kmtproto.client.RpcResponse
import iris.kmtproto.client.SentMessage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.io.ByteArrayByteSource
import iris.kmtproto.io.ByteSource
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.gen.DocumentAttribute
import iris.kmtproto.tl.gen.DocumentAttributeAnimated
import iris.kmtproto.tl.gen.DocumentAttributeAudio
import iris.kmtproto.tl.gen.DocumentAttributeFilename
import iris.kmtproto.tl.gen.DocumentAttributeVideo
import iris.kmtproto.tl.gen.InputMedia
import iris.kmtproto.tl.gen.InputMediaUploadedDocument
import iris.kmtproto.tl.gen.InputMediaUploadedPhoto
import iris.kmtproto.tl.gen.InputPeer
import iris.kmtproto.tl.gen.InputQuickReplyShortcut
import iris.kmtproto.tl.gen.InputReplyTo
import iris.kmtproto.tl.gen.InputRichMessage
import iris.kmtproto.tl.gen.MessageEntity
import iris.kmtproto.tl.gen.MessagesSendMedia
import iris.kmtproto.tl.gen.MessagesSendMessage
import iris.kmtproto.tl.gen.ReplyMarkup
import iris.kmtproto.tl.gen.SuggestedPost
import kotlinx.coroutines.Deferred

class Messages(
    private val client: TelegramClient,
) {
    fun sendAsync(peer: InputPeer, text: String = "", randomId: Long? = null, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { send(peer, text, randomId, noWebpage, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost, richMessage) }

    fun sendAsync(peerId: Long, text: String = "", randomId: Long? = null, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { send(client.inputPeerFromId(peerId), text, randomId, noWebpage, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost, richMessage) }

    suspend fun send(peerId: Long, text: String = "", randomId: Long? = null, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): RpcResponse<SentMessage> =
        send(client.inputPeerFromId(peerId), text, randomId, noWebpage, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost, richMessage)

    suspend fun send(peer: InputPeer, text: String = "", randomId: Long? = null, noWebpage: Boolean = false, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null, richMessage: InputRichMessage? = null): RpcResponse<SentMessage> {
        require(text.isNotEmpty() || richMessage != null) { "empty message" }
        val raw = client.invoke(MessagesSendMessage(peer = peer, message = text, randomId = nextRandomId(randomId), noWebpage = noWebpage, silent = silent, background = background, clearDraft = clearDraft, noforwards = noforwards, updateStickersetsOrder = updateStickersetsOrder, invertMedia = invertMedia, allowPaidFloodskip = allowPaidFloodskip, replyTo = replyTo, replyMarkup = replyMarkup, entities = entities, scheduleDate = scheduleDate, scheduleRepeatPeriod = scheduleRepeatPeriod, sendAs = sendAs ?: sendAsId?.let { client.inputPeerFromId(it) }, quickReplyShortcut = quickReplyShortcut, effect = effect, allowPaidStars = allowPaidStars, suggestedPost = suggestedPost, richMessage = richMessage))
        return raw.map { SentMessage.from(it, text) }
    }

    fun sendPhotoAsync(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int? = null, randomId: Long? = null, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendPhoto(peer, source, caption, fileName, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost) }

    fun sendPhotoAsync(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int? = null, randomId: Long? = null, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendPhoto(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost) }

    suspend fun sendPhoto(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int? = null, randomId: Long? = null, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null): RpcResponse<SentMessage> =
        sendPhoto(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost)

    suspend fun sendPhoto(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "photo.jpg", spoiler: Boolean = false, ttlSeconds: Int? = null, randomId: Long? = null, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null): RpcResponse<SentMessage> {
        val file = Upload.saveFile(client, source, fileName)
        val err = file.error
        if (err != null) return RpcResponse(null, err)
        return sendMedia(peer, InputMediaUploadedPhoto(file = file.result!!, spoiler = spoiler, ttlSeconds = ttlSeconds), caption, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost)
    }

    fun sendVideoAsync(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "video.mp4", mimeType: String? = null, duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteSource? = null, supportsStreaming: Boolean = true, roundMessage: Boolean = false, nosound: Boolean = false, spoiler: Boolean = false, ttlSeconds: Int? = null, randomId: Long? = null, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendVideo(peer, source, caption, fileName, mimeType, duration, width, height, thumb, supportsStreaming, roundMessage, nosound, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost) }

    fun sendVideoAsync(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "video.mp4", mimeType: String? = null, duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteArray? = null, supportsStreaming: Boolean = true, roundMessage: Boolean = false, nosound: Boolean = false, spoiler: Boolean = false, ttlSeconds: Int? = null, randomId: Long? = null, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendVideo(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, mimeType, duration, width, height, thumb?.let { ByteArrayByteSource(it) }, supportsStreaming, roundMessage, nosound, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost) }

    suspend fun sendVideo(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "video.mp4", mimeType: String? = null, duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteArray? = null, supportsStreaming: Boolean = true, roundMessage: Boolean = false, nosound: Boolean = false, spoiler: Boolean = false, ttlSeconds: Int? = null, randomId: Long? = null, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null): RpcResponse<SentMessage> =
        sendVideo(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, mimeType, duration, width, height, thumb?.let { ByteArrayByteSource(it) }, supportsStreaming, roundMessage, nosound, spoiler, ttlSeconds, randomId, silent, background, clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost)

    suspend fun sendVideo(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "video.mp4", mimeType: String? = null, duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteSource? = null, supportsStreaming: Boolean = true, roundMessage: Boolean = false, nosound: Boolean = false, spoiler: Boolean = false, ttlSeconds: Int? = null, randomId: Long? = null, silent: Boolean = false, background: Boolean = false, clearDraft: Boolean = false, noforwards: Boolean = false, updateStickersetsOrder: Boolean = false, invertMedia: Boolean = false, allowPaidFloodskip: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null, entities: List<MessageEntity>? = null, scheduleDate: Int? = null, scheduleRepeatPeriod: Int? = null, sendAs: InputPeer? = null, sendAsId: Long? = null, quickReplyShortcut: InputQuickReplyShortcut? = null, effect: Long? = null, allowPaidStars: Long? = null, suggestedPost: SuggestedPost? = null): RpcResponse<SentMessage> =
        sendUploadedDocument(peer = peer, source = source, fileName = fileName, mimeType = mimeType ?: mimeFromName(fileName), attributes = listOf(DocumentAttributeVideo(duration = duration, w = width, h = height, roundMessage = roundMessage, supportsStreaming = supportsStreaming, nosound = nosound), DocumentAttributeFilename(fileName)), caption = caption, thumb = thumb, forceFile = false, spoiler = spoiler, ttlSeconds = ttlSeconds, randomId = randomId, silent = silent, background = background, clearDraft = clearDraft, noforwards = noforwards, updateStickersetsOrder = updateStickersetsOrder, invertMedia = invertMedia, allowPaidFloodskip = allowPaidFloodskip, replyTo = replyTo, replyMarkup = replyMarkup, entities = entities, scheduleDate = scheduleDate, scheduleRepeatPeriod = scheduleRepeatPeriod, sendAs = sendAs, sendAsId = sendAsId, quickReplyShortcut = quickReplyShortcut, effect = effect, allowPaidStars = allowPaidStars, suggestedPost = suggestedPost)

    fun sendVoiceAsync(peer: InputPeer, source: ByteSource, duration: Int = 0, fileName: String = "voice.ogg", waveform: ByteArray? = null, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendVoice(peer, source, duration, fileName, waveform, caption, silent, replyTo, replyMarkup) }

    fun sendVoiceAsync(peerId: Long, bytes: ByteArray, duration: Int = 0, fileName: String = "voice.ogg", waveform: ByteArray? = null, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendVoice(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), duration, fileName, waveform, caption, silent, replyTo, replyMarkup) }

    suspend fun sendVoice(peerId: Long, bytes: ByteArray, duration: Int = 0, fileName: String = "voice.ogg", waveform: ByteArray? = null, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendVoice(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), duration, fileName, waveform, caption, silent, replyTo, replyMarkup)

    suspend fun sendVoice(peer: InputPeer, source: ByteSource, duration: Int = 0, fileName: String = "voice.ogg", waveform: ByteArray? = null, caption: String = "", silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendUploadedDocument(peer = peer, source = source, fileName = fileName, mimeType = "audio/ogg", attributes = listOf(DocumentAttributeAudio(duration = duration, voice = true, waveform = waveform)), caption = caption, thumb = null, forceFile = false, spoiler = false, ttlSeconds = null, randomId = null, silent = silent, background = false, clearDraft = false, noforwards = false, updateStickersetsOrder = false, invertMedia = false, allowPaidFloodskip = false, replyTo = replyTo, replyMarkup = replyMarkup, entities = null, scheduleDate = null, scheduleRepeatPeriod = null, sendAs = null, sendAsId = null, quickReplyShortcut = null, effect = null, allowPaidStars = null, suggestedPost = null)

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
        sendUploadedDocument(peer = peer, source = source, fileName = fileName, mimeType = mimeType ?: mimeFromName(fileName), attributes = listOf(DocumentAttributeFilename(fileName)), caption = caption, thumb = thumb, forceFile = true, spoiler = false, ttlSeconds = null, randomId = null, silent = silent, background = false, clearDraft = false, noforwards = false, updateStickersetsOrder = false, invertMedia = false, allowPaidFloodskip = false, replyTo = replyTo, replyMarkup = replyMarkup, entities = null, scheduleDate = null, scheduleRepeatPeriod = null, sendAs = null, sendAsId = null, quickReplyShortcut = null, effect = null, allowPaidStars = null, suggestedPost = null)

    fun sendGifAsync(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteSource? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendGif(peer, source, caption, fileName, duration, width, height, thumb, spoiler, silent, replyTo, replyMarkup) }

    fun sendGifAsync(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteArray? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): Deferred<RpcResponse<SentMessage>> =
        client.apiAsync { sendGif(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, duration, width, height, thumb?.let { ByteArrayByteSource(it) }, spoiler, silent, replyTo, replyMarkup) }

    suspend fun sendGif(peerId: Long, bytes: ByteArray, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteArray? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendGif(client.inputPeerFromId(peerId), ByteArrayByteSource(bytes), caption, fileName, duration, width, height, thumb?.let { ByteArrayByteSource(it) }, spoiler, silent, replyTo, replyMarkup)

    suspend fun sendGif(peer: InputPeer, source: ByteSource, caption: String = "", fileName: String = "animation.mp4", duration: Double = 0.0, width: Int = 0, height: Int = 0, thumb: ByteSource? = null, spoiler: Boolean = false, silent: Boolean = false, replyTo: InputReplyTo? = null, replyMarkup: ReplyMarkup? = null): RpcResponse<SentMessage> =
        sendUploadedDocument(peer = peer, source = source, fileName = fileName, mimeType = if (fileName.endsWith(".gif", ignoreCase = true)) "image/gif" else "video/mp4", attributes = listOf(DocumentAttributeAnimated, DocumentAttributeVideo(duration = duration, w = width, h = height, supportsStreaming = true), DocumentAttributeFilename(fileName)), caption = caption, thumb = thumb, forceFile = false, spoiler = spoiler, ttlSeconds = null, randomId = null, silent = silent, background = false, clearDraft = false, noforwards = false, updateStickersetsOrder = false, invertMedia = false, allowPaidFloodskip = false, replyTo = replyTo, replyMarkup = replyMarkup, entities = null, scheduleDate = null, scheduleRepeatPeriod = null, sendAs = null, sendAsId = null, quickReplyShortcut = null, effect = null, allowPaidStars = null, suggestedPost = null)

    private suspend fun sendMedia(peer: InputPeer, media: InputMedia, caption: String, randomId: Long?, silent: Boolean, background: Boolean, clearDraft: Boolean, noforwards: Boolean, updateStickersetsOrder: Boolean, invertMedia: Boolean, allowPaidFloodskip: Boolean, replyTo: InputReplyTo?, replyMarkup: ReplyMarkup?, entities: List<MessageEntity>?, scheduleDate: Int?, scheduleRepeatPeriod: Int?, sendAs: InputPeer?, sendAsId: Long?, quickReplyShortcut: InputQuickReplyShortcut?, effect: Long?, allowPaidStars: Long?, suggestedPost: SuggestedPost?): RpcResponse<SentMessage> {
        val raw = client.invoke(MessagesSendMedia(peer = peer, media = media, message = caption, randomId = nextRandomId(randomId), silent = silent, background = background, clearDraft = clearDraft, noforwards = noforwards, updateStickersetsOrder = updateStickersetsOrder, invertMedia = invertMedia, allowPaidFloodskip = allowPaidFloodskip, replyTo = replyTo, replyMarkup = replyMarkup, entities = entities, scheduleDate = scheduleDate, scheduleRepeatPeriod = scheduleRepeatPeriod, sendAs = sendAs ?: sendAsId?.let { client.inputPeerFromId(it) }, quickReplyShortcut = quickReplyShortcut, effect = effect, allowPaidStars = allowPaidStars, suggestedPost = suggestedPost))
        return raw.map { SentMessage.from(it, caption) }
    }

    private suspend fun sendUploadedDocument(peer: InputPeer, source: ByteSource, fileName: String, mimeType: String, attributes: List<DocumentAttribute>, caption: String, thumb: ByteSource?, forceFile: Boolean, spoiler: Boolean, ttlSeconds: Int?, randomId: Long?, silent: Boolean, background: Boolean, clearDraft: Boolean, noforwards: Boolean, updateStickersetsOrder: Boolean, invertMedia: Boolean, allowPaidFloodskip: Boolean, replyTo: InputReplyTo?, replyMarkup: ReplyMarkup?, entities: List<MessageEntity>?, scheduleDate: Int?, scheduleRepeatPeriod: Int?, sendAs: InputPeer?, sendAsId: Long?, quickReplyShortcut: InputQuickReplyShortcut?, effect: Long?, allowPaidStars: Long?, suggestedPost: SuggestedPost?): RpcResponse<SentMessage> {
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

    private fun nextRandomId(randomId: Long?): Long {
        if (randomId != null && randomId != 0L) return randomId
        var id = PlatformCrypto.randomBytes(8).readLongLe()
        if (id == 0L) id = 1L
        return id
    }
}
