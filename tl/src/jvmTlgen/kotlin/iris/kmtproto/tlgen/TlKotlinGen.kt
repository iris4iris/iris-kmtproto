package iris.kmtproto.tlgen

object TlKotlinGen {
    val demoTypes = setOf("Bool", "True", "Peer", "MessageFwdHeader", "MessageReplies")

    private val kotlinKeywords = setOf(
        "as", "break", "class", "continue", "do", "else", "false", "for", "fun",
        "if", "in", "interface", "is", "null", "object", "package", "return",
        "super", "this", "throw", "true", "try", "typealias", "typeof", "val",
        "var", "when", "while", "by", "catch", "constructor", "delegate",
        "dynamic", "field", "file", "get", "init", "param", "property",
        "receiver", "set", "value", "where", "import",
    )

    fun generate(schema: TlSchema, resultTypes: Set<String> = demoTypes): String {
        val included = typeCombinators(schema).filter { resultName(it) in resultTypes }
        return emitFile(schema.layer, included, functions = emptyList())
    }

    fun generateFiles(schema: TlSchema): Map<String, String> {
        val types = typeCombinators(schema)
        val byResult = types.groupBy { resultName(it) }
        val files = LinkedHashMap<String, String>()
        val groups = byResult.entries.groupBy { safeTypeName(it.key).first().uppercaseChar() }
        for ((letter, entries) in groups.toSortedMap()) {
            val ctors = entries.flatMap { it.value }
            files["T_$letter.kt"] = emitFile(schema.layer, ctors, functions = emptyList())
        }
        val fns = functionCombinators(schema)
        val fnByNs = fns.groupBy { fnNamespace(it.name) }
        for ((ns, list) in fnByNs.toSortedMap()) {
            val safe = ns.ifEmpty { "_" }
            files["Fn_$safe.kt"] = emitFile(schema.layer, emptyList(), functions = list)
        }
        files["Register.kt"] = emitRegister(types)
        return files
    }

    private fun typeCombinators(schema: TlSchema): List<TlCombinator> =
        schema.types.filter { it.id != null && !it.vectorBuiltin && it.generics.isEmpty() }

    private fun functionCombinators(schema: TlSchema): List<TlCombinator> =
        schema.functions.filter { it.id != null && it.generics.isEmpty() && it.result is TlType.Named }

    private fun resultName(c: TlCombinator): String = (c.result as TlType.Named).ident

    private fun fnNamespace(name: String): String =
        if ('.' in name) name.substringBefore('.').replaceFirstChar { it.uppercase() } else "_"

    private fun emitFile(
        layer: Int?,
        types: List<TlCombinator>,
        functions: List<TlCombinator>,
    ): String {
        val out = StringBuilder()
        out.appendLine("package iris.kmtproto.tl.gen")
        out.appendLine()
        out.appendLine("import iris.kmtproto.tl.TlMethod")
        out.appendLine("import iris.kmtproto.tl.TlObject")
        out.appendLine("import iris.kmtproto.tl.TlReader")
        out.appendLine("import iris.kmtproto.tl.TlWriter")
        out.appendLine()
        out.appendLine("/** Generated from schema/api.tl layer $layer. Do not edit. */")
        out.appendLine()
        val byResult = types.groupBy { resultName(it) }
        for (result in byResult.keys.sorted()) {
            emitResult(out, result, byResult.getValue(result))
            out.appendLine()
        }
        val typeNames = byResult.keys.map { safeTypeName(it) }.toSet()
        for (fn in functions.sortedBy { it.name }) {
            emitFunction(out, fn, typeNames)
            out.appendLine()
        }
        return out.toString().trimEnd() + "\n"
    }

    private fun emitResult(out: StringBuilder, result: String, ctors: List<TlCombinator>) {
        val typeName = safeTypeName(result)
        if (ctors.size == 1) {
            emitCtor(out, ctors[0], supertype = null, className = typeName)
            out.appendLine()
            emitReader(out, typeName, ctors)
            return
        }
        out.appendLine("sealed interface $typeName : TlObject")
        out.appendLine()
        for (c in ctors) {
            emitCtor(out, c, supertype = typeName, className = ctorTypeName(typeName, c.name))
            out.appendLine()
        }
        emitReader(out, typeName, ctors)
    }

    private fun emitReader(
        out: StringBuilder,
        typeName: String,
        ctors: List<TlCombinator>,
    ) {
        out.appendLine("fun read$typeName(reader: TlReader): $typeName {")
        out.appendLine("    val id = reader.readInt()")
        if (ctors.size == 1) {
            val c = ctors[0]
            val empty = c.params.all { it.type is TlType.Flags }
            out.appendLine("    check(id == ${hexLit(c.id!!)}) { \"expected $typeName, got 0x\${id.toUInt().toString(16)}\" }")
            out.appendLine(if (empty) "    return $typeName" else "    return $typeName.read(reader)")
            out.appendLine("}")
            return
        }
        out.appendLine("    return when (id) {")
        for (c in ctors) {
            val cls = ctorTypeName(typeName, c.name)
            val empty = c.params.all { it.type is TlType.Flags }
            out.appendLine("        ${hexLit(c.id!!)} -> ${if (empty) cls else "$cls.read(reader)"}")
        }
        out.appendLine("        else -> error(\"expected $typeName, got 0x\${id.toUInt().toString(16)}\")")
        out.appendLine("    }")
        out.appendLine("}")
    }

    private fun emitCtor(
        out: StringBuilder,
        c: TlCombinator,
        supertype: String?,
        className: String,
    ) {
        val fields = c.params.filter { it.type !is TlType.Flags }
        val required = fields.filter { it.condition == null }
        val optional = fields.filter { it.condition != null }
        val kotlinFields = (required + optional).map { kotlinField(it) }
        val impl = if (supertype != null) " : $supertype" else " : TlObject"
        val idLit = hexLit(c.id!!)
        if (kotlinFields.isEmpty()) {
            out.appendLine("object $className$impl {")
            out.appendLine("    override val constructorId: Int = $idLit")
            out.appendLine("    override fun serialize(writer: TlWriter) = Unit")
            out.appendLine("}")
            return
        }
        out.appendLine("data class $className(")
        kotlinFields.forEachIndexed { idx, f ->
            val comma = if (idx == kotlinFields.lastIndex) "" else ","
            out.appendLine("    val ${f.name}: ${f.kotlinType}${f.default}$comma")
        }
        out.appendLine(")$impl {")
        out.appendLine("    override val constructorId: Int = $idLit")
        out.appendLine("    override fun serialize(writer: TlWriter) {")
        emitSerialize(out, c, "        ")
        out.appendLine("    }")
        out.appendLine()
        out.appendLine("    companion object {")
        out.appendLine("        fun read(reader: TlReader): $className {")
        emitRead(out, c, className, "            ")
        out.appendLine("        }")
        out.appendLine("    }")
        out.appendLine("}")
    }

    private fun emitFunction(out: StringBuilder, c: TlCombinator, typeNames: Set<String>) {
        var cls = ctorClassName(c.name)
        if (cls in typeNames || cls in kotlinKeywords.map { it.replaceFirstChar { c -> c.uppercase() } }) {
            cls += "Request"
        }
        val result = safeTypeName((c.result as TlType.Named).ident)
        val fields = c.params.filter { it.type !is TlType.Flags }
        val required = fields.filter { it.condition == null }
        val optional = fields.filter { it.condition != null }
        val kotlinFields = (required + optional).map { kotlinField(it) }
        val idLit = hexLit(c.id!!)
        if (kotlinFields.isEmpty()) {
            out.appendLine("object $cls : TlMethod<$result> {")
            out.appendLine("    override val constructorId: Int = $idLit")
            out.appendLine("    override fun serialize(writer: TlWriter) = Unit")
            out.appendLine("}")
            return
        }
        out.appendLine("data class $cls(")
        kotlinFields.forEachIndexed { idx, f ->
            val comma = if (idx == kotlinFields.lastIndex) "" else ","
            out.appendLine("    val ${f.name}: ${f.kotlinType}${f.default}$comma")
        }
        out.appendLine(") : TlMethod<$result> {")
        out.appendLine("    override val constructorId: Int = $idLit")
        out.appendLine("    override fun serialize(writer: TlWriter) {")
        emitSerialize(out, c, "        ")
        out.appendLine("    }")
        out.appendLine("}")
    }

    private data class KField(
        val name: String,
        val kotlinType: String,
        val default: String,
        val param: TlParam,
    )

    private fun kotlinField(p: TlParam): KField {
        val name = ident(camel(p.name))
        return when {
            p.type is TlType.True -> KField(name, "Boolean", " = false", p)
            p.condition != null -> KField(name, kotlinType(p.type) + "?", " = null", p)
            else -> KField(name, kotlinType(p.type), "", p)
        }
    }

    private fun emitSerialize(out: StringBuilder, c: TlCombinator, indent: String) {
        val flagNames = c.params.filter { it.type is TlType.Flags }.map { it.name }
        for (fp in flagNames) {
            out.appendLine("${indent}var $fp = 0")
            for (p in c.params) {
                val cond = p.condition ?: continue
                if (cond.flagsParam != fp) continue
                val field = ident(camel(p.name))
                val check = if (p.type is TlType.True) field else "$field != null"
                out.appendLine("${indent}if ($check) $fp = $fp or (1 shl ${cond.bit})")
            }
            out.appendLine("${indent}writer.writeInt($fp)")
        }
        for (p in c.params) {
            if (p.type is TlType.Flags || p.type is TlType.True) continue
            val field = ident(camel(p.name))
            if (p.condition != null) {
                out.appendLine("${indent}if ($field != null) {")
                emitWrite(out, field, p.type, indent + "    ")
                out.appendLine("$indent}")
            } else {
                emitWrite(out, field, p.type, indent)
            }
        }
    }

    private fun emitRead(out: StringBuilder, c: TlCombinator, cls: String, indent: String) {
        for (p in c.params) {
            when {
                p.type is TlType.Flags ->
                    out.appendLine("${indent}val ${p.name} = reader.readInt()")
                p.type is TlType.True -> {
                    val cond = p.condition!!
                    out.appendLine(
                        "${indent}val ${ident(camel(p.name))} = ${cond.flagsParam} and (1 shl ${cond.bit}) != 0",
                    )
                }
                p.condition != null -> {
                    val cond = p.condition
                    out.appendLine(
                        "${indent}val ${ident(camel(p.name))} = if (${cond.flagsParam} and (1 shl ${cond.bit}) != 0) ${readExpr(p.type)} else null",
                    )
                }
                else ->
                    out.appendLine("${indent}val ${ident(camel(p.name))} = ${readExpr(p.type)}")
            }
        }
        val names = c.params.filter { it.type !is TlType.Flags }.map { ident(camel(it.name)) }
        val args = names.joinToString(", ") { "$it = $it" }
        out.appendLine("${indent}return $cls($args)")
    }

    private fun emitWrite(out: StringBuilder, expr: String, type: TlType, indent: String) {
        when (type) {
            is TlType.Named -> out.appendLine("$indent${writeStmt(expr, type)}")
            is TlType.Vector -> {
                out.appendLine("${indent}writer.writeInt(TlWriter.VECTOR)")
                out.appendLine("${indent}writer.writeInt($expr.size)")
                out.appendLine("${indent}$expr.forEach {")
                emitWrite(out, "it", type.inner, indent + "    ")
                out.appendLine("$indent}")
            }
            is TlType.Bang -> out.appendLine("${indent}writer.writeObject($expr)")
            TlType.Flags, TlType.True -> Unit
        }
    }

    private fun writeStmt(expr: String, type: TlType.Named): String = when (type.ident) {
        "int" -> "writer.writeInt($expr)"
        "long" -> "writer.writeLong($expr)"
        "double" -> "writer.writeDouble($expr)"
        "string" -> "writer.writeString($expr)"
        "bytes" -> "writer.writeTlBytes($expr)"
        "int128" -> "writer.writeInt128($expr)"
        "int256" -> "writer.writeInt256($expr)"
        else -> "writer.writeObject($expr)"
    }

    private fun readExpr(type: TlType): String = when (type) {
        is TlType.Named -> when (type.ident) {
            "int" -> "reader.readInt()"
            "long" -> "reader.readLong()"
            "double" -> "reader.readDouble()"
            "string" -> "reader.readString()"
            "bytes" -> "reader.readTlBytes()"
            "int128" -> "reader.readInt128()"
            "int256" -> "reader.readInt256()"
            else -> "read${safeTypeName(type.ident)}(reader)"
        }
        is TlType.Vector -> "reader.readVector { ${readExpr(type.inner)} }"
        is TlType.Bang -> "reader.readObject()"
        TlType.Flags -> error("flags")
        TlType.True -> error("true")
    }

    private fun kotlinType(type: TlType): String = when (type) {
        is TlType.Named -> when (type.ident) {
            "int" -> "Int"
            "long" -> "Long"
            "double" -> "Double"
            "string" -> "String"
            "bytes", "int128", "int256" -> "ByteArray"
            else -> safeTypeName(type.ident)
        }
        is TlType.Vector -> "List<${kotlinType(type.inner)}>"
        is TlType.Bang -> "TlObject"
        TlType.True -> "Boolean"
        TlType.Flags -> error("flags")
    }

    private fun safeTypeName(ident: String): String {
        val base = ident.split('.').joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
        return when (base) {
            "Error" -> "TlError"
            "Null" -> "TlNull"
            else -> base
        }
    }

    private fun ctorClassName(name: String): String =
        name.split('.').joinToString("") { part ->
            camel(part).replaceFirstChar { it.uppercase() }
        }

    private fun ctorTypeName(typeName: String, combinatorName: String): String {
        val ctor = ctorClassName(combinatorName)
        return if (ctor == typeName) "${typeName}Ctor" else ctor
    }

    private fun camel(name: String): String {
        val parts = name.split('_')
        return parts[0] + parts.drop(1).joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
    }

    private fun ident(name: String): String = if (name in kotlinKeywords) "`$name`" else name

    private fun hexLit(id: Int): String {
        val hex = id.toUInt().toString(16)
        return if (id < 0) "0x$hex.toInt()" else "0x$hex"
    }

    private fun emitRegister(types: List<TlCombinator>): String {
        val out = StringBuilder()
        out.appendLine("package iris.kmtproto.tl.gen")
        out.appendLine()
        out.appendLine("import iris.kmtproto.tl.TlRegistry")
        out.appendLine()
        out.appendLine("/** Registers generated constructors. Handshake/mtproto types stay handwritten. */")
        out.appendLine("fun registerGenerated() {")
        val chunks = types.chunked(200)
        chunks.forEachIndexed { i, _ ->
            out.appendLine("    register$i()")
        }
        out.appendLine("}")
        out.appendLine()
        chunks.forEachIndexed { i, chunk ->
            out.appendLine("private fun register$i() {")
            for (c in chunk) {
                val result = safeTypeName(resultName(c))
                val multi = typeCombinatorsGroupSize(types, resultName(c)) > 1
                val cls = if (multi) {
                    ctorTypeName(result, c.name)
                } else {
                    result
                }
                val empty = c.params.all { it.type is TlType.Flags }
                val reader = if (empty) "{ $cls }" else "{ $cls.read(it) }"
                out.appendLine("    TlRegistry.register(${hexLit(c.id!!)}, $reader)")
            }
            out.appendLine("}")
            out.appendLine()
        }
        return out.toString()
    }

    private fun typeCombinatorsGroupSize(types: List<TlCombinator>, result: String): Int =
        types.count { resultName(it) == result }
}
