package iris.kmtproto.tlgen

object TlParser {
    fun parse(text: String): TlSchema {
        var layer: Int? = null
        var functions = false
        val out = ArrayList<TlCombinator>()
        text.lineSequence().forEachIndexed { idx, raw ->
            val n = idx + 1
            val trimmed = raw.trim()
            Regex("""^//\s*LAYER\s+(\d+)\s*$""").matchEntire(trimmed)?.let {
                layer = it.groupValues[1].toInt()
                return@forEachIndexed
            }
            val stripped = stripComment(raw).trim()
            if (stripped.isEmpty()) return@forEachIndexed
            when {
                stripped == "---functions---" -> functions = true
                stripped == "---types---" -> functions = false
                stripped.startsWith("//") -> Unit
                else -> {
                    Regex("""^LAYER\s+(\d+)$""").matchEntire(stripped)?.let {
                        layer = it.groupValues[1].toInt()
                        return@forEachIndexed
                    }
                    parseLine(stripped, n, functions)?.let { out += it }
                }
            }
        }
        return TlSchema(layer, out)
    }

    private fun stripComment(line: String): String {
        val i = line.indexOf("//")
        return if (i >= 0) line.substring(0, i) else line
    }

    private fun parseLine(line: String, n: Int, functions: Boolean): TlCombinator? {
        val body = line.removeSuffix(";").trim()
        if (body.isEmpty()) return null
        if (isNativeAlias(body)) return null

        val eq = body.lastIndexOf('=')
        if (eq < 0) throw TlParseException(n, "missing '=' in `$body`")
        val left = body.substring(0, eq).trim()
        val resultRaw = body.substring(eq + 1).trim()
        if (left.startsWith("int128 ") || left.startsWith("int256 ")) return null

        var rest = left
        val nameMatch = Regex("""^([a-zA-Z_][\w.]*)(?:#([0-9a-fA-F]+))?(.*)""").matchEntire(rest)
            ?: throw TlParseException(n, "bad combinator `$left`")
        val name = nameMatch.groupValues[1]
        val idHex = nameMatch.groupValues[2].ifEmpty { null }
        rest = nameMatch.groupValues[3].trim()

        val generics = ArrayList<String>()
        while (rest.startsWith("{")) {
            val close = rest.indexOf('}')
            if (close < 0) throw TlParseException(n, "unclosed generic in `$left`")
            val inner = rest.substring(1, close)
            val gname = inner.substringBefore(':').trim()
            generics += gname
            rest = rest.substring(close + 1).trim()
        }

        if (rest.startsWith("# [") || rest.startsWith("#[")) {
            val id = idHex?.toLong(16)?.toInt()
            return TlCombinator(
                name = name,
                id = id,
                generics = generics,
                params = emptyList(),
                result = parseType(resultRaw, n),
                function = functions,
                line = n,
                vectorBuiltin = true,
            )
        }

        val params = ArrayList<TlParam>()
        if (rest.isNotEmpty()) {
            for (token in rest.split(Regex("""\s+"""))) {
                if (token.isEmpty()) continue
                params += parseParam(token, n)
            }
        }

        val id = idHex?.toLong(16)?.toInt()
        return TlCombinator(
            name = name,
            id = id,
            generics = generics,
            params = params,
            result = parseType(resultRaw, n),
            function = functions,
            line = n,
        )
    }

    private fun isNativeAlias(body: String): Boolean {
        // int ? = Int;
        return Regex("""^(int|long|double|string)\s+\?\s+=\s+\w+$""").matches(body)
    }

    private fun parseParam(token: String, n: Int): TlParam {
        val colon = token.indexOf(':')
        if (colon <= 0) throw TlParseException(n, "bad param `$token`")
        val name = token.substring(0, colon)
        val typePart = token.substring(colon + 1)
        val cond = Regex("""^([a-zA-Z_][\w]*)\.(\d+)\?(.+)$""").matchEntire(typePart)
        return if (cond != null) {
            TlParam(
                name = name,
                type = parseType(cond.groupValues[3], n),
                condition = TlCondition(cond.groupValues[1], cond.groupValues[2].toInt()),
            )
        } else {
            TlParam(name, parseType(typePart, n))
        }
    }

    internal fun parseType(raw: String, n: Int): TlType {
        val s = raw.trim()
        if (s.isEmpty()) throw TlParseException(n, "empty type")
        if (s == "#") return TlType.Flags
        if (s == "true") return TlType.True
        if (s.startsWith("!")) return TlType.Bang(parseType(s.drop(1), n))
        if (s.startsWith("%")) {
            val inner = parseType(s.drop(1), n)
            return if (inner is TlType.Named) inner else inner
        }
        val vec = Regex("""^(Vector|vector)<(.+)>$""").matchEntire(s)
        if (vec != null) {
            return TlType.Vector(parseType(vec.groupValues[2], n), boxed = vec.groupValues[1] == "Vector")
        }
        if (s.startsWith("Vector ") || s.startsWith("vector ")) {
            val inner = s.substringAfter(' ').trim()
            return TlType.Vector(parseType(inner, n), boxed = s.startsWith("Vector"))
        }
        if (!Regex("""^[a-zA-Z_][\w.]*$""").matches(s)) {
            throw TlParseException(n, "bad type `$s`")
        }
        return TlType.Named(s)
    }
}
