package iris.kmtproto.example

import iris.kmtproto.client.Storage
import java.io.File

class SimpleFileStorage(private val file: File) : Storage {
	constructor(path: String) : this(File(path))

	private val hashes = HashMap<Long, Long>()

	init {
		if (file.exists()) {
			file.readLines().forEach {
				val (peerId, hash) = it.split(';')
				hashes[peerId.toLong()] = hash.toLong()
			}
		}
	}

	@Synchronized
	override fun getAccessHash(id: Long): Long {
		return hashes[id] ?: 0L
	}

	@Synchronized
	override fun putAccessHash(id: Long, hash: Long) {
		if (hash == 0L) return
		val res = hashes.put(id, hash) ?: 0L
		if (res != hash)
			updateFile()
	}

	private fun updateFile() {
		if (!file.exists()) file.createNewFile()
		file.writeText(hashes.entries.joinToString("\n") { (k, v) -> "$k;$v" })
	}
}
