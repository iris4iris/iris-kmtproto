package iris.kmtproto.api.user

import iris.kmtproto.client.ClientInfo
import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.RpcResponse
import iris.kmtproto.client.Storage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.asInputChannel
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tl.RpcError
import iris.kmtproto.tl.gen.ChannelsJoinChannel
import iris.kmtproto.tl.gen.ChannelsLeaveChannel
import iris.kmtproto.tl.gen.InputChannelCtor
import iris.kmtproto.tl.gen.InputUserSelf
import iris.kmtproto.tl.gen.MessagesChatInviteJoinResult
import iris.kmtproto.tl.gen.MessagesChatInviteJoinResultOk
import iris.kmtproto.tl.gen.MessagesChatInviteJoinResultWebView
import iris.kmtproto.tl.gen.MessagesDeleteChatUser
import iris.kmtproto.tl.gen.MessagesImportChatInvite
import iris.kmtproto.tl.gen.Peer
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.Updates
import iris.kmtproto.tl.gen.UpdatesCombined
import iris.kmtproto.tl.gen.UpdatesCtor
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

    fun joinAsync(link: String): Deferred<RpcResponse<MessagesChatInviteJoinResult>> = client.apiAsync { join(link) }

    suspend fun join(link: String): RpcResponse<MessagesChatInviteJoinResult> {
        val invite = stripInviteHash(link)
        if (invite.isNotEmpty()) {
            val raw = client.invoke(MessagesImportChatInvite(invite))
            raw.result?.let { rememberJoin(it) }
            return raw
        }
        val resolved = contacts.resolveUsername(link)
        val err = resolved.error
        if (err != null) return RpcResponse(null, err)
        return joinPeer(resolved.result!!.peer)
    }

    fun joinAsync(peerId: Long): Deferred<RpcResponse<MessagesChatInviteJoinResult>> = client.apiAsync { join(peerId) }

    suspend fun join(peerId: Long): RpcResponse<MessagesChatInviteJoinResult> {
        val channel = client.inputPeerFromId(peerId).asInputChannel()
            ?: return RpcResponse(null, RpcError(400, "PEER_ID_INVALID"))
        val raw = client.invoke(ChannelsJoinChannel(channel))
        raw.result?.let { rememberJoin(it) }
        return raw
    }

    fun leaveAsync(peerId: Long): Deferred<RpcResponse<Updates>> = client.apiAsync { leave(peerId) }

    suspend fun leave(peerId: Long): RpcResponse<Updates> {
        val peer = client.inputPeerFromId(peerId)
        val channel = peer.asInputChannel()
        val raw = if (channel != null) {
            client.invoke(ChannelsLeaveChannel(channel))
        } else if (peerId < 0L) {
            client.invoke(MessagesDeleteChatUser(-peerId, InputUserSelf))
        } else {
            return RpcResponse(null, RpcError(400, "PEER_ID_INVALID"))
        }
        raw.result?.let { rememberUpdates(it) }
        return raw
    }

    private suspend fun joinPeer(peer: Peer): RpcResponse<MessagesChatInviteJoinResult> {
        val channel = (peer as? PeerChannel)?.let { InputChannelCtor(it.channelId, client.accessHash(it.botApiChatId())) }
            ?: return RpcResponse(null, RpcError(400, "PEER_ID_INVALID"))
        val raw = client.invoke(ChannelsJoinChannel(channel))
        raw.result?.let { rememberJoin(it) }
        return raw
    }

    private fun rememberJoin(result: MessagesChatInviteJoinResult) {
        when (result) {
            is MessagesChatInviteJoinResultOk -> rememberUpdates(result.updates)
            is MessagesChatInviteJoinResultWebView -> client.rememberUsers(result.users)
        }
    }

    private fun rememberUpdates(updates: Updates) {
        when (updates) {
            is UpdatesCtor -> {
                client.rememberUsers(updates.users)
                client.rememberChats(updates.chats)
            }
            is UpdatesCombined -> {
                client.rememberUsers(updates.users)
                client.rememberChats(updates.chats)
            }
            else -> Unit
        }
    }
}
