package iris.kmtproto.tl

interface TlObject {
    val constructorId: Int
    fun serialize(w: TlWriter)
}

interface TlMethod<T : TlObject> : TlObject

class TlUnknown(
    override val constructorId: Int,
    val rest: ByteArray,
) : TlObject {
    override fun serialize(w: TlWriter) = w.writeRaw(rest)
}

internal fun missingConstructor(id: Int, r: TlReader): Nothing {
    val hex = id.toUInt().toString(16)
    val name = TlNames.of(id)
    val label = if (name != null) "$name#$hex" else "#$hex"
    error("unparsed constructor $label at offset ${r.position}, ${r.remaining} bytes left")
}

object TlRegistry {
    private val readers = HashMap<Int, (TlReader) -> TlObject>()

    fun register(id: Int, reader: (TlReader) -> TlObject) {
        readers[id] = reader
    }

    fun read(id: Int, r: TlReader): TlObject {
        val fn = readers[id] ?: missingConstructor(id, r)
        return fn(r)
    }

    fun readOrThrow(id: Int, r: TlReader): TlObject {
        val fn = readers[id] ?: missingConstructor(id, r)
        return fn(r)
    }

    init {
        register(TlIds.RES_PQ) { ResPq.read(it) }
        register(TlIds.SERVER_DH_PARAMS_OK) { ServerDhParamsOk.read(it) }
        register(TlIds.SERVER_DH_PARAMS_FAIL) { ServerDhParamsFail.read(it) }
        register(TlIds.SERVER_DH_INNER_DATA) { ServerDhInnerData.read(it) }
        register(TlIds.DH_GEN_OK) { DhGenOk.read(it) }
        register(TlIds.DH_GEN_RETRY) { DhGenRetry.read(it) }
        register(TlIds.DH_GEN_FAIL) { DhGenFail.read(it) }
        register(TlIds.PING) { Ping.read(it) }
        register(TlIds.PONG) { Pong.read(it) }
        register(TlIds.RPC_RESULT) { RpcResult.read(it) }
        register(TlIds.RPC_ERROR) { RpcError.read(it) }
        register(TlIds.MSGS_ACK) { MsgsAck.read(it) }
        register(TlIds.NEW_SESSION_CREATED) { NewSessionCreated.read(it) }
        register(TlIds.BAD_SERVER_SALT) { BadServerSalt.read(it) }
        register(TlIds.BAD_MSG_NOTIFICATION) { BadMsgNotification.read(it) }
        register(TlIds.GZIP_PACKED) { GzipPacked.read(it) }
        register(TlIds.MSG_CONTAINER) { MsgContainer.read(it) }
        iris.kmtproto.tl.gen.registerGenerated()
    }
}

data class ReqPqMulti(val nonce: ByteArray) : TlMethod<ResPq> {
    override val constructorId: Int = TlIds.REQ_PQ_MULTI
    override fun serialize(w: TlWriter) = w.writeInt128(nonce)
}

data class ResPq(
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val pq: ByteArray,
    val fingerprints: LongArray,
) : TlObject {
    override val constructorId: Int = TlIds.RES_PQ
    override fun serialize(w: TlWriter) {
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeTlBytes(pq)
        w.writeVectorLong(fingerprints)
    }

    companion object {
        fun read(r: TlReader) = ResPq(
            nonce = r.readInt128(),
            serverNonce = r.readInt128(),
            pq = r.readTlBytes(),
            fingerprints = r.readVectorLong(),
        )
    }
}

data class PqInnerDataDc(
    val pq: ByteArray,
    val p: ByteArray,
    val q: ByteArray,
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val newNonce: ByteArray,
    val dc: Int,
) : TlObject {
    override val constructorId: Int = TlIds.PQ_INNER_DATA_DC
    override fun serialize(w: TlWriter) {
        w.writeTlBytes(pq)
        w.writeTlBytes(p)
        w.writeTlBytes(q)
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeInt256(newNonce)
        w.writeInt(dc)
    }
}

data class PqInnerData(
    val pq: ByteArray,
    val p: ByteArray,
    val q: ByteArray,
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val newNonce: ByteArray,
) : TlObject {
    override val constructorId: Int = TlIds.PQ_INNER_DATA
    override fun serialize(w: TlWriter) {
        w.writeTlBytes(pq)
        w.writeTlBytes(p)
        w.writeTlBytes(q)
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeInt256(newNonce)
    }
}

data class ReqDhParams(
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val p: ByteArray,
    val q: ByteArray,
    val publicKeyFingerprint: Long,
    val encryptedData: ByteArray,
) : TlMethod<TlObject> {
    override val constructorId: Int = TlIds.REQ_DH_PARAMS
    override fun serialize(w: TlWriter) {
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeTlBytes(p)
        w.writeTlBytes(q)
        w.writeLong(publicKeyFingerprint)
        w.writeTlBytes(encryptedData)
    }
}

data class ServerDhParamsOk(
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val encryptedAnswer: ByteArray,
) : TlObject {
    override val constructorId: Int = TlIds.SERVER_DH_PARAMS_OK
    override fun serialize(w: TlWriter) {
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeTlBytes(encryptedAnswer)
    }

    companion object {
        fun read(r: TlReader) = ServerDhParamsOk(
            r.readInt128(), r.readInt128(), r.readTlBytes(),
        )
    }
}

data class ServerDhParamsFail(
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val newNonceHash: ByteArray,
) : TlObject {
    override val constructorId: Int = TlIds.SERVER_DH_PARAMS_FAIL
    override fun serialize(w: TlWriter) {
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeInt128(newNonceHash)
    }

    companion object {
        fun read(r: TlReader) = ServerDhParamsFail(
            r.readInt128(), r.readInt128(), r.readInt128(),
        )
    }
}

data class ServerDhInnerData(
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val g: Int,
    val dhPrime: ByteArray,
    val gA: ByteArray,
    val serverTime: Int,
) : TlObject {
    override val constructorId: Int = TlIds.SERVER_DH_INNER_DATA
    override fun serialize(w: TlWriter) {
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeInt(g)
        w.writeTlBytes(dhPrime)
        w.writeTlBytes(gA)
        w.writeInt(serverTime)
    }

    companion object {
        fun read(r: TlReader) = ServerDhInnerData(
            r.readInt128(), r.readInt128(), r.readInt(),
            r.readTlBytes(), r.readTlBytes(), r.readInt(),
        )
    }
}

data class ClientDhInnerData(
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val retryId: Long,
    val gB: ByteArray,
) : TlObject {
    override val constructorId: Int = TlIds.CLIENT_DH_INNER_DATA
    override fun serialize(w: TlWriter) {
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeLong(retryId)
        w.writeTlBytes(gB)
    }
}

data class SetClientDhParams(
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val encryptedData: ByteArray,
) : TlMethod<TlObject> {
    override val constructorId: Int = TlIds.SET_CLIENT_DH_PARAMS
    override fun serialize(w: TlWriter) {
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeTlBytes(encryptedData)
    }
}

data class DhGenOk(
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val newNonceHash1: ByteArray,
) : TlObject {
    override val constructorId: Int = TlIds.DH_GEN_OK
    override fun serialize(w: TlWriter) {
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeInt128(newNonceHash1)
    }

    companion object {
        fun read(r: TlReader) = DhGenOk(r.readInt128(), r.readInt128(), r.readInt128())
    }
}

data class DhGenRetry(
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val newNonceHash2: ByteArray,
) : TlObject {
    override val constructorId: Int = TlIds.DH_GEN_RETRY
    override fun serialize(w: TlWriter) {
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeInt128(newNonceHash2)
    }

    companion object {
        fun read(r: TlReader) = DhGenRetry(r.readInt128(), r.readInt128(), r.readInt128())
    }
}

data class DhGenFail(
    val nonce: ByteArray,
    val serverNonce: ByteArray,
    val newNonceHash3: ByteArray,
) : TlObject {
    override val constructorId: Int = TlIds.DH_GEN_FAIL
    override fun serialize(w: TlWriter) {
        w.writeInt128(nonce)
        w.writeInt128(serverNonce)
        w.writeInt128(newNonceHash3)
    }

    companion object {
        fun read(r: TlReader) = DhGenFail(r.readInt128(), r.readInt128(), r.readInt128())
    }
}

data class Ping(val pingId: Long) : TlMethod<Pong> {
    override val constructorId: Int = TlIds.PING
    override fun serialize(w: TlWriter) = w.writeLong(pingId)

    companion object {
        fun read(r: TlReader) = Ping(r.readLong())
    }
}

data class Pong(val msgId: Long, val pingId: Long) : TlObject {
    override val constructorId: Int = TlIds.PONG
    override fun serialize(w: TlWriter) {
        w.writeLong(msgId)
        w.writeLong(pingId)
    }

    companion object {
        fun read(r: TlReader) = Pong(r.readLong(), r.readLong())
    }
}

data class RpcResult(val reqMsgId: Long, val result: TlObject) : TlObject {
    override val constructorId: Int = TlIds.RPC_RESULT
    override fun serialize(w: TlWriter) {
        w.writeLong(reqMsgId)
        w.writeObject(result)
    }

    companion object {
        fun read(r: TlReader) = RpcResult(r.readLong(), r.readObject())
    }
}

data class RpcError(val errorCode: Int, val errorMessage: String) : TlObject {
    override val constructorId: Int = TlIds.RPC_ERROR
    override fun serialize(w: TlWriter) {
        w.writeInt(errorCode)
        w.writeString(errorMessage)
    }

    companion object {
        fun read(r: TlReader) = RpcError(r.readInt(), r.readString())
    }
}

data class GzipPacked(val packedData: ByteArray) : TlObject {
    override val constructorId: Int = TlIds.GZIP_PACKED
    override fun serialize(w: TlWriter) = w.writeTlBytes(packedData)

    companion object {
        fun read(r: TlReader) = GzipPacked(r.readTlBytes())
    }
}

data class MsgsAck(val msgIds: LongArray) : TlObject {
    override val constructorId: Int = TlIds.MSGS_ACK
    override fun serialize(w: TlWriter) = w.writeVectorLong(msgIds)

    companion object {
        fun read(r: TlReader) = MsgsAck(r.readVectorLong())
    }
}

data class NewSessionCreated(
    val firstMsgId: Long,
    val uniqueId: Long,
    val serverSalt: Long,
) : TlObject {
    override val constructorId: Int = TlIds.NEW_SESSION_CREATED
    override fun serialize(w: TlWriter) {
        w.writeLong(firstMsgId)
        w.writeLong(uniqueId)
        w.writeLong(serverSalt)
    }

    companion object {
        fun read(r: TlReader) = NewSessionCreated(r.readLong(), r.readLong(), r.readLong())
    }
}

data class BadServerSalt(
    val badMsgId: Long,
    val badMsgSeqno: Int,
    val errorCode: Int,
    val newServerSalt: Long,
) : TlObject {
    override val constructorId: Int = TlIds.BAD_SERVER_SALT
    override fun serialize(w: TlWriter) {
        w.writeLong(badMsgId)
        w.writeInt(badMsgSeqno)
        w.writeInt(errorCode)
        w.writeLong(newServerSalt)
    }

    companion object {
        fun read(r: TlReader) = BadServerSalt(r.readLong(), r.readInt(), r.readInt(), r.readLong())
    }
}

data class BadMsgNotification(
    val badMsgId: Long,
    val badMsgSeqno: Int,
    val errorCode: Int,
) : TlObject {
    override val constructorId: Int = TlIds.BAD_MSG_NOTIFICATION
    override fun serialize(w: TlWriter) {
        w.writeLong(badMsgId)
        w.writeInt(badMsgSeqno)
        w.writeInt(errorCode)
    }

    companion object {
        fun read(r: TlReader) = BadMsgNotification(r.readLong(), r.readInt(), r.readInt())
    }
}

data class MtMessage(
    val msgId: Long,
    val seqNo: Int,
    val body: TlObject,
) : TlObject {
    override val constructorId: Int = 0
    override fun serialize(w: TlWriter) {
        w.writeLong(msgId)
        w.writeInt(seqNo)
        val inner = TlWriter().also { it.writeObject(body) }.toByteArray()
        w.writeInt(inner.size)
        w.writeRaw(inner)
    }
}

data class MsgContainer(val messages: List<MtMessage>) : TlObject {
    override val constructorId: Int = TlIds.MSG_CONTAINER
    override fun serialize(w: TlWriter) {
        w.writeInt(messages.size)
        for (m in messages) m.serialize(w)
    }

    companion object {
        fun read(r: TlReader): MsgContainer {
            val n = r.readInt()
            val list = ArrayList<MtMessage>(n)
            repeat(n) {
                val msgId = r.readLong()
                val seq = r.readInt()
                val len = r.readInt()
                val bodyBytes = r.readRaw(len)
                val body = TlReader(bodyBytes).readObject()
                list.add(MtMessage(msgId, seq, body))
            }
            return MsgContainer(list)
        }
    }
}

object BoolTrue : TlObject {
    override val constructorId: Int = TlIds.BOOL_TRUE
    override fun serialize(w: TlWriter) = Unit
}

object BoolFalse : TlObject {
    override val constructorId: Int = TlIds.BOOL_FALSE
    override fun serialize(w: TlWriter) = Unit
}

fun TlObject.toBytes(): ByteArray {
    val w = TlWriter()
    w.writeObject(this)
    return w.toByteArray()
}
