package iris.kmtproto.api.user

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.PaymentsGetStarGifts
import iris.kmtproto.tl.gen.PaymentsStarGifts
import iris.kmtproto.tl.gen.PaymentsStarGiftsCtor
import kotlinx.coroutines.Deferred

class Payments(private val client: TelegramClient) {
    fun getStarGiftsAsync(hash: Int = 0): Deferred<PaymentsStarGifts> = client.apiAsync { getStarGifts(hash) }

    suspend fun getStarGifts(hash: Int = 0): PaymentsStarGifts {
        val raw = client.invoke(PaymentsGetStarGifts(hash))
        if (raw is PaymentsStarGiftsCtor) {
            client.rememberUsers(raw.users)
            client.rememberChats(raw.chats)
        }
        return raw
    }
}
