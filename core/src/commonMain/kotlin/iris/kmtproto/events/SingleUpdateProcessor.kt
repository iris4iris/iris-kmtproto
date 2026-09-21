package iris.kmtproto.events

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.Update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * One collect on [updates]: then `launch` so the collector never waits on user code.
 * [SingleEventDispatcher] decides what the item is.
 *
 * [start] uses [CoroutineStart.UNDISPATCHED] so the collect is subscribed before it returns.
 */
class SingleUpdateProcessor<T>(
    private val updates: Flow<T>,
    private val dispatcher: SingleEventDispatcher<T>,
) {

    @Volatile private var job: Job? = null
    private val defaultScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun start(scope: CoroutineScope = defaultScope): Job {
        job?.cancel()
        val started = scope.launch(start = CoroutineStart.UNDISPATCHED) {
            supervisorScope {
                updates.collect { update ->
                    launch {
                        guard("single") { dispatcher.dispatch(update) }
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

fun SingleUpdateProcessor(
    client: TelegramClient,
    dispatcher: SingleEventDispatcher<Update>,
) = SingleUpdateProcessor(client.incomingUpdates(), dispatcher)

fun SingleUpdateProcessor(
    updates: Flow<Update>,
    handler: SingleEventHandler,
) = SingleUpdateProcessor(updates, DefaultSingleEventDispatcher(handler))

fun SingleUpdateProcessor(
    client: TelegramClient,
    handler: SingleEventHandler,
) = SingleUpdateProcessor(client.incomingUpdates(), handler)
