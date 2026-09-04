package iris.kmtproto.client

import iris.kmtproto.mtproto.RpcException
import iris.kmtproto.tl.RpcError

class RpcResponse<T>(
    val result: T?,
    val error: RpcError?,
) {
    val isOk: Boolean get() = error == null && result != null

    fun <R> map(transform: (T) -> R): RpcResponse<R> {
        val err = error
        if (err != null) return RpcResponse(null, err)
        val value = result ?: return RpcResponse(null, null)
        return RpcResponse(transform(value), null)
    }
}

val RpcError.floodWaitSeconds: Int?
    get() = if (errorMessage.startsWith("FLOOD_WAIT_")) errorMessage.removePrefix("FLOOD_WAIT_").toIntOrNull() else null

internal fun <T> RpcResponse<T>.orThrow(): T {
    val e = error
    if (e != null) throw RpcException(e.errorCode, e.errorMessage)
    return result ?: error("empty rpc response")
}
