package iris.kmtproto.client

/**
 * Bound auth_key + bot identity. Reuse this on connect() so loginBot()
 * does not call importBotAuthorization again (FLOOD_WAIT).
 *
 * Setting user locally without this key yields AUTH_KEY_UNREGISTERED.
 */
data class ClientSession(
    val dcId: Int,
    val authKey: ByteArray,
    val salt: Long = 0L,
    val userId: Long = 0L,
    val accessHash: Long = 0L,
) {
    init {
        require(authKey.size == 256) { "auth_key must be 256 bytes" }
    }
}
