package dev.mycroblog.core.designsystem.post

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Reply
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.mycroblog.core.designsystem.R
import dev.mycroblog.core.model.Account
import dev.mycroblog.core.model.ContentRun
import dev.mycroblog.core.model.TextRun
import dev.mycroblog.core.model.TimelinePost

/**
 * A post as every list of posts shows it (#41): who boosted it, its author and time, its content warning, its content
 * with links and custom emoji, its media, and its counts. The header is one element for accessibility services, and
 * the content another, so that its links stay reachable.
 *
 * The reader opens a content warning with [onToggleContent] ([contentOpen]), and shows sensitive media with
 * [onToggleMedia] ([mediaShown]); both are screen state, kept apart from the post.
 */
@Composable
fun PostItem(
    post: TimelinePost,
    contentOpen: Boolean,
    onToggleContent: () -> Unit,
    mediaShown: Boolean,
    onToggleMedia: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        post.boostedBy?.let { BoostedBy(it) }
        Header(post)
        val warning = post.contentWarning
        if (warning != null) ContentWarning(warning, post, contentOpen, onToggleContent)
        if (warning == null || contentOpen) {
            if (post.content.isNotEmpty()) Content(post)
            if (post.media.isNotEmpty()) {
                MediaGrid(post.media, covered = post.post.sensitive && !mediaShown, onToggle = onToggleMedia)
            }
            if (post.omittedMediaCount > 0) {
                Text(
                    text = pluralStringResource(
                        R.plurals.post_omitted_media,
                        post.omittedMediaCount,
                        post.omittedMediaCount,
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Counts(post)
    }
}

@Composable
private fun BoostedBy(booster: Account) {
    val template = stringResource(R.string.post_boosted_by, NAME_SLOT)
    val runs = remember(template, booster) { template.aroundName(booster.displayName) }
    Row(
        modifier = Modifier.semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Outlined.Repeat,
            contentDescription = null,
            modifier = Modifier.padding(start = 24.dp).size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        RunsText(
            runs = runs,
            emojis = booster.emojis,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
        )
    }
}

/** The runs of a string that names someone, with their [name]'s runs in the place of [NAME_SLOT]. */
private fun String.aroundName(name: List<ContentRun>): List<ContentRun> {
    val at = indexOf(NAME_SLOT)
    if (at < 0) return listOf(TextRun(this))
    val before = substring(0, at)
    val after = substring(at + NAME_SLOT.length)
    return listOfNotNull(before.takeIf { it.isNotEmpty() }?.let(::TextRun)) + name +
        listOfNotNull(after.takeIf { it.isNotEmpty() }?.let(::TextRun))
}

private const val NAME_SLOT = "\u0000"

@Composable
private fun Header(post: TimelinePost) {
    val author = post.author
    Row(
        modifier = Modifier.semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Avatar(author.avatar)
        Column(Modifier.weight(1f)) {
            RunsText(
                runs = author.displayName,
                emojis = author.emojis,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
            )
            author.handle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            val time = remember(post.createdAt) {
                DateUtils.getRelativeTimeSpanString(
                    post.createdAt,
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE,
                ).toString()
            }
            Text(time, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (post.edited) {
                Text(
                    text = stringResource(R.string.post_edited),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun Avatar(url: String?) {
    val modifier = Modifier.size(AvatarSize).clip(CircleShape)
    if (url == null) {
        Icon(
            Icons.Filled.AccountCircle,
            contentDescription = null,
            modifier = modifier,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    } else {
        AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        )
    }
}

private val AvatarSize = 40.dp

@Composable
private fun ContentWarning(warning: List<ContentRun>, post: TimelinePost, open: Boolean, onToggle: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        RunsText(runs = warning, emojis = post.post.emojis, style = MaterialTheme.typography.bodyLarge)
        OutlinedButton(onClick = onToggle) {
            Text(stringResource(if (open) R.string.post_show_less else R.string.post_show_more))
        }
    }
}

@Composable
private fun Content(post: TimelinePost) {
    val colors = postTextColors()
    val text = remember(post.content, colors) { postAnnotatedString(post.content, colors) }
    // Its own element for accessibility services, which offer its links.
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        inlineContent = rememberEmojiContent(post.post.emojis),
    )
}

@Composable
private fun Counts(post: TimelinePost) {
    val counts = post.counts
    val resources = LocalResources.current
    val description = listOf(
        resources.getQuantityString(R.plurals.post_replies, counts.replies, counts.replies),
        resources.getQuantityString(R.plurals.post_boosts, counts.boosts, counts.boosts),
        resources.getQuantityString(R.plurals.post_favourites, counts.favourites, counts.favourites),
    ).joinToString(", ")
    Row(
        modifier = Modifier.clearAndSetSemantics { contentDescription = description },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Count(Icons.AutoMirrored.Outlined.Reply, counts.replies)
        Spacer(Modifier.width(24.dp))
        Count(Icons.Outlined.Repeat, counts.boosts)
        Spacer(Modifier.width(24.dp))
        Count(Icons.Outlined.StarBorder, counts.favourites)
    }
}

@Composable
private fun Count(icon: ImageVector, count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        val color = MaterialTheme.colorScheme.onSurfaceVariant
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = color)
        Text(count.toString(), style = MaterialTheme.typography.labelMedium, color = color)
    }
}

/** The box behind content that has not loaded, or that has nothing to show. */
@Composable
internal fun EmptyBox(modifier: Modifier = Modifier, content: @Composable () -> Unit = {}) {
    Box(modifier.background(MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center) {
        content()
    }
}
