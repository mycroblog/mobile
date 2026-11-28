package dev.mycroblog.core.model

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * The content of the shared fixtures, in the shape shared/fixtures/post-content/cases.json describes, as the types of
 * this module: a run has `text` or `emoji`, optional `styles` and an optional `link`; a block has a `type` and `runs`.
 * An unknown name fails the test that reads it.
 */
object ContentJson {
    fun emojis(array: JsonArray): List<CustomEmoji> = array.map { element ->
        val emoji = element.jsonObject
        CustomEmoji(emoji.text("shortcode"), emoji.text("url"), emoji.text("static_url"))
    }

    fun runs(array: JsonArray): List<ContentRun> = array.map { run(it.jsonObject) }

    fun blocks(array: JsonArray): List<ContentBlock> = array.map { element ->
        val block = element.jsonObject
        ContentBlock(BlockType.valueOf(block.text("type").uppercase()), runs(block.getValue("runs").jsonArray))
    }

    private fun run(run: JsonObject): ContentRun {
        val link = run.orNull("link")?.jsonObject?.let { link ->
            RunLink(LinkType.valueOf(link.text("type").uppercase()), link.text("href"))
        }
        run.orNull("emoji")?.let { return EmojiRun(it.jsonPrimitive.content, link) }
        val styles = run.orNull("styles")?.jsonArray?.map { RunStyle.valueOf(it.jsonPrimitive.content.uppercase()) }
        return TextRun(run.text("text"), styles.orEmpty(), link)
    }

    private fun JsonObject.text(key: String): String = getValue(key).jsonPrimitive.content

    private fun JsonObject.orNull(key: String): JsonElement? = this[key]?.takeUnless { it is JsonNull }
}
