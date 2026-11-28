package dev.mycroblog.core.data

import java.time.ZoneOffset
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/** [retryAtMillis] reads the Retry-After of a 429 as a number of seconds or an HTTP date (RFC 9110 §10.2.3). */
class RetryAfterTest {
    private val now = ZonedDateTime.of(2026, 11, 28, 12, 0, 0, 0, ZoneOffset.UTC).toInstant().toEpochMilli()

    @Test
    fun secondsAreFromNow() {
        assertEquals(now + 120_000, retryAtMillis("120", now))
        assertEquals(now, retryAtMillis("0", now))
        assertEquals(now + 5_000, retryAtMillis(" 5 ", now))
    }

    @Test
    fun anHttpDateIsThatTime() {
        val at = ZonedDateTime.of(2026, 11, 28, 12, 30, 15, 0, ZoneOffset.UTC).toInstant().toEpochMilli()
        assertEquals(at, retryAtMillis("Sat, 28 Nov 2026 12:30:15 GMT", now))
    }

    @Test
    fun anythingElseIsNoTime() {
        assertNull(retryAtMillis("", now))
        assertNull(retryAtMillis("soon", now))
        assertNull(retryAtMillis("-5", now))
        assertNull(retryAtMillis("1.5", now))
    }
}
