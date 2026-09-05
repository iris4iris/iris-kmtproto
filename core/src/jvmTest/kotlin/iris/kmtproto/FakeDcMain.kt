package iris.kmtproto

/**
 * Standalone Fake DC process. Prints one ready line, then accepts one client.
 *
 * ```
 * kmtproto fake-dc ready host=127.0.0.1 port=… key=… salt=… session=… frames=… messages=… frameBytes=…
 * ```
 */
fun main(args: Array<String>) {
    val a = parseFakeDcArgs(args)
    val total = a.warmup + a.frames
    val tape = FakeDcCache.loadOrBuild(a.messages, total)
    FakeDc(a.bind, a.port).use { dc ->
        println(
            "kmtproto fake-dc ready host=${a.bind} port=${dc.port} key=${tape.authKey.key.toHex()} " +
                "salt=${tape.salt.toUnsignedHex()} session=${tape.sessionId.toUnsignedHex()} " +
                "frames=${a.frames} messages=${a.messages} warmup=${a.warmup} frameBytes=${tape.frameBytes}",
        )
        System.out.flush()
        dc.start(tape).join()
    }
}

internal class FakeDcArgs(
    val bind: String,
    val port: Int,
    val frames: Int,
    val messages: Int,
    val warmup: Int,
)

internal fun parseFakeDcArgs(args: Array<String>): FakeDcArgs {
    val m = HashMap<String, String>()
    var i = 0
    while (i < args.size) {
        val raw = args[i]
        if (raw.startsWith("--") && raw.contains('=')) {
            val eq = raw.indexOf('=')
            m[raw.substring(2, eq)] = raw.substring(eq + 1)
        } else if (raw.startsWith("--") && i + 1 < args.size && !args[i + 1].startsWith("--")) {
            m[raw.substring(2)] = args[++i]
        }
        i++
    }
    return FakeDcArgs(
        bind = m["bind"] ?: "127.0.0.1",
        port = m["port"]?.toInt() ?: 0,
        frames = m["frames"]?.toInt() ?: 1_000,
        messages = m["messages"]?.toInt() ?: 1,
        warmup = m["warmup"]?.toInt() ?: benchWarmup(),
    )
}

internal fun Long.toUnsignedHex(): String = java.lang.Long.toUnsignedString(this, 16)

internal fun parseUnsignedHex(s: String): Long = java.lang.Long.parseUnsignedLong(s, 16)
