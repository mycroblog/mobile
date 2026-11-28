package dev.mycroblog.feature.timeline.impl

import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.feature.settings.api.ChooseServerKey
import dev.mycroblog.feature.timeline.api.FederatedTimelineKey
import dev.mycroblog.feature.timeline.api.HomeTimelineKey
import dev.mycroblog.feature.timeline.api.LocalTimelineKey

/**
 * The destinations of the timeline feature, for the app's NavDisplay. [navigateUp] says what the Up button
 * of the screen of a key does, or null when it has none, [navigate] opens a key over the shown destination, and
 * [openDestination] shows the top-level destination of a key, such as the local timeline's.
 */
fun EntryProviderScope<NavKey>.timelineEntries(
    navigateUp: (NavKey) -> (() -> Unit)?,
    navigate: (NavKey) -> Unit,
    openDestination: (NavKey) -> Unit = navigate,
) {
    entry<HomeTimelineKey> { key ->
        HomeRoute(
            onChooseServer = { navigate(ChooseServerKey) },
            onOpenTimeline = openDestination,
            onUp = navigateUp(key),
        )
    }
    entry<LocalTimelineKey> { key ->
        PublicTimelineRoute(
            title = stringResource(R.string.local_timeline_title),
            viewModel = hiltViewModel<LocalTimelineViewModel>(),
            onUp = navigateUp(key),
        )
    }
    entry<FederatedTimelineKey> { key ->
        PublicTimelineRoute(
            title = stringResource(R.string.federated_timeline_title),
            viewModel = hiltViewModel<FederatedTimelineViewModel>(),
            onUp = navigateUp(key),
        )
    }
}
