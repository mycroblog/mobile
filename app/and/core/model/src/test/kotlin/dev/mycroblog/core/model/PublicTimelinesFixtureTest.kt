package dev.mycroblog.core.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * [offeredPublicTimelines] offers a reader who has not signed in the timelines of each case of
 * shared/fixtures/public-timelines/cases.json, in its order, as both apps must.
 */
class PublicTimelinesFixtureTest {
    private val cases = Json.parseToJsonElement(Fixtures.read("public-timelines/cases.json")).jsonObject
        .getValue("cases").jsonArray.map { it.jsonObject }

    private fun audience(name: String): TimelineAudience = TimelineAudience.valueOf(name.uppercase())

    private fun timeline(name: String): PublicTimeline =
        PublicTimeline.entries.singleOrNull { it.apiName == name } ?: error("no PublicTimeline named \"$name\"")

    @Test
    fun everyCase() {
        assertTrue("public-timelines/cases.json has no cases", cases.isNotEmpty())
        val failures = cases.mapNotNull { case ->
            val timelines = case.getValue("timelines").jsonObject
            val offered = offeredPublicTimelines(
                local = audience(timelines.getValue("local").jsonPrimitive.content),
                federated = audience(timelines.getValue("federated").jsonPrimitive.content),
                singleUser = case.getValue("single_user").jsonPrimitive.boolean,
            )
            val expected = case.getValue("offered").jsonArray.map { timeline(it.jsonPrimitive.content) }
            if (offered == expected) null else "$case: expected $expected, but offered $offered"
        }
        assertEquals(
            "the cases of public-timelines/cases.json that differ:\n" + failures.joinToString("\n"),
            0,
            failures.size,
        )
    }

    @Test
    fun anAudienceTheAppDoesNotKnowIsNotOffered() {
        assertEquals(
            emptyList<PublicTimeline>(),
            offeredPublicTimelines(TimelineAudience.UNKNOWN, TimelineAudience.UNKNOWN, singleUser = false),
        )
    }

    @Test
    fun theApiNamesAreThoseOfThePaths() {
        assertEquals(listOf("local", "federated"), PublicTimeline.entries.map { it.apiName })
    }
}
