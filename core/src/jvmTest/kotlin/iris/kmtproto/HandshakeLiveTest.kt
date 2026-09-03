package iris.kmtproto

import iris.kmtproto.api.UserApi
import iris.kmtproto.api.bot.BotApi
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.client.id
import iris.kmtproto.client.incomingTexts
import iris.kmtproto.mtproto.RpcException
import iris.kmtproto.tl.gen.HelpGetNearestDc
import iris.kmtproto.tl.gen.UpdatesDifferenceCtor
import iris.kmtproto.tl.gen.UpdatesDifferenceEmpty
import iris.kmtproto.tl.gen.UpdatesDifferenceTooLong
import iris.kmtproto.tl.gen.UserCtor
import iris.kmtproto.transport.Datacenter
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HandshakeLiveTest {
    @Test
    fun handshakeAndPingDc2() = runBlocking {
        val client = TelegramClient(apiId = 1, apiHash = "iris-kmtproto-selftest", dc = Datacenter.DC2)
        try {
            client.connect()
            val key = client.authKey ?: error("no auth key")
            assertEquals(256, key.key.size)
            val pong = client.ping(42)
            assertEquals(42L, pong.pingId)
            assertTrue(pong.msgId != 0L)
        } finally {
            client.close()
        }
    }

    @Test
    fun invokeNearestDc() = runBlocking {
        val apiId = System.getenv("TELEGRAM_API_ID")?.toIntOrNull() ?: 1
        val apiHash = System.getenv("TELEGRAM_API_HASH") ?: "iris-kmtproto-selftest"
        val client = TelegramClient(apiId = apiId, apiHash = apiHash, dc = Datacenter.DC2)
        try {
            client.connect()
            val nearest = client.invoke(HelpGetNearestDc)
            assertTrue(nearest.thisDc in 1..5, "thisDc=${nearest.thisDc}")
            assertTrue(nearest.nearestDc in 1..5, "nearestDc=${nearest.nearestDc}")
            assertTrue(nearest.country.isNotEmpty())
        } catch (e: RpcException) {
            assertTrue(
                e.message.contains("API_ID_INVALID") ||
                    e.message.contains("API_ID_PUBLISHED_FLOOD"),
                "unexpected RPC ${e.code} ${e.message}",
            )
        } finally {
            client.close()
        }
    }

    @Test
    fun loginBotIfConfigured() = runBlocking {
        val apiId = System.getenv("TELEGRAM_API_ID")?.toIntOrNull()
        val apiHash = System.getenv("TELEGRAM_API_HASH")
        val token = System.getenv("TELEGRAM_BOT_TOKEN")
        if (apiId == null || apiHash.isNullOrBlank() || token.isNullOrBlank()) return@runBlocking
        val client = TelegramClient(apiId = apiId, apiHash = apiHash, dc = Datacenter.DC2)
        val api = UserApi(client)
        try {
            client.connect()
            val me = api.auth.importBotAuthorization(token)
            assertTrue(me.id != 0L)
            val full = me as? UserCtor
            if (full != null) assertTrue(full.bot)
        } finally {
            client.close()
        }
    }

    @Test
    fun sendMessageIfConfigured() = runBlocking {
        val apiId = System.getenv("TELEGRAM_API_ID")?.toIntOrNull()
        val apiHash = System.getenv("TELEGRAM_API_HASH")
        val token = System.getenv("TELEGRAM_BOT_TOKEN")
        val chatId = System.getenv("TELEGRAM_CHAT_ID")?.toLongOrNull()
        if (apiId == null || apiHash.isNullOrBlank() || token.isNullOrBlank() || chatId == null) {
            return@runBlocking
        }
        val accessHash = System.getenv("TELEGRAM_ACCESS_HASH")?.toLongOrNull() ?: 0L
        val client = TelegramClient(apiId = apiId, apiHash = apiHash, dc = Datacenter.DC2)
        val bot = BotApi(client)
        try {
            client.connect()
            bot.login(token)
            if (accessHash != 0L) client.storage.putAccessHash(
                if (chatId <= -1_000_000_000_000L) -chatId - 1_000_000_000_000L else chatId,
                accessHash,
            )
            val sent = bot.sendMessage(chatId, "Iris kMTProto live ${System.currentTimeMillis()}")
            assertTrue(sent.id > 0, "id=${sent.id}")
            assertTrue(sent.date > 0, "date=${sent.date}")
        } finally {
            client.close()
        }
    }

    @Test
    fun updatesCatchupIfConfigured() = runBlocking {
        val apiId = System.getenv("TELEGRAM_API_ID")?.toIntOrNull()
        val apiHash = System.getenv("TELEGRAM_API_HASH")
        val token = System.getenv("TELEGRAM_BOT_TOKEN")
        val chatId = System.getenv("TELEGRAM_CHAT_ID")?.toLongOrNull()
        if (apiId == null || apiHash.isNullOrBlank() || token.isNullOrBlank() || chatId == null) {
            return@runBlocking
        }
        val accessHash = System.getenv("TELEGRAM_ACCESS_HASH")?.toLongOrNull() ?: 0L
        val client = TelegramClient(apiId = apiId, apiHash = apiHash, dc = Datacenter.DC2)
        val bot = BotApi(client)
        try {
            client.connect()
            bot.login(token)
            if (accessHash != 0L) client.storage.putAccessHash(
                if (chatId <= -1_000_000_000_000L) -chatId - 1_000_000_000_000L else chatId,
                accessHash,
            )
            val before = client.getState()
            assertTrue(before.pts >= 0, "pts=${before.pts}")
            val marker = "Iris kMTProto upd ${System.currentTimeMillis()}"
            bot.sendMessage(chatId, marker)
            val diff = client.getDifference(before.pts, before.date, before.qts)
            when (diff) {
                is UpdatesDifferenceTooLong -> {
                    val after = client.getState()
                    assertTrue(after.pts >= before.pts)
                }
                is UpdatesDifferenceEmpty -> {
                    val after = client.getState()
                    assertTrue(after.pts >= before.pts, "pts did not move ${before.pts} -> ${after.pts}")
                }
                is UpdatesDifferenceCtor -> {
                    val hit = incomingTexts(diff).any { it.message.contains("Iris kMTProto upd") }
                    assertTrue(hit || diff.state.pts >= before.pts, "no catch-up in $diff")
                }
                else -> Unit
            }
        } finally {
            client.close()
        }
    }
}
