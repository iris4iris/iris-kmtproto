package iris.kmtproto

import kotlin.coroutines.cancellation.CancellationException

internal fun logCaught(where: String, e: Throwable) {
    if (e is CancellationException) {
        println("kmtproto [$where] cancelled: ${e.message}")
        return
    }
    println("kmtproto [$where] ${e::class.simpleName}: ${e.message}")
    e.printStackTrace()
}
