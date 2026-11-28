package dev.mycroblog.core.data

import dev.mycroblog.core.api.ApiJson
import dev.mycroblog.core.api.models.StatusPage
import dev.mycroblog.core.model.Media
import dev.mycroblog.core.model.MediaType
import dev.mycroblog.core.model.PostCounts
import dev.mycroblog.core.model.TimelinePost
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * A page of `GET /api/v1/timelines/local`, shared/fixtures/timeline-page/response.json, shows each post as
 * timeline-page/expected.json says, as both apps must: the author, who boosted it, the content warning, what is hidden,
 * the media, and the counts.
 */
class TimelinePageFixtureTest {
    private val page = ApiJson.decodeFromString(StatusPage.serializer(), Fixtures.read("timeline-page/response.json"))
    private val expected = ApiJson.parseToJsonElement(Fixtures.read("timeline-page/expected.json")).jsonObject
    private val expectedItems = expected.getValue("items").jsonArray.map { it.jsonObject }
    private val posts = page.items.map { it.toTimelinePost() }

    private fun JsonObject.orNull(key: String): JsonElement? = getValue(key).takeUnless { it is JsonNull }

    private fun JsonObject.textOrNull(key: String): String? = orNull(key)?.jsonPrimitive?.content

    /** Each field of a post that differs from its item of expected.json, as `<field>: expected <e>, shown <a>`. */
    private fun differences(item: JsonObject, post: TimelinePost): List<String> {
        val fields = mutableListOf<Pair<String, Pair<Any?, Any?>>>()
        fun field(name: String, expected: Any?, actual: Any?) {
            fields += name to (expected to actual)
        }
        field("id", item.getValue("id").jsonPrimitive.content, post.id)

        val author = item.getValue("author").jsonObject
        field(
            "author.display_name",
            ContentJson.runs(author.getValue("display_name").jsonArray),
            post.author.displayName,
        )
        field("author.handle", author.textOrNull("handle"), post.author.handle)
        field("author.avatar", author.textOrNull("avatar"), post.author.avatar)

        val booster = item.orNull("boosted_by")?.jsonObject
        field("boosted_by", booster != null, post.boostedBy != null)
        if (booster != null && post.boostedBy != null) {
            field(
                "boosted_by.display_name",
                ContentJson.runs(booster.getValue("display_name").jsonArray),
                post.boostedBy!!.displayName,
            )
            field("boosted_by.handle", booster.textOrNull("handle"), post.boostedBy!!.handle)
        }

        field("content_warning", item.orNull("content_warning")?.jsonArray?.let(ContentJson::runs), post.contentWarning)
        field("content", ContentJson.blocks(item.getValue("content").jsonArray), post.content)
        field("content_hidden", item.getValue("content_hidden").jsonPrimitive.boolean, post.contentHidden)
        field("media_hidden", item.getValue("media_hidden").jsonPrimitive.boolean, post.mediaHidden)
        field(
            "media",
            item.getValue("media").jsonArray.map { element ->
                val media = element.jsonObject
                listOf(
                    MediaType.valueOf(media.getValue("type").jsonPrimitive.content.uppercase()),
                    media.textOrNull("description"),
                    media.textOrNull("preview"),
                    media.textOrNull("blurhash"),
                )
            },
            post.media.map { media: Media -> listOf(media.type, media.description, media.preview, media.blurhash) },
        )
        field("omitted_media_count", item.getValue("omitted_media_count").jsonPrimitive.int, post.omittedMediaCount)
        field("edited", item.getValue("edited").jsonPrimitive.boolean, post.edited)
        val counts = item.getValue("counts").jsonObject
        field(
            "counts",
            PostCounts(
                replies = counts.getValue("replies").jsonPrimitive.int,
                boosts = counts.getValue("boosts").jsonPrimitive.int,
                favourites = counts.getValue("favourites").jsonPrimitive.int,
            ),
            post.counts,
        )
        return fields.filter { (_, values) -> values.first != values.second }
            .map { (name, values) -> "$name: expected ${values.first}, shown ${values.second}" }
    }

    @Test
    fun thePageHasThePostsOfExpectedInItsOrder() {
        assertEquals(
            "the ids of the posts shown of timeline-page/response.json differ from timeline-page/expected.json",
            expectedItems.map { it.getValue("id").jsonPrimitive.content },
            posts.map { it.id },
        )
    }

    @Test
    fun everyPostShowsWhatExpectedSays() {
        val failures = expectedItems.zip(posts).flatMap { (item, post) ->
            differences(item, post).map { "${post.id} $it" }
        }
        assertEquals(
            "the posts of timeline-page/response.json shown otherwise than timeline-page/expected.json:\n" +
                failures.joinToString("\n"),
            0,
            failures.size,
        )
    }

    @Test
    fun theNextCursorIsTheOneOfThePage() {
        assertEquals(expected.getValue("next_cursor").jsonPrimitive.content, page.nextCursor)
    }
}
