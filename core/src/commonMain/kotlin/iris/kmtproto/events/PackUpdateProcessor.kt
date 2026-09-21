package iris.kmtproto.events

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.Update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * Producer only enqueues. The loop drains the whole queue, then
 * [PackEventDispatcher.dispatch] — default split-by-type is [DefaultPackEventDispatcher].
 * Empty queue: [Channel.receive] suspends.
 *
 * [start] uses [CoroutineStart.UNDISPATCHED] so the collect is subscribed before it returns.
 */
class PackUpdateProcessor<T>(
    private val updates: Flow<T>,
    private val dispatcher: PackEventDispatcher<T>,
    private val queueLimit: Int = 10_000,
) {

    init {
        require(queueLimit >= 1) { "queueLimit >= 1" }
    }

    @Volatile private var job: Job? = null
    private val defaultScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun start(scope: CoroutineScope = defaultScope): Job {
        job?.cancel()
        val started = scope.launch(start = CoroutineStart.UNDISPATCHED) {
            supervisorScope {
                val queue = Channel<T>(queueLimit, BufferOverflow.DROP_OLDEST)
                launch(start = CoroutineStart.UNDISPATCHED) {
                    updates.collect { queue.send(it) }
                }
                launch {
                    while (true) {
                        val batch = drain(queue)
                        dispatcher.dispatch(batch)
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

    private suspend fun drain(queue: Channel<T>): List<T> {
        val first = queue.receive()
        val extra = queue.tryReceive().getOrNull() ?: return listOf(first)
        val out = ArrayList<T>()
        out.add(first)
        out.add(extra)
        while (true) {
            val next = queue.tryReceive().getOrNull() ?: break
            out.add(next)
        }
        return out
    }
}

fun PackUpdateProcessor(
    client: TelegramClient,
    dispatcher: PackEventDispatcher<Update>,
    queueLimit: Int = 10_000,
) = PackUpdateProcessor(client.incomingUpdates(), dispatcher, queueLimit)

fun PackUpdateProcessor(
    updates: Flow<Update>,
    handler: PackEventHandler,
    queueLimit: Int = 10_000,
) = PackUpdateProcessor(updates, DefaultPackEventDispatcher(handler), queueLimit)

fun PackUpdateProcessor(
    client: TelegramClient,
    handler: PackEventHandler,
    queueLimit: Int = 10_000,
) = PackUpdateProcessor(client.incomingUpdates(), handler, queueLimit)
