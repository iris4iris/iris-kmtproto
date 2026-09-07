package iris.kmtproto.events

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateUserStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

fun interface PackFilter<T> {
    suspend fun take(batch: List<T>): List<T>
}

/**
 * Table of (filter, handler) per kind. Filter keeps a subset and that subset goes to
 * the handler; the next route sees the remainder (identity, original order).
 * Plug into [PackUpdateProcessor] or call [start].
 */
class PackEventRouter : PackEventHandler {
    private val messages = ArrayList<PackRoute<MessageCtor>>()
    private val edits = ArrayList<PackRoute<MessageCtor>>()
    private val members = ArrayList<PackRoute<ChatMemberEvent>>()
    private val callbacks = ArrayList<PackRoute<UpdateBotCallbackQuery>>()
    private val statuses = ArrayList<PackRoute<UpdateUserStatus>>()
    private val unknown = ArrayList<PackRoute<Update>>()
    private var processor: PackUpdateProcessor? = null

    fun onMessages(handler: suspend (List<MessageCtor>) -> Unit) =
        onMessages(keepAll(), handler)

    fun onMessages(filter: PackFilter<MessageCtor>, handler: suspend (List<MessageCtor>) -> Unit) {
        messages.add(PackRoute(filter, handler))
    }

    fun onEdits(handler: suspend (List<MessageCtor>) -> Unit) =
        onEdits(keepAll(), handler)

    fun onEdits(filter: PackFilter<MessageCtor>, handler: suspend (List<MessageCtor>) -> Unit) {
        edits.add(PackRoute(filter, handler))
    }

    fun onChatMembers(handler: suspend (List<ChatMemberEvent>) -> Unit) =
        onChatMembers(keepAll(), handler)

    fun onChatMembers(filter: PackFilter<ChatMemberEvent>, handler: suspend (List<ChatMemberEvent>) -> Unit) {
        members.add(PackRoute(filter, handler))
    }

    fun onCallbacks(handler: suspend (List<UpdateBotCallbackQuery>) -> Unit) =
        onCallbacks(keepAll(), handler)

    fun onCallbacks(filter: PackFilter<UpdateBotCallbackQuery>, handler: suspend (List<UpdateBotCallbackQuery>) -> Unit) {
        callbacks.add(PackRoute(filter, handler))
    }

    fun onUserStatuses(handler: suspend (List<UpdateUserStatus>) -> Unit) =
        onUserStatuses(keepAll(), handler)

    fun onUserStatuses(filter: PackFilter<UpdateUserStatus>, handler: suspend (List<UpdateUserStatus>) -> Unit) {
        statuses.add(PackRoute(filter, handler))
    }

    fun onUnknown(handler: suspend (List<Update>) -> Unit) =
        onUnknown(keepAll(), handler)

    fun onUnknown(filter: PackFilter<Update>, handler: suspend (List<Update>) -> Unit) {
        unknown.add(PackRoute(filter, handler))
    }

    override suspend fun handleMessage(messages: List<MessageCtor>) = dispatch(this.messages, messages)
    override suspend fun handleEdit(messages: List<MessageCtor>) = dispatch(edits, messages)
    override suspend fun handleChatMember(events: List<ChatMemberEvent>) = dispatch(members, events)
    override suspend fun handleCallback(queries: List<UpdateBotCallbackQuery>) = dispatch(callbacks, queries)
    override suspend fun handleUserStatus(updates: List<UpdateUserStatus>) = dispatch(statuses, updates)
    override suspend fun handleUnknown(updates: List<Update>) = dispatch(unknown, updates)

    fun start(scope: CoroutineScope, client: TelegramClient): Job =
        start(scope, client.incomingUpdates())

    fun start(scope: CoroutineScope, updates: Flow<Update>): Job {
        processor?.close()
        val p = PackUpdateProcessor(updates, this)
        processor = p
        return p.start(scope)
    }

    fun close() {
        processor?.close()
        processor = null
    }
}

private class PackRoute<T>(
    val filter: PackFilter<T>,
    val handler: suspend (List<T>) -> Unit,
)

private val KeepAll: PackFilter<Any?> = PackFilter { it }

@Suppress("UNCHECKED_CAST")
private fun <T> keepAll(): PackFilter<T> = KeepAll as PackFilter<T>

private suspend fun <T> dispatch(routes: ArrayList<PackRoute<T>>, batch: List<T>) {
    var remaining = batch
    for (i in routes.indices) {
        if (remaining.isEmpty()) return
        val r = routes[i]
        val taken = r.filter.take(remaining)
        if (taken.isNotEmpty()) r.handler(taken)
        remaining = withoutTaken(remaining, taken)
    }
}

private fun <T> withoutTaken(remaining: List<T>, taken: List<T>): List<T> {
    if (taken.isEmpty()) return remaining
    if (taken === remaining) return emptyList()
    return remaining.filter { item -> taken.none { it === item } }
}
