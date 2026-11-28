package dev.mycroblog.core.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * [PostContentParser] turns each case of shared/fixtures/post-content/cases.json into the blocks it lists, as both apps
 * must: the HTML of a post's content (`html`) through [PostContentParser.parseHtml], and the plain text of a content
 * warning or a display name (`text`) through [PostContentParser.parsePlainText].
 */
class PostContentFixtureTest {
    private class Case(
        val name: String,
        val format: String,
        val input: String,
        val emojis: List<CustomEmoji>,
        val blocks: List<ContentBlock>,
    )

    private val cases = Json.parseToJsonElement(Fixtures.read("post-content/cases.json")).jsonObject
        .getValue("cases").jsonArray.map { element ->
            val case = element.jsonObject
            Case(
                name = case.getValue("name").jsonPrimitive.content,
                format = case.getValue("format").jsonPrimitive.content,
                input = case.getValue("input").jsonPrimitive.content,
                emojis = ContentJson.emojis(case.getValue("emojis").jsonArray),
                blocks = ContentJson.blocks(case.getValue("blocks").jsonArray),
            )
        }

    @Test
    fun everyCase() {
        assertTrue("post-content/cases.json has no cases", cases.isNotEmpty())
        val failures = cases.mapNotNull { case ->
            val blocks = when (case.format) {
                "html" -> PostContentParser.parseHtml(case.input, case.emojis)
                "text" -> PostContentParser.parsePlainText(case.input, case.emojis)
                else -> return@mapNotNull "\"${case.name}\": unknown format \"${case.format}\""
            }
            if (blocks == case.blocks) null else "\"${case.name}\":\n  expected ${case.blocks}\n  but parsed $blocks"
        }
        assertEquals(
            "the cases of post-content/cases.json that differ:\n" + failures.joinToString("\n"),
            0,
            failures.size,
        )
    }

    @Test
    fun thePlainRunsOfATextCaseAreTheRunsOfItsParagraph() {
        val failures = cases.filter { it.format == "text" }.mapNotNull { case ->
            val runs = PostContentParser.parsePlainRuns(case.input, case.emojis)
            val expected = case.blocks.flatMap { it.runs }
            if (runs == expected) null else "\"${case.name}\": expected $expected, but parsed $runs"
        }
        assertEquals("the text cases whose plain runs differ:\n" + failures.joinToString("\n"), 0, failures.size)
    }

    @Test
    fun emptyPlainTextIsNoBlock() {
        assertEquals(emptyList<ContentBlock>(), PostContentParser.parsePlainText("", emptyList()))
    }
}
