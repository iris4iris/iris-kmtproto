package iris.kmtproto.crypto

import iris.kmtproto.concat
import iris.kmtproto.padLeft
import iris.kmtproto.tl.TlWriter
import iris.kmtproto.xor

internal data class RsaPublicKey(
    val fingerprint: Long,
    val modulus: MpInt,
    val exponent: MpInt,
    val modulusBytes: ByteArray,
)

internal object RsaPad {
    /**
     * MTProto 2.0 RSA_PAD. Produces a 256-byte ciphertext.
     * https://core.telegram.org/mtproto/auth_key
     */
    fun encrypt(data: ByteArray, key: RsaPublicKey): ByteArray {
        require(data.size <= 144) { "RSA_PAD data too large: ${data.size}" }
        val n = key.modulus
        val zeroIv = ByteArray(32)
        val dataWithPadding = data.copyOf(192)
        while (true) {
            PlatformCrypto.randomBytes(192 - data.size).copyInto(dataWithPadding, data.size)
            val tempKey = PlatformCrypto.randomBytes(32)
            val dataPadReversed = dataWithPadding.reversedArray()
            val dataWithHash = concat(dataPadReversed, PlatformCrypto.sha256(concat(tempKey, dataWithPadding)))
            val aesEncrypted = AesIge.encrypt(tempKey, zeroIv, dataWithHash)
            val tempKeyXor = tempKey.xor(PlatformCrypto.sha256(aesEncrypted))
            val keyAesEncrypted = concat(tempKeyXor, aesEncrypted)
            val x = mpIntFromUnsigned(keyAesEncrypted)
            if (x.compare(n) < 0) {
                val encrypted = x.modPow(key.exponent, n)
                return encrypted.toUnsignedBytes().padLeft(256)
            }
        }
    }

    /** Legacy MTProto 1.0 padding (still accepted by some DCs). */
    fun encryptLegacy(data: ByteArray, key: RsaPublicKey): ByteArray {
        val payload = concat(
            PlatformCrypto.sha1(data),
            data,
            PlatformCrypto.randomBytes(235 - data.size),
        )
        require(payload.size == 255)
        val x = mpIntFromUnsigned(payload)
        return x.modPow(key.exponent, key.modulus).toUnsignedBytes().padLeft(256)
    }
}

internal object ServerKeys {
    val keys: List<RsaPublicKey> by lazy { load() }

    fun byFingerprint(fingerprint: Long): RsaPublicKey? =
        keys.firstOrNull { it.fingerprint == fingerprint }

    private fun load(): List<RsaPublicKey> {
        val pems = listOf(
            """
            -----BEGIN RSA PUBLIC KEY-----
            MIIBCgKCAQEAruw2yP/BCcsJliRoW5eBVBVle9dtjJw+OYED160Wybum9SXtBBLX
            riwt4rROd9csv0t0OHCaTmRqBcQ0J8fxhN6/cpR1GWgOZRUAiQxoMnlt0R93LCX/
            j1dnVa/gVbCjdSxpbrfY2g2L4frzjJvdl84Kd9ORYjDEAyFnEA7dD556OptgLQQ2
            e2iVNq8NZLYTzLp5YpOdO1doK+ttrltggTCy5SrKeLoCPPbOgGsdxJxyz5KKcZnS
            Lj16yE5HvJQn0CNpRdENvRUXe6tBP78O39oJ8BTHp9oIjd6XWXAsp2CvK45Ol8wF
            XGF710w9lwCGNbmNxNYhtIkdqfsEcwR5JwIDAQAB
            -----END RSA PUBLIC KEY-----
            """.trimIndent(),
            """
            -----BEGIN RSA PUBLIC KEY-----
            MIIBCgKCAQEAvfLHfYH2r9R70w8prHblWt/nDkh+XkgpflqQVcnAfSuTtO05lNPs
            pQmL8Y2XjVT4t8cT6xAkdgfmmvnvRPOOKPi0OfJXoRVylFzAQG/j83u5K3kRLbae
            7fLccVhKZhY46lvsueI1hQdLgNV9n1cQ3TDS2pQOCtovG4eDl9wacrXOJTG2990V
            jgnIKNA0UMoP+KF03qzryqIt3oTvZq03DyWdGK+AZjgBLaDKSnC6qD2cFY81UryR
            WOab8zKkWAnhw2kFpcqhI0jdV5QaSCExvnsjVaX0Y1N0870931/5Jb9ICe4nweZ9
            kSDF/gip3kWLG0o8XQpChDfyvsqB9OLV/wIDAQAB
            -----END RSA PUBLIC KEY-----
            """.trimIndent(),
            """
            -----BEGIN RSA PUBLIC KEY-----
            MIIBCgKCAQEAs/ditzm+mPND6xkhzwFIz6J/968CtkcSE/7Z2qAJiXbmZ3UDJPGr
            zqTDHkO30R8VeRM/Kz2f4nR05GIFiITl4bEjvpy7xqRDspJcCFIOcyXm8abVDhF+
            th6knSU0yLtNKuQVP6voMrnt9MV1X92LGZQLgdHZbPQz0Z5qIpaKhdyA8DEvWWvS
            Uwwc+yi1/gGaybwlzZwqXYoPOhwMebzKUk0xW14htcJrRrq+PXXQbRzTMynseCoP
            Ioke0dtCodbA3qQxQovE16q9zz4Otv2k4j63cz53J+mhkVWAeWxVGI0lltJmWtEY
            K6er8VqqWot3nqmWMXogrgRLggv/NbbooQIDAQAB
            -----END RSA PUBLIC KEY-----
            """.trimIndent(),
            """
            -----BEGIN RSA PUBLIC KEY-----
            MIIBCgKCAQEAvmpxVY7ld/8DAjz6F6q05shjg8/4p6047bn6/m8yPy1RBsvIyvuD
            uGnP/RzPEhzXQ9UJ5Ynmh2XJZgHoE9xbnfxL5BXHplJhMtADXKM9bWB11PU1Eioc
            3+AXBB8QiNFBn2XI5UkO5hPhbb9mJpjA9Uhw8EdfqJP8QetVsI/xrCEbwEXe0xvi
            fRLJbY08/Gp66KpQvy7g8w7VB8wlgePexW3pT13Ap6vuC+mQuJPyiHvSxjEKHgqe
            Pji9NP3tJUFQjcECqcm0yV7/2d0t/pbCm+ZH1sadZspQCEPPrtbkQBlvHb4OLiIW
            PGHKSMeRFvp3IWcmdJqXahxLCUS1Eh6MAQIDAQAB
            -----END RSA PUBLIC KEY-----
            """.trimIndent(),
        )
        return pems.map { pem ->
            val (n, e) = parsePkcs1(pem)
            val fp = fingerprint(n, e)
            RsaPublicKey(fp, mpIntFromUnsigned(n), mpIntFromUnsigned(e), n)
        }
    }

    internal fun fingerprint(n: ByteArray, e: ByteArray): Long {
        val w = TlWriter()
        w.writeTlBytes(n)
        w.writeTlBytes(e)
        val hash = PlatformCrypto.sha1(w.toByteArray())
        var v = 0L
        for (i in 0 until 8) {
            v = v or ((hash[hash.size - 8 + i].toLong() and 0xff) shl (8 * i))
        }
        return v
    }

    private fun parsePkcs1(pem: String): Pair<ByteArray, ByteArray> {
        val b64 = pem.lineSequence().filter { !it.startsWith("-") && it.isNotBlank() }.joinToString("")
        val der = base64Decode(b64)
        var i = 0
        require(der[i] == 0x30.toByte()) { "not a SEQUENCE" }
        i++
        i = skipLen(der, i)
        require(der[i] == 0x02.toByte())
        i++
        val (nLen, nStart) = readLen(der, i)
        var n = der.copyOfRange(nStart, nStart + nLen)
        if (n.isNotEmpty() && n[0] == 0.toByte()) n = n.copyOfRange(1, n.size)
        i = nStart + nLen
        require(der[i] == 0x02.toByte())
        i++
        val (eLen, eStart) = readLen(der, i)
        var e = der.copyOfRange(eStart, eStart + eLen)
        if (e.isNotEmpty() && e[0] == 0.toByte()) e = e.copyOfRange(1, e.size)
        return n to e
    }

    private fun skipLen(data: ByteArray, i: Int): Int = readLen(data, i).second

    private fun readLen(data: ByteArray, i: Int): Pair<Int, Int> {
        val first = data[i].toInt() and 0xff
        if (first and 0x80 == 0) return first to (i + 1)
        val n = first and 0x7f
        var v = 0
        for (k in 0 until n) v = (v shl 8) or (data[i + 1 + k].toInt() and 0xff)
        return v to (i + 1 + n)
    }

    private fun base64Decode(s: String): ByteArray {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
        val clean = s.filter { it != '=' && it != '\n' && it != '\r' }
        val out = ArrayList<Byte>((clean.length * 3) / 4)
        var acc = 0
        var bits = 0
        for (ch in clean) {
            val v = alphabet.indexOf(ch)
            require(v >= 0) { "bad b64 char $ch" }
            acc = (acc shl 6) or v
            bits += 6
            if (bits >= 8) {
                bits -= 8
                out.add(((acc shr bits) and 0xff).toByte())
            }
        }
        return ByteArray(out.size) { out[it] }
    }
}
