package dev.mycroblog.core.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * [ServerAddress.parse] reads each case of shared/fixtures/server-address/cases.json as both apps must: the origin and
 * host it names, or none (null) for an invalid address.
 */
class ServerAddressFixtureTest {
    private data class Case(val input: String, val origin: String?, val host: String?)

    private val cases = Json.parseToJsonElement(Fixtures.read("server-address/cases.json")).jsonArray.map { element ->
        val case = element.jsonObject
        fun text(key: String) = case.getValue(key).takeUnless { it is JsonNull }?.jsonPrimitive?.content
        Case(case.getValue("input").jsonPrimitive.content, text("origin"), text("host"))
    }

    @Test
    fun everyCase() {
        val failures = cases.mapNotNull { case ->
            val address = ServerAddress.parse(case.input)
            val actual = Case(case.input, address?.origin, address?.host)
            if (actual == case) null else "\"${case.input}\": expected $case, but parsed $actual"
        }
        assertEquals(
            "the cases of server-address/cases.json that differ:\n" + failures.joinToString("\n"),
            0,
            failures.size,
        )
    }
}
