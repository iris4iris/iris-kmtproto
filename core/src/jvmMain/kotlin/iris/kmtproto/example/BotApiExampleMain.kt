package iris.kmtproto.example

import iris.kmtproto.api.bot.BotApi
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.id
import iris.kmtproto.tl.gen.UserCtor
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

/**
 * Minimal BotApi: login, optional send, print incoming.
 *
 * TELEGRAM_API_ID, TELEGRAM_API_HASH, TELEGRAM_BOT_TOKEN
 * optional TELEGRAM_CHAT_ID
 *
 *   ./gradlew :core:runBotApi
 */
fun main() {
    try {
        runBlocking { runBotApiExample() }
    } catch (e: Throwable) {
        System.err.println("bot-api: ${e.message}")
        e.printStackTrace()
        exitProcess(1)
    }
}

private suspend fun runBotApiExample() {
    val creds = requireEnv("TELEGRAM_API_ID", "TELEGRAM_API_HASH", "TELEGRAM_BOT_TOKEN")
    val apiId = creds["TELEGRAM_API_ID"]!!.toInt()
    val sessionFile = sessionFile("session.properties")
    val client = TelegramClient(apiId, creds["TELEGRAM_API_HASH"]!!)
    val bot = BotApi(client)
    try {
        client.connect(session = loadSession(sessionFile))
        val me = bot.login(creds["TELEGRAM_BOT_TOKEN"]!!)
        client.session()?.let { saveSession(sessionFile, it) }
        val name = (me as? UserCtor)?.username ?: me.id.toString()
        println("BotApi login @$name id=${me.id}")

        val chatId = env("TELEGRAM_CHAT_ID")?.toLongOrNull()
        if (chatId != null) {
            val sent = bot.sendMessage(chatId, "Iris kMTProto BotApi ${System.currentTimeMillis()}")
            println("sent id=${sent.id} chat=$chatId")
        } else {
            println("TELEGRAM_CHAT_ID not set — skip send")
        }

        println("listening (Ctrl+C to stop)")
        bot.incomingMessages().collect { msg ->
            println("in #${msg.messageId} chat=${msg.chatId} from=${msg.fromId}: ${msg.text}")
            bot.sendMessageAsync(msg.chatId, "echo: ${msg.text.take(400)}")
        }
    } finally {
        client.close()
    }
}
