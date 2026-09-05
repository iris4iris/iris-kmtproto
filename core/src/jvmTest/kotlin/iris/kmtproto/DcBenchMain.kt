package iris.kmtproto

import iris.kmtproto.crypto.AuthKey
import iris.kmtproto.tl.toBytes
import kotlinx.coroutines.runBlocking

/**
 * Client side of the Fake DC bench. Default: spawn [FakeDcMain] in another JVM.
 * Attach to an already running server with `--port --key --salt --session`.
 */
fun main(args: Array<String>) {
    val a = parseFakeDcArgs(args)
    val attached = parseAttached(args)
    if (attached != null) {
        runBlocking {
            runFakeDcClient(
                label = "${a.messages} updates",
                host = attached.host,
                port = attached.port,
                authKey = attached.authKey,
                salt = attached.salt,
                sessionId = attached.sessionId,
                messagesPerFrame = a.messages,
                frames = a.frames,
                frameBytes = attached.frameBytes,
                warmup = a.warmup,
            )
        }
        return
    }
    startFakeDcProcess(a.messages, a.frames, warmup = a.warmup).use { dc ->
        check(dc.ready.messages == a.messages && dc.ready.frames == a.frames) {
            "fake-dc ready mismatch: ${dc.ready.messages}×${dc.ready.frames}"
        }
        runBlocking {
            runFakeDcClient(
                label = "${a.messages} updates",
                host = dc.ready.host,
                port = dc.ready.port,
                authKey = dc.ready.authKey,
                salt = dc.ready.salt,
                sessionId = dc.ready.sessionId,
                messagesPerFrame = dc.ready.messages,
                frames = dc.ready.frames,
                frameBytes = dc.ready.frameBytes,
                warmup = dc.ready.warmup,
            )
        }
    }
}

private fun parseAttached(args: Array<String>): FakeDcReady? {
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
    val port = m["port"] ?: return null
    val keyHex = m["key"] ?: return null
    val messages = m["messages"]?.toInt() ?: 1
    val frameBytes = m["frameBytes"]?.toInt()
        ?: InboundEncoder().payloadBytes(sampleUpdates(messages).toBytes().size)
    return FakeDcReady(
        host = m["bind"] ?: m["host"] ?: "127.0.0.1",
        port = port.toInt(),
        authKey = AuthKey(keyHex.hexToBytes()),
        salt = parseUnsignedHex(m.getValue("salt")),
        sessionId = parseUnsignedHex(m.getValue("session")),
        frames = m["frames"]?.toInt() ?: 0,
        messages = messages,
        warmup = m["warmup"]?.toInt() ?: benchWarmup(),
        frameBytes = frameBytes,
    )
}
