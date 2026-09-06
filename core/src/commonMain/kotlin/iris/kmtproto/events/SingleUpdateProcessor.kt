package iris.kmtproto.events

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.Update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * One collect on [TelegramClient.incomingUpdates]: classify, then `launch` so the collector
 * never waits on user code. Handlers of one event run sequentially inside that coroutine.
 *
 * [start] uses [CoroutineStart.UNDISPATCHED] so the collect is subscribed before it returns.
 */
class SingleUpdateProcessor(
    private val updates: Flow<Update>,
    private val handler: SingleEventHandler,
) {
    constructor(client: TelegramClient, handler: SingleEventHandler) : this(client.incomingUpdates(), handler)

    @Volatile private var job: Job? = null

    fun start(scope: CoroutineScope): Job {
        job?.cancel()
        val started = scope.launch(start = CoroutineStart.UNDISPATCHED) {
            supervisorScope {
                updates.collect { update ->
                    launch {
                        guard("single") { dispatchSingle(update, handler) }
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
