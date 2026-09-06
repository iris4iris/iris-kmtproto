package iris.kmtproto.api.user
import iris.kmtproto.client.ClientInfo
import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.RpcResponse
import iris.kmtproto.client.Storage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tl.gen.InputUserSelf
import iris.kmtproto.tl.gen.User
import iris.kmtproto.tl.gen.UserCtor
import iris.kmtproto.tl.gen.UsersGetFullUser
import iris.kmtproto.transport.Datacenter
import iris.kmtproto.transport.Proxy
import kotlinx.coroutines.Deferred

/**
 * Namespaced user-layer methods. Transport stays on [client].
 */
class UserApi(val client: TelegramClient) {
    val auth = Auth(client)
    val messages = Messages(client)
    val contacts = Contacts(client)
    val payments = Payments(client)

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

    fun getMeAsync(): Deferred<RpcResponse<User>> = client.apiAsync { getMe() }

    suspend fun getMe(): RpcResponse<User> {
        val raw = client.invoke(UsersGetFullUser(InputUserSelf))
        raw.result?.let {
            client.rememberUsers(it.users)
            client.rememberChats(it.chats)
        }
        return raw.map { full ->
            val me = full.users.filterIsInstance<UserCtor>().firstOrNull { it.self }
                ?: full.users.firstOrNull()
                ?: error("users.getFullUser: empty users")
            client.user = me
            me
        }
    }
}
