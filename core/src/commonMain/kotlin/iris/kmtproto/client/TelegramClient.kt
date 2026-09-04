package iris.kmtproto.client

import iris.kmtproto.isDisconnect
import iris.kmtproto.logCaught
import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.mtproto.Handshake
import iris.kmtproto.mtproto.RpcException
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tl.BadMsgNotification
import iris.kmtproto.tl.BadServerSalt
import iris.kmtproto.tl.InitConnection
import iris.kmtproto.tl.InvokeWithLayer
import iris.kmtproto.tl.InvokeWithoutUpdates
import iris.kmtproto.tl.Ping
import iris.kmtproto.tl.Pong
import iris.kmtproto.tl.RpcError
import iris.kmtproto.tl.RpcResult
import iris.kmtproto.tl.TlMethod
import iris.kmtproto.tl.TlObject
import iris.kmtproto.tl.gen.Channel
import iris.kmtproto.tl.gen.ChannelMessagesFilterEmpty
import iris.kmtproto.tl.gen.InputChannelCtor
import iris.kmtproto.tl.gen.InputPeer
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateChannelTooLong
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UpdateShort
import iris.kmtproto.tl.gen.UpdateShortChatMessage
import iris.kmtproto.tl.gen.UpdateShortMessage
import iris.kmtproto.tl.gen.UpdateShortSentMessage
import iris.kmtproto.tl.gen.Updates
import iris.kmtproto.tl.gen.UpdatesChannelDifferenceCtor
import iris.kmtproto.tl.gen.UpdatesChannelDifferenceEmpty
import iris.kmtproto.tl.gen.UpdatesChannelDifferenceTooLong
import iris.kmtproto.tl.gen.UpdatesCombined
import iris.kmtproto.tl.gen.UpdatesCtor
import iris.kmtproto.tl.gen.UpdatesDifference
import iris.kmtproto.tl.gen.UpdatesDifferenceCtor
import iris.kmtproto.tl.gen.UpdatesDifferenceEmpty
import iris.kmtproto.tl.gen.UpdatesDifferenceSlice
import iris.kmtproto.tl.gen.UpdatesDifferenceTooLong
import iris.kmtproto.tl.gen.UpdatesGetChannelDifference
import iris.kmtproto.tl.gen.UpdatesGetDifference
import iris.kmtproto.tl.gen.UpdatesGetState
import iris.kmtproto.tl.gen.UpdatesState
import iris.kmtproto.tl.gen.UpdatesTooLong
import iris.kmtproto.tl.gen.UploadGetCdnFile
import iris.kmtproto.tl.gen.UploadGetFile
import iris.kmtproto.tl.gen.UploadGetWebFile
import iris.kmtproto.tl.gen.UploadSaveBigFilePart
import iris.kmtproto.tl.gen.UploadSaveFilePart
import iris.kmtproto.tl.gen.User
import iris.kmtproto.transport.Datacenter
import iris.kmtproto.transport.Proxy
import iris.kmtproto.transport.connectObfuscated
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

data class ClientInfo(
    val deviceModel: String = "Iris kMTProto",
    val systemVersion: String = "JVM",
    val appVersion: String = "0.1.0",
    val systemLangCode: String = "en",
    val langPack: String = "",
    val langCode: String = "en",
)

/**
 * MTProto client. Three sockets: updates (subscribed), rpc (`invokeWithoutUpdates`),
 * media (files, closes after [MEDIA_IDLE_MS] idle). Short RPC names are suspend; `*Async` returns [Deferred].
 * Common pts = DM + basic groups. Each channel/supergroup has its own pts (LRU).
 */
class TelegramClient(
    val apiId: Int,
    val apiHash: String,
    val dc: Datacenter = Datacenter.DC2,
    val info: ClientInfo = ClientInfo(),
    val layer: Int = API_LAYER,
    val storage: Storage = MemoryStorage(),
    val proxy: Proxy? = null,
) {
    private var currentDc: Datacenter = dc
    private var supervisor = SupervisorJob()
    private var scope = CoroutineScope(supervisor + Dispatchers.Default)
    private var apiScope = CoroutineScope(supervisor + Dispatchers.Default)
    private val channels = ChannelCursors()
    private val incomingUpdates = MutableSharedFlow<Update>(extraBufferCapacity = 256, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private var catchingCommon = false
    private var catchingChannel: Long? = null
    private var updatesLink: SocketLink? = null
    private var rpcLink: SocketLink? = null
    private var mediaLink: SocketLink? = null
    private val mediaMutex = Mutex()
    private var mediaLastUse = 0L

    var user: User? = null
        internal set
    var updatesState: UpdatesState? = null
        internal set
    internal var loadedSession: ClientSession? = null

    val isConnected: Boolean get() = updatesLink?.isBound == true && rpcLink?.isBound == true
    val authKey: AuthKey? get() = updatesLink?.connection?.authKey
    val datacenter: Datacenter get() = currentDc

    fun session(): ClientSession? {
        val keyBytes = updatesLink?.connection?.authKey?.key ?: loadedSession?.authKey ?: return null
        return ClientSession(
            dcId = currentDc.id,
            authKey = keyBytes.copyOf(),
            salt = updatesLink?.connection?.salt ?: loadedSession?.salt ?: 0L,
            userId = user?.id ?: loadedSession?.userId ?: 0L,
            accessHash = user?.accessHashOrZero ?: loadedSession?.accessHash ?: 0L,
        )
    }

    fun incomingUpdates(): Flow<Update> = incomingUpdates.asSharedFlow()

    fun incomingMessages(): Flow<MessageCtor> = incomingUpdates().mapNotNull { textFromUpdate(it) }

    fun accessHash(id: Long): Long = storage.getAccessHash(id)

    /** Bot-API chat id → [InputPeer], access_hash from [storage]. */
    fun inputPeerFromId(peerId: Long): InputPeer = inputPeerFromBotApiId(peerId, hashFor(peerId))

    private fun hashFor(peerId: Long): Long = when {
        peerId > 0L -> accessHash(peerId)
        peerId <= -1_000_000_000_000L -> accessHash(-peerId - 1_000_000_000_000L)
        else -> 0L
    }

    suspend fun connect(target: Datacenter = currentDc, session: ClientSession? = null) {
        close()
        supervisor = SupervisorJob()
        scope = CoroutineScope(supervisor + Dispatchers.Default)
        apiScope = CoroutineScope(supervisor + Dispatchers.Default)
        loadedSession = session
        currentDc = session?.let { Datacenter.production(it.dcId) } ?: target
        updatesState = null
        user = null
        channels.clear()
        val updates = SocketLink("updates")
        val rpc = SocketLink("rpc")
        updatesLink = updates
        rpcLink = rpc
        val (key, salt, timeOffset) = withContext(updates.threads.read) {
            val t = connectObfuscated(currentDc, proxy)
            val opened = if (session != null) {
                Triple(AuthKey(session.authKey), session.salt, 0)
            } else {
                val hs = Handshake.perform(t, currentDc.id)
                Triple(hs.authKey, hs.serverSalt, hs.timeOffset)
            }
            updates.attach(t, opened.first, opened.second, opened.third)
            opened
        }
        rpc.open(currentDc, proxy, key, salt, 0)
        startMux()
    }

    private fun startMux() {
        val updates = updatesLink ?: return
        val rpc = rpcLink ?: return
        scope.launch {
            updates.readerLoop(
                alive = { supervisor.isActive },
                onEvent = { obj ->
                    try {
                        dispatch(obj)
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        if (!isDisconnect(e)) logCaught("dispatch", e)
                    }
                },
                reconnect = { rebindLink(updates) },
            )
        }
        scope.launch {
            rpc.readerLoop(
                alive = { supervisor.isActive },
                onEvent = { },
                reconnect = { rebindLink(rpc) },
            )
        }
        apiScope.launch {
            while (supervisor.isActive) {
                delay(30_000)
                pingOpen(updates)
                pingOpen(rpc)
                mediaLink?.let { pingOpen(it) }
            }
        }
        apiScope.launch { mediaIdleLoop() }
    }

    private suspend fun pingOpen(link: SocketLink) {
        if (!link.isBound) return
        runCatching { invokeRaw(link, Ping(PlatformCrypto.randomBytes(8).readLongLe())) }.onFailure {
            if (it.message == "call connect() first") return@onFailure
            if (!isDisconnect(it) && it !is CancellationException) logCaught("${link.name}-ping", it)
        }
    }

    private suspend fun rebindLink(link: SocketLink) {
        if (link.stop) return
        val snap = session() ?: return
        link.rebind(currentDc, proxy, AuthKey(snap.authKey), snap.salt)
        loadedSession = snap.copy(salt = link.connection?.salt ?: snap.salt)
    }

    private suspend fun mediaIdleLoop() {
        while (supervisor.isActive) {
            delay(1_000)
            val link = mediaLink ?: continue
            if (PlatformCrypto.currentTimeMillis() - mediaLastUse < MEDIA_IDLE_MS) continue
            mediaMutex.withLock {
                val cur = mediaLink ?: return@withLock
                if (PlatformCrypto.currentTimeMillis() - mediaLastUse < MEDIA_IDLE_MS) return@withLock
                println("kmtproto [media] idle ${MEDIA_IDLE_MS}ms, closing")
                mediaLink = null
                cur.shutdown()
            }
        }
    }

    private suspend fun ensureMedia(): SocketLink = mediaMutex.withLock {
        val open = mediaLink
        if (open != null && open.isBound && !open.stop) {
            mediaLastUse = PlatformCrypto.currentTimeMillis()
            return@withLock open
        }
        open?.shutdown()
        val snap = session() ?: error("call connect() first")
        val link = SocketLink("media")
        link.open(currentDc, proxy, AuthKey(snap.authKey), snap.salt, 0)
        mediaLink = link
        mediaLastUse = PlatformCrypto.currentTimeMillis()
        scope.launch {
            link.readerLoop(
                alive = { supervisor.isActive && !link.stop },
                onEvent = { },
                reconnect = {
                    if (!link.stop) rebindLink(link)
                    mediaLastUse = PlatformCrypto.currentTimeMillis()
                },
            )
        }
        link
    }

    fun pingAsync(pingId: Long = PlatformCrypto.randomBytes(8).readLongLe()): Deferred<Pong> =
        apiAsync { ping(pingId) }

    suspend fun ping(pingId: Long = PlatformCrypto.randomBytes(8).readLongLe()): Pong {
        val link = updatesLink ?: error("call connect() first")
        return invokeRaw(link, Ping(pingId))
    }

    fun getStateAsync(): Deferred<UpdatesState> = apiAsync { getState() }

    suspend fun getState(): UpdatesState {
        val state = invoke(UpdatesGetState)
        updatesState = state
        return state
    }

    fun getDifferenceAsync(
        pts: Int? = null,
        date: Int? = null,
        qts: Int? = null,
    ): Deferred<UpdatesDifference> = apiAsync { getDifference(pts, date, qts) }

    suspend fun getDifference(
        pts: Int? = null,
        date: Int? = null,
        qts: Int? = null,
    ): UpdatesDifference {
        val st = updatesState
        val diff = invoke(
            UpdatesGetDifference(
                pts = pts ?: st?.pts ?: error("call getState() first"),
                date = date ?: st?.date ?: 0,
                qts = qts ?: st?.qts ?: 0,
            ),
        )
        applyDifference(diff)
        return diff
    }

    fun syncUpdatesAsync(): Deferred<UpdatesDifferenceCtor?> = apiAsync { syncUpdates() }

    suspend fun syncUpdates(): UpdatesDifferenceCtor? {
        if (updatesState == null) getState()
        val messages = ArrayList<iris.kmtproto.tl.gen.Message>()
        val encrypted = ArrayList<iris.kmtproto.tl.gen.EncryptedMessage>()
        val updates = ArrayList<Update>()
        val chats = ArrayList<iris.kmtproto.tl.gen.Chat>()
        val users = ArrayList<User>()
        var lastState: UpdatesState? = updatesState
        var any = false
        while (true) {
            when (val d = getDifference()) {
                is UpdatesDifferenceEmpty -> break
                is UpdatesDifferenceTooLong -> {
                    getState()
                    break
                }
                is UpdatesDifferenceCtor -> {
                    any = true
                    messages += d.newMessages
                    encrypted += d.newEncryptedMessages
                    updates += d.otherUpdates
                    chats += d.chats
                    users += d.users
                    lastState = d.state
                    return UpdatesDifferenceCtor(messages, encrypted, updates, chats, users, d.state)
                }
                is UpdatesDifferenceSlice -> {
                    any = true
                    messages += d.newMessages
                    encrypted += d.newEncryptedMessages
                    updates += d.otherUpdates
                    chats += d.chats
                    users += d.users
                    lastState = d.intermediateState
                }
            }
        }
        val state = lastState ?: return null
        if (!any) return null
        return UpdatesDifferenceCtor(messages, encrypted, updates, chats, users, state)
    }

    fun <T : TlObject> invokeAsync(method: TlMethod<T>): Deferred<T> = apiAsync { invoke(method) }

    internal fun <T> apiAsync(block: suspend () -> T): Deferred<T> =
        apiScope.async { block() }

    suspend fun <T : TlObject> invoke(method: TlMethod<T>): T {
        val link = when {
            isUpdatesMethod(method) -> updatesLink ?: error("call connect() first")
            isMediaMethod(method) -> ensureMedia()
            else -> rpcLink ?: error("call connect() first")
        }
        if (link === mediaLink) mediaLastUse = PlatformCrypto.currentTimeMillis()
        val wrapped = wrapFor(link, method)
        return try {
            val result = invokeRaw(link, wrapped)
            link.layerReady = true
            result
        } catch (e: RpcException) {
            val migrateTo = migrateDc(e.message)
            if (migrateTo == null || migrateTo == currentDc.id) throw e
            connect(Datacenter.production(migrateTo))
            invoke(method)
        }
    }

    private fun <T : TlObject> wrapFor(link: SocketLink, method: TlMethod<T>): TlMethod<T> {
        val body: TlMethod<T> = if (link === updatesLink) method else InvokeWithoutUpdates(method)
        if (link.layerReady) return body
        return InvokeWithLayer(
            layer = layer,
            query = InitConnection(
                apiId = apiId,
                deviceModel = info.deviceModel,
                systemVersion = info.systemVersion,
                appVersion = info.appVersion,
                systemLangCode = info.systemLangCode,
                langPack = info.langPack,
                langCode = info.langCode,
                query = body,
            ),
        )
    }

    private fun isUpdatesMethod(method: TlObject): Boolean =
        method is UpdatesGetState || method is UpdatesGetDifference || method is UpdatesGetChannelDifference

    private fun isMediaMethod(method: TlObject): Boolean =
        method is UploadSaveFilePart ||
            method is UploadSaveBigFilePart ||
            method is UploadGetFile ||
            method is UploadGetWebFile ||
            method is UploadGetCdnFile

    private suspend fun <T : TlObject> invokeRaw(link: SocketLink, method: TlMethod<T>): T {
        when (val raw = link.sendRpc(method)) {
            is RpcError -> throw RpcException(raw.errorCode, raw.errorMessage)
            is RpcResult -> {
                val r = raw.result
                if (r is RpcError) throw RpcException(r.errorCode, r.errorMessage)
                @Suppress("UNCHECKED_CAST")
                return r as T
            }
            is Pong -> {
                @Suppress("UNCHECKED_CAST")
                return raw as T
            }
            is BadServerSalt -> return invokeRaw(link, method)
            is BadMsgNotification -> error("bad_msg_notification code=${raw.errorCode} msg=${raw.badMsgId}")
            else -> error("no rpc_result for $method (got $raw)")
        }
    }

    private fun dispatch(obj: TlObject) {
        if (obj is Update) emitUpdate(obj)
        when (obj) {
            is Updates -> dispatchUpdates(obj)
            is UpdateNewMessage -> onCommon(obj.pts, obj.ptsCount, obj.message.asText())
            is UpdateNewChannelMessage -> onChannel(obj)
            is UpdateChannelTooLong -> scheduleCatchUpChannel(obj.channelId, obj.pts)
            else -> Unit
        }
    }

    private fun dispatchUpdates(raw: Updates) {
        when (raw) {
            is UpdateShortSentMessage -> applyCommonPts(raw.pts, raw.ptsCount)
            is UpdateShortMessage -> {
                applyCommonPts(raw.pts, raw.ptsCount)
                emitUpdate(
                    UpdateNewMessage(
                        message = MessageCtor(
                            id = raw.id,
                            peerId = PeerUser(raw.userId),
                            date = raw.date,
                            message = raw.message,
                            out = raw.out,
                            fromId = PeerUser(raw.userId),
                        ),
                        pts = raw.pts,
                        ptsCount = raw.ptsCount,
                    ),
                )
            }
            is UpdateShortChatMessage -> {
                applyCommonPts(raw.pts, raw.ptsCount)
                emitUpdate(
                    UpdateNewMessage(
                        message = MessageCtor(
                            id = raw.id,
                            peerId = PeerChat(raw.chatId),
                            date = raw.date,
                            message = raw.message,
                            out = raw.out,
                            fromId = PeerUser(raw.fromId),
                        ),
                        pts = raw.pts,
                        ptsCount = raw.ptsCount,
                    ),
                )
            }
            is UpdateShort -> dispatch(raw.update)
            is UpdatesCtor -> {
                rememberUsers(raw.users)
                rememberChats(raw.chats)
                raw.updates.forEach { dispatch(it) }
                if (raw.seq > 0) {
                    val st = updatesState
                    if (st != null) updatesState = st.updated(date = raw.date, seq = raw.seq)
                }
            }
            is UpdatesCombined -> {
                rememberUsers(raw.users)
                rememberChats(raw.chats)
                raw.updates.forEach { dispatch(it) }
                if (raw.seq > 0) {
                    val st = updatesState
                    if (st != null) updatesState = st.updated(date = raw.date, seq = raw.seq)
                }
            }
            is UpdatesTooLong -> scheduleCatchUpCommon()
        }
    }

    private fun onCommon(pts: Int, count: Int, msg: MessageCtor?) {
        val st = updatesState
        if (st == null) {
            scheduleCatchUpCommon()
            return
        }
        when {
            pts <= st.pts -> Unit
            count > 0 && pts == st.pts + count -> {
                updatesState = st.updated(pts = pts)
            }
            else -> scheduleCatchUpCommon()
        }
    }

    private fun onChannel(u: UpdateNewChannelMessage) {
        val msg = u.message.asText() ?: return
        val id = (msg.peerId as? PeerChannel)?.channelId ?: return
        val cur = channels.get(id)
        when {
            cur == null || cur.pts == 0 -> {
                channels.put(id, u.pts)
            }
            u.pts <= cur.pts -> Unit
            u.ptsCount > 0 && u.pts == cur.pts + u.ptsCount -> {
                channels.put(id, u.pts)
            }
            else -> scheduleCatchUpChannel(id, cur.pts)
        }
    }

    private fun scheduleCatchUpCommon() {
        if (catchingCommon) return
        catchingCommon = true
        apiScope.launch {
            try {
                catchUpCommon()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                if (!isDisconnect(e)) logCaught("catch-up", e)
            } finally {
                catchingCommon = false
            }
        }
    }

    private fun scheduleCatchUpChannel(channelId: Long, ptsHint: Int?) {
        if (catchingChannel == channelId) return
        if (storage.getAccessHash(channelId) == 0L) return
        catchingChannel = channelId
        apiScope.launch {
            try {
                catchUpChannel(channelId, ptsHint)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                if (!isDisconnect(e)) logCaught("catch-up-channel", e)
            } finally {
                if (catchingChannel == channelId) catchingChannel = null
            }
        }
    }

    private suspend fun catchUpCommon() {
        if (updatesState == null) getState()
        when (val diff = syncUpdates()) {
            null -> Unit
            else -> {
                rememberUsers(diff.users)
                rememberChats(diff.chats)
                diff.newMessages.mapNotNull { it.asText() }.forEach { emitUpdate(UpdateNewMessage(message = it, pts = 0, ptsCount = 0)) }
                diff.otherUpdates.forEach { if (it is Update) emitUpdate(it) }
            }
        }
    }

    private suspend fun catchUpChannel(channelId: Long, ptsHint: Int?) {
        val cur = channels.get(channelId)
        val hash = storage.getAccessHash(channelId)
        if (hash == 0L) return
        val pts = ptsHint ?: cur?.pts ?: return
        when (
            val diff = invoke(
                UpdatesGetChannelDifference(
                    channel = InputChannelCtor(channelId, hash),
                    filter = ChannelMessagesFilterEmpty,
                    pts = pts,
                    limit = 100,
                ),
            )
        ) {
            is UpdatesChannelDifferenceEmpty -> channels.put(channelId, diff.pts)
            is UpdatesChannelDifferenceCtor -> {
                rememberUsers(diff.users)
                rememberChats(diff.chats)
                channels.put(channelId, diff.pts)
                diff.newMessages.mapNotNull { it.asText() }.forEach { emitUpdate(UpdateNewChannelMessage(message = it, pts = diff.pts, ptsCount = 0)) }
                diff.otherUpdates.forEach { dispatch(it) }
            }
            is UpdatesChannelDifferenceTooLong -> channels.remove(channelId)
        }
    }

    private fun applyCommonPts(pts: Int, count: Int) {
        val st = updatesState ?: return
        if (count <= 0) return
        if (pts == st.pts + count) updatesState = st.updated(pts = pts)
    }

    private fun applyDifference(diff: UpdatesDifference) {
        when (diff) {
            is UpdatesDifferenceEmpty -> {
                val st = updatesState
                if (st != null) updatesState = st.updated(date = diff.date, seq = diff.seq)
            }
            is UpdatesDifferenceTooLong -> Unit
            is UpdatesDifferenceCtor -> {
                updatesState = diff.state
                rememberUsers(diff.users)
                rememberChats(diff.chats)
            }
            is UpdatesDifferenceSlice -> {
                updatesState = diff.intermediateState
                rememberUsers(diff.users)
                rememberChats(diff.chats)
            }
        }
    }

    private fun rememberChats(list: List<iris.kmtproto.tl.gen.Chat>) {
        for (obj in list) {
            if (obj is Channel) {
                val hash = obj.accessHash
                if (hash != null) {
                    storage.putAccessHash(obj.id, hash)
                }
            }
        }
    }

    private fun rememberUsers(list: List<User>) {
        for (obj in list) rememberUser(obj)
    }

    internal fun rememberUser(user: User) {
        val hash = user.accessHashOrZero
        if (hash != 0L) storage.putAccessHash(user.id, hash)
    }

    private fun emitUpdate(update: Update) {
        incomingUpdates.tryEmit(update)
    }

    suspend fun close() {
        supervisor.cancel()
        updatesLink?.shutdown()
        rpcLink?.shutdown()
        mediaLink?.shutdown()
        updatesLink = null
        rpcLink = null
        mediaLink = null
        updatesState = null
        channels.clear()
    }
}

private fun UpdatesState.updated(
    pts: Int = this.pts,
    date: Int = this.date,
    seq: Int = this.seq,
): UpdatesState = UpdatesState(
    pts = pts,
    qts = qts,
    date = date,
    seq = seq,
    unreadCount = unreadCount,
)

internal fun migrateDc(message: String): Int? {
    val match = Regex("(USER|PHONE|NETWORK|STATS)_MIGRATE_(\\d+)").find(message) ?: return null
    return match.groupValues[2].toInt()
}

internal fun incomingTexts(diff: UpdatesDifferenceCtor): List<MessageCtor> {
    val out = ArrayList<MessageCtor>()
    out.addAll(diff.newMessages.mapNotNull { it.asText() })
    for (u in diff.otherUpdates) {
        val t = textFromUpdate(u)
        if (t != null) out += t
    }
    return out
}
