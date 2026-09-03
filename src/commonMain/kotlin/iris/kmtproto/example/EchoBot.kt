package iris.kmtproto.example

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.id
import iris.kmtproto.client.inputPeerFrom
import iris.kmtproto.mtproto.RpcException
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.Peer
import iris.kmtproto.tl.gen.PeerUser
import kotlinx.coroutines.flow.collect

/**
 * Listens on the MTProto socket (incomingMessages) and echoes text.
 * Own messages (out=true / from self) are skipped so the bot does not loop.
 */
suspend fun runEchoBot(
    client: TelegramClient,
    log: (String) -> Unit = { println(it) },
) {
    val me = client.user?.id ?: error("loginBot() first")
    client.getState()
    log("echo on, me=$me pts=${client.updatesState?.pts}")
    client.incomingMessages().collect { msg ->
        try {
            handle(client, me, msg, log)
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
    client: TelegramClient,
    me: Long,
    msg: MessageCtor,
    log: (String) -> Unit,
) {
    if (msg.out) return
    if (msg.fromId is PeerUser && (msg.fromId as PeerUser).userId == me) return
    val text = msg.message.trim()
    if (text.isEmpty()) return
    val peer = replyPeer(msg, me)
    val input = inputPeerFrom(peer, client.accessHash(peer.id))
    val reply = if (text.startsWith("/start")) "Echo is on." else text.take(4096)
    try {
        val sent = client.sendMessage(input, reply)
        log("echo #${sent.id} -> ${peer::class.simpleName} ${peer.id}")
    } catch (e: RpcException) {
        log("send failed ${e.code} ${e.message} peer=${peer.id}")
        e.printStackTrace()
    }
}

internal fun replyPeer(msg: MessageCtor, me: Long): Peer = when (val p = msg.peerId) {
    is PeerUser -> if (p.userId != me) p else (msg.fromId ?: p)
    else -> p
}
