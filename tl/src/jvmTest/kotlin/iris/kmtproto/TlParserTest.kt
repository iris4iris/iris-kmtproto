package iris.kmtproto

import iris.kmtproto.tl.API_LAYER
import iris.kmtproto.tlgen.TlCondition
import iris.kmtproto.tlgen.TlParser
import iris.kmtproto.tlgen.TlType
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TlParserTest {
    private val api by lazy { TlParser.parse(findSchema("api.tl").readText()) }
    private val mtproto by lazy { TlParser.parse(findSchema("mtproto.tl").readText()) }

    @Test
    fun apiLayerIs229() {
        assertEquals(229, api.layer)
        assertEquals(API_LAYER, api.layer)
    }

    @Test
    fun countsMatchSchema() {
        assertEquals(1658, api.types.size)
        assertEquals(813, api.functions.size)
        assertTrue(api.combinators.all { it.id != null })
    }

    @Test
    fun boolTrue() {
        val c = api.byName("boolTrue")!!
        assertEquals(0x997275b5.toInt(), c.id)
        assertEquals(emptyList(), c.params)
        assertEquals(TlType.Named("Bool"), c.result)
        assertFalse(c.function)
    }

    @Test
    fun vectorBuiltin() {
        val c = api.byName("vector")!!
        assertTrue(c.vectorBuiltin)
        assertEquals(listOf("t"), c.generics)
        assertEquals(TlType.Vector(TlType.Named("t"), boxed = true), c.result)
    }

    @Test
    fun updateShort() {
        val c = api.byName("updateShort")!!
        assertEquals(0x78d4dec1, c.id)
        assertEquals(listOf("update", "date"), c.params.map { it.name })
        assertEquals(TlType.Named("Update"), c.params[0].type)
        assertEquals(TlType.Named("int"), c.params[1].type)
        assertEquals(TlType.Named("Updates"), c.result)
    }

    @Test
    fun messageFwdFromIsOptional() {
        val c = api.byName("message")!!
        assertEquals(0x7600b9d3, c.id)
        val flags = c.params.first { it.name == "flags" }
        assertEquals(TlType.Flags, flags.type)
        val flags2 = c.params.first { it.name == "flags2" }
        assertEquals(TlType.Flags, flags2.type)
        val fwd = c.params.first { it.name == "fwd_from" }
        assertEquals(TlType.Named("MessageFwdHeader"), fwd.type)
        assertEquals(TlCondition("flags", 2), fwd.condition)
        val out = c.params.first { it.name == "out" }
        assertEquals(TlType.True, out.type)
        assertEquals(TlCondition("flags", 1), out.condition)
        val fromRank = c.params.first { it.name == "from_rank" }
        assertEquals(TlCondition("flags2", 12), fromRank.condition)
        assertEquals(TlType.Named("string"), fromRank.type)
    }

    @Test
    fun userHasFlagsAndId() {
        val c = api.byName("user")!!
        assertNotNull(c.id)
        assertEquals(TlType.Named("User"), c.result)
        assertTrue(c.params.any { it.name == "id" && it.type == TlType.Named("long") })
        assertTrue(c.params.any { it.name == "self" && it.type == TlType.True })
    }

    @Test
    fun invokeWithLayerIsFunction() {
        val c = api.byName("invokeWithLayer")!!
        assertTrue(c.function)
        assertEquals(listOf("X"), c.generics)
        assertEquals("layer", c.params[0].name)
        assertEquals(TlType.Named("int"), c.params[0].type)
        assertEquals(TlType.Bang(TlType.Named("X")), c.params[1].type)
        assertEquals(TlType.Named("X"), c.result)
    }

    @Test
    fun mtprotoHandshakeAndPing() {
        val resPq = mtproto.byName("resPQ")!!
        assertEquals(0x05162463, resPq.id)
        assertFalse(resPq.function)
        val ping = mtproto.byName("ping")!!
        assertTrue(ping.function)
        assertEquals(0x7abe77ec.toInt(), ping.id)
        assertEquals(TlType.Named("Pong"), ping.result)
        assertNull(mtproto.layer)
        val vec = mtproto.byName("vector")
        assertTrue(vec == null || vec.vectorBuiltin)
    }
}

private fun findSchema(name: String): File {
    val paths = listOf(
        "schema/$name",
        "tl/schema/$name",
        "kmtproto/schema/$name",
        "kmtproto/tl/schema/$name",
    )
    val roots = listOf(File("."), File(".."), File(System.getProperty("user.dir")))
    for (root in roots) {
        for (p in paths) {
            val f = File(root, p)
            if (f.isFile) return f.canonicalFile
        }
    }
    error("schema/$name not found")
}
