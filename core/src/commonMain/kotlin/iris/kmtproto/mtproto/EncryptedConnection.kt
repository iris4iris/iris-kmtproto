package iris.kmtproto.mtproto

import iris.kmtproto.logCaught
import iris.kmtproto.concat
import iris.kmtproto.crypto.AesIge
import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.MsgKeys
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.readIntLe
import iris.kmtproto.readLongLe
import iris.kmtproto.tl.BadMsgNotification
import iris.kmtproto.tl.BadServerSalt
import iris.kmtproto.tl.GzipPacked
import iris.kmtproto.tl.MsgContainer
import iris.kmtproto.tl.MsgsAck
import iris.kmtproto.tl.MtMessage
import iris.kmtproto.tl.NewSessionCreated
import iris.kmtproto.tl.Pong
import iris.kmtproto.tl.RpcResult
import iris.kmtproto.tl.TlObject
import iris.kmtproto.tl.TlReader
import iris.kmtproto.tl.gen.Updates
import iris.kmtproto.tl.toBytes
import iris.kmtproto.toLeBytes
import iris.kmtproto.transport.MtprotoTransport
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeout

class RpcException(val code: Int, override val message: String) : RuntimeException("RPC $code: $message") {
    val floodWaitSeconds: Int?
        get() = if (message.startsWith("FLOOD_WAIT_")) message.removePrefix("FLOOD_WAIT_").toIntOrNull() else null
}

internal const val ACK_BATCH = 1000
internal const val ACK_FLUSH_MS = 1_000L

internal class EncryptedConnection(
    private val transport: MtprotoTransport,
    val authKey: AuthKey,
    var salt: Long,
    val sessionId: Long,
    val msgIds: MsgIdFactory,
) {
    private var seq = 0
    private val sendMutex = Mutex()
    private val pendingMutex = Mutex()
    private val pending = HashMap<Long, CompletableDeferred<TlObject>>()
    /** Writer-only: reader never waits for TCP flush. */
    private val outgoing = Channel<ByteArray>(Channel.UNLIMITED)
    private val ackBuf = ArrayList<Long>(ACK_BATCH)

    private fun nextSeq(contentRelated: Boolean): Int {
        val value = seq * 2 + if (contentRelated) 1 else 0
        if (contentRelated) seq++
        return value
    }

    suspend fun sendRpc(obj: TlObject, timeoutMs: Long = 20_000): TlObject {
        val deferred = CompletableDeferred<TlObject>()
        val msgId = enqueue(obj, contentRelated = true, deferred)
        return try {
            withTimeout(timeoutMs) { deferred.await() }
        } catch (e: TimeoutCancellationException) {
            pendingMutex.withLock { pending.remove(msgId) }
            throw e
        }
    }

    /**
     * Read loop + write loop. Socket read and write run concurrently;
     * [sendMutex] only serializes msg_id / seq / AES-IGE, not TCP.
     */
    suspend fun runReader(onEvent: (TlObject) -> Unit) {
        coroutineScope {
            val writer = launch { drainWrites() }
            val acks = launch {
                while (true) {
                    delay(ACK_FLUSH_MS)
                    sendMutex.withLock { drainAcksLocked() }
                }
            }
            try {
                readLoop(onEvent)
            } finally {
                acks.cancel()
                sendMutex.withLock { drainAcksLocked() }
                outgoing.close()
                writer.join()
            }
        }
    }

    private suspend fun drainWrites() {
        for (packet in outgoing) {
            try {
                transport.send(packet)
            } catch (e: CancellationException) {
                failPending(e)
                throw e
            } catch (e: Exception) {
                logCaught("writer", e)
                failPending(e)
                throw e
            }
        }
    }

    private suspend fun enqueue(
        obj: TlObject,
        contentRelated: Boolean,
        deferred: CompletableDeferred<TlObject>? = null,
    ): Long = sendMutex.withLock {
        drainAcksLocked()
        val (id, packet) = prepare(obj, contentRelated)
        if (deferred != null) pendingMutex.withLock { pending[id] = deferred }
        val sent = outgoing.trySend(packet)
        if (sent.isFailure) {
            if (deferred != null) pendingMutex.withLock { pending.remove(id) }
            throw sent.exceptionOrNull() ?: IllegalStateException("outgoing closed")
        }
        id
    }

    /** Caller holds [sendMutex]. */
    private fun drainAcksLocked() {
        if (ackBuf.isEmpty()) return
        val batch = ackBuf.distinct().toLongArray()
        ackBuf.clear()
        val (_, packet) = prepare(MsgsAck(batch), contentRelated = false)
        val sent = outgoing.trySend(packet)
        if (sent.isFailure) {
            logCaught(
                "ack-enqueue",
                sent.exceptionOrNull() ?: IllegalStateException("outgoing closed"),
            )
        }
    }

    private suspend fun readLoop(onEvent: (TlObject) -> Unit) {
        while (true) {
            val (acks, events) = try {
                receiveUnwrapped()
            } catch (e: CancellationException) {
                failPending(e)
                throw e
            } catch (e: Exception) {
                failPending(e)
                throw e
            }
            try {
                if (acks.isNotEmpty()) {
                    sendMutex.withLock {
                        ackBuf.addAll(acks)
                        if (ackBuf.size >= ACK_BATCH) drainAcksLocked()
                    }
                }
                for (e in events) {
                    try {
                        route(e, onEvent)
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        logCaught("route", e)
                    }
                }
            } catch (e: CancellationException) {
                failPending(e)
                throw e
            } catch (e: Exception) {
                logCaught("reader-ack", e)
            }
        }
    }

    fun failPending(cause: Throwable) {
        val waiters = pending.values.toList()
        pending.clear()
        waiters.forEach { it.completeExceptionally(cause) }
    }

    private suspend fun route(e: TlObject, onEvent: (TlObject) -> Unit) {
        when (e) {
            is NewSessionCreated -> salt = e.serverSalt
            is RpcResult -> {
                complete(e.reqMsgId, e)
                if (e.result is Updates) onEvent(e.result)
            }
            is Pong -> complete(e.msgId, e)
            is BadServerSalt -> {
                salt = e.newServerSalt
                complete(e.badMsgId, e)
            }
            is BadMsgNotification -> complete(e.badMsgId, e)
            else -> onEvent(e)
        }
    }

    private suspend fun complete(id: Long, obj: TlObject) {
        val d = pendingMutex.withLock { pending.remove(id) }
        d?.complete(obj)
    }

    private fun prepare(obj: TlObject, contentRelated: Boolean): Pair<Long, ByteArray> {
        val body = obj.toBytes()
        val msgId = msgIds.next()
        val seqNo = nextSeq(contentRelated)
        val inner = buildInner(msgId, seqNo, body)
        val msgKey = MsgKeys.msgKey(authKey.key, inner, x = 0)
        val (aesKey, aesIv) = MsgKeys.deriveAes(authKey.key, msgKey, x = 0)
        val encrypted = AesIge.encrypt(aesKey, aesIv, inner)
        return msgId to concat(authKey.keyId.toLeBytes(), msgKey, encrypted)
    }

    private fun buildInner(msgId: Long, seqNo: Int, body: ByteArray): ByteArray {
        val headerAndBody = concat(
            salt.toLeBytes(),
            sessionId.toLeBytes(),
            msgId.toLeBytes(),
            seqNo.toLeBytes(),
            body.size.toLeBytes(),
            body,
        )
        var pad = 12
        while ((headerAndBody.size + pad) % 16 != 0) pad++
        return concat(headerAndBody, PlatformCrypto.randomBytes(pad))
    }

    private suspend fun receiveUnwrapped(): Pair<List<Long>, List<TlObject>> {
        val frame = transport.receive()
        require(frame.size >= 24) { "encrypted frame too short" }
        val keyId = frame.readLongLe(0)
        check(keyId == authKey.keyId) { "auth_key_id mismatch" }
        val msgKey = frame.copyOfRange(8, 24)
        val cipher = frame.copyOfRange(24, frame.size)
        val (aesKey, aesIv) = MsgKeys.deriveAes(authKey.key, msgKey, x = 8)
        val inner = AesIge.decrypt(aesKey, aesIv, cipher)
        val computed = MsgKeys.msgKey(authKey.key, inner, x = 8)
        check(computed.contentEquals(msgKey)) { "msg_key mismatch (server)" }

        val session = inner.readLongLe(8)
        check(session == sessionId) { "session_id mismatch" }
        val msgId = inner.readLongLe(16)
        val seqNo = inner.readIntLe(24)
        val length = inner.readIntLe(28)
        val body = inner.copyOfRange(32, 32 + length)
        val acks = ArrayList<Long>()
        if (seqNo % 2 == 1) acks += msgId
        val obj = try {
            TlReader(body).readObject()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logCaught("tl-read", e)
            return acks to emptyList()
        }
        try {
            collectAcks(obj, msgId, seqNo, acks)
            return acks to flatten(obj)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logCaught("tl-unwrap", e)
            return acks to emptyList()
        }
    }

    private fun collectAcks(obj: TlObject, msgId: Long, seqNo: Int, into: MutableList<Long>) {
        when (obj) {
            is MsgContainer -> obj.messages.forEach { collectAcks(it.body, it.msgId, it.seqNo, into) }
            is GzipPacked -> collectAcks(
                TlReader(PlatformCrypto.gunzip(obj.packedData)).readObject(),
                msgId,
                seqNo,
                into,
            )
            is MtMessage -> collectAcks(obj.body, obj.msgId, obj.seqNo, into)
            else -> if (seqNo % 2 == 1) into += msgId
        }
    }

    private fun flatten(obj: TlObject): List<TlObject> {
        return when (obj) {
            is MsgContainer -> obj.messages.flatMap { flatten(it.body) }
            is GzipPacked -> flatten(TlReader(PlatformCrypto.gunzip(obj.packedData)).readObject())
            is MtMessage -> flatten(obj.body)
            is RpcResult -> {
                val inner = flatten(obj.result).singleOrNull() ?: obj.result
                listOf(obj.copy(result = inner))
            }
            else -> listOf(obj)
        }
    }
}
