package iris.kmtproto.example

import iris.kmtproto.LongIntPair
import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.Storage
import iris.kmtproto.tl.gen.Chat
import iris.kmtproto.tl.gen.Message
import iris.kmtproto.tl.gen.UserCtor
import java.io.File

class SimpleFileStorage(private val file: File) : Storage {
	constructor(path: String) : this(File(path))

	private val hashes = HashMap<Long, Long>()
	private val channelPts = HashMap<Long, Int>()
	private val entities = MemoryStorage()

	init {
		if (file.exists()) {
			file.readLines().forEach { line ->
				val parts = line.split(';')
				when {
					parts.size == 3 && parts[0] == "c" -> channelPts[parts[1].toLong()] = parts[2].toInt()
					parts.size >= 2 && parts[0].isNotEmpty() -> hashes[parts[0].toLong()] = parts[1].toLong()
				}
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

	override fun getMessage(key: LongIntPair): Message? = entities.getMessage(key)

	override fun rememberMessage(message: Message) = entities.rememberMessage(message)

	override fun clearEntities() = entities.clearEntities()

	@Synchronized
	override fun getChannelPts(channelId: Long): Int = channelPts[channelId] ?: 0

	@Synchronized
	override fun putChannelPts(channelId: Long, pts: Int) {
		if (pts == 0) {
			if (channelPts.remove(channelId) != null) updateFile()
			return
		}
		if (channelPts.put(channelId, pts) != pts) updateFile()
	}

	@Synchronized
	override fun removeChannelPts(channelId: Long) {
		if (channelPts.remove(channelId) != null) updateFile()
	}

	private fun updateFile() {
		if (!file.exists()) file.createNewFile()
		val hashesText = hashes.entries.joinToString("\n") { (k, v) -> "$k;$v" }
		val ptsText = channelPts.entries.joinToString("\n") { (k, v) -> "c;$k;$v" }
		file.writeText(listOf(hashesText, ptsText).filter { it.isNotEmpty() }.joinToString("\n"))
	}
}
