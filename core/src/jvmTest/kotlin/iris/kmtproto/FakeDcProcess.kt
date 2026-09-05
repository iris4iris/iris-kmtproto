package iris.kmtproto

import iris.kmtproto.crypto.AuthKey
import java.io.File
import java.util.concurrent.TimeUnit

internal class FakeDcReady(
    val host: String,
    val port: Int,
    val authKey: AuthKey,
    val salt: Long,
    val sessionId: Long,
    val frames: Int,
    val messages: Int,
    val warmup: Int,
    val frameBytes: Int,
)

internal class FakeDcProcess(
    val ready: FakeDcReady,
    private val proc: Process,
) : AutoCloseable {
    override fun close() {
        proc.destroy()
        if (!proc.waitFor(5, TimeUnit.SECONDS)) proc.destroyForcibly()
    }
}

internal fun startFakeDcProcess(
    messagesPerFrame: Int,
    frames: Int,
    warmup: Int = benchWarmup(),
): FakeDcProcess {
    val javaHome = File(System.getProperty("java.home"), "bin")
    val java = listOf("java.exe", "java").map { File(javaHome, it) }.first { it.isFile }.absolutePath
    val cp = System.getProperty("java.class.path")
        ?: error("java.class.path is missing; cannot spawn FakeDc")
    val proc = ProcessBuilder(
        java,
        "--add-opens=java.base/com.sun.crypto.provider=ALL-UNNAMED",
        "-Xms256m",
        "-Xmx2g",
        "-cp",
        cp,
        "iris.kmtproto.FakeDcMainKt",
        "--messages",
        messagesPerFrame.toString(),
        "--frames",
        frames.toString(),
        "--warmup",
        warmup.toString(),
    ).redirectError(ProcessBuilder.Redirect.INHERIT).start()
    try {
        val reader = proc.inputStream.bufferedReader()
        val readyLine = generateSequence { reader.readLine() }.firstOrNull { line ->
            if (!line.startsWith("kmtproto fake-dc ready")) {
                println(line)
                false
            } else {
                true
            }
        } ?: error("fake-dc: no ready line (alive=${proc.isAlive} exit=${runCatching { proc.exitValue() }.getOrNull()})")
        return FakeDcProcess(parseFakeDcReady(readyLine), proc)
    } catch (e: Throwable) {
        proc.destroyForcibly()
        throw e
    }
}

internal fun parseFakeDcReady(line: String): FakeDcReady {
    require(line.startsWith("kmtproto fake-dc ready")) { line }
    val map = HashMap<String, String>()
    for (tok in line.split(' ')) {
        val eq = tok.indexOf('=')
        if (eq > 0) map[tok.substring(0, eq)] = tok.substring(eq + 1)
    }
    val key = map.getValue("key").hexToBytes()
    require(key.size == 256) { "auth_key ${key.size}" }
    return FakeDcReady(
        host = map["host"] ?: "127.0.0.1",
        port = map.getValue("port").toInt(),
        authKey = AuthKey(key),
        salt = parseUnsignedHex(map.getValue("salt")),
        sessionId = parseUnsignedHex(map.getValue("session")),
        frames = map.getValue("frames").toInt(),
        messages = map.getValue("messages").toInt(),
        warmup = map["warmup"]?.toInt() ?: 0,
        frameBytes = map.getValue("frameBytes").toInt(),
    )
}
