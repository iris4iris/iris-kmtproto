package iris.kmtproto.example

import iris.kmtproto.api.bot.BotApi
import iris.kmtproto.bot.Message
import iris.kmtproto.client.floodWaitSeconds
import iris.kmtproto.client.id

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
        handle(bot, me, msg.message ?: return@collect, log)
    }
}

private fun handle(
    bot: BotApi,
    me: Long,
    msg: Message,
    log: (String) -> Unit,
) {
    if ((msg.from?.id ?: 0) == me) return
    val text = msg.text.orEmpty().trim()
    if (text.isEmpty()) return
    val reply = if (text.startsWith("/start")) "Echo is on." else text.take(4096)
    val chatId = msg.chat?.id ?: 0L
    val d = bot.sendMessageAsync(chatId, reply)
    d.invokeOnCompletion { e ->
        if (e != null) {
            log("send failed ${e.message} chat=${chatId}")
            e.printStackTrace()
            return@invokeOnCompletion
        }
        val r = d.getCompleted()
        val err = r.error
        if (err != null) {
            val wait = err.floodWaitSeconds
            if (wait != 0) log("FLOOD_WAIT $wait s")
            else log("send failed ${err.errorCode} ${err.errorMessage} chat=${chatId}")
        } else {
            log("echo #${r.result!!.id} -> chat ${chatId}")
        }
    }
}
