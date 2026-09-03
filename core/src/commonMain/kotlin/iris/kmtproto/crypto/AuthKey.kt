package iris.kmtproto.crypto

import iris.kmtproto.concat
import iris.kmtproto.readLongLe

class AuthKey(val key: ByteArray) {
    init {
        require(key.size == 256) { "auth_key must be 256 bytes, got ${key.size}" }
    }

    val auxHash: ByteArray = PlatformCrypto.sha1(key).copyOfRange(0, 8)
    val keyId: Long = PlatformCrypto.sha1(key).let { hash ->
        hash.copyOfRange(hash.size - 8, hash.size).readLongLe()
    }

    fun newNonceHash(newNonce: ByteArray, number: Int): ByteArray {
        val data = concat(newNonce, byteArrayOf(number.toByte()), auxHash)
        return PlatformCrypto.sha1(data).copyOfRange(4, 20)
    }
}

internal object MsgKeys {
    /** x = 0 client→server, x = 8 server→client */
    fun deriveAes(authKey: ByteArray, msgKey: ByteArray, x: Int): Pair<ByteArray, ByteArray> {
        val sha256a = PlatformCrypto.sha256(concat(msgKey, authKey.copyOfRange(x, x + 36)))
        val sha256b = PlatformCrypto.sha256(concat(authKey.copyOfRange(40 + x, 40 + x + 36), msgKey))
        val aesKey = concat(
            sha256a.copyOfRange(0, 8),
            sha256b.copyOfRange(8, 24),
            sha256a.copyOfRange(24, 32),
        )
        val aesIv = concat(
            sha256b.copyOfRange(0, 8),
            sha256a.copyOfRange(8, 24),
            sha256b.copyOfRange(24, 32),
        )
        return aesKey to aesIv
    }

    fun msgKey(authKey: ByteArray, plaintext: ByteArray, x: Int): ByteArray {
        val large = PlatformCrypto.sha256(concat(authKey.copyOfRange(88 + x, 88 + x + 32), plaintext))
        return large.copyOfRange(8, 24)
    }
}

internal fun tmpAesFromNonce(serverNonce: ByteArray, newNonce: ByteArray): Pair<ByteArray, ByteArray> {
    val nsn = PlatformCrypto.sha1(concat(newNonce, serverNonce))
    val snn = PlatformCrypto.sha1(concat(serverNonce, newNonce))
    val nn = PlatformCrypto.sha1(concat(newNonce, newNonce))
    val key = concat(nsn, snn.copyOfRange(0, 12))
    val iv = concat(snn.copyOfRange(12, 20), nn, newNonce.copyOfRange(0, 4))
    return key to iv
}

/** Official 2048-bit DH prime from Telegram security guidelines. */
internal val DH_PRIME_HEX =
    "C71CAEB9C6B1C9048E6C522F70F13F73980D40238E3E21C14934D037563D930F" +
        "48198A0AA7C14058229493D22530F4DBFA336F6E0AC925139543AED44CCE7C37" +
        "20FD51F69458705AC68CD4FE6B6B13ABDC9746512969328454F18FAF8C595F64" +
        "2477FE96BB2A941D5BCD1D4AC8CC49880708FA9B378E3C4F3A9060BEE67CF9A4" +
        "A4A695811051907E162753B56B0F6B410DBA74D8A84B2A14B3144E0EF1284754" +
        "FD17ED950D5965B4B9DD46582DB1178D169C6BC465B0D6FF9CA3928FEF5B9AE4" +
        "E418FC15E83EBEA0F87FA9FF5EED70050DED2849F47BF959D956850CE929851F" +
        "0D8115F635B105EE2E4E15D04B2454BF6F4FADF034B10403119CD8E3B92FCC5B"
