package iris.kmtproto.io

/**
 * Sequential bytes with known [size]. Blocking [read].
 * [saveFile][iris.kmtproto.api.user.Upload.saveFile] consumes and does not close;
 * caller uses [use] for files.
 */
interface ByteSource : AutoCloseable {
    val size: Long
    fun read(buf: ByteArray, offset: Int, length: Int): Int
}

class ByteArrayByteSource(private val bytes: ByteArray) : ByteSource {
    private var pos = 0
    override val size: Long get() = bytes.size.toLong()

    override fun read(buf: ByteArray, offset: Int, length: Int): Int {
        if (length == 0) return 0
        if (pos >= bytes.size) return -1
        val n = minOf(length, bytes.size - pos)
        bytes.copyInto(buf, offset, pos, pos + n)
        pos += n
        return n
    }

    override fun close() {}
}
