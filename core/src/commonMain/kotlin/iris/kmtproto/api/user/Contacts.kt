package iris.kmtproto.api.user

import iris.kmtproto.client.TelegramClient
import iris.kmtproto.tl.gen.ChannelsGetFullChannel
import iris.kmtproto.tl.gen.ContactsResolveUsername
import iris.kmtproto.tl.gen.ContactsResolvedPeer
import iris.kmtproto.tl.gen.InputChannelCtor
import iris.kmtproto.tl.gen.InputUserCtor
import iris.kmtproto.tl.gen.MessagesGetFullChat
import iris.kmtproto.tl.gen.PeerChannel
import iris.kmtproto.tl.gen.PeerChat
import iris.kmtproto.tl.gen.PeerUser
import iris.kmtproto.tl.gen.UsersGetFullUser
import kotlinx.coroutines.Deferred

class Contacts(private val client: TelegramClient) {
    fun resolveUsernameAsync(username: String, referer: String? = null): Deferred<ContactsResolvedPeer> = client.apiAsync { resolveUsername(username, referer) }

    suspend fun resolveUsername(username: String, referer: String? = null): ContactsResolvedPeer {
        val name = stripUsername(username)
        require(name.isNotEmpty()) { "empty username" }
        val raw = client.invoke(ContactsResolveUsername(username = name, referer = referer))
        client.rememberUsers(raw.users)
        client.rememberChats(raw.chats)
        return raw
    }

    fun resolveAsync(peerId: Long): Deferred<ContactsResolvedPeer> = client.apiAsync { resolve(peerId) }

    suspend fun resolve(peerId: Long): ContactsResolvedPeer {
        val hash = client.accessHash(peerId)
        val raw = when {
            peerId > 0L -> {
                val full = client.invoke(UsersGetFullUser(InputUserCtor(peerId, hash)))
                ContactsResolvedPeer(peer = PeerUser(peerId), chats = full.chats, users = full.users)
            }
            peerId <= -1_000_000_000_000L -> {
                val channelId = -(peerId + 1_000_000_000_000L)
                val full = client.invoke(ChannelsGetFullChannel(InputChannelCtor(channelId, hash)))
                ContactsResolvedPeer(peer = PeerChannel(channelId), chats = full.chats, users = full.users)
            }
            peerId < 0L -> {
                val full = client.invoke(MessagesGetFullChat(-peerId))
                ContactsResolvedPeer(peer = PeerChat(-peerId), chats = full.chats, users = full.users)
            }
            else -> error("peer id 0")
        }
        client.rememberUsers(raw.users)
        client.rememberChats(raw.chats)
        return raw
    }
}

internal fun stripUsername(raw: String): String {
    var s = raw.trim()
    if (s.startsWith("https://", ignoreCase = true)) s = s.substring(8)
    else if (s.startsWith("http://", ignoreCase = true)) s = s.substring(7)
    s = s.trimStart('/')
    when {
        s.startsWith("t.me/", ignoreCase = true) -> s = s.substring(5)
        s.startsWith("telegram.me/", ignoreCase = true) -> s = s.substring(12)
        s.startsWith("telegram.dog/", ignoreCase = true) -> s = s.substring(13)
    }
    s = s.substringBefore('/').substringBefore('?').trim()
    if (s.startsWith("@")) s = s.drop(1)
    return s
}
