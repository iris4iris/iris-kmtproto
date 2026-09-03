package iris.kmtproto.crypto

/** Pollard's rho. Telegram pq is ~64 bit; MpInt is enough. */
internal object Factorize {
    fun factorize(pqBytes: ByteArray): Pair<ByteArray, ByteArray> {
        val n = mpIntFromUnsigned(pqBytes)
        val factor = pollard(n)
        val other = n.divide(factor)
        val (p, q) = if (factor.compare(other) < 0) factor to other else other to factor
        return p.toUnsignedBytes() to q.toUnsignedBytes()
    }

    private fun pollard(n: MpInt): MpInt {
        if (n.remainder(MP_TWO).compare(MP_ZERO) == 0) return MP_TWO
        var x = mpIntFromLong(2)
        var y = mpIntFromLong(2)
        var d = MP_ONE
        val c = MP_ONE
        var guard = 0
        while (d.compare(MP_ONE) == 0) {
            x = x.multiply(x).add(c).remainder(n)
            y = y.multiply(y).add(c).remainder(n)
            y = y.multiply(y).add(c).remainder(n)
            val diff = if (x.compare(y) > 0) x.subtract(y) else y.subtract(x)
            d = diff.gcd(n)
            if (++guard > 2_000_000) error("factorization failed")
        }
        if (d.compare(n) == 0) error("factorization failed (trivial)")
        return d
    }
}
