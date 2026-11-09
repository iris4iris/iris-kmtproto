package iris.kmtproto

import iris.kmtproto.client.RpcResponse
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.botApiChatId
import iris.kmtproto.tl.gen.MessageCtor
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.UpdateNewChannelMessage
import iris.kmtproto.tl.gen.UpdatesChannelDifferenceCtor
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.test.Test
import kotlin.test.assertEquals

class ChannelDifferenceTest {
    @Test
    fun fetchesBothPagesAndStopsAtFinal() = runBlocking {
        val client = TelegramClient(apiId = 1, apiHash = "test")
        val peer = PeerChannel(123L)
        client.storage.putAccessHash(peer.botApiChatId(), 456L)
        client.storage.putChannelPts(peer.channelId, 10)

        val messages = async(start = CoroutineStart.UNDISPATCHED) {
            client.incomingUpdates()
                .filterIsInstance<UpdateNewChannelMessage>()
                .map { (it.message as MessageCtor).id }
                .take(2)
                .toList()
        }
        val pages = listOf(
            page(peer, pts = 11, final = false),
            page(peer, pts = 12, final = true),
        )
        val requestedPts = mutableListOf<Int>()

        client.catchUpChannel(peer.channelId, ptsHint = 0) { request ->
            assertEquals(request.pts, client.storage.getChannelPts(peer.channelId))
            requestedPts += request.pts
            RpcResponse(pages[requestedPts.lastIndex], null)
        }

        assertEquals(listOf(10, 11), requestedPts)
        assertEquals(12, client.storage.getChannelPts(peer.channelId))
        assertEquals(listOf(11, 12), withTimeout(2_000) { messages.await() })
    }

    @Test
    fun replaysLiveUpdatesAfterApplyingTheDifference() = runBlocking {
        val client = TelegramClient(apiId = 1, apiHash = "test")
        val peer = PeerChannel(123L)
        client.storage.putAccessHash(peer.botApiChatId(), 456L)
        client.storage.putChannelPts(peer.channelId, 10)

        val messages = async(start = CoroutineStart.UNDISPATCHED) {
            client.incomingUpdates()
                .filterIsInstance<UpdateNewChannelMessage>()
                .map { (it.message as MessageCtor).id }
                .take(2)
                .toList()
        }
        val reply = CompletableDeferred<UpdatesChannelDifferenceCtor>()
        val catchUp = async(start = CoroutineStart.UNDISPATCHED) {
            client.catchUpChannel(peer.channelId, ptsHint = 0) { request ->
                assertEquals(10, request.pts)
                RpcResponse(reply.await(), null)
            }
        }

        // The first live message also appears in the pending reply. The second is newer.
        client.dispatch(UpdateNewChannelMessage(message(peer, 11), pts = 11, ptsCount = 1))
        client.dispatch(UpdateNewChannelMessage(message(peer, 12), pts = 12, ptsCount = 1))
        assertEquals(10, client.storage.getChannelPts(peer.channelId))

        reply.complete(page(peer, pts = 11, final = true))
        withTimeout(2_000) { catchUp.await() }

        assertEquals(12, client.storage.getChannelPts(peer.channelId))
        assertEquals(listOf(11, 12), withTimeout(2_000) { messages.await() })
    }

    private fun page(peer: PeerChannel, pts: Int, final: Boolean) = UpdatesChannelDifferenceCtor(
        pts = pts,
        newMessages = listOf(message(peer, pts)),
        otherUpdates = emptyList(),
        chats = emptyList(),
        users = emptyList(),
        final = final,
    )

    private fun message(peer: PeerChannel, id: Int) =
        MessageCtor(id = id, peerId = peer, date = 0, message = "message $id")
}
