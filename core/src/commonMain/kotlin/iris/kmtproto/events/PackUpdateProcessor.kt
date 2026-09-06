package iris.kmtproto.events

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.Update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * Producer only enqueues. The loop drains the whole queue, splits by type, calls the one
 * [PackEventHandler], waits for it, then takes whatever arrived during processing.
 * Empty queue: [Channel.receive] suspends.
 *
 * [start] uses [CoroutineStart.UNDISPATCHED] so the collect is subscribed before it returns.
 */
class PackUpdateProcessor(
    private val updates: Flow<Update>,
    private val handler: PackEventHandler,
    private val queueLimit: Int = 10_000,
) {
    constructor(
        client: TelegramClient,
        handler: PackEventHandler,
        queueLimit: Int = 10_000,
    ) : this(client.incomingUpdates(), handler, queueLimit)

    init {
        require(queueLimit >= 1) { "queueLimit >= 1" }
    }

    @Volatile private var job: Job? = null

    fun start(scope: CoroutineScope): Job {
        job?.cancel()
        val started = scope.launch(start = CoroutineStart.UNDISPATCHED) {
            supervisorScope {
                val queue = Channel<Update>(queueLimit, BufferOverflow.DROP_OLDEST)
                launch(start = CoroutineStart.UNDISPATCHED) {
                    updates.collect { queue.send(it) }
                }
                launch {
                    while (true) {
                        val batch = drain(queue)
                        dispatchPack(batch, handler)
                    }
                }
            }
        }
        job = started
        return started
    }

    fun close() {
        job?.cancel()
        job = null
    }
}

private suspend fun drain(queue: Channel<Update>): List<Update> {
    val first = queue.receive()
    val extra = queue.tryReceive().getOrNull() ?: return listOf(first)
    val out = ArrayList<Update>()
    out.add(first)
    out.add(extra)
    while (true) {
        val next = queue.tryReceive().getOrNull() ?: break
        out.add(next)
    }
    return out
}
