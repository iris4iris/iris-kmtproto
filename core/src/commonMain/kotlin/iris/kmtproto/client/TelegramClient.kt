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
import iris.kmtproto.tl.gen.AuthExportAuthorization
import iris.kmtproto.tl.gen.AuthExportedAuthorization
import iris.kmtproto.tl.gen.AuthImportAuthorization
import iris.kmtproto.tl.gen.Channel
import iris.kmtproto.tl.gen.ChannelForbidden
import iris.kmtproto.tl.gen.ChannelMessagesFilterEmpty
import iris.kmtproto.tl.gen.InputChannelCtor
import iris.kmtproto.tl.gen.InputPeer
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateChannelTooLong
import iris.kmtproto.tl.gen.UpdateChannelWebPage
import iris.kmtproto.tl.gen.UpdateDeleteChannelMessages
import iris.kmtproto.tl.gen.UpdateDeleteMessages
import iris.kmtproto.tl.gen.UpdateEditChannelMessage
import iris.kmtproto.tl.gen.UpdateEditMessage
import iris.kmtproto.tl.gen.UpdateFolderPeers
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UpdatePinnedChannelMessages
import iris.kmtproto.tl.gen.UpdatePinnedMessages
import iris.kmtproto.tl.gen.UpdateReadHistoryInbox
import iris.kmtproto.tl.gen.UpdateReadHistoryOutbox
import iris.kmtproto.tl.gen.UpdateReadMessagesContents
import iris.kmtproto.tl.gen.UpdateShort
import iris.kmtproto.tl.gen.UpdateShortChatMessage
import iris.kmtproto.tl.gen.UpdateShortMessage
import iris.kmtproto.tl.gen.UpdateShortSentMessage
import iris.kmtproto.tl.gen.UpdateWebPage
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
    private var catchingChannel: Long = 0
    private var updatesLink: SocketLink? = null
    private var rpcLink: SocketLink? = null
    private var mediaLink: SocketLink? = null
    private val mediaMutex = Mutex()
    private var mediaLastUse = 0L
    private val fileDcMutex = Mutex()
    private val fileDcs = mutableMapOf<Int, SocketLink>()

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
            accessHash = user?.accessHash ?: loadedSession?.accessHash ?: 0L,
        )
    }

    fun incomingUpdates(): Flow<Update> = incomingUpdates.asSharedFlow()

    fun incomingMessages(): Flow<MessageCtor> = incomingUpdates().mapNotNull { textFromUpdate(it) }

    fun accessHash(id: Long): Long = storage.getAccessHash(id)

    /** Bot-API chat id → [InputPeer], access_hash from [storage]. */
    fun inputPeerFromId(peerId: Long): InputPeer = inputPeerFromBotApiId(peerId, hashFor(peerId))

    private fun hashFor(peerId: Long): Long = when {
        peerId > 0L || peerId <= -1_000_000_000_000L -> accessHash(peerId)
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
        val r = runCatching { invokeRaw(link, Ping(PlatformCrypto.randomBytes(8).readLongLe())) }.getOrElse {
            if (it.message == "call connect() first") return
            if (!isDisconnect(it) && it !is CancellationException) logCaught("${link.name}-ping", it)
            return
        }
        val err = r.error ?: return
        if (err.errorMessage.contains("FLOOD_WAIT")) logCaught("${link.name}-ping", RpcException(err.errorCode, err.errorMessage))
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

    fun pingAsync(pingId: Long = PlatformCrypto.randomBytes(8).readLongLe()): Deferred<RpcResponse<Pong>> =
        apiAsync { ping(pingId) }

    suspend fun ping(pingId: Long = PlatformCrypto.randomBytes(8).readLongLe()): RpcResponse<Pong> {
        val link = updatesLink ?: error("call connect() first")
        return invokeRaw(link, Ping(pingId))
    }

    fun getStateAsync(): Deferred<UpdatesState> = apiAsync { getState() }

    suspend fun getState(): UpdatesState {
        val state = invoke(UpdatesGetState).orThrow()
        updatesState = state
        return state
    }

    fun getDifferenceAsync(
        pts: Int = -1,
        date: Int = -1,
        qts: Int = -1,
    ): Deferred<UpdatesDifference> = apiAsync { getDifference(pts, date, qts) }

    suspend fun getDifference(
        pts: Int = -1,
        date: Int = -1,
        qts: Int = -1,
    ): UpdatesDifference {
        val st = updatesState
        val diff = invoke(
            UpdatesGetDifference(
                pts = if (pts != -1) pts else st?.pts ?: error("call getState() first"),
                date = if (date != -1) date else st?.date ?: 0,
                qts = if (qts != -1) qts else st?.qts ?: 0,
            ),
        ).orThrow()
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

    fun <T : TlObject> invokeAsync(method: TlMethod<T>): Deferred<RpcResponse<T>> = apiAsync { invoke(method) }

    internal fun <T> apiAsync(block: suspend () -> T): Deferred<T> =
        apiScope.async { block() }

    suspend fun <T : TlObject> invoke(method: TlMethod<T>): RpcResponse<T> {
        val link = when {
            isUpdatesMethod(method) -> updatesLink ?: error("call connect() first")
            isMediaMethod(method) -> ensureMedia()
            else -> rpcLink ?: error("call connect() first")
        }
        if (link === mediaLink) mediaLastUse = PlatformCrypto.currentTimeMillis()
        val wrapped = wrapFor(link, method)
        val result = invokeRaw(link, wrapped)
        val err = result.error
        if (err != null) {
            val migrateTo = migrateDc(err.errorMessage)
            if (migrateTo == 0 || migrateTo == currentDc.id) return result
            connect(Datacenter.production(migrateTo))
            return invoke(method)
        }
        link.layerReady = true
        return result
    }

    internal suspend fun <T : TlObject> invokeOnDc(dcId: Int, method: TlMethod<T>): RpcResponse<T> {
        if (dcId <= 0 || dcId == currentDc.id) return invoke(method)
        if (dcId > 5) return RpcResponse(null, RpcError(400, "FILE_MIGRATE_$dcId"))
        val opened = openFileDc(dcId)
        val err = opened.second
        if (err != null) return RpcResponse(null, err)
        return invokeOnLink(opened.first!!, method)
    }

    private suspend fun <T : TlObject> invokeOnLink(link: SocketLink, method: TlMethod<T>): RpcResponse<T> {
        val wrapped = wrapFor(link, method)
        val result = invokeRaw(link, wrapped)
        link.layerReady = true
        return result
    }

    private suspend fun openFileDc(dcId: Int): Pair<SocketLink?, RpcError?> {
        fileDcMutex.withLock {
            val existing = fileDcs[dcId]
            if (existing != null && existing.isBound && !existing.stop) return existing to null
        }
        val exported = invoke(AuthExportAuthorization(dcId))
        val expErr = exported.error
        if (expErr != null) return null to expErr
        val auth = exported.result!!
        val link = fileDcMutex.withLock {
            val existing = fileDcs[dcId]
            if (existing != null && existing.isBound && !existing.stop) return@withLock existing
            startFileDc(dcId)
        }
        if (link.layerReady) return link to null
        val imported = invokeOnLink(link, AuthImportAuthorization(auth.id, auth.bytes))
        val impErr = imported.error
        if (impErr != null) {
            dropFileDc(dcId, link)
            return null to impErr
        }
        return link to null
    }

    private suspend fun startFileDc(dcId: Int): SocketLink {
        val dc = Datacenter.production(dcId)
        val link = SocketLink("file-dc$dcId")
        withContext(link.threads.read) {
            val t = connectObfuscated(dc, proxy)
            val hs = Handshake.perform(t, dc.id)
            link.attach(t, hs.authKey, hs.serverSalt, hs.timeOffset)
        }
        fileDcs[dcId] = link
        scope.launch {
            try {
                link.readerLoop(
                    alive = { supervisor.isActive && !link.stop },
                    onEvent = { },
                    reconnect = { link.stop = true },
                )
            } finally {
                fileDcMutex.withLock { if (fileDcs[dcId] === link) fileDcs.remove(dcId) }
            }
        }
        return link
    }

    private suspend fun dropFileDc(dcId: Int, link: SocketLink) {
        link.stop = true
        fileDcMutex.withLock { if (fileDcs[dcId] === link) fileDcs.remove(dcId) }
        link.shutdown()
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

    private suspend fun <T : TlObject> invokeRaw(link: SocketLink, method: TlMethod<T>): RpcResponse<T> {
        when (val raw = link.sendRpc(method)) {
            is RpcError -> return RpcResponse(null, raw)
            is RpcResult -> {
                val r = raw.result
                if (r is RpcError) return RpcResponse(null, r)
                @Suppress("UNCHECKED_CAST")
                return RpcResponse(r as T, null)
            }
            is Pong -> {
                @Suppress("UNCHECKED_CAST")
                return RpcResponse(raw as T, null)
            }
            is BadServerSalt -> return invokeRaw(link, method)
            is BadMsgNotification -> error("bad_msg_notification code=${raw.errorCode} msg=${raw.badMsgId}")
            else -> error("no rpc_result for $method (got $raw)")
        }
    }

    private fun dispatch(obj: TlObject) {
        when (obj) {
            is Updates -> dispatchUpdates(obj)
            is UpdateChannelTooLong -> scheduleCatchUpChannel(obj.channelId, obj.pts)
            is Update -> if (shouldEmit(obj)) emitUpdate(obj)
            else -> Unit
        }
    }

    private fun dispatchUpdates(raw: Updates) {
        when (raw) {
            is UpdateShortSentMessage -> acceptCommonPts(raw.pts, raw.ptsCount)
            is UpdateShortMessage -> {
                if (!acceptCommonPts(raw.pts, raw.ptsCount)) return
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
                if (!acceptCommonPts(raw.pts, raw.ptsCount)) return
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

    /** False = duplicate (`pts <= last`) or a gap (catch-up scheduled). */
    private fun shouldEmit(u: Update): Boolean {
        commonPts(u)?.let { (pts, count) -> return acceptCommonPts(pts, count) }
        channelPts(u)?.let { (id, pts, count) -> return acceptChannelPts(id, pts, count) }
        return true
    }

    private fun acceptCommonPts(pts: Int, count: Int): Boolean {
        val st = updatesState
        if (st == null) {
            scheduleCatchUpCommon()
            return false
        }
        return when {
            pts <= st.pts -> false
            count > 0 && pts == st.pts + count -> {
                updatesState = st.updated(pts = pts)
                true
            }
            else -> {
                scheduleCatchUpCommon()
                false
            }
        }
    }

    private fun acceptChannelPts(channelId: Long, pts: Int, count: Int): Boolean {
        val cur = channels.get(channelId)
        return when {
            cur == null || cur.pts == 0 -> {
                channels.put(channelId, pts)
                true
            }
            pts <= cur.pts -> false
            count > 0 && pts == cur.pts + count -> {
                channels.put(channelId, pts)
                true
            }
            else -> {
                scheduleCatchUpChannel(channelId, cur.pts)
                false
            }
        }
    }

    private fun commonPts(u: Update): Pair<Int, Int>? = when (u) {
        is UpdateNewMessage -> u.pts to u.ptsCount
        is UpdateDeleteMessages -> u.pts to u.ptsCount
        is UpdateReadHistoryInbox -> u.pts to u.ptsCount
        is UpdateReadHistoryOutbox -> u.pts to u.ptsCount
        is UpdateWebPage -> u.pts to u.ptsCount
        is UpdateReadMessagesContents -> u.pts to u.ptsCount
        is UpdateEditMessage -> u.pts to u.ptsCount
        is UpdateFolderPeers -> u.pts to u.ptsCount
        is UpdatePinnedMessages -> u.pts to u.ptsCount
        else -> null
    }

    private fun channelPts(u: Update): Triple<Long, Int, Int>? = when (u) {
        is UpdateNewChannelMessage -> {
            val id = (u.message.asText()?.peerId as? PeerChannel)?.channelId ?: return null
            Triple(id, u.pts, u.ptsCount)
        }
        is UpdateEditChannelMessage -> {
            val id = (u.message.asText()?.peerId as? PeerChannel)?.channelId ?: return null
            Triple(id, u.pts, u.ptsCount)
        }
        is UpdateDeleteChannelMessages -> Triple(u.channelId, u.pts, u.ptsCount)
        is UpdateChannelWebPage -> Triple(u.channelId, u.pts, u.ptsCount)
        is UpdatePinnedChannelMessages -> Triple(u.channelId, u.pts, u.ptsCount)
        else -> null
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

    private fun scheduleCatchUpChannel(channelId: Long, ptsHint: Int = 0) {
        if (catchingChannel == channelId) return
        if (storage.getAccessHash(PeerChannel(channelId).botApiChatId()) == 0L) return
        catchingChannel = channelId
        apiScope.launch {
            try {
                catchUpChannel(channelId, ptsHint)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                if (!isDisconnect(e)) logCaught("catch-up-channel", e)
            } finally {
                if (catchingChannel == channelId) catchingChannel = 0
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

    private suspend fun catchUpChannel(channelId: Long, ptsHint: Int) {
        val cur = channels.get(channelId)
        val hash = storage.getAccessHash(PeerChannel(channelId).botApiChatId())
        if (hash == 0L) return
        val pts = if (ptsHint != 0) ptsHint else cur?.pts ?: return
        val r = invoke(
            UpdatesGetChannelDifference(
                channel = InputChannelCtor(channelId, hash),
                filter = ChannelMessagesFilterEmpty,
                pts = pts,
                limit = 100,
            ),
        )
        when (val diff = r.result) {
            null -> return
            is UpdatesChannelDifferenceEmpty -> channels.put(channelId, diff.pts)
            is UpdatesChannelDifferenceCtor -> {
                rememberUsers(diff.users)
                rememberChats(diff.chats)
                channels.put(channelId, diff.pts)
                diff.newMessages.mapNotNull { it.asText() }.forEach { emitUpdate(UpdateNewChannelMessage(message = it, pts = diff.pts, ptsCount = 0)) }
                diff.otherUpdates.forEach { if (it is Update) emitUpdate(it) }
            }
            is UpdatesChannelDifferenceTooLong -> channels.remove(channelId)
        }
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

    internal fun rememberChats(list: List<iris.kmtproto.tl.gen.Chat>) {
        for (obj in list) {
            when (obj) {
                is Channel -> {
                    val hash = obj.accessHash
                    if (hash == 0L) continue
                    storage.putAccessHash(PeerChannel(obj.id).botApiChatId(), hash)
                }
                is ChannelForbidden -> storage.putAccessHash(PeerChannel(obj.id).botApiChatId(), obj.accessHash)
                else -> Unit
            }
        }
    }

    internal fun rememberUsers(list: List<User>) {
        for (obj in list) rememberUser(obj)
    }

    internal fun rememberUpdates(updates: Updates) {
        when (updates) {
            is UpdatesCtor -> {
                rememberUsers(updates.users)
                rememberChats(updates.chats)
            }
            is UpdatesCombined -> {
                rememberUsers(updates.users)
                rememberChats(updates.chats)
            }
            else -> Unit
        }
    }

    internal fun rememberUser(user: User) {
        val hash = user.accessHash
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
        val extra = fileDcMutex.withLock { fileDcs.values.toList().also { fileDcs.clear() } }
        extra.forEach { it.shutdown() }
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

internal fun migrateDc(message: String): Int {
    val match = Regex("(USER|PHONE|NETWORK|STATS)_MIGRATE_(\\d+)").find(message) ?: return 0
    return match.groupValues[2].toInt()
}

internal fun fileMigrateDc(message: String): Int {
    if (!message.startsWith("FILE_MIGRATE_")) return 0
    return message.removePrefix("FILE_MIGRATE_").toIntOrNull() ?: 0
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
