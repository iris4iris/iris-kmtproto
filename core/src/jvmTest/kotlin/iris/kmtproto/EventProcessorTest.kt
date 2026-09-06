package iris.kmtproto

import iris.kmtproto.events.ArrayPackEventHandler
import iris.kmtproto.events.ArraySingleEventHandler
import iris.kmtproto.events.ChatMemberEvent
import iris.kmtproto.events.ListSingleEventHandler
import iris.kmtproto.events.OneSingleEventHandler
import iris.kmtproto.events.PackEventFilter
import iris.kmtproto.events.PackEventHandler
import iris.kmtproto.events.PackUpdateProcessor
import iris.kmtproto.events.SingleEventFilter
import iris.kmtproto.events.SingleEventHandler
import iris.kmtproto.events.SingleUpdateProcessor
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.Update
import iris.kmtproto.tl.gen.UpdateChatParticipantAdd
import iris.kmtproto.tl.gen.UpdateDeleteMessages
import iris.kmtproto.tl.gen.UpdateNewMessage
import iris.kmtproto.tl.gen.UpdateUserStatus
import iris.kmtproto.tl.gen.UserStatusOffline
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EventProcessorTest {
    @Test
    fun singleDeliversToEveryArrayHandler() = eventsTest { scope ->
        val seen = mutableListOf<Int>()
        val done = CompletableDeferred<Unit>()
        val h1 = object : SingleEventHandler {
            override suspend fun handleMessage(message: MessageCtor) {
                seen += 1
            }
        }
        val h2 = object : SingleEventHandler {
            override suspend fun handleMessage(message: MessageCtor) {
                seen += 2
                done.complete(Unit)
            }
        }
        val src = newSrc()
        val p = SingleUpdateProcessor(src, ArraySingleEventHandler(handlers = arrayOf(h1, h2)))
        p.start(scope)
        src.awaitSubscriber()
        src.emit(newMsg("hi"))
        done.await()
        p.close()
        assertEquals(listOf(1, 2), seen)
    }

    @Test
    fun singleFilterStopsHandler() = eventsTest { scope ->
        val seen = mutableListOf<String>()
        val done = CompletableDeferred<Unit>()
        val filter = object : SingleEventFilter {
            override suspend fun filterMessage(message: MessageCtor) = message.message.isNotEmpty()
        }
        val handler = object : SingleEventHandler {
            override suspend fun handleMessage(message: MessageCtor) {
                seen += message.message
            }

            override suspend fun handleUnknown(update: Update) {
                done.complete(Unit)
            }
        }
        val src = newSrc()
        val p = SingleUpdateProcessor(src, OneSingleEventHandler(filter, handler))
        p.start(scope)
        src.awaitSubscriber()
        src.emit(newMsg(""))
        src.emit(newMsg("ok"))
        src.emit(UpdateDeleteMessages(intArrayOf(1), pts = 1, ptsCount = 1))
        done.await()
        p.close()
        assertEquals(listOf("ok"), seen)
    }

    @Test
    fun listFiltersAreAnd() = eventsTest { scope ->
        val seen = mutableListOf<String>()
        val done = CompletableDeferred<Unit>()
        val notEmpty = object : SingleEventFilter {
            override suspend fun filterMessage(message: MessageCtor) = message.message.isNotEmpty()
        }
        val notHi = object : SingleEventFilter {
            override suspend fun filterMessage(message: MessageCtor) = message.message != "hi"
        }
        val handler = object : SingleEventHandler {
            override suspend fun handleMessage(message: MessageCtor) {
                seen += message.message
            }

            override suspend fun handleUnknown(update: Update) {
                done.complete(Unit)
            }
        }
        val src = newSrc()
        val p = SingleUpdateProcessor(
            src,
            ListSingleEventHandler(filters = listOf(notEmpty, notHi), handlers = listOf(handler)),
        )
        p.start(scope)
        src.awaitSubscriber()
        src.emit(newMsg(""))
        src.emit(newMsg("hi"))
        src.emit(newMsg("yo"))
        src.emit(UpdateDeleteMessages(intArrayOf(1), pts = 1, ptsCount = 1))
        done.await()
        p.close()
        assertEquals(listOf("yo"), seen)
    }

    @Test
    fun chatMemberFromParticipantAdd() = eventsTest { scope ->
        val got = CompletableDeferred<ChatMemberEvent>()
        val handler = object : SingleEventHandler {
            override suspend fun handleChatMember(event: ChatMemberEvent) {
                got.complete(event)
            }
        }
        val src = newSrc()
        val p = SingleUpdateProcessor(src, handler)
        p.start(scope)
        src.awaitSubscriber()
        src.emit(UpdateChatParticipantAdd(chatId = 10, userId = 20, inviterId = 30, date = 9, version = 1))
        val e = got.await()
        p.close()
        assertEquals(10, e.chatId)
        assertEquals(20, e.userId)
        assertEquals(30, e.actorId)
        assertTrue(e.joined)
        assertTrue(!e.left)
    }

    @Test
    fun packGroupsByTypeAndWaitsBeforeNextBatch() = eventsTest { scope ->
        val firstStarted = CompletableDeferred<Unit>()
        val firstMayFinish = CompletableDeferred<Unit>()
        val secondDone = CompletableDeferred<Int>()
        val sizes = mutableListOf<Int>()
        val handler = object : PackEventHandler {
            override suspend fun handleMessage(messages: List<MessageCtor>) {
                sizes += messages.size
                if (sizes.size == 1) {
                    firstStarted.complete(Unit)
                    firstMayFinish.await()
                } else {
                    secondDone.complete(messages.size)
                }
            }
        }
        val src = newSrc()
        val p = PackUpdateProcessor(src, handler)
        p.start(scope)
        src.awaitSubscriber()
        src.emit(newMsg("a"))
        firstStarted.await()
        src.emit(newMsg("b"))
        src.emit(newMsg("c"))
        firstMayFinish.complete(Unit)
        val second = secondDone.await()
        p.close()
        assertEquals(listOf(1, 2), sizes)
        assertEquals(2, second)
    }

    @Test
    fun packFilterShrinksList() = eventsTest { scope ->
        val got = CompletableDeferred<List<String>>()
        val onlyYo = object : PackEventFilter {
            override suspend fun filterMessage(messages: List<MessageCtor>) =
                messages.filter { it.message == "yo" }
        }
        val handler = object : PackEventHandler {
            override suspend fun handleMessage(messages: List<MessageCtor>) {
                got.complete(messages.map { it.message })
            }
        }
        val src = newSrc()
        val p = PackUpdateProcessor(src, ArrayPackEventHandler(filters = arrayOf(onlyYo), handlers = arrayOf(handler)))
        p.start(scope)
        src.awaitSubscriber()
        src.emit(newMsg("hi"))
        src.emit(newMsg("yo"))
        src.emit(newMsg("no"))
        val texts = got.await()
        p.close()
        assertEquals(listOf("yo"), texts)
    }

    @Test
    fun packSplitsMessageAndStatus() = eventsTest { scope ->
        var msgCount = 0
        var stCount = 0
        val done = CompletableDeferred<Unit>()
        val handler = object : PackEventHandler {
            override suspend fun handleMessage(messages: List<MessageCtor>) {
                msgCount += messages.size
                if (msgCount >= 2 && stCount >= 2) done.complete(Unit)
            }

            override suspend fun handleUserStatus(updates: List<UpdateUserStatus>) {
                stCount += updates.size
                if (msgCount >= 2 && stCount >= 2) done.complete(Unit)
            }
        }
        val src = newSrc()
        val p = PackUpdateProcessor(src, handler)
        p.start(scope)
        src.awaitSubscriber()
        src.emit(newMsg("a"))
        src.emit(UpdateUserStatus(1, UserStatusOffline(0)))
        src.emit(newMsg("b"))
        src.emit(UpdateUserStatus(2, UserStatusOffline(0)))
        done.await()
        p.close()
        assertEquals(2, msgCount)
        assertEquals(2, stCount)
    }
}

fun main() {
    EventProcessorTest().run {
        singleDeliversToEveryArrayHandler()
        singleFilterStopsHandler()
        listFiltersAreAnd()
        chatMemberFromParticipantAdd()
        packGroupsByTypeAndWaitsBeforeNextBatch()
        packFilterShrinksList()
        packSplitsMessageAndStatus()
    }
    println("EventProcessorTest ok")
}

/**
 * extraBufferCapacity = 0: [MutableSharedFlow.emit] waits for the collector instead of
 * silently dropping when nobody is subscribed yet (replay = 0).
 */
private fun newSrc() = MutableSharedFlow<Update>()

private suspend fun MutableSharedFlow<*>.awaitSubscriber() {
    subscriptionCount.first { it > 0 }
}

/**
 * Processor collect is infinite — do not attach it to [runBlocking]. Close + cancel in finally
 * so a hung await fails via [withTimeout] instead of sitting until the Gradle worker dies.
 */
private fun eventsTest(test: suspend (CoroutineScope) -> Unit) {
    val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    try {
        runBlocking {
            withTimeout(8_000) { test(scope) }
        }
    } finally {
        scope.cancel()
    }
}

private fun newMsg(text: String, id: Int = text.hashCode()) = UpdateNewMessage(
    message = MessageCtor(
        id = id,
        peerId = PeerUser(42),
        date = 1,
        message = text,
        fromId = PeerUser(42),
    ),
    pts = 1,
    ptsCount = 1,
)
