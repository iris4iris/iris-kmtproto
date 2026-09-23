package iris.kmtproto.api.bot

/**
 * Builds every nested Bot API object ([Update], [Message], [User], …).
 * [BotApiWriter] defaults to [HashMap]. Pass a factory that returns your [MutableMap].
 */
fun interface BotApiMapFactory {
    fun create(): MutableMap<String, Any?>
}
