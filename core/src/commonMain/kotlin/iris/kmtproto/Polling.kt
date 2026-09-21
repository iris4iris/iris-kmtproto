package iris.kmtproto

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * Subscribe in [scope] and return immediately. Each item is handled in its own child
 * so a slow collector does not stall the flow (SharedFlow drops oldest when full).
 */
fun <T> Flow<T>.startPolling(
    scope: CoroutineScope,
    collector: suspend (T) -> Unit,
): Job = scope.launch(start = CoroutineStart.UNDISPATCHED) {
    supervisorScope {
        collect { value ->
            launch {
                try {
                    collector(value)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Throwable) {
                    logCaught("polling", e)
                }
            }
        }
    }
}
