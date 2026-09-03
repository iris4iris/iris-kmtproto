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
        client.apiAsync { sendAwait(peer, text) }

    fun send(peerId: Long, text: String): Deferred<SentMessage> =
        client.apiAsync { sendAwait(client.inputPeerFromId(peerId), text) }

    suspend fun sendAwait(peer: InputPeer, text: String): SentMessage {
        require(text.isNotEmpty()) { "empty message" }
        var randomId = PlatformCrypto.randomBytes(8).readLongLe()
        if (randomId == 0L) randomId = 1L
        val raw = client.invokeAwait(MessagesSendMessage(peer = peer, message = text, randomId = randomId))
        return SentMessage.from(raw, text)
    }
}
