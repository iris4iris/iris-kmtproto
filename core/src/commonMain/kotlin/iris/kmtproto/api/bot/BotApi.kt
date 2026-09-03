package iris.kmtproto.api.bot

import iris.kmtproto.api.user.UserApi
import iris.kmtproto.client.ClientInfo
import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.SentMessage
import iris.kmtproto.client.Storage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.User
import iris.kmtproto.transport.Datacenter
import iris.kmtproto.transport.Proxy
import kotlinx.coroutines.Deferred
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

    fun login(token: String): Deferred<User> = client.apiAsync {
        val me = user.auth.importBotAuthorizationAwait(token)
        client.getStateAwait()
        me
    }

    fun sendMessage(chatId: Long, text: String): Deferred<SentMessage> = client.apiAsync {
        user.messages.sendAwait(client.inputPeerFromId(chatId), text)
    }

    fun incomingMessages(): Flow<BotMessage> =
        client.incomingMessages()
            .filter { !it.out }
            .map { it.toBotMessage() }
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
