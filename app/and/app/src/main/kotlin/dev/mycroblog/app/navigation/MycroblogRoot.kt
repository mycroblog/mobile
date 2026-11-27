package dev.mycroblog.app.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
        timelineEntries(state::navigateUp)
        notificationsEntries(state::navigateUp)
        searchEntries(state::navigateUp)
        messagesEntries(state::navigateUp)
        profileEntries(state::navigateUp)
        bookmarksEntries(state::navigateUp)
        settingsEntries(state::navigateUp)
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

    val shownEntries = state.shownStacks.flatMap { entries.getValue(it) }
    val navigationInsets = if (state.barLayout) {
        NavigationBarDefaults.windowInsets.only(WindowInsetsSides.Bottom)
    } else {
        NavigationRailDefaults.windowInsets.only(WindowInsetsSides.Start)
    }
    NavigationSuiteScaffoldLayout(
        navigationSuite = { if (state.barLayout) RootNavigationBar(state) else RootNavigationRail(state) },
        layoutType = if (state.barLayout) NavigationSuiteType.NavigationBar else NavigationSuiteType.NavigationRail,
    ) {
        Box(Modifier.consumeWindowInsets(navigationInsets)) {
            NavDisplay(entries = shownEntries, onBack = state::back)
        }
    }
}

/** The navigation bar: the destinations that fit and "More", each label on one line at every font scale. */
@Composable
private fun RootNavigationBar(state: RootNavigationState) {
    NavigationBar {
        state.items.forEach { destination ->
            val selected = !state.moreSelected && state.selected == destination
            NavigationBarItem(
                selected = selected,
                onClick = { state.select(destination) },
                icon = { DestinationIcon(destination, selected) },
                label = { SingleLineLabel(stringResource(destination.label)) },
            )
        }
        NavigationBarItem(
            selected = state.moreSelected,
            onClick = state::openMore,
            icon = { Icon(Icons.Filled.MoreHoriz, contentDescription = null) },
            label = { SingleLineLabel(stringResource(R.string.destination_more)) },
        )
    }
}

/**
 * The navigation rail: every destination in order (ADR-0010 §4). Its items scroll when the window is too short for
 * them all, as on a phone in landscape, and the selected one is scrolled into view.
 */
@Composable
private fun RootNavigationRail(state: RootNavigationState) {
    NavigationRail {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            state.items.forEach { destination ->
                val selected = state.selected == destination
                val requester = remember { BringIntoViewRequester() }
                if (selected) LaunchedEffect(Unit) { requester.bringIntoView() }
                NavigationRailItem(
                    selected = selected,
                    onClick = { state.select(destination) },
                    icon = { DestinationIcon(destination, selected) },
                    label = { SingleLineLabel(stringResource(destination.label)) },
                    modifier = Modifier.bringIntoViewRequester(requester),
                )
            }
        }
    }
}

@Composable
private fun DestinationIcon(destination: TopLevelDestination, selected: Boolean) {
    Icon(if (selected) destination.selectedIcon else destination.icon, contentDescription = null)
}

@Composable
private fun SingleLineLabel(text: String) {
    Text(text, maxLines = 1, overflow = TextOverflow.Ellipsis)
}
