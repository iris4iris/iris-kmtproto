package iris.kmtproto.api.user

import iris.kmtproto.client.ClientInfo
import iris.kmtproto.client.MemoryStorage
import iris.kmtproto.client.RpcResponse
import iris.kmtproto.client.Storage
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.asInputChannel
import iris.kmtproto.client.asInputUser
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tl.RpcError
import iris.kmtproto.tl.gen.ChannelParticipant
import iris.kmtproto.tl.gen.ChannelParticipantCtor
import iris.kmtproto.tl.gen.ChannelParticipantsFilter
import iris.kmtproto.tl.gen.ChannelParticipantsRecent
import iris.kmtproto.tl.gen.ChannelsChannelParticipantsCtor
import iris.kmtproto.tl.gen.ChannelsChannelParticipantsNotModified
import iris.kmtproto.tl.gen.ChannelsEditBanned
import iris.kmtproto.tl.gen.ChannelsGetParticipants
import iris.kmtproto.tl.gen.ChannelsJoinChannel
import iris.kmtproto.tl.gen.ChannelsLeaveChannel
import iris.kmtproto.tl.gen.ChatBannedRights
import iris.kmtproto.tl.gen.ChatFullCtor
import iris.kmtproto.tl.gen.ChatParticipant
import iris.kmtproto.tl.gen.ChatParticipantAdmin
import iris.kmtproto.tl.gen.ChatParticipantCreator
import iris.kmtproto.tl.gen.ChatParticipantCtor
import iris.kmtproto.tl.gen.ChatParticipantsCtor
import iris.kmtproto.tl.gen.InputChannel
import iris.kmtproto.tl.gen.InputChannelCtor
import iris.kmtproto.tl.gen.InputUserSelf
import iris.kmtproto.tl.gen.MessagesChatInviteJoinResult
import iris.kmtproto.tl.gen.MessagesChatInviteJoinResultOk
import iris.kmtproto.tl.gen.MessagesChatInviteJoinResultWebView
import iris.kmtproto.tl.gen.MessagesDeleteChatUser
import iris.kmtproto.tl.gen.MessagesGetFullChat
import iris.kmtproto.tl.gen.MessagesImportChatInvite
import iris.kmtproto.tl.gen.Peer
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.Updates
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
        raw.result?.let { client.rememberUpdates(it) }
        return raw
    }

    fun participantsAsync(peerId: Long, limit: Int = 200, filter: ChannelParticipantsFilter = ChannelParticipantsRecent): Deferred<RpcResponse<List<ChannelParticipant>>> =
        client.apiAsync { participants(peerId, limit, filter) }

    suspend fun participants(peerId: Long, limit: Int = 200, filter: ChannelParticipantsFilter = ChannelParticipantsRecent): RpcResponse<List<ChannelParticipant>> {
        val channel = client.inputPeerFromId(peerId).asInputChannel()
        if (channel != null) return channelParticipants(channel, limit, filter)
        if (peerId < 0L) return chatParticipants(-peerId, limit)
        return RpcResponse(null, RpcError(400, "PEER_ID_INVALID"))
    }

    fun banAsync(peerId: Long, userId: Long, untilDate: Int = 0, revokeHistory: Boolean = false): Deferred<RpcResponse<Updates>> =
        client.apiAsync { ban(peerId, userId, untilDate, revokeHistory) }

    suspend fun ban(peerId: Long, userId: Long, untilDate: Int = 0, revokeHistory: Boolean = false): RpcResponse<Updates> {
        val channel = client.inputPeerFromId(peerId).asInputChannel()
        if (channel != null) return restrict(peerId, userId, ChatBannedRights(untilDate = untilDate, viewMessages = true))
        if (peerId < 0L) {
            val user = client.inputPeerFromId(userId).asInputUser()
                ?: return RpcResponse(null, RpcError(400, "PEER_ID_INVALID"))
            val raw = client.invoke(MessagesDeleteChatUser(-peerId, user, revokeHistory))
            raw.result?.let { client.rememberUpdates(it) }
            return raw
        }
        return RpcResponse(null, RpcError(400, "PEER_ID_INVALID"))
    }

    fun unbanAsync(peerId: Long, userId: Long): Deferred<RpcResponse<Updates>> = client.apiAsync { unban(peerId, userId) }

    suspend fun unban(peerId: Long, userId: Long): RpcResponse<Updates> =
        restrict(peerId, userId, ChatBannedRights(untilDate = 0))

    fun restrictAsync(peerId: Long, userId: Long, rights: ChatBannedRights): Deferred<RpcResponse<Updates>> =
        client.apiAsync { restrict(peerId, userId, rights) }

    suspend fun restrict(peerId: Long, userId: Long, rights: ChatBannedRights): RpcResponse<Updates> {
        val channel = client.inputPeerFromId(peerId).asInputChannel()
            ?: return RpcResponse(null, RpcError(400, "PEER_ID_INVALID"))
        val raw = client.invoke(ChannelsEditBanned(channel, client.inputPeerFromId(userId), rights))
        raw.result?.let { client.rememberUpdates(it) }
        return raw
    }

    fun kickAsync(peerId: Long, userId: Long, revokeHistory: Boolean = false): Deferred<RpcResponse<Updates>> =
        client.apiAsync { kick(peerId, userId, revokeHistory) }

    suspend fun kick(peerId: Long, userId: Long, revokeHistory: Boolean = false): RpcResponse<Updates> {
        if (client.inputPeerFromId(peerId).asInputChannel() == null) {
            return ban(peerId, userId, revokeHistory = revokeHistory)
        }
        val banned = ban(peerId, userId)
        if (banned.error != null) return banned
        return unban(peerId, userId)
    }

    private suspend fun joinPeer(peer: Peer): RpcResponse<MessagesChatInviteJoinResult> {
        val channel = (peer as? PeerChannel)?.let { InputChannelCtor(it.channelId, client.accessHash(it.botApiChatId())) }
            ?: return RpcResponse(null, RpcError(400, "PEER_ID_INVALID"))
        val raw = client.invoke(ChannelsJoinChannel(channel))
        raw.result?.let { rememberJoin(it) }
        return raw
    }

    private suspend fun channelParticipants(channel: InputChannel, limit: Int, filter: ChannelParticipantsFilter): RpcResponse<List<ChannelParticipant>> {
        val want = minOf(limit.coerceAtLeast(0), PARTICIPANTS_CAP)
        if (want == 0) return RpcResponse(emptyList(), null)
        val out = ArrayList<ChannelParticipant>(minOf(want, PARTICIPANTS_PAGE))
        var offset = 0
        while (out.size < want) {
            val batch = minOf(PARTICIPANTS_PAGE, want - out.size)
            val raw = client.invoke(ChannelsGetParticipants(channel, filter, offset, batch, 0L))
            val err = raw.error
            if (err != null) return if (out.isEmpty()) RpcResponse(null, err) else RpcResponse(out, null)
            when (val pack = raw.result!!) {
                is ChannelsChannelParticipantsNotModified -> break
                is ChannelsChannelParticipantsCtor -> {
                    client.rememberUsers(pack.users)
                    client.rememberChats(pack.chats)
                    if (pack.participants.isEmpty()) break
                    out += pack.participants
                    offset += pack.participants.size
                    if (pack.participants.size < batch || out.size >= pack.count) break
                }
            }
        }
        return RpcResponse(if (out.size > want) out.subList(0, want) else out, null)
    }

    private suspend fun chatParticipants(chatId: Long, limit: Int): RpcResponse<List<ChannelParticipant>> {
        val raw = client.invoke(MessagesGetFullChat(chatId))
        raw.result?.let {
            client.rememberUsers(it.users)
            client.rememberChats(it.chats)
        }
        return raw.map { full ->
            val parts = when (val chat = full.fullChat) {
                is ChatFullCtor -> (chat.participants as? ChatParticipantsCtor)?.participants.orEmpty()
                else -> emptyList()
            }
            val mapped = parts.map { it.asChannelParticipant() }
            val want = minOf(limit.coerceAtLeast(0), PARTICIPANTS_CAP)
            if (mapped.size > want) mapped.subList(0, want) else mapped
        }
    }

    private fun rememberJoin(result: MessagesChatInviteJoinResult) {
        when (result) {
            is MessagesChatInviteJoinResultOk -> client.rememberUpdates(result.updates)
            is MessagesChatInviteJoinResultWebView -> client.rememberUsers(result.users)
        }
    }
}

private const val PARTICIPANTS_PAGE = 200
private const val PARTICIPANTS_CAP = 10_000

private fun ChatParticipant.asChannelParticipant(): ChannelParticipant = when (this) {
    is ChatParticipantCtor -> ChannelParticipantCtor(userId = userId, date = date, rank = rank)
    is ChatParticipantCreator -> ChannelParticipantCtor(userId = userId, date = 0, rank = rank)
    is ChatParticipantAdmin -> ChannelParticipantCtor(userId = userId, date = date, rank = rank)
}
