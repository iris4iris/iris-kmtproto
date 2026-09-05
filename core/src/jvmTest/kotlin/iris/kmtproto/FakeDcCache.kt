package iris.kmtproto

import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.tl.toBytes
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File

/**
 * Pre-encoded intermediate frames (4-byte length + IGE payload).
 * Obfuscation CTR cannot live in the file: keys come from the client's 64-byte init.
 * After handshake Fake DC CTRs this blob in-place once, then writes it.
 */
internal class FakeDcTape(
    val authKey: AuthKey,
    val salt: Long,
    val sessionId: Long,
    val messages: Int,
    val frames: Int,
    val frameBytes: Int,
    val blob: ByteArray,
) {
    val stride: Int get() = 4 + frameBytes
}

internal object FakeDcCache {
    private const val MAGIC = "KMTDC01\n"
    private val defaultSalt = 0x1111_2222_3333_4444L
    private val defaultSession = 0x5555_6666_7777_8888L

    fun dir(): File {
        val override = System.getenv("KMTPROTO_FAKE_DC_CACHE")
        val d = if (!override.isNullOrBlank()) File(override) else File("build/fake-dc-cache")
        d.mkdirs()
        return d
    }

    fun loadOrBuild(messages: Int, frames: Int): FakeDcTape {
        require(messages > 0 && frames > 0)
        val file = File(dir(), "m$messages-n$frames.bin")
        if (file.isFile) {
            val tape = read(file)
            if (tape.messages == messages && tape.frames == frames) {
                System.err.println(
                    "kmtproto fake-dc cache load ${file.name} " +
                        "${tape.blob.size / (1024 * 1024)} MiB",
                )
                return tape
            }
        }
        System.err.println("kmtproto fake-dc cache build ${file.name} frames=$frames")
        val tape = build(messages, frames)
        writeAtomic(file, tape)
        return tape
    }

    private fun build(messages: Int, frames: Int): FakeDcTape {
        val key = AuthKey(PlatformCrypto.randomBytes(256))
        val body = sampleUpdates(messages).toBytes()
        val encoder = InboundEncoder()
        val frameBytes = encoder.payloadBytes(body.size)
        val stride = 4 + frameBytes
        val blobBytes = frames.toLong() * stride
        require(blobBytes <= Int.MAX_VALUE) { "tape too large: $blobBytes" }
        val blob = try {
            ByteArray(blobBytes.toInt())
        } catch (e: OutOfMemoryError) {
            throw IllegalStateException(
                "Fake DC tape $frames×$stride B needs ~${blobBytes / (1024 * 1024)} MiB; raise -Xmx (2g)",
                e,
            )
        }
        var msgId = 8L
        var off = 0
        repeat(frames) {
            blob.putIntLe(off, frameBytes)
            encoder.encodeInto(key, defaultSalt, defaultSession, msgId, seqNo = 1, body, blob, off + 4)
            msgId += 2
            off += stride
        }
        return FakeDcTape(key, defaultSalt, defaultSession, messages, frames, frameBytes, blob)
    }

    private fun writeAtomic(file: File, tape: FakeDcTape) {
        val tmp = File(file.path + ".tmp")
        tmp.outputStream().buffered(1 shl 20).use { raw ->
            val out = DataOutputStream(raw)
            out.writeBytes(MAGIC)
            out.writeInt(tape.messages)
            out.writeInt(tape.frames)
            out.writeInt(tape.frameBytes)
            out.writeLong(tape.salt)
            out.writeLong(tape.sessionId)
            out.write(tape.authKey.key)
            out.write(tape.blob)
        }
        if (!tmp.renameTo(file)) {
            file.delete()
            check(tmp.renameTo(file)) { "rename $tmp → $file" }
        }
    }

    private fun read(file: File): FakeDcTape {
        file.inputStream().buffered(1 shl 20).use { raw ->
            val input = DataInputStream(raw)
            val magic = ByteArray(MAGIC.length)
            input.readFully(magic)
            require(magic.decodeToString() == MAGIC) { "bad fake-dc cache magic" }
            val messages = input.readInt()
            val frames = input.readInt()
            val frameBytes = input.readInt()
            val salt = input.readLong()
            val session = input.readLong()
            val key = ByteArray(256)
            input.readFully(key)
            val stride = 4 + frameBytes
            val blobBytes = frames.toLong() * stride
            require(blobBytes <= Int.MAX_VALUE)
            val blob = ByteArray(blobBytes.toInt())
            input.readFully(blob)
            return FakeDcTape(AuthKey(key), salt, session, messages, frames, frameBytes, blob)
        }
    }
}

private fun ByteArray.putIntLe(off: Int, v: Int) {
    this[off] = v.toByte()
    this[off + 1] = (v shr 8).toByte()
    this[off + 2] = (v shr 16).toByte()
    this[off + 3] = (v shr 24).toByte()
}
