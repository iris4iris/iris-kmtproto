package iris.kmtproto.example

import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.Storage
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.UserCtor
import java.io.File

class SimpleFileStorage(private val file: File) : Storage {
	constructor(path: String) : this(File(path))

	private val hashes = HashMap<Long, Long>()
	private val entities = MemoryStorage()

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

	override fun getUser(id: Long): UserCtor? = entities.getUser(id)

	override fun getChat(id: Long): Chat? = entities.getChat(id)

	override fun rememberUser(user: UserCtor) = entities.rememberUser(user)

	override fun rememberChat(chat: Chat) = entities.rememberChat(chat)

	override fun clearEntities() = entities.clearEntities()

	private fun updateFile() {
		if (!file.exists()) file.createNewFile()
		file.writeText(hashes.entries.joinToString("\n") { (k, v) -> "$k;$v" })
	}
}
