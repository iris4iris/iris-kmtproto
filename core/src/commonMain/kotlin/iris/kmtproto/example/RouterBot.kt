package iris.kmtproto.example

import iris.kmtproto.api.bot.BotApi
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.client.floodWaitSeconds
import iris.kmtproto.client.id
import iris.kmtproto.events.EventFilter
import iris.kmtproto.events.SingleEventRouter
import iris.kmtproto.tl.gen.MessageCtor

/**
 * [SingleEventRouter]: `/start` first, then echo. Own messages (`out`) are dropped in the filters.
 */
suspend fun runRouterBot(
    bot: BotApi,
    log: (String) -> Unit = { println(it) },
) {
    val me = bot.client.user?.id ?: error("login() first")
    log("router on, me=$me pts=${bot.client.updatesState?.pts}")

    val router = SingleEventRouter()
    router.onMessage(EventFilter { !it.out && it.message.startsWith("/start") }) { m ->
        reply(bot, m, "Echo is on.", log)
    }

    router.onMessage(EventFilter { !it.out && it.message == "ping" }) { m ->
        reply(bot, m, "PONG!", log)
    }

    router.onMessage(EventFilter { !it.out && it.message.isNotEmpty() }) { m ->
        reply(bot, m, m.message.trim().take(4096), log)
    }
    router.start(bot.client)
}

private fun reply(bot: BotApi, m: MessageCtor, text: String, log: (String) -> Unit) {
    val chatId = m.peerId.botApiChatId()
    val d = bot.sendMessageAsync(chatId, text)
    d.invokeOnCompletion { e ->
        if (e != null) {
            log("send failed ${e.message} chat=$chatId")
            e.printStackTrace()
            return@invokeOnCompletion
        }
        val r = d.getCompleted()
        val err = r.error
        if (err != null) {
            val wait = err.floodWaitSeconds
            if (wait != 0) log("FLOOD_WAIT $wait s")
            else log("send failed ${err.errorCode} ${err.errorMessage} chat=$chatId")
        } else {
            log("echo #${r.result!!.id} -> chat $chatId")
        }
    }
}
