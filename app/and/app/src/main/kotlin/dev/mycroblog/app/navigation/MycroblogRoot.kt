package dev.mycroblog.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.window.core.layout.WindowSizeClass
import dev.mycroblog.app.R
import dev.mycroblog.feature.bookmarks.impl.bookmarksEntries
import dev.mycroblog.feature.messages.impl.messagesEntries
import dev.mycroblog.feature.notifications.impl.notificationsEntries
import dev.mycroblog.feature.profile.impl.profileEntries
import dev.mycroblog.feature.search.impl.searchEntries
import dev.mycroblog.feature.settings.impl.settingsEntries
import dev.mycroblog.feature.timeline.impl.timelineEntries

/**
 * The root of the app: the top-level destinations in the adaptive navigation (ADR-0010 §4), a navigation bar on a
 * compact width and a navigation rail on a medium width and wider, whatever the height, and the shown back stacks in
 * one NavDisplay (ADR-0010 §2).
 */
@Composable
fun MycroblogRoot() {
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    val state = rememberRootNavigationState(
        barLayout = !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND),
    )

    val entryProvider = entryProvider<NavKey> {
        timelineEntries()
        notificationsEntries()
        searchEntries()
        messagesEntries()
        profileEntries()
        bookmarksEntries()
        settingsEntries()
        entry<MoreKey> { MoreScreen(destinations = state.moreItems, onSelect = state::select) }
    }
    // Each back stack keeps the saved state and the ViewModels of its entries while another one is shown.
    val entries = (state.stacks.values + listOf(state.moreStack)).associateWith { stack ->
        rememberDecoratedNavEntries(
            backStack = stack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider,
        )
    }

    NavigationSuiteScaffold(
        layoutType = if (state.barLayout) NavigationSuiteType.NavigationBar else NavigationSuiteType.NavigationRail,
        navigationSuiteItems = {
            state.items.forEach { destination ->
                val selected = !state.moreSelected && state.selected == destination
                item(
                    selected = selected,
                    onClick = { state.select(destination) },
                    icon = {
                        Icon(if (selected) destination.selectedIcon else destination.icon, contentDescription = null)
                    },
                    label = { Text(stringResource(destination.label)) },
                )
            }
            if (state.barLayout) {
                item(
                    selected = state.moreSelected,
                    onClick = state::openMore,
                    icon = { Icon(Icons.Filled.MoreHoriz, contentDescription = null) },
                    label = { Text(stringResource(R.string.destination_more)) },
                )
            }
        },
    ) {
        NavDisplay(
            entries = state.shownStacks.flatMap { entries.getValue(it) },
            onBack = state::back,
        )
    }
}
