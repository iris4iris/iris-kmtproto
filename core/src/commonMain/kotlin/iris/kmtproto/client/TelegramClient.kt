package iris.kmtproto.client

import iris.kmtproto.logCaught
import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.mtproto.EncryptedConnection
import iris.kmtproto.mtproto.Handshake
import iris.kmtproto.mtproto.MsgIdFactory
import iris.kmtproto.mtproto.RpcException
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tl.BadMsgNotification
import iris.kmtproto.tl.BadServerSalt
import iris.kmtproto.tl.InitConnection
import iris.kmtproto.tl.InvokeWithLayer
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
import iris.kmtproto.tl.gen.User
import iris.kmtproto.transport.Datacenter
import iris.kmtproto.transport.MtprotoTransport
import iris.kmtproto.transport.Proxy
import iris.kmtproto.transport.connectObfuscated
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel as EventChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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
 * MTProto client. One socket reader. Short RPC names are suspend; `*Async` returns [Deferred].
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
    private var transport: MtprotoTransport? = null
    private var connection: EncryptedConnection? = null
    private var layerInitialized = false
    private var supervisor = SupervisorJob()
    private var scope = CoroutineScope(supervisor + Dispatchers.Default)
    private val channels = ChannelCursors()
    private var eventQueue = EventChannel<TlObject>(EventChannel.UNLIMITED)
    private var incoming = EventChannel<MessageCtor>(256, BufferOverflow.DROP_OLDEST)
    private var catchingCommon = false
    private var catchingChannel: Long? = null
    private val bindMutex = Mutex()

    var user: User? = null
        internal set
    var updatesState: UpdatesState? = null
        internal set
    internal var loadedSession: ClientSession? = null

    val isConnected: Boolean get() = connection != null
    val authKey: AuthKey? get() = connection?.authKey
    val datacenter: Datacenter get() = currentDc

    fun session(): ClientSession? {
        val keyBytes = connection?.authKey?.key ?: loadedSession?.authKey ?: return null
        return ClientSession(
            dcId = currentDc.id,
            authKey = keyBytes.copyOf(),
            salt = connection?.salt ?: loadedSession?.salt ?: 0L,
            userId = user?.id ?: loadedSession?.userId ?: 0L,
            accessHash = user?.accessHashOrZero ?: loadedSession?.accessHash ?: 0L,
        )
    }

    fun incomingMessages(): Flow<MessageCtor> = incoming.receiveAsFlow()

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
        loadedSession = session
        currentDc = session?.let { Datacenter.production(it.dcId) } ?: target
        layerInitialized = false
        updatesState = null
        user = null
        channels.clear()
        eventQueue = EventChannel(EventChannel.UNLIMITED)
        incoming = EventChannel(256, BufferOverflow.DROP_OLDEST)
        val t = connectObfuscated(currentDc, proxy)
        transport = t
        val key: AuthKey
        val salt: Long
        val timeOffset: Int
        if (session != null) {
            key = AuthKey(session.authKey)
            salt = session.salt
            timeOffset = 0
        } else {
            val hs = Handshake.perform(t, currentDc.id)
            key = hs.authKey
            salt = hs.serverSalt
            timeOffset = hs.timeOffset
        }
        t.setReadTimeoutMs(0)
        val sessionId = PlatformCrypto.randomBytes(8).readLongLe()
        val conn = EncryptedConnection(
            transport = t,
            authKey = key,
            salt = salt,
            sessionId = sessionId,
            msgIds = MsgIdFactory(timeOffset),
        )
        connection = conn
        startMux()
    }

    private fun startMux() {
        scope.launch { readerLoop() }
        scope.launch {
            for (e in eventQueue) {
                runCatching { dispatch(e) }.onFailure { logCaught("dispatch", it) }
            }
        }
        scope.launch {
            while (supervisor.isActive) {
                delay(30_000)
                runCatching { ping() }.onFailure { logCaught("ping", it) }
            }
        }
    }

    private suspend fun readerLoop() {
        var backoff = 500L
        while (supervisor.isActive) {
            val conn = connection ?: break
            try {
                conn.runReader {
                    try {
                        eventQueue.trySend(it)
                    } catch (e: Exception) {
                        logCaught("eventQueue", e)
                    }
                }
                break
            } catch (e: CancellationException) {
                if (!supervisor.isActive) break
                logCaught("reader-cancel", e)
                delay(backoff)
            } catch (e: Throwable) {
                if (!supervisor.isActive) break
                logCaught("reader", e)
                runCatching { rebindSocket() }.onFailure { logCaught("rebind", it) }
                delay(backoff)
                backoff = (backoff * 2).coerceAtMost(15_000L)
            }
        }
    }

    /** Same auth_key, new TCP + session_id. Does not loginBot. */
    private suspend fun rebindSocket() = bindMutex.withLock {
        val snap = session() ?: return@withLock
        val oldConn = connection
        val oldTransport = transport
        connection = null
        transport = null
        oldConn?.failPending(CancellationException("reconnect"))
        runCatching { oldTransport?.close() }.onFailure { logCaught("rebind-close", it) }
        val t = connectObfuscated(currentDc, proxy)
        t.setReadTimeoutMs(0)
        transport = t
        val conn = EncryptedConnection(
            transport = t,
            authKey = AuthKey(snap.authKey),
            salt = snap.salt,
            sessionId = PlatformCrypto.randomBytes(8).readLongLe(),
            msgIds = MsgIdFactory(0),
        )
        layerInitialized = false
        connection = conn
        loadedSession = snap.copy(salt = conn.salt)
    }

    fun pingAsync(pingId: Long = PlatformCrypto.randomBytes(8).readLongLe()): Deferred<Pong> =
        apiAsync { ping(pingId) }

    suspend fun ping(pingId: Long = PlatformCrypto.randomBytes(8).readLongLe()): Pong {
        val raw = sendRpc(Ping(pingId))
        return raw as? Pong
            ?: (raw as? RpcResult)?.result as? Pong
            ?: error("ping without pong: $raw")
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
        scope.async { block() }

    suspend fun <T : TlObject> invoke(method: TlMethod<T>): T {
        val wrapped: TlMethod<T> = if (layerInitialized) {
            method
        } else {
            InvokeWithLayer(
                layer = layer,
                query = InitConnection(
                    apiId = apiId,
                    deviceModel = info.deviceModel,
                    systemVersion = info.systemVersion,
                    appVersion = info.appVersion,
                    systemLangCode = info.systemLangCode,
                    langPack = info.langPack,
                    langCode = info.langCode,
                    query = method,
                ),
            )
        }
        return try {
            val result = invokeRaw(wrapped)
            layerInitialized = true
            result
        } catch (e: RpcException) {
            val migrateTo = migrateDc(e.message)
            if (migrateTo == null || migrateTo == currentDc.id) throw e
            connect(Datacenter.production(migrateTo))
            invoke(method)
        }
    }

    private suspend fun <T : TlObject> invokeRaw(method: TlMethod<T>): T {
        when (val raw = sendRpc(method)) {
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
            is BadServerSalt -> return invokeRaw(method)
            is BadMsgNotification -> error("bad_msg_notification code=${raw.errorCode} msg=${raw.badMsgId}")
            else -> error("no rpc_result for $method (got $raw)")
        }
    }

    private suspend fun sendRpc(obj: TlObject): TlObject {
        val conn = connection ?: error("call connect() first")
        return conn.sendRpc(obj)
    }

    private suspend fun dispatch(obj: TlObject) {
        when (obj) {
            is Updates -> dispatchUpdates(obj)
            is UpdateNewMessage -> onCommon(obj.pts, obj.ptsCount, obj.message.asText())
            is UpdateNewChannelMessage -> onChannel(obj)
            is UpdateChannelTooLong -> catchUpChannel(obj.channelId, obj.pts)
            is Update -> Unit
            else -> Unit
        }
    }

    private suspend fun dispatchUpdates(raw: Updates) {
        when (raw) {
            is UpdateShortSentMessage -> applyCommonPts(raw.pts, raw.ptsCount)
            is UpdateShortMessage -> {
                applyCommonPts(raw.pts, raw.ptsCount)
                emit(
                    MessageCtor(
                        id = raw.id,
                        peerId = PeerUser(raw.userId),
                        date = raw.date,
                        message = raw.message,
                        out = raw.out,
                        fromId = PeerUser(raw.userId),
                    ),
                )
            }
            is UpdateShortChatMessage -> applyCommonPts(raw.pts, raw.ptsCount)
            is UpdateShort -> dispatch(raw.update)
            is UpdatesCtor -> {
                rememberUsers(raw.users)
                rememberChats(raw.chats)
                raw.updates.forEach { dispatch(it) }
                if (raw.seq > 0) {
                    val st = updatesState
                    if (st != null) updatesState = st.copy(date = raw.date, seq = raw.seq)
                }
            }
            is UpdatesCombined -> {
                rememberUsers(raw.users)
                rememberChats(raw.chats)
                raw.updates.forEach { dispatch(it) }
                if (raw.seq > 0) {
                    val st = updatesState
                    if (st != null) updatesState = st.copy(date = raw.date, seq = raw.seq)
                }
            }
            is UpdatesTooLong -> catchUpCommon()
        }
    }

    private suspend fun onCommon(pts: Int, count: Int, msg: MessageCtor?) {
        val st = updatesState
        if (st == null) {
            getState()
            return
        }
        when {
            pts <= st.pts -> Unit
            count > 0 && pts == st.pts + count -> {
                updatesState = st.copy(pts = pts)
                if (msg != null) emit(msg)
            }
            else -> catchUpCommon()
        }
    }

    private suspend fun onChannel(u: UpdateNewChannelMessage) {
        val msg = u.message.asText() ?: return
        val id = (msg.peerId as? PeerChannel)?.channelId ?: return
        val cur = channels.get(id)
        when {
            cur == null || cur.pts == 0 -> {
                channels.put(id, u.pts)
                emit(msg)
            }
            u.pts <= cur.pts -> Unit
            u.ptsCount > 0 && u.pts == cur.pts + u.ptsCount -> {
                channels.put(id, u.pts)
                emit(msg)
            }
            else -> catchUpChannel(id, cur.pts)
        }
    }

    private suspend fun catchUpCommon() {
        if (catchingCommon) return
        catchingCommon = true
        try {
            if (updatesState == null) getState()
            when (val diff = syncUpdates()) {
                null -> Unit
                else -> {
                    rememberUsers(diff.users)
                    rememberChats(diff.chats)
                    incomingTexts(diff).forEach { emit(it) }
                }
            }
        } finally {
            catchingCommon = false
        }
    }

    private suspend fun catchUpChannel(channelId: Long, ptsHint: Int?) {
        if (catchingChannel == channelId) return
        val cur = channels.get(channelId)
        val hash = storage.getAccessHash(channelId)
        if (hash == 0L) return
        val pts = ptsHint ?: cur?.pts ?: return
        catchingChannel = channelId
        try {
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
                    diff.newMessages.mapNotNull { it.asText() }.forEach { emit(it) }
                    diff.otherUpdates.forEach { dispatch(it) }
                }
                is UpdatesChannelDifferenceTooLong -> channels.remove(channelId)
            }
        } finally {
            catchingChannel = null
        }
    }

    private fun applyCommonPts(pts: Int, count: Int) {
        val st = updatesState ?: return
        if (count <= 0) return
        if (pts == st.pts + count) updatesState = st.copy(pts = pts)
    }

    private fun applyDifference(diff: UpdatesDifference) {
        when (diff) {
            is UpdatesDifferenceEmpty -> {
                val st = updatesState
                if (st != null) updatesState = st.copy(date = diff.date, seq = diff.seq)
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

    private fun emit(msg: MessageCtor) {
        incoming.trySend(msg)
    }

    suspend fun close() {
        supervisor.cancel()
        connection?.failPending(CancellationException("closed"))
        runCatching { transport?.close() }.onFailure { logCaught("close", it) }
        transport = null
        connection = null
        layerInitialized = false
        updatesState = null
        channels.clear()
        eventQueue.close()
        incoming.close()
    }
}

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
