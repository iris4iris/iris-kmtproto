package iris.kmtproto.events

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateUserStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

/**
 * Table of (filter, handler) per kind. First matching route runs, the rest do not.
 * Plug into [SingleUpdateProcessor] or call [start].
 */
class SingleEventRouter : SingleEventHandler {
    private val messages = ArrayList<Route<MessageCtor>>()
    private val edits = ArrayList<Route<MessageCtor>>()
    private val members = ArrayList<Route<ChatMemberEvent>>()
    private val callbacks = ArrayList<Route<UpdateBotCallbackQuery>>()
    private val statuses = ArrayList<Route<UpdateUserStatus>>()
    private val unknown = ArrayList<Route<Update>>()
    private var processor: SingleUpdateProcessor? = null

    fun onMessage(handler: suspend (MessageCtor) -> Unit) =
        onMessage(always(), handler)

    fun onMessage(filter: EventFilter<MessageCtor>, handler: suspend (MessageCtor) -> Unit) {
        messages.add(Route(filter, handler))
    }

    fun onEdit(handler: suspend (MessageCtor) -> Unit) =
        onEdit(always(), handler)

    fun onEdit(filter: EventFilter<MessageCtor>, handler: suspend (MessageCtor) -> Unit) {
        edits.add(Route(filter, handler))
    }

    fun onChatMember(handler: suspend (ChatMemberEvent) -> Unit) =
        onChatMember(always(), handler)

    fun onChatMember(filter: EventFilter<ChatMemberEvent>, handler: suspend (ChatMemberEvent) -> Unit) {
        members.add(Route(filter, handler))
    }

    fun onCallback(handler: suspend (UpdateBotCallbackQuery) -> Unit) =
        onCallback(always(), handler)

    fun onCallback(filter: EventFilter<UpdateBotCallbackQuery>, handler: suspend (UpdateBotCallbackQuery) -> Unit) {
        callbacks.add(Route(filter, handler))
    }

    fun onUserStatus(handler: suspend (UpdateUserStatus) -> Unit) =
        onUserStatus(always(), handler)

    fun onUserStatus(filter: EventFilter<UpdateUserStatus>, handler: suspend (UpdateUserStatus) -> Unit) {
        statuses.add(Route(filter, handler))
    }

    fun onUnknown(handler: suspend (Update) -> Unit) =
        onUnknown(always(), handler)

    fun onUnknown(filter: EventFilter<Update>, handler: suspend (Update) -> Unit) {
        unknown.add(Route(filter, handler))
    }

    override suspend fun handleMessage(message: MessageCtor) = dispatch(messages, message)
    override suspend fun handleEdit(message: MessageCtor) = dispatch(edits, message)
    override suspend fun handleChatMember(event: ChatMemberEvent) = dispatch(members, event)
    override suspend fun handleCallback(query: UpdateBotCallbackQuery) = dispatch(callbacks, query)
    override suspend fun handleUserStatus(update: UpdateUserStatus) = dispatch(statuses, update)
    override suspend fun handleUnknown(update: Update) = dispatch(unknown, update)

    suspend fun start(client: TelegramClient) {
        coroutineScope { start(this, client) }
    }

    suspend fun start(updates: Flow<Update>) {
        coroutineScope { start(this, updates) }
    }

    fun start(scope: CoroutineScope, client: TelegramClient): Job =
        start(scope, client.incomingUpdates())

    fun start(scope: CoroutineScope, updates: Flow<Update>): Job {
        processor?.close()
        val p = SingleUpdateProcessor(updates, this)
        processor = p
        return p.start(scope)
    }

    fun close() {
        processor?.close()
        processor = null
    }
}

private class Route<T>(
    val filter: EventFilter<T>,
    val handler: suspend (T) -> Unit,
)

private val Always: EventFilter<Any?> = EventFilter { true }

@Suppress("UNCHECKED_CAST")
private fun <T> always(): EventFilter<T> = Always as EventFilter<T>

private suspend fun <T> dispatch(routes: ArrayList<Route<T>>, value: T) {
    for (i in routes.indices) {
        val r = routes[i]
        if (r.filter.test(value)) {
            r.handler(value)
            return
        }
    }
}
