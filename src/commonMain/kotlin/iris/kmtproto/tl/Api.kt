package iris.kmtproto.tl

/** Default `invokeWithLayer` version. Must match `// LAYER` in `kmtproto/schema/api.tl`. Override via `TelegramClient(layer = …)`. */
const val API_LAYER = 229

data class InvokeWithLayer<T : TlObject>(
    val layer: Int,
    val query: TlMethod<T>,
) : TlMethod<T> {
    override val constructorId: Int = TlIds.INVOKE_WITH_LAYER
    override fun serialize(w: TlWriter) {
        w.writeInt(layer)
        w.writeObject(query)
    }
}

data class InitConnection<T : TlObject>(
    val flags: Int = 0,
    val apiId: Int,
    val deviceModel: String,
    val systemVersion: String,
    val appVersion: String,
    val systemLangCode: String,
    val langPack: String,
    val langCode: String,
    val query: TlMethod<T>,
) : TlMethod<T> {
    override val constructorId: Int = TlIds.INIT_CONNECTION
    override fun serialize(w: TlWriter) {
        w.writeInt(flags)
        w.writeInt(apiId)
        w.writeString(deviceModel)
        w.writeString(systemVersion)
        w.writeString(appVersion)
        w.writeString(systemLangCode)
        w.writeString(langPack)
        w.writeString(langCode)
        w.writeObject(query)
    }
}
