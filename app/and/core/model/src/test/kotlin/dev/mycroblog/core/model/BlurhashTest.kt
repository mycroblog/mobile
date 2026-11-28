package dev.mycroblog.core.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** [Blurhash.decode] gives the placeholder that sensitive media stay behind, or none for a hash it cannot read. */
class BlurhashTest {
    // The blurhash of the sensitive image of shared/fixtures/timeline-page/response.json.
    private val hash = "LEHV6nWB2yk8pyo0adR*.7kCMdnj"

    @Test
    fun aValidHashIsOneOpaquePixelPerPoint() {
        val pixels = Blurhash.decode(hash, width = 8, height = 6)
        assertNotNull("the blurhash $hash did not decode", pixels)
        assertEquals(8 * 6, pixels!!.size)
        assertTrue("a decoded pixel is not opaque", pixels.all { it ushr 24 == 0xFF })
        assertTrue("every decoded pixel has the same colour", pixels.toSet().size > 1)
    }

    @Test
    fun anInvalidHashIsNone() {
        assertNull(Blurhash.decode("", 4, 3))
        assertNull(Blurhash.decode("LEHV6", 4, 3))
        assertNull(Blurhash.decode("LあHV6nWB2yk8pyo0adR*.7kCMdnj", 4, 3))
    }
}
