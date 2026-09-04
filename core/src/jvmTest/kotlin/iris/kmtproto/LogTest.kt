package iris.kmtproto

import java.io.EOFException
import java.net.SocketException
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LogTest {
    @Test
    fun eofIsDisconnect() {
        assertTrue(isDisconnect(EOFException()))
        assertTrue(isDisconnect(SocketException("Connection reset")))
        assertTrue(isDisconnect(IllegalStateException("x", EOFException())))
        assertFalse(isDisconnect(IllegalStateException("unparsed constructor")))
    }
}
