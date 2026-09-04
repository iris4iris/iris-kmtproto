package iris.kmtproto.api.user

import iris.kmtproto.client.SentMessage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.crypto.PlatformCrypto
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

    fun sendPhotoAsync(
        peer: InputPeer,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "photo.jpg",
        spoiler: Boolean = false,
        ttlSeconds: Int? = null,
        randomId: Long? = null,
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
    ): Deferred<SentMessage> = client.apiAsync {
        sendPhoto(
            peer, bytes, caption, fileName, spoiler, ttlSeconds, randomId, silent, background,
            clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip,
            replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId,
            quickReplyShortcut, effect, allowPaidStars, suggestedPost,
        )
    }

    fun sendPhotoAsync(
        peerId: Long,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "photo.jpg",
        spoiler: Boolean = false,
        ttlSeconds: Int? = null,
        randomId: Long? = null,
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
    ): Deferred<SentMessage> = client.apiAsync {
        sendPhoto(
            peerId, bytes, caption, fileName, spoiler, ttlSeconds, randomId, silent, background,
            clearDraft, noforwards, updateStickersetsOrder, invertMedia, allowPaidFloodskip,
            replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod, sendAs, sendAsId,
            quickReplyShortcut, effect, allowPaidStars, suggestedPost,
        )
    }

    suspend fun sendPhoto(
        peerId: Long,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "photo.jpg",
        spoiler: Boolean = false,
        ttlSeconds: Int? = null,
        randomId: Long? = null,
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
    ): SentMessage = sendPhoto(
        peer = client.inputPeerFromId(peerId),
        bytes = bytes,
        caption = caption,
        fileName = fileName,
        spoiler = spoiler,
        ttlSeconds = ttlSeconds,
        randomId = randomId,
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
    )

    suspend fun sendPhoto(
        peer: InputPeer,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "photo.jpg",
        spoiler: Boolean = false,
        ttlSeconds: Int? = null,
        randomId: Long? = null,
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
    ): SentMessage {
        val file = Upload(client).saveFile(bytes, fileName)
        return sendMedia(
            peer = peer,
            media = InputMediaUploadedPhoto(
                file = file,
                spoiler = spoiler,
                ttlSeconds = ttlSeconds,
            ),
            caption = caption,
            randomId = randomId,
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
        )
    }

    fun sendVideoAsync(
        peer: InputPeer,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "video.mp4",
        mimeType: String? = null,
        duration: Double = 0.0,
        width: Int = 0,
        height: Int = 0,
        thumb: ByteArray? = null,
        supportsStreaming: Boolean = true,
        roundMessage: Boolean = false,
        nosound: Boolean = false,
        spoiler: Boolean = false,
        ttlSeconds: Int? = null,
        randomId: Long? = null,
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
    ): Deferred<SentMessage> = client.apiAsync {
        sendVideo(
            peer, bytes, caption, fileName, mimeType, duration, width, height, thumb,
            supportsStreaming, roundMessage, nosound, spoiler, ttlSeconds, randomId, silent,
            background, clearDraft, noforwards, updateStickersetsOrder, invertMedia,
            allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod,
            sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost,
        )
    }

    fun sendVideoAsync(
        peerId: Long,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "video.mp4",
        mimeType: String? = null,
        duration: Double = 0.0,
        width: Int = 0,
        height: Int = 0,
        thumb: ByteArray? = null,
        supportsStreaming: Boolean = true,
        roundMessage: Boolean = false,
        nosound: Boolean = false,
        spoiler: Boolean = false,
        ttlSeconds: Int? = null,
        randomId: Long? = null,
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
    ): Deferred<SentMessage> = client.apiAsync {
        sendVideo(
            peerId, bytes, caption, fileName, mimeType, duration, width, height, thumb,
            supportsStreaming, roundMessage, nosound, spoiler, ttlSeconds, randomId, silent,
            background, clearDraft, noforwards, updateStickersetsOrder, invertMedia,
            allowPaidFloodskip, replyTo, replyMarkup, entities, scheduleDate, scheduleRepeatPeriod,
            sendAs, sendAsId, quickReplyShortcut, effect, allowPaidStars, suggestedPost,
        )
    }

    suspend fun sendVideo(
        peerId: Long,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "video.mp4",
        mimeType: String? = null,
        duration: Double = 0.0,
        width: Int = 0,
        height: Int = 0,
        thumb: ByteArray? = null,
        supportsStreaming: Boolean = true,
        roundMessage: Boolean = false,
        nosound: Boolean = false,
        spoiler: Boolean = false,
        ttlSeconds: Int? = null,
        randomId: Long? = null,
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
    ): SentMessage = sendVideo(
        peer = client.inputPeerFromId(peerId),
        bytes = bytes,
        caption = caption,
        fileName = fileName,
        mimeType = mimeType,
        duration = duration,
        width = width,
        height = height,
        thumb = thumb,
        supportsStreaming = supportsStreaming,
        roundMessage = roundMessage,
        nosound = nosound,
        spoiler = spoiler,
        ttlSeconds = ttlSeconds,
        randomId = randomId,
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
    )

    suspend fun sendVideo(
        peer: InputPeer,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "video.mp4",
        mimeType: String? = null,
        duration: Double = 0.0,
        width: Int = 0,
        height: Int = 0,
        thumb: ByteArray? = null,
        supportsStreaming: Boolean = true,
        roundMessage: Boolean = false,
        nosound: Boolean = false,
        spoiler: Boolean = false,
        ttlSeconds: Int? = null,
        randomId: Long? = null,
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
    ): SentMessage {
        val upload = Upload(client)
        val file = upload.saveFile(bytes, fileName)
        val thumbFile = thumb?.let { upload.saveFile(it, "thumb.jpg") }
        return sendMedia(
            peer = peer,
            media = InputMediaUploadedDocument(
                file = file,
                mimeType = mimeType ?: mimeFromName(fileName),
                attributes = listOf(
                    DocumentAttributeVideo(
                        duration = duration,
                        w = width,
                        h = height,
                        roundMessage = roundMessage,
                        supportsStreaming = supportsStreaming,
                        nosound = nosound,
                    ),
                    DocumentAttributeFilename(fileName),
                ),
                spoiler = spoiler,
                thumb = thumbFile,
                ttlSeconds = ttlSeconds,
            ),
            caption = caption,
            randomId = randomId,
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
        )
    }

    fun sendVoiceAsync(
        peer: InputPeer,
        bytes: ByteArray,
        duration: Int = 0,
        fileName: String = "voice.ogg",
        waveform: ByteArray? = null,
        caption: String = "",
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): Deferred<SentMessage> = client.apiAsync {
        sendVoice(peer, bytes, duration, fileName, waveform, caption, silent, replyTo, replyMarkup)
    }

    fun sendVoiceAsync(
        peerId: Long,
        bytes: ByteArray,
        duration: Int = 0,
        fileName: String = "voice.ogg",
        waveform: ByteArray? = null,
        caption: String = "",
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): Deferred<SentMessage> = client.apiAsync {
        sendVoice(peerId, bytes, duration, fileName, waveform, caption, silent, replyTo, replyMarkup)
    }

    suspend fun sendVoice(
        peerId: Long,
        bytes: ByteArray,
        duration: Int = 0,
        fileName: String = "voice.ogg",
        waveform: ByteArray? = null,
        caption: String = "",
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): SentMessage = sendVoice(
        client.inputPeerFromId(peerId), bytes, duration, fileName, waveform, caption, silent, replyTo, replyMarkup,
    )

    suspend fun sendVoice(
        peer: InputPeer,
        bytes: ByteArray,
        duration: Int = 0,
        fileName: String = "voice.ogg",
        waveform: ByteArray? = null,
        caption: String = "",
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): SentMessage = sendUploadedDocument(
        peer = peer,
        bytes = bytes,
        fileName = fileName,
        mimeType = "audio/ogg",
        attributes = listOf(
            DocumentAttributeAudio(duration = duration, voice = true, waveform = waveform),
        ),
        caption = caption,
        silent = silent,
        replyTo = replyTo,
        replyMarkup = replyMarkup,
    )

    fun sendVideoNoteAsync(
        peer: InputPeer,
        bytes: ByteArray,
        duration: Double = 0.0,
        length: Int = 384,
        fileName: String = "video_note.mp4",
        thumb: ByteArray? = null,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): Deferred<SentMessage> = client.apiAsync {
        sendVideoNote(peer, bytes, duration, length, fileName, thumb, silent, replyTo, replyMarkup)
    }

    fun sendVideoNoteAsync(
        peerId: Long,
        bytes: ByteArray,
        duration: Double = 0.0,
        length: Int = 384,
        fileName: String = "video_note.mp4",
        thumb: ByteArray? = null,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): Deferred<SentMessage> = client.apiAsync {
        sendVideoNote(peerId, bytes, duration, length, fileName, thumb, silent, replyTo, replyMarkup)
    }

    suspend fun sendVideoNote(
        peerId: Long,
        bytes: ByteArray,
        duration: Double = 0.0,
        length: Int = 384,
        fileName: String = "video_note.mp4",
        thumb: ByteArray? = null,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): SentMessage = sendVideoNote(
        client.inputPeerFromId(peerId), bytes, duration, length, fileName, thumb, silent, replyTo, replyMarkup,
    )

    suspend fun sendVideoNote(
        peer: InputPeer,
        bytes: ByteArray,
        duration: Double = 0.0,
        length: Int = 384,
        fileName: String = "video_note.mp4",
        thumb: ByteArray? = null,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): SentMessage = sendVideo(
        peer = peer,
        bytes = bytes,
        fileName = fileName,
        duration = duration,
        width = length,
        height = length,
        thumb = thumb,
        supportsStreaming = true,
        roundMessage = true,
        silent = silent,
        replyTo = replyTo,
        replyMarkup = replyMarkup,
    )

    fun sendDocumentAsync(
        peer: InputPeer,
        bytes: ByteArray,
        fileName: String,
        caption: String = "",
        mimeType: String? = null,
        thumb: ByteArray? = null,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): Deferred<SentMessage> = client.apiAsync {
        sendDocument(peer, bytes, fileName, caption, mimeType, thumb, silent, replyTo, replyMarkup)
    }

    fun sendDocumentAsync(
        peerId: Long,
        bytes: ByteArray,
        fileName: String,
        caption: String = "",
        mimeType: String? = null,
        thumb: ByteArray? = null,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): Deferred<SentMessage> = client.apiAsync {
        sendDocument(peerId, bytes, fileName, caption, mimeType, thumb, silent, replyTo, replyMarkup)
    }

    suspend fun sendDocument(
        peerId: Long,
        bytes: ByteArray,
        fileName: String,
        caption: String = "",
        mimeType: String? = null,
        thumb: ByteArray? = null,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): SentMessage = sendDocument(
        client.inputPeerFromId(peerId), bytes, fileName, caption, mimeType, thumb, silent, replyTo, replyMarkup,
    )

    suspend fun sendDocument(
        peer: InputPeer,
        bytes: ByteArray,
        fileName: String,
        caption: String = "",
        mimeType: String? = null,
        thumb: ByteArray? = null,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): SentMessage = sendUploadedDocument(
        peer = peer,
        bytes = bytes,
        fileName = fileName,
        mimeType = mimeType ?: mimeFromName(fileName),
        attributes = listOf(DocumentAttributeFilename(fileName)),
        caption = caption,
        thumb = thumb,
        forceFile = true,
        silent = silent,
        replyTo = replyTo,
        replyMarkup = replyMarkup,
    )

    fun sendGifAsync(
        peer: InputPeer,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "animation.mp4",
        duration: Double = 0.0,
        width: Int = 0,
        height: Int = 0,
        thumb: ByteArray? = null,
        spoiler: Boolean = false,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): Deferred<SentMessage> = client.apiAsync {
        sendGif(peer, bytes, caption, fileName, duration, width, height, thumb, spoiler, silent, replyTo, replyMarkup)
    }

    fun sendGifAsync(
        peerId: Long,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "animation.mp4",
        duration: Double = 0.0,
        width: Int = 0,
        height: Int = 0,
        thumb: ByteArray? = null,
        spoiler: Boolean = false,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): Deferred<SentMessage> = client.apiAsync {
        sendGif(peerId, bytes, caption, fileName, duration, width, height, thumb, spoiler, silent, replyTo, replyMarkup)
    }

    suspend fun sendGif(
        peerId: Long,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "animation.mp4",
        duration: Double = 0.0,
        width: Int = 0,
        height: Int = 0,
        thumb: ByteArray? = null,
        spoiler: Boolean = false,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): SentMessage = sendGif(
        client.inputPeerFromId(peerId), bytes, caption, fileName, duration, width, height, thumb,
        spoiler, silent, replyTo, replyMarkup,
    )

    suspend fun sendGif(
        peer: InputPeer,
        bytes: ByteArray,
        caption: String = "",
        fileName: String = "animation.mp4",
        duration: Double = 0.0,
        width: Int = 0,
        height: Int = 0,
        thumb: ByteArray? = null,
        spoiler: Boolean = false,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): SentMessage = sendUploadedDocument(
        peer = peer,
        bytes = bytes,
        fileName = fileName,
        mimeType = if (fileName.endsWith(".gif", ignoreCase = true)) "image/gif" else "video/mp4",
        attributes = listOf(
            DocumentAttributeAnimated,
            DocumentAttributeVideo(duration = duration, w = width, h = height, supportsStreaming = true),
            DocumentAttributeFilename(fileName),
        ),
        caption = caption,
        thumb = thumb,
        spoiler = spoiler,
        silent = silent,
        replyTo = replyTo,
        replyMarkup = replyMarkup,
    )

    private suspend fun sendMedia(
        peer: InputPeer,
        media: InputMedia,
        caption: String,
        randomId: Long?,
        silent: Boolean,
        background: Boolean,
        clearDraft: Boolean,
        noforwards: Boolean,
        updateStickersetsOrder: Boolean,
        invertMedia: Boolean,
        allowPaidFloodskip: Boolean,
        replyTo: InputReplyTo?,
        replyMarkup: ReplyMarkup?,
        entities: List<MessageEntity>?,
        scheduleDate: Int?,
        scheduleRepeatPeriod: Int?,
        sendAs: InputPeer?,
        sendAsId: Long?,
        quickReplyShortcut: InputQuickReplyShortcut?,
        effect: Long?,
        allowPaidStars: Long?,
        suggestedPost: SuggestedPost?,
    ): SentMessage {
        val raw = client.invoke(
            MessagesSendMedia(
                peer = peer,
                media = media,
                message = caption,
                randomId = nextRandomId(randomId),
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
            ),
        )
        return SentMessage.from(raw, caption)
    }

    private suspend fun sendUploadedDocument(
        peer: InputPeer,
        bytes: ByteArray,
        fileName: String,
        mimeType: String,
        attributes: List<DocumentAttribute>,
        caption: String = "",
        thumb: ByteArray? = null,
        forceFile: Boolean = false,
        spoiler: Boolean = false,
        ttlSeconds: Int? = null,
        silent: Boolean = false,
        replyTo: InputReplyTo? = null,
        replyMarkup: ReplyMarkup? = null,
    ): SentMessage {
        val upload = Upload(client)
        val file = upload.saveFile(bytes, fileName)
        val thumbFile = thumb?.let { upload.saveFile(it, "thumb.jpg") }
        return sendMedia(
            peer = peer,
            media = InputMediaUploadedDocument(
                file = file,
                mimeType = mimeType,
                attributes = attributes,
                forceFile = forceFile,
                spoiler = spoiler,
                thumb = thumbFile,
                ttlSeconds = ttlSeconds,
            ),
            caption = caption,
            randomId = null,
            silent = silent,
            background = false,
            clearDraft = false,
            noforwards = false,
            updateStickersetsOrder = false,
            invertMedia = false,
            allowPaidFloodskip = false,
            replyTo = replyTo,
            replyMarkup = replyMarkup,
            entities = null,
            scheduleDate = null,
            scheduleRepeatPeriod = null,
            sendAs = null,
            sendAsId = null,
            quickReplyShortcut = null,
            effect = null,
            allowPaidStars = null,
            suggestedPost = null,
        )
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
