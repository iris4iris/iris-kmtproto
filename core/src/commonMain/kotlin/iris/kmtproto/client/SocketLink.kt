package iris.kmtproto.client

import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.isDisconnect
import iris.kmtproto.logCaught
import iris.kmtproto.mtproto.EncryptedConnection
import iris.kmtproto.mtproto.MsgIdFactory
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.TlObject
import iris.kmtproto.transport.Datacenter
import iris.kmtproto.transport.MtprotoTransport
import iris.kmtproto.transport.Proxy
import iris.kmtproto.transport.connectObfuscated
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal const val MEDIA_IDLE_MS = 30_000L

internal class SocketLink(val name: String) {
    val threads = MuxThreads(name)
    val bindMutex = Mutex()
    var transport: MtprotoTransport? = null
        private set
    var connection: EncryptedConnection? = null
        private set
    var layerReady = false
    @Volatile var stop = false

    val isBound: Boolean get() = connection != null

    suspend fun attach(
        transport: MtprotoTransport,
        key: AuthKey,
        salt: Long,
        timeOffset: Int,
    ) {
        bindMutex.withLock {
            transport.setReadTimeoutMs(0)
            this.transport = transport
            connection = EncryptedConnection(
                transport = transport,
                authKey = key,
                salt = salt,
                sessionId = PlatformCrypto.randomBytes(8).readLongLe(),
                msgIds = MsgIdFactory(timeOffset),
                writeContext = threads.write,
            )
            layerReady = false
        }
    }

    suspend fun open(dc: Datacenter, proxy: Proxy?, key: AuthKey, salt: Long, timeOffset: Int) {
        val t = connectObfuscated(dc, proxy)
        attach(t, key, salt, timeOffset)
    }

    suspend fun rebind(dc: Datacenter, proxy: Proxy?, key: AuthKey, salt: Long) {
        bindMutex.withLock {
            val oldConn = connection
            val oldT = transport
            connection = null
            transport = null
            oldConn?.failPending(CancellationException("reconnect"))
            runCatching { oldT?.close() }.onFailure { logCaught("$name-rebind-close", it) }
            val t = connectObfuscated(dc, proxy)
            t.setReadTimeoutMs(0)
            transport = t
            connection = EncryptedConnection(
                transport = t,
                authKey = key,
                salt = salt,
                sessionId = PlatformCrypto.randomBytes(8).readLongLe(),
                msgIds = MsgIdFactory(0),
                writeContext = threads.write,
            )
            layerReady = false
        }
    }

    suspend fun sendRpc(obj: TlObject): TlObject {
        val conn = connection ?: error("call connect() first")
        return conn.sendRpc(obj)
    }

    suspend fun readerLoop(
        alive: () -> Boolean,
        onEvent: (TlObject) -> Unit,
        reconnect: suspend () -> Unit,
    ) {
        var backoff = 500L
        while (alive() && !stop) {
            val conn = connection
            if (conn == null) {
                if (stop || !alive()) break
                runCatching { reconnect() }.onFailure { logRebind(it) }
                if (connection == null) delay(backoff)
                backoff = (backoff * 2).coerceAtMost(15_000L)
                continue
            }
            try {
                backoff = 500L
                conn.runReader(onEvent)
                if (stop || !alive()) break
                println("kmtproto [$name] disconnected, reconnecting")
                runCatching { reconnect() }.onFailure { logRebind(it) }
                if (connection == null) delay(backoff)
                backoff = (backoff * 2).coerceAtMost(15_000L)
            } catch (e: CancellationException) {
                if (stop || !alive()) break
                logCaught("$name-cancel", e)
                runCatching { reconnect() }.onFailure { logRebind(it) }
                if (connection == null) delay(backoff)
                backoff = (backoff * 2).coerceAtMost(15_000L)
            } catch (e: Throwable) {
                if (stop || !alive()) break
                if (!isDisconnect(e)) logCaught(name, e)
                else println("kmtproto [$name] disconnected, reconnecting")
                runCatching { reconnect() }.onFailure { logRebind(it) }
                if (connection == null) delay(backoff)
                backoff = (backoff * 2).coerceAtMost(15_000L)
            }
        }
    }

    suspend fun shutdown() {
        stop = true
        val conn = connection
        val t = transport
        connection = null
        transport = null
        conn?.failPending(CancellationException("closed"))
        runCatching { t?.close() }.onFailure { logCaught("$name-close", it) }
        threads.close()
    }

    private fun logRebind(e: Throwable) {
        if (!isDisconnect(e)) logCaught("$name-rebind", e)
        else println("kmtproto [$name-rebind] ${e::class.simpleName}: ${e.message}")
    }
}
