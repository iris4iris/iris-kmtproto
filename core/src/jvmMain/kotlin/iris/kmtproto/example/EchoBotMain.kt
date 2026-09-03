package iris.kmtproto.example

import iris.kmtproto.client.ClientSession
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.id
import iris.kmtproto.tl.gen.UserCtor
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.Properties
import kotlin.system.exitProcess

fun main() {
    try {
        runBlocking { run() }
    } catch (e: Throwable) {
        System.err.println("echo-bot: ${e.message}")
        e.printStackTrace()
        exitProcess(1)
    }
}

private suspend fun run() {
    val creds = loadCreds()
    val sessionFile = sessionFile()
    val client = TelegramClient(creds.apiId, creds.apiHash)
    try {
        client.connect(session = loadSession(sessionFile))
        val me = client.loginBot(creds.token)
        client.session()?.let { saveSession(sessionFile, it) }
        val name = (me as? UserCtor)?.username ?: me.id.toString()
        println("logged in as @$name (auth_key reused=${sessionFile.isFile})")
        runEchoBot(client)
    } finally {
        client.close()
    }
}

internal data class BotCreds(val apiId: Int, val apiHash: String, val token: String)

internal fun loadCreds(): BotCreds {
    val file = findLocalProperties()
    val props = Properties()
    file?.inputStream()?.use { props.load(it) }
    fun value(name: String): String? =
        System.getenv(name)?.takeIf { it.isNotBlank() }
            ?: System.getProperty(name)?.takeIf { it.isNotBlank() }
            ?: props.getProperty(name)?.trim()?.takeIf { it.isNotBlank() }

    val missing = listOf("TELEGRAM_API_ID", "TELEGRAM_API_HASH", "TELEGRAM_BOT_TOKEN")
        .filter { value(it) == null }
    if (missing.isNotEmpty()) {
        error(
            "нет ${missing.joinToString()}\n" +
                "  IDEA: Run → Edit Configurations → Environment variables\n" +
                "  или файл local.properties рядом с build.gradle.kts\n" +
                "  (сейчас local.properties: ${file?.absolutePath ?: "не найден"})",
        )
    }
    val apiId = value("TELEGRAM_API_ID")!!.toIntOrNull()
        ?: error("TELEGRAM_API_ID must be int")
    return BotCreds(apiId, value("TELEGRAM_API_HASH")!!, value("TELEGRAM_BOT_TOKEN")!!)
}

internal fun findLocalProperties(): File? {
    val names = listOf(
        "local.properties",
        "core/local.properties",
        "kmtproto/local.properties",
    )
    val roots = listOf(File("."), File(".."), File(System.getProperty("user.dir")))
    for (root in roots) {
        for (name in names) {
            val f = File(root, name)
            if (f.isFile) return f.canonicalFile
        }
    }
    return null
}

private fun sessionFile(): File {
    val props = findLocalProperties()
    val dir = props?.parentFile ?: File(System.getProperty("user.dir"))
    return File(dir, "session.properties")
}

private fun loadSession(file: File): ClientSession? {
    if (!file.isFile) return null
    val props = Properties()
    file.inputStream().use { props.load(it) }
    val keyHex = props.getProperty("authKey") ?: return null
    val key = hexToBytes(keyHex)
    if (key.size != 256) return null
    return ClientSession(
        dcId = props.getProperty("dc")?.toIntOrNull() ?: return null,
        authKey = key,
        salt = props.getProperty("salt")?.toLongOrNull() ?: 0L,
        userId = props.getProperty("userId")?.toLongOrNull() ?: 0L,
        accessHash = props.getProperty("accessHash")?.toLongOrNull() ?: 0L,
    )
}

private fun saveSession(file: File, session: ClientSession) {
    val props = Properties()
    props["dc"] = session.dcId.toString()
    props["salt"] = session.salt.toString()
    props["userId"] = session.userId.toString()
    props["accessHash"] = session.accessHash.toString()
    props["authKey"] = session.authKey.joinToString("") { b ->
        (b.toInt() and 0xff).toString(16).padStart(2, '0')
    }
    file.writer().use { props.store(it, "Iris kMTProto bot session — do not commit") }
}

private fun hexToBytes(hex: String): ByteArray {
    val clean = hex.trim()
    require(clean.length % 2 == 0) { "odd hex" }
    return ByteArray(clean.length / 2) { i ->
        clean.substring(i * 2, i * 2 + 2).toInt(16).toByte()
    }
}
