package iris.kmtproto.botgen

import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.Locale

private val KEYWORDS = setOf(
    "as", "break", "class", "continue", "do", "else", "false", "for", "fun", "if",
    "in", "interface", "is", "null", "object", "package", "return", "super", "this",
    "throw", "true", "try", "typealias", "typeof", "val", "var", "when", "while",
)
private val SCALARS = setOf("Integer", "String", "Boolean", "Float")

fun main() {
    val root = File(".").canonicalFile
    val schema = listOf(
        File(root, "schema/api.min.json"),
        File(root, "bot/schema/api.min.json"),
    ).firstOrNull { it.isFile } ?: error("schema/api.min.json not found from $root")
    val out = File(root, "src/commonMain/kotlin/iris/kmtproto/bot/gen").takeIf { it.isDirectory }
        ?: File(root, "bot/src/commonMain/kotlin/iris/kmtproto/bot/gen").takeIf { it.isDirectory }
        ?: error("bot gen output dir not found from $root")

    val spec = JSONObject(schema.readText())
    val typesObj = spec.getJSONObject("types")
    val typeNames = typesObj.keys().asSequence().toList()
    val types = LinkedHashMap<String, JSONObject>()
    for (name in typeNames) types[name] = typesObj.getJSONObject(name)
    val version = spec.getString("version")
    val date = spec.getString("release_date")

    val parents = typeNames.filter { types.getValue(it).subtypes().isNotEmpty() }.toSet()
    val unions = LinkedHashMap<List<String>, String>()
    for (tName in typeNames) {
        val t = types.getValue(tName)
        for (field in t.fields()) {
            val names = field.typeNames()
            if (names.size < 2 || names in unions) continue
            if (names == listOf("Integer", "String") || names == listOf("String", "Integer")) continue
            if (names.any { it !in types }) error("unhandled union $tName.${field.getString("name")}: $names")
            val parentSets = names.map { n -> types.getValue(n).subtypeOf().filter { it in parents }.toSet() }
            val common = parentSets.reduce { a, b -> a.intersect(b) }
            val fname = field.getString("name")
            unions[names] = if (common.size == 1) {
                common.first()
            } else {
                fname.take(1).uppercase(Locale.ROOT) + camel(fname).drop(1) + "Of" + t.getString("name")
            }
        }
    }
    val extraIfaces = unions.values.filter { it !in parents && it !in types }.toSortedSet()

    val header = "// Generated from $version ($date). Do not edit.\n" +
        "// Regenerate: ./gradlew :bot:generateBotApi\n" +
        "package iris.kmtproto.bot\n\n"

    val typeChunks = mutableListOf(header)
    for (iface in extraIfaces) typeChunks.add("sealed interface $iface\n\n")
    for (name in parents.sorted()) {
        val subs = types.getValue(name).subtypes()
        val weird = subs.filter { it !in types }
        val note = if (weird.isEmpty()) "" else " Non-object values: " + weird.joinToString(", ") + "."
        val href = types.getValue(name).optString("href", "")
        typeChunks.add("/** [$name]($href).$note */\nsealed interface $name\n\n")
    }
    for (name in typeNames.sorted()) {
        if (name in parents) continue
        val t = types.getValue(name)
        val href = t.optString("href", "")
        val supers = t.subtypeOf().toMutableList()
        for (field in t.fields()) {
            val iface = unions[field.typeNames()] ?: continue
            if (iface !in parents && iface !in supers && iface !in types && name in field.typeNames()) supers.add(iface)
        }
        for ((key, iface) in unions) {
            if (name in key && iface !in parents && iface !in supers) supers.add(iface)
        }
        val superS = if (supers.isEmpty()) "" else " : " + supers.joinToString(", ")
        val fields = t.fields()
        if (fields.isEmpty()) {
            typeChunks.add(
                if (name == "InputFile") {
                    "/** [$name]($href). Path, file_id, or attach:// name. */\n" +
                        "class InputFile(\n    val value: String = \"\",\n)\n\n"
                } else {
                    "/** [$name]($href). */\ndata object $name\n\n"
                },
            )
            continue
        }
        val lines = mutableListOf("/** [$name]($href). */\nclass $name(")
        for (field in fields) {
            val ktype = kotlinRef(field.typeNames(), unions)
            val decl = when {
                isPrimitive(field.typeNames()) && field.getBoolean("required") ->
                    "$ktype = ${scalarDefault(field.typeNames()[0])}"
                isList(field.typeNames()) && field.getBoolean("required") ->
                    "$ktype = emptyList()"
                else -> "$ktype? = null"
            }
            lines.add("    val ${camel(field.getString("name"))}: $decl,")
        }
        lines.add(")$superS\n")
        typeChunks.add(lines.joinToString("\n") + "\n")
    }
    if ("RichText" in parents) {
        typeChunks.add(
            "/** Plain string used where the spec allows a [RichText] value to be a String. */\n" +
                "class RichTextPlain(\n    val text: String = \"\",\n) : RichText\n\n" +
                "/** List used where the spec allows a [RichText] value to be an array. */\n" +
                "class RichTextParts(\n    val parts: List<RichText> = emptyList(),\n) : RichText\n\n",
        )
    }
    File(out, "Types.kt").writeText(typeChunks.joinToString(""))

    val dec = mutableListOf(header)
    for (name in typeNames.sorted()) {
        if (name in parents) continue
        emitStruct(dec, name, types, unions)
    }
    for (iface in extraIfaces) {
        val seen = LinkedHashSet<String>()
        for ((key, v) in unions) if (v == iface) seen.addAll(key)
        val alts = seen.joinToString("\n") { n ->
            val fields = fieldNames(types, n).joinToString(", ") { "\"$it\"" }
            "        BotAlt(setOf($fields)) { ${fn(n)}(it) },"
        }
        dec.add(
            "fun ${fn(iface)}(raw: Any?): $iface? {\n" +
                "    val m = raw as? Map<*, *> ?: return null\n" +
                "    return listOf(\n$alts\n    ).bestMatch(m)\n" +
                "}\n\n",
        )
    }
    for (name in parents.sorted()) {
        emitParent(dec, name, types, unions)
    }
    val update = types["Update"]
    if (update != null) {
        val checks = update.fields().mapNotNull { field ->
            if (field.getString("name") == "update_id") return@mapNotNull null
            val prop = camel(field.getString("name"))
            val names = field.typeNames()
            when {
                isPrimitive(names) && field.getBoolean("required") -> "$prop != ${scalarDefault(names[0])}"
                isList(names) && field.getBoolean("required") -> "$prop.isNotEmpty()"
                else -> "$prop != null"
            }
        }
        dec.add("fun Update.hasPayload(): Boolean =\n    " + checks.joinToString(" ||\n    ") + "\n\n")
    }
    File(out, "Decode.kt").writeText(dec.joinToString(""))
    println("$version types=${types.size} parents=${parents.size} unions=${unions.size} -> ${out.path}")
}

private fun emitStruct(
    dec: MutableList<String>,
    name: String,
    types: Map<String, JSONObject>,
    unions: Map<List<String>, String>,
) {
    val fields = types.getValue(name).fields()
    if (fields.isEmpty()) {
        dec.add(
            if (name == "InputFile") {
                "fun inputFileFromMap(raw: Any?): InputFile? = when (raw) {\n" +
                    "    is String -> InputFile(raw)\n" +
                    "    is Number -> InputFile(raw.toString())\n" +
                    "    else -> null\n" +
                    "}\n\n"
            } else {
                "fun ${fn(name)}(raw: Any?): $name? =\n" +
                    "    if (raw is Map<*, *> || raw == true) $name else null\n\n"
            },
        )
        return
    }
    val args = fields.joinToString("\n") { field ->
        val raw = "m[\"${field.getString("name")}\"]"
        var expr = decodeExpr(field.typeNames(), raw, unions)
        expr = when {
            isPrimitive(field.typeNames()) && field.getBoolean("required") -> "$expr ?: ${scalarDefault(field.typeNames()[0])}"
            isList(field.typeNames()) && field.getBoolean("required") -> "$expr ?: emptyList()"
            else -> expr
        }
        "        ${camel(field.getString("name"))} = $expr,"
    }
    dec.add(
        "fun ${fn(name)}(raw: Any?): $name? {\n" +
            "    val m = raw as? Map<*, *> ?: return null\n" +
            "    return $name(\n$args\n    )\n" +
            "}\n\n",
    )
}

private fun emitParent(
    dec: MutableList<String>,
    name: String,
    types: Map<String, JSONObject>,
    unions: Map<List<String>, String>,
) {
    val subs = types.getValue(name).subtypes().filter { it in types }
    if (name == "RichText") {
        val groups = LinkedHashMap<String, MutableList<Pair<String, String>>>()
        for (sub in subs) {
            val d = discOf(types.getValue(sub)) ?: continue
            groups.getOrPut(d.second) { mutableListOf() }.add(sub to d.first)
        }
        val branches = groups.entries.joinToString("\n") { (lit, group) ->
            if (group.size == 1) {
                "        \"$lit\" -> ${fn(group[0].first)}(raw)"
            } else {
                val alts = group.joinToString("\n") { (sub, _) ->
                    val fields = fieldNames(types, sub).joinToString(", ") { "\"$it\"" }
                    "            BotAlt(setOf($fields)) { ${fn(sub)}(it) },"
                }
                "        \"$lit\" -> listOf(\n$alts\n        ).bestMatch(m)"
            }
        }
        dec.add(
            "fun richTextFromMap(raw: Any?): RichText? = when (raw) {\n" +
                "    is String -> RichTextPlain(raw)\n" +
                "    is List<*> -> RichTextParts(raw.mapNotNull { richTextFromMap(it) })\n" +
                "    is Map<*, *> -> when (raw[\"type\"] as? String) {\n" +
                branches +
                "\n        else -> null\n    }\n    else -> null\n}\n\n",
        )
        return
    }
    val discs = mutableListOf<Triple<String, String, String>>()
    val nodisc = mutableListOf<String>()
    for (sub in subs) {
        val d = discOf(types.getValue(sub))
        if (d == null) nodisc.add(sub) else discs.add(Triple(sub, d.first, d.second))
    }
    val fieldsUsed = discs.map { it.second }.toSet()
    if (discs.isNotEmpty() && nodisc.isEmpty() && fieldsUsed.size == 1) {
        val field = fieldsUsed.first()
        val groups = LinkedHashMap<String, MutableList<String>>()
        for ((sub, _, lit) in discs) groups.getOrPut(lit) { mutableListOf() }.add(sub)
        val branches = groups.entries.joinToString("\n") { (lit, group) ->
            if (group.size == 1) {
                "        \"$lit\" -> ${fn(group[0])}(raw)"
            } else {
                val alts = group.joinToString("\n") { sub ->
                    val fields = fieldNames(types, sub).joinToString(", ") { "\"$it\"" }
                    "            BotAlt(setOf($fields)) { ${fn(sub)}(it) },"
                }
                "        \"$lit\" -> listOf(\n$alts\n        ).bestMatch(m)"
            }
        }
        dec.add(
            "fun ${fn(name)}(raw: Any?): $name? {\n" +
                "    val m = raw as? Map<*, *> ?: return null\n" +
                "    return when (m[\"$field\"] as? String) {\n" +
                branches +
                "\n        else -> null\n    }\n}\n\n",
        )
    } else {
        val alts = subs.joinToString("\n") { sub ->
            val fields = fieldNames(types, sub).joinToString(", ") { "\"$it\"" }
            "        BotAlt(setOf($fields)) { ${fn(sub)}(it) },"
        }
        dec.add(
            "fun ${fn(name)}(raw: Any?): $name? {\n" +
                "    val m = raw as? Map<*, *> ?: return null\n" +
                "    return listOf(\n$alts\n    ).bestMatch(m)\n" +
                "}\n\n",
        )
    }
}

private fun camel(name: String): String {
    val parts = name.split("_")
    val out = parts[0] + parts.drop(1).filter { it.isNotEmpty() }.joinToString("") {
        it.take(1).uppercase(Locale.ROOT) + it.drop(1)
    }
    return if (out in KEYWORDS) "`$out`" else out
}

private fun fn(name: String) = name[0].lowercase(Locale.ROOT) + name.substring(1) + "FromMap"

private fun literalOf(field: JSONObject): String? {
    if (field.typeNames() != listOf("String")) return null
    val desc = field.opt("description") as? String ?: return null
    Regex("""always "([^"]+)"""").find(desc)?.let { return it.groupValues[1] }
    Regex("""must be ([A-Za-z0-9_]+)\b(?! of)""").find(desc)?.let { return it.groupValues[1] }
    return null
}

private fun discOf(typeDef: JSONObject): Pair<String, String>? {
    val found = typeDef.fields().mapNotNull { field -> literalOf(field)?.let { field.getString("name") to it } }
    if (found.isEmpty()) return null
    return found.firstOrNull { it.first in setOf("type", "status", "source") } ?: found[0]
}

private fun kotlinRef(names: List<String>, unions: Map<List<String>, String>): String {
    if (names == listOf("Integer", "String") || names == listOf("String", "Integer")) return "LongOrString"
    if (names.size > 1) return unions.getValue(names)
    return kotlinOne(names[0], unions)
}

private fun kotlinOne(t: String, unions: Map<List<String>, String>): String = when {
    t == "Integer" -> "Long"
    t == "Float" -> "Double"
    t == "String" || t == "Boolean" -> t
    t.startsWith("Array of ") -> "List<${kotlinOne(t.removePrefix("Array of "), unions)}>"
    else -> t
}

private fun decodeOne(t: String, raw: String, unions: Map<List<String>, String>): String = when {
    t == "Integer" -> "botLong($raw)"
    t == "String" -> "botString($raw)"
    t == "Boolean" -> "botBool($raw)"
    t == "Float" -> "botDouble($raw)"
    t.startsWith("Array of ") -> "botList($raw) { ${decodeOne(t.removePrefix("Array of "), "it", unions)} }"
    else -> "${fn(t)}($raw)"
}

private fun decodeExpr(names: List<String>, raw: String, unions: Map<List<String>, String>): String {
    if (names == listOf("Integer", "String") || names == listOf("String", "Integer")) return "LongOrString.of($raw)"
    if (names.size == 1) return decodeOne(names[0], raw, unions)
    return "${fn(unions.getValue(names))}($raw)"
}

private fun isPrimitive(names: List<String>) = names.size == 1 && names[0] in SCALARS

private fun isList(names: List<String>) = names.size == 1 && names[0].startsWith("Array of ")

private fun scalarDefault(t: String) = when (t) {
    "Integer" -> "0"
    "String" -> "\"\""
    "Boolean" -> "false"
    "Float" -> "0.0"
    else -> error(t)
}

private fun fieldNames(types: Map<String, JSONObject>, name: String) =
    types.getValue(name).fields().map { it.getString("name") }

private fun JSONObject.fields(): List<JSONObject> {
    val arr = optJSONArray("fields") ?: return emptyList()
    return (0 until arr.length()).map { arr.getJSONObject(it) }
}

private fun JSONObject.typeNames(): List<String> = getJSONArray("types").strings()

private fun JSONObject.subtypeOf(): List<String> = optJSONArray("subtype_of")?.strings().orEmpty()

private fun JSONObject.subtypes(): List<String> = optJSONArray("subtypes")?.strings().orEmpty()

private fun JSONArray.strings(): List<String> = (0 until length()).map { getString(it) }
