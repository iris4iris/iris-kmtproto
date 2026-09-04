package iris.kmtproto.client

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext

/**
 * Dedicated threads for one MTProto socket.
 * Blocking readFully cannot share a thread with the writer, or outgoing
 * RPC sits until the next inbound frame.
 */
@OptIn(DelicateCoroutinesApi::class)
internal class MuxThreads(name: String = "mux") {
    val read: ExecutorCoroutineDispatcher = newSingleThreadContext("iris-kmtproto-$name-read")
    val write: ExecutorCoroutineDispatcher = newSingleThreadContext("iris-kmtproto-$name-write")

    fun close() {
        runCatching { read.close() }
        runCatching { write.close() }
    }
}
