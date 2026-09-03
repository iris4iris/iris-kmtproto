package iris.kmtproto.api.bot

import iris.kmtproto.api.UserApi
import iris.kmtproto.client.ClientInfo
import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.SentMessage
import iris.kmtproto.client.Storage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.client.inputPeerFromBotApiId
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.User
import iris.kmtproto.transport.Datacenter
import iris.kmtproto.transport.Proxy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

data class BotMessage(
    val messageId: Int,
    val chatId: Long,
    val fromId: Long?,
    val text: String,
    val date: Int,
    val out: Boolean,
)

/**
 * Bot-API-shaped adapter over MTProto. Not HTTP getUpdates.
 */
class BotApi(val client: TelegramClient) {
    val user = UserApi(client)

    constructor(
        apiId: Int,
        apiHash: String,
        dc: Datacenter = Datacenter.DC2,
        storage: Storage = MemoryStorage(),
        proxy: Proxy? = null,
        info: ClientInfo = ClientInfo(),
        layer: Int = API_LAYER,
    ) : this(
        TelegramClient(
            apiId = apiId,
            apiHash = apiHash,
            dc = dc,
            info = info,
            layer = layer,
            storage = storage,
            proxy = proxy,
        ),
    )

    suspend fun login(token: String): User {
        val me = user.auth.importBotAuthorization(token)
        client.getState()
        return me
    }

    suspend fun sendMessage(chatId: Long, text: String): SentMessage {
        val peer = inputPeerFromBotApiId(chatId, hashFor(chatId))
        return user.messages.send(peer, text)
    }

    fun incomingMessages(): Flow<BotMessage> =
        client.incomingMessages()
            .filter { !it.out }
            .map { it.toBotMessage() }

    private fun hashFor(chatId: Long): Long = when {
        chatId > 0L -> client.accessHash(chatId)
        chatId <= -1_000_000_000_000L -> client.accessHash(-chatId - 1_000_000_000_000L)
        else -> 0L
    }
}

fun MessageCtor.toBotMessage(): BotMessage = BotMessage(
    messageId = id,
    chatId = peerId.botApiChatId(),
    fromId = when (val from = fromId) {
        is PeerUser -> from.userId
        is PeerChat -> from.chatId
        is PeerChannel -> from.channelId
        null -> null
        else -> null
    },
    text = message,
    date = date,
    out = out,
)
