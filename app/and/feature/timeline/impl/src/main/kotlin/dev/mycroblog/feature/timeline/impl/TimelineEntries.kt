package dev.mycroblog.feature.timeline.impl

import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.core.designsystem.PlaceholderScreen
import dev.mycroblog.feature.timeline.api.FederatedTimelineKey
import dev.mycroblog.feature.timeline.api.HomeTimelineKey
import dev.mycroblog.feature.timeline.api.LocalTimelineKey

/**
 * The destinations of the timeline feature, for the app's NavDisplay. [navigateUp] says what the Up button
 * of the screen of a key does, or null when it has none.
 */
fun EntryProviderScope<NavKey>.timelineEntries(navigateUp: (NavKey) -> (() -> Unit)?) {
    entry<HomeTimelineKey> { key ->
        PlaceholderScreen(stringResource(R.string.home_timeline_title), onUp = navigateUp(key))
    }
    entry<LocalTimelineKey> { key ->
        PlaceholderScreen(stringResource(R.string.local_timeline_title), onUp = navigateUp(key))
    }
    entry<FederatedTimelineKey> { key ->
        PlaceholderScreen(stringResource(R.string.federated_timeline_title), onUp = navigateUp(key))
    }
}
