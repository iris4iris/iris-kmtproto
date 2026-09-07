package iris.kmtproto.example

import iris.kmtproto.api.bot.BotApi
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.id
import iris.kmtproto.tl.gen.UserCtor
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

fun main() {
    try {
        runBlocking { run() }
    } catch (e: Throwable) {
        System.err.println("router-bot: ${e.message}")
        e.printStackTrace()
        exitProcess(1)
    }
}

private suspend fun run() {
    val creds = requireEnv("TELEGRAM_API_ID", "TELEGRAM_API_HASH", "TELEGRAM_BOT_TOKEN", "FILE_STORAGE")
    val sessionFile = sessionFile("session.properties")
    val client = TelegramClient(
        creds["TELEGRAM_API_ID"]!!.toInt(),
        creds["TELEGRAM_API_HASH"]!!,
        storage = SimpleFileStorage(creds["FILE_STORAGE"]!!),
    )
    val bot = BotApi(client)
    try {
        client.connect(session = loadSession(sessionFile))
        val me = bot.login(creds["TELEGRAM_BOT_TOKEN"]!!)
        client.session()?.let { saveSession(sessionFile, it) }
        val name = (me as? UserCtor)?.username ?: me.id.toString()
        println("logged in as @$name (auth_key reused=${sessionFile.isFile})")
        runRouterBot(bot)
    } finally {
        client.close()
    }
}
