package iris.kmtproto.crypto

import iris.kmtproto.concat
import iris.kmtproto.padLeft
import iris.kmtproto.tl.gen.AccountPassword
import iris.kmtproto.tl.gen.InputCheckPasswordSRP
import iris.kmtproto.tl.gen.InputCheckPasswordSRPCtor
import iris.kmtproto.tl.gen.PasswordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow
import iris.kmtproto.xor

/**
 * Telegram 2FA (SRP). https://core.telegram.org/api/srp
 */
internal object PasswordSrp {
    fun check(account: AccountPassword, password: String): InputCheckPasswordSRP {
        require(account.hasPassword) { "account has no cloud password" }
        val algo = account.currentAlgo
            ?: error("account.password has no current_algo")
        val srpB = account.srpB ?: error("account.password has no srp_B")
        val srpId = account.srpId ?: error("account.password has no srp_id")
        val kdf = algo as? PasswordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow
            ?: error("unsupported PasswordKdfAlgo: $algo — client is outdated")
        return compute(kdf, password.encodeToByteArray(), srpB, srpId)
    }

    fun compute(
        algo: PasswordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow,
        password: ByteArray,
        srpB: ByteArray,
        srpId: Long,
        aBytes: ByteArray = PlatformCrypto.randomBytes(256),
    ): InputCheckPasswordSRPCtor {
        val p = mpIntFromUnsigned(algo.p)
        val g = mpIntFromLong(algo.g.toLong())
        val gPad = g.toUnsignedBytes().padLeft(256)
        val pPad = algo.p.padLeft(256)

        val x = mpIntFromUnsigned(ph2(password, algo.salt1, algo.salt2))
        val a = mpIntFromUnsigned(aBytes)
        val gA = g.modPow(a, p)
        val gAPad = gA.toUnsignedBytes().padLeft(256)
        val gB = mpIntFromUnsigned(srpB)
        val gBPad = srpB.padLeft(256)
        requireGoodDh(gA, p, "g_a")
        requireGoodDh(gB, p, "g_b")

        val u = mpIntFromUnsigned(H(concat(gAPad, gBPad)))
        val k = mpIntFromUnsigned(H(concat(pPad, gPad)))
        val gX = g.modPow(x, p)
        val kGx = k.multiply(gX).remainder(p)
        var t = gB.subtract(kGx)
        if (t.compare(MP_ZERO) < 0) t = t.add(p)
        t = t.remainder(p)
        val exp = a.add(u.multiply(x))
        val sA = t.modPow(exp, p)
        val kA = H(sA.toUnsignedBytes().padLeft(256))
        val m1 = H(
            concat(
                H(pPad).xor(H(gPad)),
                H(algo.salt1),
                H(algo.salt2),
                gAPad,
                gBPad,
                kA,
            ),
        )
        return InputCheckPasswordSRPCtor(srpId = srpId, A = gAPad, M1 = m1)
    }

    fun ph2(password: ByteArray, salt1: ByteArray, salt2: ByteArray): ByteArray {
        val ph1 = SH(SH(password, salt1), salt2)
        val dk = pbkdf2HmacSha512(ph1, salt1, iterations = 100_000, dkLen = 64)
        return SH(dk, salt2)
    }

    fun pbkdf2HmacSha512(
        password: ByteArray,
        salt: ByteArray,
        iterations: Int,
        dkLen: Int,
    ): ByteArray {
        val hLen = 64
        val blocks = (dkLen + hLen - 1) / hLen
        val out = ByteArray(dkLen)
        var offset = 0
        for (i in 1..blocks) {
            val block = f(password, salt, iterations, i)
            val n = minOf(hLen, dkLen - offset)
            block.copyInto(out, offset, 0, n)
            offset += n
        }
        return out
    }

    private fun f(password: ByteArray, salt: ByteArray, iterations: Int, i: Int): ByteArray {
        val be = byteArrayOf(
            (i ushr 24).toByte(),
            (i ushr 16).toByte(),
            (i ushr 8).toByte(),
            i.toByte(),
        )
        var u = PlatformCrypto.hmacSha512(password, concat(salt, be))
        val acc = u.copyOf()
        repeat(iterations - 1) {
            u = PlatformCrypto.hmacSha512(password, u)
            for (j in acc.indices) acc[j] = (acc[j].toInt() xor u[j].toInt()).toByte()
        }
        return acc
    }

    private fun H(data: ByteArray): ByteArray = PlatformCrypto.sha256(data)

    private fun SH(data: ByteArray, salt: ByteArray): ByteArray = H(concat(salt, data, salt))

    private fun requireGoodDh(v: MpInt, p: MpInt, name: String) {
        val pMinus = p.subtract(MP_ONE)
        if (v.compare(MP_ONE) <= 0 || v.compare(pMinus) >= 0) {
            error("bad DH $name for SRP")
        }
    }
}
