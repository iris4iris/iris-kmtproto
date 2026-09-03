package iris.kmtproto.example

import iris.kmtproto.client.ClientSession
import java.io.File
import java.util.Properties

internal fun env(name: String): String? {
    val file = findLocalProperties()
    val props = Properties()
    file?.inputStream()?.use { props.load(it) }
    return System.getenv(name)?.takeIf { it.isNotBlank() }
        ?: System.getProperty(name)?.takeIf { it.isNotBlank() }
        ?: props.getProperty(name)?.trim()?.takeIf { it.isNotBlank() }
}

internal fun requireEnv(vararg names: String): Map<String, String> {
    val missing = names.filter { env(it) == null }
    if (missing.isNotEmpty()) {
        val file = findLocalProperties()
        error(
            "нет ${missing.joinToString()}\n" +
                "  IDEA: Run → Edit Configurations → Environment variables\n" +
                "  или local.properties рядом с build.gradle.kts\n" +
                "  (сейчас: ${file?.absolutePath ?: "не найден"})",
        )
    }
    return names.associateWith { env(it)!! }
}

internal fun findLocalProperties(): File? {
    val names = listOf(
        "local.properties",
        "core/local.properties",
        "kmtproto/local.properties",
    )
    val roots = listOf(File("."), File(".."), File(System.getProperty("user.dir")))
    for (root in roots) {
        for (name in names) {
            val f = File(root, name)
            if (f.isFile) return f.canonicalFile
        }
    }
    return null
}

internal fun sessionFile(name: String): File {
    val dir = findLocalProperties()?.parentFile ?: File(System.getProperty("user.dir"))
    return File(dir, name)
}

internal fun loadSession(file: File): ClientSession? {
    if (!file.isFile) return null
    val props = Properties()
    file.inputStream().use { props.load(it) }
    val keyHex = props.getProperty("authKey") ?: return null
    val key = hexToBytes(keyHex)
    if (key.size != 256) return null
    return ClientSession(
        dcId = props.getProperty("dc")?.toIntOrNull() ?: return null,
        authKey = key,
        salt = props.getProperty("salt")?.toLongOrNull() ?: 0L,
        userId = props.getProperty("userId")?.toLongOrNull() ?: 0L,
        accessHash = props.getProperty("accessHash")?.toLongOrNull() ?: 0L,
    )
}

internal fun saveSession(file: File, session: ClientSession) {
    val props = Properties()
    props["dc"] = session.dcId.toString()
    props["salt"] = session.salt.toString()
    props["userId"] = session.userId.toString()
    props["accessHash"] = session.accessHash.toString()
    props["authKey"] = session.authKey.joinToString("") { b ->
        (b.toInt() and 0xff).toString(16).padStart(2, '0')
    }
    file.writer().use { props.store(it, "Iris kMTProto session — do not commit") }
}

internal fun prompt(label: String): String {
    print("$label: ")
    System.out.flush()
    return readlnOrNull()?.trim().orEmpty()
}

private fun hexToBytes(hex: String): ByteArray {
    val clean = hex.trim()
    require(clean.length % 2 == 0) { "odd hex" }
    return ByteArray(clean.length / 2) { i ->
        clean.substring(i * 2, i * 2 + 2).toInt(16).toByte()
    }
}
