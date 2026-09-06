package iris.kmtproto.api.user

import iris.kmtproto.client.RpcResponse
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.fileMigrateDc
import iris.kmtproto.concat
import iris.kmtproto.crypto.Md5Hasher
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.io.ByteArrayByteSource
import iris.kmtproto.io.ByteSource
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.RpcError
import iris.kmtproto.tl.gen.BoolTrue
import iris.kmtproto.tl.gen.Document
import iris.kmtproto.tl.gen.DocumentCtor
import iris.kmtproto.tl.gen.InputDocumentFileLocation
import iris.kmtproto.tl.gen.InputFile
import iris.kmtproto.tl.gen.InputFileBig
import iris.kmtproto.tl.gen.InputFileCtor
import iris.kmtproto.tl.gen.InputFileLocation
import iris.kmtproto.tl.gen.InputPhotoFileLocation
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.MessageMediaDocument
import iris.kmtproto.tl.gen.MessageMediaPhoto
import iris.kmtproto.tl.gen.Photo
import iris.kmtproto.tl.gen.PhotoCachedSize
import iris.kmtproto.tl.gen.PhotoCtor
import iris.kmtproto.tl.gen.PhotoSize
import iris.kmtproto.tl.gen.PhotoSizeCtor
import iris.kmtproto.tl.gen.PhotoSizeEmpty
import iris.kmtproto.tl.gen.PhotoSizeProgressive
import iris.kmtproto.tl.gen.PhotoStrippedSize
import iris.kmtproto.tl.gen.PhotoPathSize
import iris.kmtproto.tl.gen.UploadFileCdnRedirect
import iris.kmtproto.tl.gen.UploadFileCtor
import iris.kmtproto.tl.gen.UploadGetFile
import iris.kmtproto.tl.gen.UploadSaveBigFilePart
import iris.kmtproto.tl.gen.UploadSaveFilePart
import iris.kmtproto.toHex
import kotlinx.coroutines.Deferred

/** `upload.saveFilePart` / `upload.saveBigFilePart`. */
object Upload {
    internal const val PART = 512 * 1024
    internal const val PART_LONG = PART.toLong()
    internal const val BIG_FILE = 10 * 1024 * 1024L

    fun saveFileAsync(client: TelegramClient, bytes: ByteArray, name: String): Deferred<RpcResponse<InputFile>> =
        client.apiAsync { saveFile(client, bytes, name) }

    fun saveFileAsync(client: TelegramClient, source: ByteSource, name: String): Deferred<RpcResponse<InputFile>> =
        client.apiAsync { saveFile(client, source, name) }

    suspend fun saveFile(client: TelegramClient, bytes: ByteArray, name: String): RpcResponse<InputFile> =
        saveFile(client, ByteArrayByteSource(bytes), name)

    suspend fun saveFile(client: TelegramClient, source: ByteSource, name: String): RpcResponse<InputFile> = source.use {
        val size = source.size
        require(size > 0L) { "empty file" }
        val big = size >= BIG_FILE
        val parts = ((size + PART - 1) / PART).toInt()
        val fileId = nextFileId()
        val hasher = if (big) null else Md5Hasher()
        val buf = ByteArray(PART)
        var remaining = size
        var index = 0
        while (remaining > 0L) {
            val want = minOf(PART_LONG, remaining).toInt()
            var filled = 0
            while (filled < want) {
                val n = source.read(buf, filled, want - filled)
                check(n > 0) { "unexpected eof at part #$index, expected $size" }
                filled += n
            }
            hasher?.update(buf, 0, filled)
            val chunk = if (filled == PART) buf else buf.copyOf(filled)
            val ok = if (big) {
                client.invoke(
                    UploadSaveBigFilePart(
                        fileId = fileId,
                        filePart = index,
                        fileTotalParts = parts,
                        bytes = chunk,
                    ),
                )
            } else {
                client.invoke(
                    UploadSaveFilePart(
                        fileId = fileId,
                        filePart = index,
                        bytes = chunk,
                    ),
                )
            }
            if (ok.error != null) return@use RpcResponse(null, ok.error)
            check(ok.result is BoolTrue) { "saveFilePart #$index failed: ${ok.result}" }
            remaining -= filled
            index++
        }
        if (big) {
            RpcResponse(InputFileBig(id = fileId, parts = parts, name = name), null)
        } else {
            RpcResponse(
                InputFileCtor(
                    id = fileId,
                    parts = parts,
                    name = name,
                    md5Checksum = hasher!!.digest().toHex(),
                ),
                null,
            )
        }
    }

    fun getFileAsync(client: TelegramClient, location: InputFileLocation, dcId: Int = 0, size: Long = 0L): Deferred<RpcResponse<ByteArray>> =
        client.apiAsync { getFile(client, location, dcId, size) }

    suspend fun getFile(client: TelegramClient, location: InputFileLocation, dcId: Int = 0, size: Long = 0L): RpcResponse<ByteArray> {
        var dc = dcId
        var offset = 0L
        var hops = 0
        val chunks = ArrayList<ByteArray>()
        var total = 0
        while (true) {
            val raw = client.invokeOnDc(
                dc,
                UploadGetFile(location = location, offset = offset, limit = PART, precise = false, cdnSupported = false),
            )
            val err = raw.error
            if (err != null) {
                val migrate = fileMigrateDc(err.errorMessage)
                if (migrate != 0 && migrate != dc && hops < 3) {
                    dc = migrate
                    hops++
                    continue
                }
                return RpcResponse(null, err)
            }
            when (val file = raw.result!!) {
                is UploadFileCtor -> {
                    if (file.bytes.isEmpty()) break
                    chunks += file.bytes
                    total += file.bytes.size
                    if (file.bytes.size < PART) break
                    if (size > 0L && total >= size) break
                    offset += file.bytes.size
                }
                is UploadFileCdnRedirect -> return RpcResponse(null, RpcError(400, "CDN_REDIRECT"))
            }
        }
        return RpcResponse(
            when {
                chunks.isEmpty() -> ByteArray(0)
                chunks.size == 1 -> chunks[0]
                else -> concat(*chunks.toTypedArray())
            },
            null,
        )
    }

    private fun nextFileId(): Long {
        var id = PlatformCrypto.randomBytes(8).readLongLe()
        if (id == 0L) id = 1L
        return id
    }
}

internal class FileRef(val location: InputFileLocation, val dcId: Int, val size: Long, val cached: ByteArray? = null)

internal fun fileRefFromMessage(message: Message, thumbSize: String = ""): FileRef? {
    val media = (message as? MessageCtor)?.media ?: return null
    return when (media) {
        is MessageMediaDocument -> media.document?.let { fileRefFromDocument(it, thumbSize) }
        is MessageMediaPhoto -> media.photo?.let { fileRefFromPhoto(it, thumbSize) }
        else -> null
    }
}

internal fun fileRefFromDocument(doc: Document, thumbSize: String = ""): FileRef? {
    val d = doc as? DocumentCtor ?: return null
    return FileRef(
        location = InputDocumentFileLocation(d.id, d.accessHash, d.fileReference, thumbSize),
        dcId = d.dcId,
        size = if (thumbSize.isEmpty()) d.size else 0L,
    )
}

internal fun fileRefFromPhoto(photo: Photo, thumbSize: String = ""): FileRef? {
    val p = photo as? PhotoCtor ?: return null
    val pick = pickPhotoSize(p, thumbSize) ?: return null
    if (pick.cached != null) return FileRef(
        location = InputPhotoFileLocation(p.id, p.accessHash, p.fileReference, pick.type),
        dcId = p.dcId,
        size = pick.cached.size.toLong(),
        cached = pick.cached,
    )
    return FileRef(
        location = InputPhotoFileLocation(p.id, p.accessHash, p.fileReference, pick.type),
        dcId = p.dcId,
        size = pick.size.toLong(),
    )
}

private class PhotoPick(val type: String, val size: Int, val cached: ByteArray? = null)

private fun pickPhotoSize(photo: PhotoCtor, thumbSize: String): PhotoPick? {
    val sizes = photo.sizes
    if (thumbSize.isNotEmpty()) {
        val hit = sizes.firstOrNull { photoSizeType(it) == thumbSize } ?: return null
        return photoPickOf(hit)
    }
    val best = sizes.maxByOrNull { photoSizeArea(it) }?.takeIf { photoSizeArea(it) > 0 }
    if (best != null) return photoPickOf(best)
    val cached = sizes.filterIsInstance<PhotoCachedSize>().maxByOrNull { it.w * it.h } ?: return null
    return PhotoPick(cached.type, cached.bytes.size, cached.bytes)
}

private fun photoPickOf(size: PhotoSize): PhotoPick = when (size) {
    is PhotoSizeCtor -> PhotoPick(size.type, size.size)
    is PhotoSizeProgressive -> PhotoPick(size.type, size.sizes.maxOrNull() ?: 0)
    is PhotoCachedSize -> PhotoPick(size.type, size.bytes.size, size.bytes)
    is PhotoStrippedSize -> PhotoPick(size.type, size.bytes.size, size.bytes)
    is PhotoPathSize -> PhotoPick(size.type, size.bytes.size, size.bytes)
    is PhotoSizeEmpty -> PhotoPick(size.type, 0)
}

private fun photoSizeType(size: PhotoSize): String = when (size) {
    is PhotoSizeCtor -> size.type
    is PhotoSizeProgressive -> size.type
    is PhotoCachedSize -> size.type
    is PhotoStrippedSize -> size.type
    is PhotoPathSize -> size.type
    is PhotoSizeEmpty -> size.type
}

private fun photoSizeArea(size: PhotoSize): Int = when (size) {
    is PhotoSizeCtor -> size.w * size.h
    is PhotoSizeProgressive -> size.w * size.h
    else -> 0
}
