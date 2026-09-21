package iris.kmtproto.api.bot

/**
 * Builds every nested Bot API object ([Update], [Message], [User], …).
 * Default is [LinkedHashMap]. Assign [BotApi.mapFactory] to use a custom
 * [MutableMap] (must still implement [MutableMap] so fields can be put).
 */
fun interface BotApiMapFactory {
    fun create(): MutableMap<String, Any?>
}
