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
    dir.listFiles()?.filter { it.extension == "kt" }?.forEach { it.delete() }
    val schema = TlParser.parse(api.readText())
    val files = TlKotlinGen.generateFiles(schema)
    for ((name, src) in files) {
        File(dir, name).writeText(src)
    }
    println("wrote ${files.size} files to ${dir.canonicalPath} layer=${schema.layer}")
}
