package iris.kmtproto.tl

class TlWriter {
    private val buf = ArrayList<Byte>(256)

    fun writeByte(value: Int) {
        buf.add(value.toByte())
    }

    fun writeInt(value: Int) {
        buf.add(value.toByte())
        buf.add((value shr 8).toByte())
        buf.add((value shr 16).toByte())
        buf.add((value shr 24).toByte())
    }

    fun writeLong(value: Long) {
        buf.add(value.toByte())
        buf.add((value shr 8).toByte())
        buf.add((value shr 16).toByte())
        buf.add((value shr 24).toByte())
        buf.add((value shr 32).toByte())
        buf.add((value shr 40).toByte())
        buf.add((value shr 48).toByte())
        buf.add((value shr 56).toByte())
    }

    fun writeDouble(value: Double) = writeLong(value.toRawBits())

    fun writeRaw(bytes: ByteArray) {
        for (b in bytes) buf.add(b)
    }

    fun writeInt128(bytes: ByteArray) {
        require(bytes.size == 16) { "int128 must be 16 bytes, got ${bytes.size}" }
        writeRaw(bytes)
    }

    fun writeInt256(bytes: ByteArray) {
        require(bytes.size == 32) { "int256 must be 32 bytes, got ${bytes.size}" }
        writeRaw(bytes)
    }

    fun writeTlBytes(bytes: ByteArray) {
        if (bytes.size < 254) {
            writeByte(bytes.size)
            writeRaw(bytes)
        } else {
            writeByte(254)
            writeByte(bytes.size)
            writeByte(bytes.size shr 8)
            writeByte(bytes.size shr 16)
            writeRaw(bytes)
        }
        val padding = (4 - (buf.size % 4)) % 4
        repeat(padding) { writeByte(0) }
    }

    fun writeString(value: String) = writeTlBytes(value.encodeToByteArray())

    fun writeVectorLong(values: LongArray) {
        writeInt(VECTOR)
        writeInt(values.size)
        for (v in values) writeLong(v)
    }

    fun writeVectorInt(values: IntArray) {
        writeInt(VECTOR)
        writeInt(values.size)
        for (v in values) writeInt(v)
    }

    fun writeBool(value: Boolean) {
        writeInt(if (value) BOOL_TRUE else BOOL_FALSE)
    }

    fun writeObject(obj: TlObject) {
        writeInt(obj.constructorId)
        obj.serialize(this)
    }

    fun toByteArray(): ByteArray = ByteArray(buf.size) { buf[it] }

    companion object {
        const val VECTOR = 0x1cb5c415
        const val BOOL_TRUE = 0x997275b5.toInt()
        const val BOOL_FALSE = 0xbc799737.toInt()
    }
}

class TlReader(
    private val data: ByteArray,
    private var pos: Int = 0,
    private val end: Int = data.size,
) {
    init {
        require(pos >= 0 && end >= pos && end <= data.size) {
            "TlReader range pos=$pos end=$end size=${data.size}"
        }
    }

    val remaining: Int get() = end - pos
    val position: Int get() = pos

    fun readByte(): Int {
        check(pos < end) { "eof" }
        return data[pos++].toInt() and 0xff
    }

    fun readInt(): Int {
        val p = pos
        check(pos + 4 <= end) { "eof" }
        val data = data
        val v = (data[p].toInt() and 0xff) or
            ((data[p + 1].toInt() and 0xff) shl 8) or
            ((data[p + 2].toInt() and 0xff) shl 16) or
            ((data[p + 3].toInt() and 0xff) shl 24)
        pos = p + 4
        return v
    }

    fun readLong(): Long {
        val p = pos
        check(pos + 8 <= end) { "eof" }
        val data = data
        val v = (data[p].toLong() and 0xff) or
            ((data[p + 1].toLong() and 0xff) shl 8) or
            ((data[p + 2].toLong() and 0xff) shl 16) or
            ((data[p + 3].toLong() and 0xff) shl 24) or
            ((data[p + 4].toLong() and 0xff) shl 32) or
            ((data[p + 5].toLong() and 0xff) shl 40) or
            ((data[p + 6].toLong() and 0xff) shl 48) or
            ((data[p + 7].toLong() and 0xff) shl 56)
        pos = p + 8
        return v
    }

    fun readDouble(): Double = Double.fromBits(readLong())

    fun readRaw(n: Int): ByteArray {
        check(n >= 0 && pos + n <= end) { "eof reading $n bytes at $pos / $end" }
        val out = data.copyOfRange(pos, pos + n)
        pos += n
        return out
    }

    fun readInt128(): ByteArray = readRaw(16)

    fun readInt256(): ByteArray = readRaw(32)

    fun readTlBytes(): ByteArray {
        var len = readByte()
        if (len == 254) {
            len = readByte() or (readByte() shl 8) or (readByte() shl 16)
        }
        val bytes = readRaw(len)
        val consumed = if (len < 254) 1 + len else 4 + len
        val padding = (4 - (consumed % 4)) % 4
        if (padding > 0) readRaw(padding)
        return bytes
    }

    fun readString(): String = readTlBytes().decodeToString()

    fun readVectorLong(): LongArray {
        val id = readInt()
        check(id == TlWriter.VECTOR) { "expected vector constructor, got ${id.toUInt().toString(16)}" }
        val n = readInt()
        return LongArray(n) { readLong() }
    }

    fun readVectorInt(): IntArray {
        val id = readInt()
        check(id == TlWriter.VECTOR) { "expected vector constructor, got ${id.toUInt().toString(16)}" }
        val n = readInt()
        return IntArray(n) { readInt() }
    }

    fun <T> readVector(readOne: () -> T): List<T> {
        val id = readInt()
        check(id == TlWriter.VECTOR) { "expected vector constructor, got ${id.toUInt().toString(16)}" }
        val n = readInt()
        return List(n) { readOne() }
    }

    fun readBool(): Boolean = when (val id = readInt()) {
        TlWriter.BOOL_TRUE -> true
        TlWriter.BOOL_FALSE -> false
        else -> error("expected Bool, got ${id.toUInt().toString(16)}")
    }

    fun readObject(): TlObject {
        val id = readInt()
        return TlRegistry.read(id, this)
    }

    fun readObjectOrThrow(): TlObject {
        val id = readInt()
        return TlRegistry.readOrThrow(id, this)
    }
}

internal fun Int.hasFlag(bit: Int): Boolean = (this and (1 shl bit)) != 0

object TlIds {
    val VECTOR = 0x1cb5c415
    val REQ_PQ_MULTI = 0xbe7e8ef1.toInt()
    val RES_PQ = 0x05162463
    val PQ_INNER_DATA = 0x83c95aec.toInt()
    val PQ_INNER_DATA_DC = 0xa9f55f95.toInt()
    val PQ_INNER_DATA_TEMP_DC = 0x56fddf88
    val REQ_DH_PARAMS = 0xd712e4be.toInt()
    val SERVER_DH_PARAMS_OK = 0xd0e8075c.toInt()
    val SERVER_DH_PARAMS_FAIL = 0x79cb045d
    val SERVER_DH_INNER_DATA = 0xb5890dba.toInt()
    val CLIENT_DH_INNER_DATA = 0x6643b654
    val SET_CLIENT_DH_PARAMS = 0xf5045f1f.toInt()
    val DH_GEN_OK = 0x3bcbf734
    val DH_GEN_RETRY = 0x46dc1fb9
    val DH_GEN_FAIL = 0xa69dae02.toInt()
    val PING = 0x7abe77ec
    val PONG = 0x347773c5
    val RPC_RESULT = 0xf35c6d01.toInt()
    val RPC_ERROR = 0x2144ca19
    val GZIP_PACKED = 0x3072cfa1
    val MSG_CONTAINER = 0x73f1f8dc
    val MSGS_ACK = 0x62d6b459
    val BAD_SERVER_SALT = 0xedab447b.toInt()
    val BAD_MSG_NOTIFICATION = 0xa7eff811.toInt()
    val NEW_SESSION_CREATED = 0x9ec20908.toInt()
    val PING_DELAY_DISCONNECT = 0xf3427b8c.toInt()
    val INVOKE_WITH_LAYER = 0xda9b0d0d.toInt()
    val INVOKE_WITHOUT_UPDATES = 0xbf9459b7.toInt()
    val INIT_CONNECTION = 0xc1cd5ea9.toInt()
    val HELP_GET_CONFIG = 0xc4f9186b.toInt()
    val HELP_GET_NEAREST_DC = 0x1fb33026
    val AUTH_IMPORT_BOT_AUTHORIZATION = 0x67a3ff2c
    val AUTH_AUTHORIZATION = 0x2ea2c0d4
    val AUTH_AUTHORIZATION_SIGNUP = 0x44747e9a
    val NEAREST_DC = 0x8e1a1775.toInt()
    val USER = 0xb1b8cc83.toInt()
    val USER_EMPTY = 0xd3bc4b7a.toInt()
    val USER_PROFILE_PHOTO_EMPTY = 0x4f11bae1
    val USER_PROFILE_PHOTO = 0x82d1f706.toInt()
    val USER_STATUS_EMPTY = 0x09d05049
    val USER_STATUS_ONLINE = 0xedb93949.toInt()
    val USER_STATUS_OFFLINE = 0x008c703f
    val USER_STATUS_RECENTLY = 0x7b197dc8
    val USER_STATUS_LAST_WEEK = 0x541a1d1a
    val USER_STATUS_LAST_MONTH = 0x65899777
    val RESTRICTION_REASON = 0xd072acb4.toInt()
    val USERNAME = 0xb4073647.toInt()
    val EMOJI_STATUS_EMPTY = 0x2de11aae
    val EMOJI_STATUS = 0xe7ff068a.toInt()
    val EMOJI_STATUS_COLLECTIBLE = 0x7184603b
    val PEER_COLOR = 0xb54b5acf.toInt()
    val PEER_COLOR_COLLECTIBLE = 0xb9c0639a.toInt()
    val INPUT_PEER_COLOR_COLLECTIBLE = 0xb8ea86a9.toInt()
    val RECENT_STORY = 0x711d692d
    val BOOL_TRUE = 0x997275b5.toInt()
    val BOOL_FALSE = 0xbc799737.toInt()
    val SEND_MESSAGE = 0xfef48f62.toInt()
    val INPUT_PEER_EMPTY = 0x7f3b18ea
    val INPUT_PEER_SELF = 0x7da07ec9
    val INPUT_PEER_CHAT = 0x35a95cb9
    val INPUT_PEER_USER = 0xdde8a54c.toInt()
    val INPUT_PEER_CHANNEL = 0x27bcbbfc
    val PEER_USER = 0x59511722
    val PEER_CHAT = 0x36c6019a
    val PEER_CHANNEL = 0xa2a5371e.toInt()
    val MESSAGE_EMPTY = 0x90a6ca84.toInt()
    val MESSAGE = 0x7600b9d3
    val MESSAGE_SERVICE = 0x7a800e0a
    val MESSAGE_FWD_HEADER = 0x4e4df4bb
    val MESSAGE_REPLY_HEADER = 0x1b97dd66
    val MESSAGE_REPLIES = 0x83d60fc2.toInt()
    val MESSAGE_MEDIA_EMPTY = 0x3ded6320
    val MESSAGE_MEDIA_UNSUPPORTED = 0x9f84f49e.toInt()
    val UPDATE_NEW_MESSAGE = 0x1f2b0afd
    val UPDATE_NEW_CHANNEL_MESSAGE = 0x62ba04d9
    val UPDATE_MESSAGE_ID = 0x4e90bfd6
    val UPDATES_TOO_LONG = 0xe317af7e.toInt()
    val UPDATE_SHORT_MESSAGE = 0x313bc7f8
    val UPDATE_SHORT_CHAT_MESSAGE = 0x4d6deea5
    val UPDATE_SHORT = 0x78d4dec1
    val UPDATES = 0x74ae4240
    val UPDATES_COMBINED = 0x725b04c3
    val UPDATE_SHORT_SENT_MESSAGE = 0x9015e101.toInt()
    val CHAT_EMPTY = 0x29562865
    val CHAT_FORBIDDEN = 0x6592a1a7
    val CHANNEL_FORBIDDEN = 0x17d493d5
    val CHAT = 0x41cbf256
    val CHANNEL = 0xd49f34c6.toInt()
    val COMMUNITY = 0x65efe954
    val COMMUNITY_FORBIDDEN = 0xfd3cdab8.toInt()
    val CHAT_PHOTO_EMPTY = 0x37c1011c
    val CHAT_PHOTO = 0x1c6e1c11
    val CHAT_ADMIN_RIGHTS = 0x5fb224d5
    val CHAT_BANNED_RIGHTS = 0x9f120418.toInt()
    val INPUT_CHANNEL_EMPTY = 0xee8c1e86.toInt()
    val INPUT_CHANNEL = 0xf35aec28.toInt()
    val INPUT_CHANNEL_FROM_MESSAGE = 0x5b934f9d
    val MESSAGE_ENTITY_UNKNOWN = 0xbb92ba95.toInt()
    val MESSAGE_ENTITY_MENTION = 0xfa04579d.toInt()
    val MESSAGE_ENTITY_HASHTAG = 0x6f635b0d
    val MESSAGE_ENTITY_BOT_COMMAND = 0x6cef8ac7
    val MESSAGE_ENTITY_URL = 0x6ed02538
    val MESSAGE_ENTITY_EMAIL = 0x64e475c2
    val MESSAGE_ENTITY_BOLD = 0xbd610bc9.toInt()
    val MESSAGE_ENTITY_ITALIC = 0x826f8b60.toInt()
    val MESSAGE_ENTITY_CODE = 0x28a20571
    val MESSAGE_ENTITY_PRE = 0x73924be0
    val MESSAGE_ENTITY_TEXT_URL = 0x76a6d327
    val MESSAGE_ENTITY_MENTION_NAME = 0xdc7b1140.toInt()
    val MESSAGE_ENTITY_PHONE = 0x9b69e34b.toInt()
    val MESSAGE_ENTITY_CASHTAG = 0x4c4e743f
    val MESSAGE_ENTITY_UNDERLINE = 0x9c4e7e8b.toInt()
    val MESSAGE_ENTITY_STRIKE = 0xbf0693d4.toInt()
    val MESSAGE_ENTITY_BANK_CARD = 0x761e6af4
    val MESSAGE_ENTITY_SPOILER = 0x32ca960f
    val MESSAGE_ENTITY_CUSTOM_EMOJI = 0xc8cf05f8.toInt()
    val MESSAGE_ENTITY_BLOCKQUOTE = 0xf1ccaaac.toInt()
    val MESSAGE_ENTITY_FORMATTED_DATE = 0x904ac7c7.toInt()
    val MESSAGE_ENTITY_DIFF_INSERT = 0x71777116
    val MESSAGE_ENTITY_DIFF_REPLACE = 0xc6c1e5a7.toInt()
    val MESSAGE_ENTITY_DIFF_DELETE = 0x0652c1c5
    val GET_STATE = 0xedd4882a.toInt()
    val GET_DIFFERENCE = 0x19c2f763
    val GET_CHANNEL_DIFFERENCE = 0x03173d78
    val CHANNEL_DIFFERENCE_EMPTY = 0x3e11affb
    val CHANNEL_DIFFERENCE = 0x2064674e
    val CHANNEL_DIFFERENCE_TOO_LONG = 0xa4bcc6fe.toInt()
    val CHANNEL_MESSAGES_FILTER_EMPTY = 0x94d42ee7.toInt()
    val UPDATES_STATE = 0xa56c2a3e.toInt()
    val DIFFERENCE_EMPTY = 0x5d75a138
    val DIFFERENCE = 0x00f49ca0
    val DIFFERENCE_SLICE = 0xa8fb1981.toInt()
    val DIFFERENCE_TOO_LONG = 0x4afe8f6d
    val UPDATE_DELETE_MESSAGES = 0xa20db0e5.toInt()
    val UPDATE_USER_TYPING = 0x2a17bf5c
    val UPDATE_CHAT_USER_TYPING = 0x83487af0.toInt()
    val UPDATE_USER_STATUS = 0xe5bdf8de.toInt()
    val UPDATE_USER_NAME = 0xa7848924.toInt()
    val UPDATE_USER_PHONE = 0x05492a13
    val UPDATE_READ_HISTORY_INBOX = 0x9e84bc99.toInt()
    val UPDATE_READ_HISTORY_OUTBOX = 0x2f2f21bf
    val UPDATE_PTS_CHANGED = 0x3354678f
    val UPDATE_CONFIG = 0xa229dd06.toInt()
    val UPDATE_RECENT_STICKERS = 0x9a422c20.toInt()
    val UPDATE_CHANNEL = 0x635b4c09
    val UPDATE_CHANNEL_TOO_LONG = 0x108d941f
    val UPDATE_BOT_MESSAGE_REACTIONS = 0x09cb7759
    val REACTION_COUNT = 0xa3d1cb80.toInt()
    val REACTION_EMPTY = 0x79f5d419
    val REACTION_EMOJI = 0x1b2286b8
    val REACTION_CUSTOM_EMOJI = 0x8935fc73.toInt()
    val REACTION_PAID = 0x523da4eb
    val UPDATE_EDIT_MESSAGE = 0xe40370a3.toInt()
    val UPDATE_EDIT_CHANNEL_MESSAGE = 0x1b3f4df7
    val UPDATE_READ_CHANNEL_INBOX = 0x922e6e10.toInt()
    val UPDATE_READ_CHANNEL_OUTBOX = 0xb75f99a9.toInt()
    val UPDATE_CHANNEL_MESSAGE_VIEWS = 0xf226ac08.toInt()
    val UPDATE_PINNED_MESSAGES = 0xed85eab5.toInt()
    val UPDATE_PEER_BLOCKED = 0xebe07752.toInt()
    val ENCRYPTED_MESSAGE = 0xed18c118.toInt()
    val ENCRYPTED_MESSAGE_SERVICE = 0x23734b06
    val ENCRYPTED_FILE_EMPTY = 0xc21f497e.toInt()
    val ENCRYPTED_FILE = 0xa8008cd8.toInt()
    val SEND_MESSAGE_TYPING = 0x16bf744e
    val SEND_MESSAGE_CANCEL = 0xfd5ec8f5.toInt()
    val SEND_MESSAGE_RECORD_VIDEO = 0xa187d66f.toInt()
    val SEND_MESSAGE_UPLOAD_VIDEO = 0xe9763aec.toInt()
    val SEND_MESSAGE_RECORD_AUDIO = 0xd52f73f7.toInt()
    val SEND_MESSAGE_UPLOAD_AUDIO = 0xf351d7ab.toInt()
    val SEND_MESSAGE_UPLOAD_PHOTO = 0xd1d34a26.toInt()
    val SEND_MESSAGE_UPLOAD_DOCUMENT = 0xaa0cd9e4.toInt()
    val SEND_MESSAGE_GEO = 0x176f8ba1
    val SEND_MESSAGE_CONTACT = 0x628cbc6f
    val SEND_MESSAGE_GAME = 0xdd6a8f48.toInt()
    val SEND_MESSAGE_RECORD_ROUND = 0x88f27fbc.toInt()
    val SEND_MESSAGE_UPLOAD_ROUND = 0x243e1c66
    val SEND_MESSAGE_CHOOSE_STICKER = 0xb05ac6b1.toInt()
}
