package iris.kmtproto.api.user

import iris.kmtproto.client.RpcResponse
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.InputPeer
import iris.kmtproto.tl.gen.InputSavedStarGift
import iris.kmtproto.tl.gen.InputSavedStarGiftChat
import iris.kmtproto.tl.gen.InputSavedStarGiftSlug
import iris.kmtproto.tl.gen.InputSavedStarGiftUser
import iris.kmtproto.tl.gen.PaymentsGetSavedStarGift
import iris.kmtproto.tl.gen.PaymentsGetStarGifts
import iris.kmtproto.tl.gen.PaymentsGetUniqueStarGift
import iris.kmtproto.tl.gen.PaymentsGetUniqueStarGiftValueInfo
import iris.kmtproto.tl.gen.PaymentsSavedStarGifts
import iris.kmtproto.tl.gen.PaymentsStarGifts
import iris.kmtproto.tl.gen.PaymentsStarGiftsCtor
import iris.kmtproto.tl.gen.PaymentsUniqueStarGift
import iris.kmtproto.tl.gen.PaymentsUniqueStarGiftValueInfo
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

    fun getUniqueStarGiftAsync(slug: String): Deferred<RpcResponse<PaymentsUniqueStarGift>> = client.apiAsync { getUniqueStarGift(slug) }

    suspend fun getUniqueStarGift(slug: String): RpcResponse<PaymentsUniqueStarGift> {
        val raw = client.invoke(PaymentsGetUniqueStarGift(stripNftSlug(slug)))
        raw.result?.let {
            client.rememberUsers(it.users)
            client.rememberChats(it.chats)
        }
        return raw
    }

    fun getUniqueStarGiftValueInfoAsync(slug: String): Deferred<RpcResponse<PaymentsUniqueStarGiftValueInfo>> = client.apiAsync { getUniqueStarGiftValueInfo(slug) }

    suspend fun getUniqueStarGiftValueInfo(slug: String): RpcResponse<PaymentsUniqueStarGiftValueInfo> = client.invoke(PaymentsGetUniqueStarGiftValueInfo(stripNftSlug(slug)))

    fun getSavedStarGiftAsync(stargift: List<InputSavedStarGift>): Deferred<RpcResponse<PaymentsSavedStarGifts>> = client.apiAsync { getSavedStarGift(stargift) }

    fun getSavedStarGiftAsync(vararg stargift: InputSavedStarGift): Deferred<RpcResponse<PaymentsSavedStarGifts>> = getSavedStarGiftAsync(stargift.toList())

    suspend fun getSavedStarGift(vararg stargift: InputSavedStarGift): RpcResponse<PaymentsSavedStarGifts> = getSavedStarGift(stargift.toList())

    suspend fun getSavedStarGift(stargift: List<InputSavedStarGift>): RpcResponse<PaymentsSavedStarGifts> {
        require(stargift.isNotEmpty()) { "empty stargift list" }
        val raw = client.invoke(PaymentsGetSavedStarGift(stargift))
        raw.result?.let {
            client.rememberUsers(it.users)
            client.rememberChats(it.chats)
        }
        return raw
    }

    suspend fun getSavedStarGift(msgId: Int): RpcResponse<PaymentsSavedStarGifts> = getSavedStarGift(listOf(InputSavedStarGiftUser(msgId)))

    suspend fun getSavedStarGift(slug: String): RpcResponse<PaymentsSavedStarGifts> = getSavedStarGift(listOf(InputSavedStarGiftSlug(stripNftSlug(slug))))

    suspend fun getSavedStarGift(peer: InputPeer, savedId: Long): RpcResponse<PaymentsSavedStarGifts> = getSavedStarGift(listOf(InputSavedStarGiftChat(peer, savedId)))

    suspend fun getSavedStarGift(peerId: Long, savedId: Long): RpcResponse<PaymentsSavedStarGifts> = getSavedStarGift(client.inputPeerFromId(peerId), savedId)
}

internal fun stripNftSlug(raw: String): String {
    var s = raw.trim()
    if (s.startsWith("https://", ignoreCase = true)) s = s.substring(8)
    else if (s.startsWith("http://", ignoreCase = true)) s = s.substring(7)
    s = s.trimStart('/')
    when {
        s.startsWith("t.me/nft/", ignoreCase = true) -> s = s.substring(9)
        s.startsWith("telegram.me/nft/", ignoreCase = true) -> s = s.substring(16)
    }
    return s.substringBefore('/').substringBefore('?').trim()
}
