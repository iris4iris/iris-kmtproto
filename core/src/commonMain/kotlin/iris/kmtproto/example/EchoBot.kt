package iris.kmtproto.example

import iris.kmtproto.api.bot.BotApi
import iris.kmtproto.api.bot.BotMessage
import iris.kmtproto.client.id
import iris.kmtproto.mtproto.RpcException
import kotlinx.coroutines.flow.collect

/**
 * Listens on the MTProto socket and echoes text.
 * Own messages (out=true) are dropped in [BotApi.incomingMessages].
 */
suspend fun runEchoBot(
    bot: BotApi,
    log: (String) -> Unit = { println(it) },
) {
    val me = bot.client.user?.id ?: error("login() first")
    log("echo on, me=$me pts=${bot.client.updatesState?.pts}")
    bot.incomingMessages().collect { msg ->
        try {
            handle(bot, me, msg, log)
        } catch (e: RpcException) {
            val wait = e.floodWaitSeconds
            if (wait != null) {
                log("FLOOD_WAIT $wait s")
            } else {
                log("send failed ${e.code} ${e.message}")
            }
            e.printStackTrace()
        }
    }
}

private suspend fun handle(
    bot: BotApi,
    me: Long,
    msg: BotMessage,
    log: (String) -> Unit,
) {
    if (msg.fromId == me) return
    val text = msg.text.trim()
    if (text.isEmpty()) return
    val reply = if (text.startsWith("/start")) "Echo is on." else text.take(4096)
    try {
        val sent = bot.sendMessage(msg.chatId, reply).await()
        log("echo #${sent.id} -> chat ${msg.chatId}")
    } catch (e: RpcException) {
        log("send failed ${e.code} ${e.message} chat=${msg.chatId}")
        e.printStackTrace()
    }
}
