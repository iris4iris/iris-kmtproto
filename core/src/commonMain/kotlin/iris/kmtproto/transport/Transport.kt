package iris.kmtproto.transport

interface MtprotoTransport {
    suspend fun send(payload: ByteArray)
    suspend fun receive(): ByteArray
    suspend fun close()
    fun setReadTimeoutMs(ms: Int) {}
}

data class Datacenter(
    val id: Int,
    val host: String,
    val port: Int = 443,
) {
    companion object {
        val DC1 = Datacenter(1, "149.154.175.53")
        val DC2 = Datacenter(2, "149.154.167.51")
        val DC3 = Datacenter(3, "149.154.175.100")
        val DC4 = Datacenter(4, "149.154.167.91")
        val DC5 = Datacenter(5, "91.108.56.130")
        fun production(id: Int) = when (id) {
            1 -> DC1
            2 -> DC2
            3 -> DC3
            4 -> DC4
            5 -> DC5
            else -> error("unknown dc $id")
        }
    }
}

expect suspend fun connectObfuscated(dc: Datacenter): MtprotoTransport
