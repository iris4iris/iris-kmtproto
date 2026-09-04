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
import iris.kmtproto.tl.TlIds
import iris.kmtproto.tl.TlObject
import iris.kmtproto.tl.TlReader
import iris.kmtproto.tl.TlWriter
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
/** Protocol: container body ≤ 2^15−4, ≤ 1020 inner messages. */
internal const val MAX_CONTAINER_MESSAGES = 1020
internal const val MAX_CONTAINER_BYTES = 32_764

private class PreparedMsg(
    val msgId: Long,
    val seqNo: Int,
    val body: ByteArray,
) {
    val innerSize: Int get() = 16 + body.size
}

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
    /** Writer packs these into msg_container; not yet encrypted. */
    private val outgoing = Channel<PreparedMsg>(Channel.UNLIMITED)
    private val ackBuf = ArrayList<Long>(ACK_BATCH)
    /** container msg_id → inner RPC msg_ids (for bad_server_salt). */
    private val bundles = HashMap<Long, LongArray>()

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
        var leftover: PreparedMsg? = null
        try {
            while (true) {
                val first = leftover ?: (outgoing.receiveCatching().getOrNull() ?: break)
                leftover = null
                val batch = ArrayList<PreparedMsg>(8)
                batch += first
                var size = first.innerSize
                while (batch.size < MAX_CONTAINER_MESSAGES) {
                    val next = outgoing.tryReceive().getOrNull() ?: break
                    if (8 + size + next.innerSize > MAX_CONTAINER_BYTES) {
                        leftover = next
                        break
                    }
                    batch += next
                    size += next.innerSize
                }
                transport.send(seal(batch))
            }
        } catch (e: CancellationException) {
            failPending(e)
            throw e
        } catch (e: Exception) {
            logCaught("writer", e)
            failPending(e)
            throw e
        }
    }

    private suspend fun seal(batch: List<PreparedMsg>): ByteArray {
        if (batch.size == 1) {
            val m = batch[0]
            return encryptPacket(m.msgId, m.seqNo, m.body)
        }
        return sendMutex.withLock {
            val cid = msgIds.next()
            val cseq = nextSeq(contentRelated = false)
            bundles[cid] = LongArray(batch.size) { batch[it].msgId }
            encryptPacket(cid, cseq, serializeContainer(batch))
        }
    }

    private suspend fun enqueue(
        obj: TlObject,
        contentRelated: Boolean,
        deferred: CompletableDeferred<TlObject>? = null,
    ): Long = sendMutex.withLock {
        drainAcksLocked()
        val msg = assign(obj, contentRelated)
        if (deferred != null) pendingMutex.withLock { pending[msg.msgId] = deferred }
        val sent = outgoing.trySend(msg)
        if (sent.isFailure) {
            if (deferred != null) pendingMutex.withLock { pending.remove(msg.msgId) }
            throw sent.exceptionOrNull() ?: IllegalStateException("outgoing closed")
        }
        msg.msgId
    }

    /** Caller holds [sendMutex]. */
    private fun drainAcksLocked() {
        if (ackBuf.isEmpty()) return
        val batch = ackBuf.distinct().toLongArray()
        ackBuf.clear()
        val sent = outgoing.trySend(assign(MsgsAck(batch), contentRelated = false))
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
        bundles.clear()
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
                bundles.remove(e.badMsgId)?.forEach { complete(it, e) }
            }
            is BadMsgNotification -> {
                complete(e.badMsgId, e)
                bundles.remove(e.badMsgId)?.forEach { complete(it, e) }
            }
            else -> onEvent(e)
        }
    }

    private suspend fun complete(id: Long, obj: TlObject) {
        val d = pendingMutex.withLock { pending.remove(id) }
        d?.complete(obj)
    }

    private fun assign(obj: TlObject, contentRelated: Boolean): PreparedMsg {
        val body = obj.toBytes()
        return PreparedMsg(msgIds.next(), nextSeq(contentRelated), body)
    }

    private fun encryptPacket(msgId: Long, seqNo: Int, body: ByteArray): ByteArray {
        val inner = buildInner(msgId, seqNo, body)
        val msgKey = MsgKeys.msgKey(authKey.key, inner, x = 0)
        val (aesKey, aesIv) = MsgKeys.deriveAes(authKey.key, msgKey, x = 0)
        val encrypted = AesIge.encrypt(aesKey, aesIv, inner)
        return concat(authKey.keyId.toLeBytes(), msgKey, encrypted)
    }

    private fun serializeContainer(msgs: List<PreparedMsg>): ByteArray {
        val w = TlWriter()
        w.writeInt(TlIds.MSG_CONTAINER)
        w.writeInt(msgs.size)
        for (m in msgs) {
            w.writeLong(m.msgId)
            w.writeInt(m.seqNo)
            w.writeInt(m.body.size)
            w.writeRaw(m.body)
        }
        return w.toByteArray()
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
        val (aesKey, aesIv) = MsgKeys.deriveAes(authKey.key, msgKey, x = 8)
        val inner = AesIge.decrypt(aesKey, aesIv, frame, 24, frame.size)
        val computed = MsgKeys.msgKey(authKey.key, inner, x = 8)
        check(computed.contentEquals(msgKey)) { "msg_key mismatch (server)" }

        val session = inner.readLongLe(8)
        check(session == sessionId) { "session_id mismatch" }
        val msgId = inner.readLongLe(16)
        val seqNo = inner.readIntLe(24)
        val length = inner.readIntLe(28)
        require(length >= 0 && 32 + length <= inner.size) { "bad mtproto length=$length inner=${inner.size}" }
        val acks = ArrayList<Long>()
        if (seqNo % 2 == 1) acks += msgId
        val obj = try {
            TlReader(inner, pos = 32, end = 32 + length).readObject()
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
