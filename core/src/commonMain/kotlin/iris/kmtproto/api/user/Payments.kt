package iris.kmtproto.api.user

import iris.kmtproto.client.RpcResponse
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.PaymentsGetStarGifts
import iris.kmtproto.tl.gen.PaymentsStarGifts
import iris.kmtproto.tl.gen.PaymentsStarGiftsCtor
import kotlinx.coroutines.Deferred

class Payments(private val client: TelegramClient) {
    fun getStarGiftsAsync(hash: Int = 0): Deferred<RpcResponse<PaymentsStarGifts>> = client.apiAsync { getStarGifts(hash) }

    suspend fun getStarGifts(hash: Int = 0): RpcResponse<PaymentsStarGifts> {
        val raw = client.invoke(PaymentsGetStarGifts(hash))
        val gifts = raw.result
        if (gifts is PaymentsStarGiftsCtor) {
            client.rememberUsers(gifts.users)
            client.rememberChats(gifts.chats)
        }
        return raw
    }
}
