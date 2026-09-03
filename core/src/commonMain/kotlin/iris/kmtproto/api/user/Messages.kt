package iris.kmtproto.api.user

import iris.kmtproto.client.SentMessage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.gen.InputPeer
import iris.kmtproto.tl.gen.MessagesSendMessage
import kotlinx.coroutines.Deferred

class Messages(private val client: TelegramClient) {
    fun sendAsync(peer: InputPeer, text: String): Deferred<SentMessage> =
        client.apiAsync { send(peer, text) }

    fun sendAsync(peerId: Long, text: String): Deferred<SentMessage> =
        client.apiAsync { send(peerId, text) }

    suspend fun send(peerId: Long, text: String): SentMessage =
        send(client.inputPeerFromId(peerId), text)

    suspend fun send(peer: InputPeer, text: String): SentMessage {
        require(text.isNotEmpty()) { "empty message" }
        var randomId = PlatformCrypto.randomBytes(8).readLongLe()
        if (randomId == 0L) randomId = 1L
        val raw = client.invoke(MessagesSendMessage(peer = peer, message = text, randomId = randomId))
        return SentMessage.from(raw, text)
    }
}
