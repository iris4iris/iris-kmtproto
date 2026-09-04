package iris.kmtproto

import iris.kmtproto.crypto.Md5Hasher
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.io.ByteArrayByteSource
import iris.kmtproto.io.FileByteSource
import java.io.File
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ByteSourceTest {
    @Test
    fun arrayReadsInChunks() {
        val data = ByteArray(3000) { it.toByte() }
        val src = ByteArrayByteSource(data)
        assertEquals(3000L, src.size)
        val buf = ByteArray(1024)
        val out = ArrayList<Byte>()
        while (true) {
            val n = src.read(buf, 0, buf.size)
            if (n < 0) break
            repeat(n) { out.add(buf[it]) }
        }
        assertContentEquals(data, out.toByteArray())
        src.close()
    }

    @Test
    fun fileMatchesArray() {
        val data = ByteArray(5000) { (it * 3).toByte() }
        val tmp = File.createTempFile("kmtproto", ".bin")
        tmp.writeBytes(data)
        FileByteSource(tmp).use { src ->
            assertEquals(data.size.toLong(), src.size)
            val buf = ByteArray(data.size)
            var filled = 0
            while (filled < buf.size) {
                val n = src.read(buf, filled, buf.size - filled)
                check(n > 0)
                filled += n
            }
            assertContentEquals(data, buf)
        }
        tmp.delete()
    }

    @Test
    fun md5IncrementalMatchesWhole() {
        val data = ByteArray(10_000) { it.toByte() }
        val h = Md5Hasher()
        h.update(data, 0, 3000)
        h.update(data, 3000, 7000)
        assertContentEquals(PlatformCrypto.md5(data), h.digest())
    }
}
