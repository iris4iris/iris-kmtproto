package iris.kmtproto.tlgen

import java.io.File

fun main() {
    val root = File(".").canonicalFile
    val api = File(root, "schema/api.tl").takeIf { it.isFile }
        ?: File(root, "kmtproto/schema/api.tl")
    val schema = TlParser.parse(api.readText())
    val dir = File(api.parentFile.parentFile, "src/commonMain/kotlin/iris/kmtproto/tl/gen")
    dir.mkdirs()
    dir.listFiles()?.filter { it.extension == "kt" }?.forEach { it.delete() }
    val files = TlKotlinGen.generateFiles(schema)
    for ((name, src) in files) {
        File(dir, name).writeText(src)
    }
    println("wrote ${files.size} files to ${dir.canonicalPath} layer=${schema.layer}")
}
