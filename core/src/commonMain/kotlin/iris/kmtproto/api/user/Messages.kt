package iris.kmtproto.api.user

import iris.kmtproto.client.SentMessage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.gen.InputPeer
import iris.kmtproto.tl.gen.MessagesSendMessage
import kotlinx.coroutines.Deferred

class Messages(private val client: TelegramClient) {
    fun send(peer: InputPeer, text: String): Deferred<SentMessage> =
        client.apiAsync { sendSuspend(peer, text) }

    fun send(peerId: Long, text: String): Deferred<SentMessage> =
        client.apiAsync { sendSuspend(client.inputPeerFromId(peerId), text) }

    suspend fun sendSuspend(peer: InputPeer, text: String): SentMessage {
        require(text.isNotEmpty()) { "empty message" }
        var randomId = PlatformCrypto.randomBytes(8).readLongLe()
        if (randomId == 0L) randomId = 1L
        val raw = client.invokeSuspend(MessagesSendMessage(peer = peer, message = text, randomId = randomId))
        return SentMessage.from(raw, text)
    }
}
