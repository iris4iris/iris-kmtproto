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
 * One collect on [TelegramClient.incomingUpdates]: then `launch` so the collector
 * never waits on user code. [SingleEventDispatcher] decides what the update is.
 *
 * [start] uses [CoroutineStart.UNDISPATCHED] so the collect is subscribed before it returns.
 */
open class SingleUpdateProcessor<T>(
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

class DefaultSingleUpdateProcessor(
     updates: Flow<Update>,
     dispatcher: SingleEventDispatcher<Update>,
) : SingleUpdateProcessor<Update>(updates, dispatcher) {
    constructor(client: TelegramClient, dispatcher: SingleEventDispatcher<Update>) :
        this(client.incomingUpdates(), dispatcher)

    constructor(updates: Flow<Update>, handler: SingleEventHandler) :
        this(updates, DefaultSingleEventDispatcher(handler))

    constructor(client: TelegramClient, handler: SingleEventHandler) :
        this(client.incomingUpdates(), handler)
}
