package iris.kmtproto.api.user

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.crypto.Md5Hasher
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.io.ByteArrayByteSource
import iris.kmtproto.io.ByteSource
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.gen.BoolTrue
import iris.kmtproto.tl.gen.InputFile
import iris.kmtproto.tl.gen.InputFileBig
import iris.kmtproto.tl.gen.InputFileCtor
import iris.kmtproto.tl.gen.UploadSaveBigFilePart
import iris.kmtproto.tl.gen.UploadSaveFilePart
import iris.kmtproto.toHex
import kotlinx.coroutines.Deferred

/** `upload.saveFilePart` / `upload.saveBigFilePart`. */
object Upload {
    internal const val PART = 512 * 1024
    internal const val PART_LONG = PART.toLong()
    internal const val BIG_FILE = 10 * 1024 * 1024L

    fun saveFileAsync(client: TelegramClient, bytes: ByteArray, name: String): Deferred<InputFile> =
        client.apiAsync { saveFile(client, bytes, name) }

    fun saveFileAsync(client: TelegramClient, source: ByteSource, name: String): Deferred<InputFile> =
        client.apiAsync { saveFile(client, source, name) }

    suspend fun saveFile(client: TelegramClient, bytes: ByteArray, name: String): InputFile =
        saveFile(client, ByteArrayByteSource(bytes), name)

    suspend fun saveFile(client: TelegramClient, source: ByteSource, name: String): InputFile = source.use {
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
            check(ok is BoolTrue) { "saveFilePart #$index failed: $ok" }
            remaining -= filled
            index++
        }
        if (big) {
            InputFileBig(id = fileId, parts = parts, name = name)
        } else {
            InputFileCtor(
                id = fileId,
                parts = parts,
                name = name,
                md5Checksum = hasher!!.digest().toHex(),
            )
        }
    }

    private fun nextFileId(): Long {
        var id = PlatformCrypto.randomBytes(8).readLongLe()
        if (id == 0L) id = 1L
        return id
    }
}
