package iris.kmtproto

import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.crypto.PlatformCrypto
import iris.kmtproto.tl.toBytes

/**
 * Standalone Fake DC process. Prints one ready line, then accepts one client.
 *
 * ```
 * kmtproto fake-dc ready host=127.0.0.1 port=… key=… salt=… session=… frames=… messages=… frameBytes=…
 * ```
 */
fun main(args: Array<String>) {
    val a = parseFakeDcArgs(args)
    val key = AuthKey(PlatformCrypto.randomBytes(256))
    val salt = 0x1111_2222_3333_4444L
    val session = 0x5555_6666_7777_8888L
    val frameBytes = InboundEncoder().payloadBytes(sampleUpdates(a.messages).toBytes().size)
    FakeDc(a.bind, a.port).use { dc ->
        println(
            "kmtproto fake-dc ready host=${a.bind} port=${dc.port} key=${key.key.toHex()} " +
                "salt=${salt.toUnsignedHex()} session=${session.toUnsignedHex()} " +
                "frames=${a.frames} messages=${a.messages} frameBytes=$frameBytes",
        )
        System.out.flush()
        dc.start(key, salt, session, a.messages, a.frames).join()
    }
}

internal class FakeDcArgs(
    val bind: String,
    val port: Int,
    val frames: Int,
    val messages: Int,
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
    )
}

internal fun Long.toUnsignedHex(): String = java.lang.Long.toUnsignedString(this, 16)

internal fun parseUnsignedHex(s: String): Long = java.lang.Long.parseUnsignedLong(s, 16)
