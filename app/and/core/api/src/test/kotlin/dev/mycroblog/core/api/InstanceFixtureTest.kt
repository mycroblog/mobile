package dev.mycroblog.core.api

import dev.mycroblog.core.api.models.Instance
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Test

/** The generated model reads the shared fixture of `GET /api/v1/instance` as shared/fixtures/instance/expected.json says. */
class InstanceFixtureTest {
    private val instance = ApiJson.decodeFromString<Instance>(Fixtures.read("instance/response.json"))
    private val expected = ApiJson.parseToJsonElement(Fixtures.read("instance/expected.json")).jsonObject

    @Test
    fun name() {
        assertEquals(
            "the name decoded from instance/response.json differs from instance/expected.json",
            expected.getValue("name").jsonPrimitive.content,
            instance.name,
        )
    }

    @Test
    fun shortDescription() {
        assertEquals(
            "the short description decoded from instance/response.json differs from instance/expected.json",
            expected.getValue("short_description").jsonPrimitive.content,
            instance.shortDescription,
        )
    }
}
