package iris.kmtproto.bot

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class BotDecodeTest {
    @Test
    fun messageUpdateKeepsChatIdSign() {
        val raw = mapOf(
            "update_id" to 7,
            "message" to mapOf(
                "message_id" to 10,
                "date" to 1_700_000_000,
                "text" to "hello",
                "chat" to mapOf("id" to -1_000_000_000_099L, "type" to "supergroup", "title" to "Room"),
                "from" to mapOf("id" to 5, "is_bot" to false, "first_name" to "Ivan"),
            ),
        )
        val update = raw.toBotUpdate()!!
        assertEquals(7, update.updateId)
        val message = update.message!!
        assertEquals("hello", message.text)
        assertEquals(-1_000_000_000_099L, message.chat!!.id)
        assertEquals("supergroup", message.chat!!.type)
        assertEquals("Ivan", message.from!!.firstName)
        assertNull(update.channelPost)
    }

    @Test
    fun chatMemberUsesStatusNotASharedId() {
        val raw = mapOf(
            "status" to "administrator",
            "user" to mapOf("id" to 9L, "is_bot" to false, "first_name" to "Ann"),
            "can_be_edited" to true,
            "is_anonymous" to false,
            "can_manage_chat" to true,
            "can_delete_messages" to false,
            "can_manage_video_chats" to false,
            "can_restrict_members" to false,
            "can_promote_members" to false,
            "can_change_info" to false,
            "can_invite_users" to false,
            "can_post_stories" to false,
            "can_edit_stories" to false,
            "can_delete_stories" to false,
        )
        val member = chatMemberFromMap(raw)
        assertIs<ChatMemberAdministrator>(member)
        assertEquals(9L, member.user!!.id)
    }
}
