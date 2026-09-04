package iris.kmtproto.io

import java.io.File
import java.io.FileInputStream

class FileByteSource(file: File) : ByteSource {
    constructor(path: String) : this(File(path))

    private val stream = FileInputStream(file)
    override val size: Long = file.length()

    override fun read(buf: ByteArray, offset: Int, length: Int): Int =
        stream.read(buf, offset, length)

    override fun close() {
        stream.close()
    }
}
