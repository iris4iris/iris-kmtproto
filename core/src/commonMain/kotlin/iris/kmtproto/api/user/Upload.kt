package iris.kmtproto.api.user

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.crypto.PlatformCrypto
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
class Upload(private val client: TelegramClient) {
    fun saveFileAsync(bytes: ByteArray, name: String): Deferred<InputFile> =
        client.apiAsync { saveFile(bytes, name) }

    suspend fun saveFile(bytes: ByteArray, name: String): InputFile {
        require(bytes.isNotEmpty()) { "empty file" }
        val fileId = nextFileId()
        val big = bytes.size >= BIG_FILE
        val parts = (bytes.size + PART - 1) / PART
        var offset = 0
        var index = 0
        while (offset < bytes.size) {
            val end = minOf(offset + PART, bytes.size)
            val chunk = bytes.copyOfRange(offset, end)
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
            offset = end
            index++
        }
        return if (big) {
            InputFileBig(id = fileId, parts = parts, name = name)
        } else {
            InputFileCtor(
                id = fileId,
                parts = parts,
                name = name,
                md5Checksum = PlatformCrypto.md5(bytes).toHex(),
            )
        }
    }

    private fun nextFileId(): Long {
        var id = PlatformCrypto.randomBytes(8).readLongLe()
        if (id == 0L) id = 1L
        return id
    }

    companion object {
        internal const val PART = 512 * 1024
        internal const val BIG_FILE = 10 * 1024 * 1024
    }
}
