package iris.kmtproto.tlgen

data class TlSchema(
    val layer: Int?,
    val combinators: List<TlCombinator>,
) {
    val types: List<TlCombinator> get() = combinators.filter { !it.function }
    val functions: List<TlCombinator> get() = combinators.filter { it.function }

    fun byName(name: String): TlCombinator? = combinators.find { it.name == name }
    fun byId(id: Int): TlCombinator? = combinators.find { it.id == id }

    fun byResult(ident: String): List<TlCombinator> =
        combinators.filter { !it.function && !it.vectorBuiltin && it.result == TlType.Named(ident) }
}

data class TlCombinator(
    val name: String,
    val id: Int?,
    val generics: List<String>,
    val params: List<TlParam>,
    val result: TlType,
    val function: Boolean,
    val line: Int,
    val vectorBuiltin: Boolean = false,
)

data class TlParam(
    val name: String,
    val type: TlType,
    val condition: TlCondition? = null,
)

data class TlCondition(val flagsParam: String, val bit: Int)

sealed class TlType {
    data class Named(val ident: String) : TlType()
    data class Vector(val inner: TlType, val boxed: Boolean) : TlType()
    data class Bang(val inner: TlType) : TlType()
    object Flags : TlType()
    object True : TlType()
}

class TlParseException(val lineNumber: Int, message: String) :
    RuntimeException("tl:$lineNumber: $message")
