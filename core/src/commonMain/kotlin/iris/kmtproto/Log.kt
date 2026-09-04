package iris.kmtproto

import kotlin.coroutines.cancellation.CancellationException

internal fun isDisconnect(e: Throwable): Boolean {
    var c: Throwable? = e
    while (c != null) {
        when (c::class.simpleName) {
            "EOFException", "SocketException", "ConnectException", "ClosedChannelException" -> return true
        }
        val m = c.message.orEmpty()
        if (m.contains("Broken pipe") ||
            m.contains("Connection reset") ||
            m.contains("Socket closed") ||
            m.contains("Connection closed")
        ) {
            return true
        }
        c = c.cause
    }
    return false
}

internal fun logCaught(where: String, e: Throwable) {
    if (e is CancellationException) {
        if (e.message == "reconnect" || e.message == "closed") return
        println("kmtproto [$where] cancelled: ${e.message}")
        return
    }
    if (isDisconnect(e)) {
        println("kmtproto [$where] disconnected")
        return
    }
    println("kmtproto [$where] ${e::class.simpleName}: ${e.message}")
    e.printStackTrace()
}
