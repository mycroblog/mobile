package dev.mycroblog.core.designsystem.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.mycroblog.core.designsystem.R
import dev.mycroblog.core.model.Media
import dev.mycroblog.core.model.MediaType

/**
 * The previews of a post's media: one at its own shape, more two by two, as wide as the post and as high as their
 * shape makes them. While [covered], each shows only its BlurHash, and one button shows them all ([onToggle]); once
 * shown, [sensitive] media have a button that hides them again.
 */
@Composable
internal fun MediaGrid(media: List<Media>, sensitive: Boolean, covered: Boolean, onToggle: () -> Unit) {
    Box {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            if (media.size == 1) {
                val ratio = (media.single().aspectRatio ?: DEFAULT_RATIO).coerceIn(MIN_RATIO, MAX_RATIO)
                MediaPreview(media.single(), covered, Modifier.fillMaxWidth().aspectRatio(ratio))
            } else {
                media.chunked(2).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        row.forEach { MediaPreview(it, covered, Modifier.weight(1f).aspectRatio(DEFAULT_RATIO)) }
                        if (row.size == 1) Box(Modifier.weight(1f))
                    }
                }
            }
        }
        if (covered) {
            val show = stringResource(R.string.post_show_media)
            FilledTonalButton(
                onClick = onToggle,
                modifier = Modifier.align(Alignment.Center).buttonSemantics(show, onToggle),
            ) {
                Text(show)
            }
        } else if (sensitive) {
            val hide = stringResource(R.string.post_hide_media)
            FilledTonalIconButton(
                onClick = onToggle,
                modifier = Modifier.align(Alignment.TopStart).padding(4.dp).semantics { contentDescription = hide },
            ) {
                Icon(Icons.Outlined.VisibilityOff, contentDescription = null)
            }
        }
    }
}

private const val DEFAULT_RATIO = 16f / 9f
private const val MIN_RATIO = 3f / 4f
private const val MAX_RATIO = 2f

@Composable
private fun MediaPreview(media: Media, covered: Boolean, modifier: Modifier) {
    val label = media.label()
    val kind = media.kindLabel()
    val blurhash = rememberBlurhashPainter(media.blurhash)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clearAndSetSemantics {
                contentDescription = label
                role = Role.Image
            },
    ) {
        when {
            covered -> if (blurhash != null) {
                Image(blurhash, null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            } else {
                EmptyBox(Modifier.fillMaxSize())
            }

            media.preview != null -> {
                var failed by remember(media.preview) { mutableStateOf(false) }
                AsyncImage(
                    model = media.preview,
                    contentDescription = null,
                    placeholder = blurhash,
                    error = blurhash,
                    onError = { failed = true },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
                // A preview that cannot load shows its BlurHash, or a placeholder that names its kind.
                if (failed && blurhash == null) MediaPlaceholder(media.type, kind)
                if (media.type == MediaType.VIDEO || media.type == MediaType.GIFV) {
                    Icon(
                        Icons.Filled.PlayCircle,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center).size(48.dp),
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }
            }

            else -> MediaPlaceholder(media.type, kind)
        }
    }
}

/** The box of a medium with no picture to show: an icon and the name of its [kind]. */
@Composable
private fun MediaPlaceholder(type: MediaType, kind: String) {
    EmptyBox(Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(
                when (type) {
                    MediaType.AUDIO -> Icons.Outlined.Audiotrack
                    MediaType.IMAGE, MediaType.GIFV -> Icons.Outlined.Photo
                    MediaType.VIDEO -> Icons.Outlined.Videocam
                    MediaType.UNKNOWN -> Icons.Outlined.Description
                },
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(kind, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

/** What accessibility services say of a medium: its kind, and its description when it has one. */
@Composable
private fun Media.label(): String {
    val kind = kindLabel()
    return description?.let { stringResource(R.string.post_media_described, kind, it) } ?: kind
}

/** The name of a medium's kind. */
@Composable
private fun Media.kindLabel(): String = stringResource(
    when (type) {
        MediaType.IMAGE -> R.string.post_media_image
        MediaType.GIFV -> R.string.post_media_gifv
        MediaType.VIDEO -> R.string.post_media_video
        MediaType.AUDIO -> R.string.post_media_audio
        MediaType.UNKNOWN -> R.string.post_media_unknown
    },
)
