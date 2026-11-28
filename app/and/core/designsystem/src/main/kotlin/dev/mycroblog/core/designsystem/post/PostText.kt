package dev.mycroblog.core.designsystem.post

import android.animation.ValueAnimator
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.em
import coil3.SingletonImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import dev.mycroblog.core.model.BlockType
import dev.mycroblog.core.model.ContentBlock
import dev.mycroblog.core.model.ContentRun
import dev.mycroblog.core.model.CustomEmoji
import dev.mycroblog.core.model.EmojiRun
import dev.mycroblog.core.model.RunStyle
import dev.mycroblog.core.model.TextRun
import kotlinx.coroutines.launch

/** The colors a post's text uses for its links, quotes and code. */
internal data class PostTextColors(val link: Color, val quote: Color, val code: Color)

@Composable
internal fun postTextColors() = PostTextColors(
    link = MaterialTheme.colorScheme.primary,
    quote = MaterialTheme.colorScheme.onSurfaceVariant,
    code = MaterialTheme.colorScheme.surfaceContainerHighest,
)

/**
 * The text of [blocks] as one string: each block a paragraph of its own that takes the direction of its own words,
 * blocks apart by a blank line (list items by a line break), links as [LinkAnnotation.Url]s that open through the
 * LocalUriHandler, and each custom emoji as inline content.
 */
internal fun postAnnotatedString(blocks: List<ContentBlock>, colors: PostTextColors): AnnotatedString =
    buildAnnotatedString {
        blocks.forEachIndexed { index, block ->
            withStyle(ParagraphStyle(textDirection = TextDirection.Content)) {
                when (block.type) {
                    BlockType.PARAGRAPH -> appendRuns(block.runs, colors)

                    BlockType.LIST_ITEM -> {
                        append("•\u00A0")
                        appendRuns(block.runs, colors)
                    }

                    BlockType.QUOTE -> withStyle(SpanStyle(color = colors.quote, fontStyle = FontStyle.Italic)) {
                        appendRuns(block.runs, colors)
                    }

                    BlockType.CODE -> withStyle(
                        SpanStyle(fontFamily = FontFamily.Monospace, background = colors.code),
                    ) {
                        appendRuns(block.runs, colors)
                    }
                }
                // A blank line before the next block, except between the items of a list.
                val next = blocks.getOrNull(index + 1)?.type
                if (next != null && !(block.type == BlockType.LIST_ITEM && next == BlockType.LIST_ITEM)) append("\n")
            }
        }
    }

/** The runs of a line such as a display name or a content warning. */
internal fun runsAnnotatedString(runs: List<ContentRun>, colors: PostTextColors): AnnotatedString =
    buildAnnotatedString { appendRuns(runs, colors) }

private fun AnnotatedString.Builder.appendRuns(runs: List<ContentRun>, colors: PostTextColors) {
    runs.forEach { run ->
        val link = run.link
        if (link != null) {
            withLink(LinkAnnotation.Url(link.href, TextLinkStyles(style = SpanStyle(color = colors.link)))) {
                appendRun(run)
            }
        } else {
            appendRun(run)
        }
    }
}

private fun AnnotatedString.Builder.appendRun(run: ContentRun) {
    when (run) {
        is TextRun -> withStyle(run.styles.toSpanStyle()) { append(run.text) }
        is EmojiRun -> appendInlineContent(emojiId(run.shortcode), ":${run.shortcode}:")
    }
}

private fun List<RunStyle>.toSpanStyle(): SpanStyle {
    val decorations = buildList {
        if (RunStyle.STRIKETHROUGH in this@toSpanStyle) add(TextDecoration.LineThrough)
        if (RunStyle.UNDERLINE in this@toSpanStyle) add(TextDecoration.Underline)
    }
    return SpanStyle(
        fontWeight = if (RunStyle.STRONG in this) FontWeight.Bold else null,
        fontStyle = if (RunStyle.EMPHASIS in this) FontStyle.Italic else null,
        fontFamily = if (RunStyle.CODE in this) FontFamily.Monospace else null,
        textDecoration = if (decorations.isEmpty()) null else TextDecoration.combine(decorations),
    )
}

private fun emojiId(shortcode: String) = "emoji:$shortcode"

/**
 * The inline images of [emojis], at the size of the text, for those whose image has loaded: until it loads, and if it
 * cannot, the text shows the emoji's `:shortcode:`, the alternate text of its run. An animated emoji does not animate
 * while the system removes animations: its static image is shown instead.
 */
@Composable
internal fun rememberEmojiContent(emojis: List<CustomEmoji>): Map<String, InlineTextContent> {
    val animate = remember { ValueAnimator.areAnimatorsEnabled() }
    val context = LocalPlatformContext.current
    val loaded = remember(emojis, animate) { mutableStateMapOf<String, Boolean>() }
    LaunchedEffect(emojis, animate) {
        emojis.forEach { emoji ->
            launch {
                val request = ImageRequest.Builder(context).data(emoji.imageUrl(animate)).build()
                val result = SingletonImageLoader.get(context).execute(request)
                if (result is SuccessResult) loaded[emoji.shortcode] = true
            }
        }
    }
    return emojis.filter { loaded[it.shortcode] == true }.associate { emoji ->
        emojiId(emoji.shortcode) to InlineTextContent(Placeholder(1.em, 1.em, PlaceholderVerticalAlign.TextCenter)) {
            // The text's alternate ":shortcode:" describes the emoji to accessibility services.
            AsyncImage(model = emoji.imageUrl(animate), contentDescription = null)
        }
    }
}

private fun CustomEmoji.imageUrl(animate: Boolean) = if (animate) url else staticUrl

/** A line of runs with their emoji, such as a display name, cut to [maxLines]. */
@Composable
internal fun RunsText(
    runs: List<ContentRun>,
    emojis: List<CustomEmoji>,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
) {
    val colors = postTextColors()
    val text = remember(runs, colors) { runsAnnotatedString(runs, colors) }
    Text(
        text = text,
        modifier = modifier,
        // A name or a warning takes the direction of its own words.
        style = style.copy(textDirection = TextDirection.Content),
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        inlineContent = rememberEmojiContent(emojis),
    )
}
