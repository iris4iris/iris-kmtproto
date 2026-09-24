package iris.kmtproto.bot

/** Bot API `Integer | String`, usually a chat id or a @username. */
data class LongOrString(
    val long: Long? = null,
    val text: String? = null,
) {
    companion object {
        fun of(raw: Any?): LongOrString? = when (raw) {
            null -> null
            is String -> LongOrString(text = raw)
            is Number -> LongOrString(long = raw.toLong())
            else -> null
        }
    }
}

internal fun botLong(raw: Any?): Long? = when (raw) {
    is Number -> raw.toLong()
    is String -> raw.toLongOrNull()
    else -> null
}

/** 32-bit Bot API Integer. Values outside [Int] are rejected, not truncated. */
internal fun botInt(raw: Any?): Int? = when (raw) {
    is Number -> raw.toLong().takeIf { it in Int.MIN_VALUE.toLong()..Int.MAX_VALUE.toLong() }?.toInt()
    is String -> raw.toIntOrNull()
    else -> null
}

internal fun botDouble(raw: Any?): Double? = when (raw) {
    is Number -> raw.toDouble()
    is String -> raw.toDoubleOrNull()
    else -> null
}

internal fun botBool(raw: Any?): Boolean? = when (raw) {
    is Boolean -> raw
    is String -> when (raw) {
        "true" -> true
        "false" -> false
        else -> null
    }
    is Number -> raw.toInt() != 0
    else -> null
}

internal fun botString(raw: Any?): String? = raw as? String

internal fun <T> botList(raw: Any?, item: (Any?) -> T?): List<T>? {
    val list = raw as? List<*> ?: return null
    return list.mapNotNull(item)
}

internal class BotAlt<out T>(val fields: Set<String>, val decode: (Any?) -> T?)

/** More matching keys wins. A tie goes to the smaller type, so a 3-field stub beats a full object. */
internal fun <T> List<BotAlt<T>>.bestMatch(raw: Map<*, *>): T? {
    val winner = maxWithOrNull(
        compareBy<BotAlt<T>>(
            { alt -> alt.fields.count { key -> raw[key] != null } },
            { alt -> -alt.fields.size },
        ),
    )
    return winner?.decode?.invoke(raw)
}

fun Map<String, Any?>.toBotUpdate(): Update? = updateFromMap(this)
