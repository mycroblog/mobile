package dev.mycroblog.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The text of a post, a content warning or a display name, as blocks of runs (the rule of
 * `shared/fixtures/post-content/cases.json`). [PostContentParser] makes it from the server's HTML or plain text.
 */
@Serializable
data class ContentBlock(val type: BlockType, val runs: List<ContentRun>)

/** What a block is: a paragraph, a quote, a code block, or one item of a list. */
@Serializable
enum class BlockType {
    @SerialName("paragraph")
    PARAGRAPH,

    @SerialName("quote")
    QUOTE,

    @SerialName("code")
    CODE,

    @SerialName("list_item")
    LIST_ITEM,
}

/** A piece of a block: styled text, or a custom emoji; either may be part of a link. */
@Serializable
sealed interface ContentRun {
    /** The link this run is part of, or null. */
    val link: RunLink?
}

/** Text with its [styles], in the order of [RunStyle], and its [link]. */
@Serializable
@SerialName("text")
data class TextRun(val text: String, val styles: List<RunStyle> = emptyList(), override val link: RunLink? = null) :
    ContentRun

/** The custom emoji [shortcode] of the post's, or the account's, emoji list. */
@Serializable
@SerialName("emoji")
data class EmojiRun(val shortcode: String, override val link: RunLink? = null) : ContentRun

/** A style of a text run, declared in the order a run lists its styles. */
@Serializable
enum class RunStyle {
    @SerialName("strong")
    STRONG,

    @SerialName("emphasis")
    EMPHASIS,

    @SerialName("strikethrough")
    STRIKETHROUGH,

    @SerialName("underline")
    UNDERLINE,

    @SerialName("code")
    CODE,
}

/** A link of a run: a mention of an account, a hashtag, or any other URL, to [href]. */
@Serializable
data class RunLink(val type: LinkType, val href: String)

@Serializable
enum class LinkType {
    @SerialName("mention")
    MENTION,

    @SerialName("hashtag")
    HASHTAG,

    @SerialName("url")
    URL,
}

/** A custom emoji: its [shortcode], its image [url] (perhaps animated), and a [staticUrl] that never animates. */
@Serializable
data class CustomEmoji(val shortcode: String, val url: String, val staticUrl: String)
