package iris.kmtproto.events

import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateBotCallbackQuery
import iris.kmtproto.tl.gen.UpdateUserStatus

interface PackEventHandler {
    suspend fun handleMessage(messages: List<MessageCtor>) {}
    suspend fun handleEdit(messages: List<MessageCtor>) {}
    suspend fun handleChatMember(events: List<ChatMemberEvent>) {}
    suspend fun handleCallback(queries: List<UpdateBotCallbackQuery>) {}
    suspend fun handleUserStatus(updates: List<UpdateUserStatus>) {}
    suspend fun handleUnknown(updates: List<Update>) {}
}

class OnePackEventHandler(
    val filter: PackEventFilter,
    val handler: PackEventHandler,
) : PackEventHandler {
    constructor(handler: PackEventHandler) : this(KeepAllPack, handler)

    override suspend fun handleMessage(messages: List<MessageCtor>) {
        val next = filter.filterMessage(messages)
        if (next.isNotEmpty()) handler.handleMessage(next)
    }

    override suspend fun handleEdit(messages: List<MessageCtor>) {
        val next = filter.filterEdit(messages)
        if (next.isNotEmpty()) handler.handleEdit(next)
    }

    override suspend fun handleChatMember(events: List<ChatMemberEvent>) {
        val next = filter.filterChatMember(events)
        if (next.isNotEmpty()) handler.handleChatMember(next)
    }

    override suspend fun handleCallback(queries: List<UpdateBotCallbackQuery>) {
        val next = filter.filterCallback(queries)
        if (next.isNotEmpty()) handler.handleCallback(next)
    }

    override suspend fun handleUserStatus(updates: List<UpdateUserStatus>) {
        val next = filter.filterUserStatus(updates)
        if (next.isNotEmpty()) handler.handleUserStatus(next)
    }

    override suspend fun handleUnknown(updates: List<Update>) {
        val next = filter.filterUnknown(updates)
        if (next.isNotEmpty()) handler.handleUnknown(next)
    }
}

class ArrayPackEventHandler(
    val filters: Array<PackEventFilter> = emptyArray(),
    val handlers: Array<PackEventHandler> = emptyArray(),
) : PackEventHandler {
    override suspend fun handleMessage(messages: List<MessageCtor>) {
        val next = applyFilters(messages) { f, cur -> f.filterMessage(cur) } ?: return
        for (i in handlers.indices) guard("pack-message") { handlers[i].handleMessage(next) }
    }

    override suspend fun handleEdit(messages: List<MessageCtor>) {
        val next = applyFilters(messages) { f, cur -> f.filterEdit(cur) } ?: return
        for (i in handlers.indices) guard("pack-edit") { handlers[i].handleEdit(next) }
    }

    override suspend fun handleChatMember(events: List<ChatMemberEvent>) {
        val next = applyFilters(events) { f, cur -> f.filterChatMember(cur) } ?: return
        for (i in handlers.indices) guard("pack-member") { handlers[i].handleChatMember(next) }
    }

    override suspend fun handleCallback(queries: List<UpdateBotCallbackQuery>) {
        val next = applyFilters(queries) { f, cur -> f.filterCallback(cur) } ?: return
        for (i in handlers.indices) guard("pack-callback") { handlers[i].handleCallback(next) }
    }

    override suspend fun handleUserStatus(updates: List<UpdateUserStatus>) {
        val next = applyFilters(updates) { f, cur -> f.filterUserStatus(cur) } ?: return
        for (i in handlers.indices) guard("pack-status") { handlers[i].handleUserStatus(next) }
    }

    override suspend fun handleUnknown(updates: List<Update>) {
        val next = applyFilters(updates) { f, cur -> f.filterUnknown(cur) } ?: return
        for (i in handlers.indices) guard("pack-unknown") { handlers[i].handleUnknown(next) }
    }

    private suspend inline fun <T> applyFilters(
        start: List<T>,
        crossinline filter: suspend (PackEventFilter, List<T>) -> List<T>,
    ): List<T>? {
        var current = start
        if (current.isEmpty()) return null
        for (i in filters.indices) {
            current = filter(filters[i], current)
            if (current.isEmpty()) return null
        }
        return current
    }
}

class ListPackEventHandler(
    val filters: List<PackEventFilter> = emptyList(),
    val handlers: List<PackEventHandler> = emptyList(),
) : PackEventHandler {
    override suspend fun handleMessage(messages: List<MessageCtor>) {
        val next = applyFilters(messages) { f, cur -> f.filterMessage(cur) } ?: return
        for (h in handlers) guard("pack-message") { h.handleMessage(next) }
    }

    override suspend fun handleEdit(messages: List<MessageCtor>) {
        val next = applyFilters(messages) { f, cur -> f.filterEdit(cur) } ?: return
        for (h in handlers) guard("pack-edit") { h.handleEdit(next) }
    }

    override suspend fun handleChatMember(events: List<ChatMemberEvent>) {
        val next = applyFilters(events) { f, cur -> f.filterChatMember(cur) } ?: return
        for (h in handlers) guard("pack-member") { h.handleChatMember(next) }
    }

    override suspend fun handleCallback(queries: List<UpdateBotCallbackQuery>) {
        val next = applyFilters(queries) { f, cur -> f.filterCallback(cur) } ?: return
        for (h in handlers) guard("pack-callback") { h.handleCallback(next) }
    }

    override suspend fun handleUserStatus(updates: List<UpdateUserStatus>) {
        val next = applyFilters(updates) { f, cur -> f.filterUserStatus(cur) } ?: return
        for (h in handlers) guard("pack-status") { h.handleUserStatus(next) }
    }

    override suspend fun handleUnknown(updates: List<Update>) {
        val next = applyFilters(updates) { f, cur -> f.filterUnknown(cur) } ?: return
        for (h in handlers) guard("pack-unknown") { h.handleUnknown(next) }
    }

    private suspend inline fun <T> applyFilters(
        start: List<T>,
        crossinline filter: suspend (PackEventFilter, List<T>) -> List<T>,
    ): List<T>? {
        var current = start
        if (current.isEmpty()) return null
        for (f in filters) {
            current = filter(f, current)
            if (current.isEmpty()) return null
        }
        return current
    }
}
