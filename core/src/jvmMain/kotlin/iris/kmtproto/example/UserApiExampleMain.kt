package iris.kmtproto.example

import iris.kmtproto.api.user.UserApi
import iris.kmtproto.client.SessionPasswordNeeded
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.id
import iris.kmtproto.mtproto.RpcException
import iris.kmtproto.tl.gen.AuthSentCodeCtor
import iris.kmtproto.tl.gen.UserCtor
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

/**
 * UserApi: SMS (+ 2FA) login, optional send by peerId.
 *
 * TELEGRAM_API_ID, TELEGRAM_API_HASH, TELEGRAM_PHONE
 * optional TELEGRAM_CODE, TELEGRAM_2FA, TELEGRAM_CHAT_ID
 *
 *   ./gradlew :core:runUserApi
 */
fun main() {
    try {
        runBlocking { runUserApiExample() }
    } catch (e: Throwable) {
        System.err.println("user-api: ${e.message}")
        e.printStackTrace()
        exitProcess(1)
    }
}

private suspend fun runUserApiExample() {
    val creds = requireEnv("TELEGRAM_API_ID", "TELEGRAM_API_HASH", "TELEGRAM_PHONE")
    val apiId = creds["TELEGRAM_API_ID"]!!.toInt()
    val phone = creds["TELEGRAM_PHONE"]!!
    val sessionFile = sessionFile("session-user.properties")
    val client = TelegramClient(apiId, creds["TELEGRAM_API_HASH"]!!)
    val api = UserApi(client)
    try {
        val saved = loadSession(sessionFile)
        client.connect(session = saved)
        if (saved != null && saved.userId != 0L) {
            try {
                client.getState()
                println("UserApi session ok userId=${saved.userId}")
            } catch (e: RpcException) {
                println("session dead (${e.message}) — SMS login")
                loginPhone(api, phone)
            }
        } else {
            loginPhone(api, phone)
        }
        client.session()?.let { saveSession(sessionFile, it) }
        val me = client.user
        val name = (me as? UserCtor)?.username ?: me?.id?.toString() ?: "?"
        println("UserApi as $name")

        val chatId = env("TELEGRAM_CHAT_ID")?.toLongOrNull()
        if (chatId != null) {
            val sent = api.messages.send(chatId, "Iris kMTProto UserApi ${System.currentTimeMillis()}")
            println("sent id=${sent.result?.id} err=${sent.error?.errorMessage} peer=$chatId")
        } else {
            println("TELEGRAM_CHAT_ID not set — skip send")
        }
        println("listening (Ctrl+C to stop)")
        client.incomingMessages().collect { msg ->
            println("in #${msg.id} peer=${msg.peerId}: ${msg.message}")
        }
    } finally {
        client.close()
    }
}

private suspend fun loginPhone(api: UserApi, phone: String) {
    val sent = api.auth.sendCode(phone)
    val hash = (sent as? AuthSentCodeCtor)?.phoneCodeHash
        ?: error("unexpected sentCode $sent")
    val code = env("TELEGRAM_CODE") ?: prompt("SMS code")
    try {
        api.auth.signIn(phone, hash, code)
    } catch (e: SessionPasswordNeeded) {
        println("2FA hint=${e.hint}")
        val password = env("TELEGRAM_2FA") ?: prompt("cloud password")
        api.auth.checkPassword(password)
    }
}
