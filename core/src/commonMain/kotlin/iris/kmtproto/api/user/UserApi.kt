package iris.kmtproto.api.user
import iris.kmtproto.client.ClientInfo
import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.Storage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.transport.Datacenter
import iris.kmtproto.transport.Proxy

/**
 * Namespaced user-layer methods. Transport stays on [client].
 */
class UserApi(val client: TelegramClient) {
    val auth = Auth(client)
    val messages = Messages(client)

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
}
