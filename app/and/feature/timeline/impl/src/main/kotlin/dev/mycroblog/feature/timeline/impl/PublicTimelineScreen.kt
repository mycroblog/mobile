package dev.mycroblog.feature.timeline.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mycroblog.core.designsystem.NavigateUpButton
import dev.mycroblog.core.designsystem.post.PostItem
import dev.mycroblog.core.model.TimelineFailure
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

/** A public timeline's screen with its ViewModel. */
@Composable
internal fun PublicTimelineRoute(title: String, viewModel: PublicTimelineViewModel, onUp: (() -> Unit)?) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PublicTimelineScreen(
        title = title,
        uiState = uiState,
        onRefresh = viewModel::refresh,
        onLoadOlder = viewModel::loadOlder,
        onRetryOlder = viewModel::retryOlder,
        onPositionRestored = viewModel::onPositionRestored,
        onScrolled = viewModel::savePosition,
        onToggleContent = viewModel::toggleContent,
        onToggleMedia = viewModel::toggleMedia,
        onUp = onUp,
    )
}

/**
 * A public timeline: its posts, pull to refresh, older posts at the end of the list, and its loading, empty and error
 * states. It holds no state but the list's scroll, which it restores once from [PublicTimelineUiState.restore] and
 * reports with [onScrolled].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicTimelineScreen(
    title: String,
    uiState: PublicTimelineUiState,
    onRefresh: () -> Unit,
    onLoadOlder: () -> Unit,
    onRetryOlder: () -> Unit,
    onPositionRestored: () -> Unit,
    onScrolled: (anchorId: String, offset: Int) -> Unit,
    onToggleContent: (String) -> Unit,
    onToggleMedia: (String) -> Unit,
    modifier: Modifier = Modifier,
    onUp: (() -> Unit)? = null,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(title)
                        uiState.server?.let {
                            Text(
                                it.host,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                },
                navigationIcon = { if (onUp != null) NavigateUpButton(onClick = onUp) },
            )
        },
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when {
                uiState.initialLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                uiState.posts.isEmpty() && uiState.refreshFailure != null -> FullMessage(
                    message = failureMessage(uiState.refreshFailure),
                    action = stringResource(R.string.timeline_retry),
                    actionEnabled = !uiState.rateLimited,
                    onAction = onRefresh,
                )

                else -> PullToRefreshBox(
                    isRefreshing = uiState.refreshing,
                    onRefresh = onRefresh,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (uiState.empty) {
                        FullMessage(message = stringResource(R.string.timeline_empty))
                    } else {
                        Posts(
                            uiState,
                            onRefresh,
                            onLoadOlder,
                            onRetryOlder,
                            onPositionRestored,
                            onScrolled,
                            onToggleContent,
                            onToggleMedia,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(FlowPreview::class)
@Composable
private fun Posts(
    uiState: PublicTimelineUiState,
    onRefresh: () -> Unit,
    onLoadOlder: () -> Unit,
    onRetryOlder: () -> Unit,
    onPositionRestored: () -> Unit,
    onScrolled: (String, Int) -> Unit,
    onToggleContent: (String) -> Unit,
    onToggleMedia: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val posts by rememberUpdatedState(uiState.posts)
    RestorePosition(uiState, listState, onPositionRestored)
    // The position is saved as the post at the top and the offset from it, once the list stops moving.
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .distinctUntilChanged()
            .debounce(SAVE_DELAY_MILLIS)
            .collect { (index, offset) -> posts.getOrNull(index)?.let { onScrolled(it.id, offset) } }
    }
    // The next older page loads as the reader nears the end of the list.
    val nearEnd by remember {
        derivedStateOf {
            val last = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@derivedStateOf false
            last >= listState.layoutInfo.totalItemsCount - OLDER_THRESHOLD
        }
    }
    LaunchedEffect(nearEnd, uiState.hasOlder) {
        if (nearEnd && uiState.hasOlder) onLoadOlder()
    }
    Column(Modifier.fillMaxSize()) {
        uiState.refreshFailure?.let { failure ->
            Banner(failureMessage(failure), enabled = !uiState.rateLimited, onRetry = onRefresh)
        }
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(uiState.posts, key = { it.id }, contentType = { "post" }) { post ->
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    PostItem(
                        post = post,
                        contentOpen = post.id in uiState.openContent,
                        onToggleContent = { onToggleContent(post.id) },
                        mediaShown = post.id in uiState.shownMedia,
                        onToggleMedia = { onToggleMedia(post.id) },
                        modifier = Modifier.widthIn(max = ContentMaxWidth),
                    )
                    HorizontalDivider()
                }
            }
            item(key = FOOTER_KEY, contentType = "footer") {
                Footer(uiState, onRetryOlder)
            }
        }
    }
}

/** Scrolls once to the saved anchor when it is in the list; when it is gone, the list starts at the newest. */
@Composable
private fun RestorePosition(uiState: PublicTimelineUiState, listState: LazyListState, onRestored: () -> Unit) {
    val restore = uiState.restore ?: return
    // The stored posts come before the network answers; with none stored, wait for the first load.
    if (uiState.posts.isEmpty() && (uiState.refreshing || !uiState.loaded)) return
    LaunchedEffect(restore) {
        val index = uiState.posts.indexOfFirst { it.id == restore.anchorId }
        if (index >= 0) listState.scrollToItem(index, restore.offset)
        onRestored()
    }
}

@Composable
private fun Footer(uiState: PublicTimelineUiState, onRetryOlder: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
        val failure = uiState.olderFailure
        when {
            failure != null -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            ) {
                Text(failureMessage(failure), textAlign = TextAlign.Center)
                OutlinedButton(onClick = onRetryOlder, enabled = !uiState.rateLimited) {
                    Text(stringResource(R.string.timeline_retry))
                }
            }

            uiState.loadingOlder -> CircularProgressIndicator()

            !uiState.hasOlder && uiState.posts.isNotEmpty() -> Text(
                stringResource(R.string.timeline_end),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun Banner(message: String, enabled: Boolean, onRetry: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.errorContainer, modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp).semantics { liveRegion = LiveRegionMode.Polite },
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(message, color = MaterialTheme.colorScheme.onErrorContainer)
            OutlinedButton(onClick = onRetry, enabled = enabled) { Text(stringResource(R.string.timeline_retry)) }
        }
    }
}

/** A message in the middle of the screen, with an optional action; it scrolls, so that pull to refresh works. */
@Composable
private fun FullMessage(
    message: String,
    action: String? = null,
    actionEnabled: Boolean = true,
    onAction: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .semantics { liveRegion = LiveRegionMode.Polite },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    ) {
        Text(message, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        if (action != null) Button(onClick = onAction, enabled = actionEnabled) { Text(action) }
    }
}

@Composable
private fun failureMessage(failure: TimelineFailure): String = when (failure) {
    TimelineFailure.NoAnswer -> stringResource(R.string.timeline_no_answer)
    TimelineFailure.Unexpected -> stringResource(R.string.timeline_unexpected)
    is TimelineFailure.Problem -> failure.title
    is TimelineFailure.RateLimited -> stringResource(R.string.timeline_rate_limited)
    TimelineFailure.NotOffered -> stringResource(R.string.timeline_not_offered)
}

private const val SAVE_DELAY_MILLIS = 500L
private const val OLDER_THRESHOLD = 5
private const val FOOTER_KEY = "footer"

/** The widest a post grows, so that its lines stay readable in a wide window. */
private val ContentMaxWidth = 600.dp
