package iris.kmtproto.events

import iris.kmtproto.logCaught
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateUserStatus
import kotlin.coroutines.cancellation.CancellationException

interface SingleEventHandler {
    suspend fun handleMessage(message: MessageCtor) {}
    suspend fun handleEdit(message: MessageCtor) {}
    suspend fun handleChatMember(event: ChatMemberEvent) {}
    suspend fun handleCallback(query: UpdateBotCallbackQuery) {}
    suspend fun handleUserStatus(update: UpdateUserStatus) {}
    suspend fun handleUnknown(update: Update) {}
}

class OneSingleEventHandler(
    val filter: SingleEventFilter,
    val handler: SingleEventHandler,
) : SingleEventHandler {
    constructor(handler: SingleEventHandler) : this(AcceptAllSingle, handler)

    override suspend fun handleMessage(message: MessageCtor) {
        if (!filter.filterMessage(message)) return
        handler.handleMessage(message)
    }

    override suspend fun handleEdit(message: MessageCtor) {
        if (!filter.filterEdit(message)) return
        handler.handleEdit(message)
    }

    override suspend fun handleChatMember(event: ChatMemberEvent) {
        if (!filter.filterChatMember(event)) return
        handler.handleChatMember(event)
    }

    override suspend fun handleCallback(query: UpdateBotCallbackQuery) {
        if (!filter.filterCallback(query)) return
        handler.handleCallback(query)
    }

    override suspend fun handleUserStatus(update: UpdateUserStatus) {
        if (!filter.filterUserStatus(update)) return
        handler.handleUserStatus(update)
    }

    override suspend fun handleUnknown(update: Update) {
        if (!filter.filterUnknown(update)) return
        handler.handleUnknown(update)
    }
}

class ArraySingleEventHandler(
    val filters: Array<SingleEventFilter> = emptyArray(),
    val handlers: Array<SingleEventHandler> = emptyArray(),
) : SingleEventHandler {
    override suspend fun handleMessage(message: MessageCtor) {
        for (i in filters.indices) if (!filters[i].filterMessage(message)) return
        for (i in handlers.indices) guard("single-message") { handlers[i].handleMessage(message) }
    }

    override suspend fun handleEdit(message: MessageCtor) {
        for (i in filters.indices) if (!filters[i].filterEdit(message)) return
        for (i in handlers.indices) guard("single-edit") { handlers[i].handleEdit(message) }
    }

    override suspend fun handleChatMember(event: ChatMemberEvent) {
        for (i in filters.indices) if (!filters[i].filterChatMember(event)) return
        for (i in handlers.indices) guard("single-member") { handlers[i].handleChatMember(event) }
    }

    override suspend fun handleCallback(query: UpdateBotCallbackQuery) {
        for (i in filters.indices) if (!filters[i].filterCallback(query)) return
        for (i in handlers.indices) guard("single-callback") { handlers[i].handleCallback(query) }
    }

    override suspend fun handleUserStatus(update: UpdateUserStatus) {
        for (i in filters.indices) if (!filters[i].filterUserStatus(update)) return
        for (i in handlers.indices) guard("single-status") { handlers[i].handleUserStatus(update) }
    }

    override suspend fun handleUnknown(update: Update) {
        for (i in filters.indices) if (!filters[i].filterUnknown(update)) return
        for (i in handlers.indices) guard("single-unknown") { handlers[i].handleUnknown(update) }
    }
}

class ListSingleEventHandler(
    val filters: List<SingleEventFilter> = emptyList(),
    val handlers: List<SingleEventHandler> = emptyList(),
) : SingleEventHandler {
    override suspend fun handleMessage(message: MessageCtor) {
        for (f in filters) if (!f.filterMessage(message)) return
        for (h in handlers) guard("single-message") { h.handleMessage(message) }
    }

    override suspend fun handleEdit(message: MessageCtor) {
        for (f in filters) if (!f.filterEdit(message)) return
        for (h in handlers) guard("single-edit") { h.handleEdit(message) }
    }

    override suspend fun handleChatMember(event: ChatMemberEvent) {
        for (f in filters) if (!f.filterChatMember(event)) return
        for (h in handlers) guard("single-member") { h.handleChatMember(event) }
    }

    override suspend fun handleCallback(query: UpdateBotCallbackQuery) {
        for (f in filters) if (!f.filterCallback(query)) return
        for (h in handlers) guard("single-callback") { h.handleCallback(query) }
    }

    override suspend fun handleUserStatus(update: UpdateUserStatus) {
        for (f in filters) if (!f.filterUserStatus(update)) return
        for (h in handlers) guard("single-status") { h.handleUserStatus(update) }
    }

    override suspend fun handleUnknown(update: Update) {
        for (f in filters) if (!f.filterUnknown(update)) return
        for (h in handlers) guard("single-unknown") { h.handleUnknown(update) }
    }
}

internal suspend inline fun guard(where: String, block: suspend () -> Unit) {
    try {
        block()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        logCaught(where, e)
    }
}
