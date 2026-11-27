package dev.mycroblog.core.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * What the shared cases of [ServerAddressFixtureTest] do not hold: the scheme and port of a valid address, the case of
 * what is typed, and more addresses that are invalid.
 */
class ServerAddressTest {
    private fun parsed(text: String): ServerAddress {
        val address = ServerAddress.parse(text)
        assertNotNull("\"$text\" is a valid server address", address)
        return address!!
    }

    private fun assertInvalid(text: String) =
        assertNull("\"$text\" is not a valid server address", ServerAddress.parse(text))

    @Test
    fun aBareHostIsHttpsWithNoPort() {
        val address = parsed("example.social")
        assertEquals("https", address.scheme)
        assertNull(address.port)
    }

    @Test
    fun aPortIsKept() {
        val address = parsed("example.social:8443")
        assertEquals("https", address.scheme)
        assertEquals(8443, address.port)
    }

    @Test
    fun httpIsTheSchemeOfTheLocalHosts() {
        for (host in ServerAddress.LOCAL_HOSTS) {
            val address = parsed("http://$host:8790")
            assertEquals("http", address.scheme)
            assertEquals(8790, address.port)
        }
    }

    @Test
    fun theLocalHostsAreTheThreeOfTheTests() {
        assertEquals(setOf("localhost", "127.0.0.1", "10.0.2.2"), ServerAddress.LOCAL_HOSTS)
    }

    @Test
    fun theHostIsLowerCase() {
        val address = parsed("Example.SOCIAL")
        assertEquals("example.social", address.host)
        assertEquals("https://example.social", address.origin)
    }

    @Test
    fun theSchemeIsReadInAnyCase() {
        assertEquals("https://example.social", parsed("HTTPS://example.social").origin)
        assertEquals("http://localhost:8790", parsed("HTTP://LocalHost:8790").origin)
    }

    @Test
    fun httpIsRejectedForAnotherPrivateAddress() {
        assertInvalid("http://192.168.1.10:8790")
    }

    @Test
    fun anotherSchemeIsRejected() {
        assertInvalid("ws://example.social")
        assertInvalid("mailto:alice@example.social")
    }

    @Test
    fun textThatNamesNoHostIsRejected() {
        assertInvalid("https:///path")
        assertInvalid("not a server")
    }
}
