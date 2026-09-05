package iris.kmtproto.tlgen

import java.io.File

fun main(args: Array<String>) {
    val root = File(".").canonicalFile
    val api = listOf(
        File(root, "schema/api.tl"),
        File(root, "tl/schema/api.tl"),
        File(root, "kmtproto/tl/schema/api.tl"),
    ).firstOrNull { it.isFile } ?: error("schema/api.tl not found from $root")
    val dir = File(args.getOrNull(0) ?: error("output dir argument required"))
    dir.mkdirs()
    val schema = TlParser.parse(api.readText())
    val files = TlKotlinGen.generateFiles(schema)
    val expected = files.keys.toSet()
    dir.listFiles()?.forEach { f ->
        if (f.extension == "kt" && f.name !in expected) f.delete()
    }
    var written = 0
    for ((name, src) in files) {
        val dest = File(dir, name)
        if (!dest.isFile || dest.readText(Charsets.UTF_8) != src) {
            dest.writeText(src, Charsets.UTF_8)
            written++
        }
    }
    println("tlgen layer=${schema.layer}: wrote $written/${files.size} to ${dir.canonicalPath}")
}
