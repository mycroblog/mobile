package dev.mycroblog.app.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
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
        settingsEntries(state::navigateUp, state::navigate)
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
    // The same NavDisplay moves between the two layouts, keeping its state when the window changes size.
    val display = remember {
        movableContentOf { shown: List<NavEntry<NavKey>> -> NavDisplay(shown, onBack = state::back) }
    }
    if (state.barLayout) {
        val barInsets = NavigationBarDefaults.windowInsets.only(WindowInsetsSides.Bottom)
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.weight(1f).consumeWindowInsets(barInsets)) {
                display(shownEntries)
            }
            RootNavigationBar(state)
        }
    } else {
        val railInsets = NavigationRailDefaults.windowInsets.only(WindowInsetsSides.Start)
        Row(Modifier.fillMaxSize()) {
            RootNavigationRail(state)
            Box(Modifier.weight(1f).consumeWindowInsets(railInsets)) {
                display(shownEntries)
            }
        }
    }
}

/**
 * The navigation bar: the destinations that fit and "More", every one labeled. The labels grow with the font scale
 * only as far as every label still fits its item on one line, so that no label is broken or cut and the icons stay
 * level.
 */
@Composable
private fun RootNavigationBar(state: RootNavigationState) {
    val labels = state.items.map { stringResource(it.label) } + stringResource(R.string.destination_more)
    val style = MaterialTheme.typography.labelMedium
    val measurer = rememberTextMeasurer()
    val density = LocalDensity.current
    BoxWithConstraints {
        // The bar pads its row and spaces its items by BarItemSpacing; a label may take its item's whole width.
        val itemWidth = (maxWidth - BarItemSpacing * 2 - BarItemSpacing * (labels.size - 1)) / labels.size
        val widest = labels.maxOf { measurer.measure(it, style, maxLines = 1, softWrap = false).size.width }
        val labelStyle = with(density) {
            val fits = itemWidth.toPx() / widest
            if (fits >= 1f) style else style.copy(fontSize = (style.fontSize.toPx() * fits * 0.98f).toSp())
        }
        NavigationBar {
            state.items.forEachIndexed { index, destination ->
                val selected = !state.moreSelected && state.selected == destination
                NavigationBarItem(
                    selected = selected,
                    onClick = { state.select(destination) },
                    icon = { DestinationIcon(destination, selected) },
                    label = { Text(labels[index], style = labelStyle, maxLines = 1, softWrap = false) },
                )
            }
            NavigationBarItem(
                selected = state.moreSelected,
                onClick = state::openMore,
                icon = { Icon(Icons.Filled.MoreHoriz, contentDescription = null) },
                label = { Text(labels.last(), style = labelStyle, maxLines = 1, softWrap = false) },
            )
        }
    }
}

private val BarItemSpacing = 8.dp

/**
 * The navigation rail: every destination in order (ADR-0010 §4), within the window's insets. Its items scroll when the
 * window is too short for them all, as on a phone in landscape, and the selected one is scrolled into view.
 */
@Composable
private fun RootNavigationRail(state: RootNavigationState) {
    Surface(color = NavigationRailDefaults.ContainerColor, modifier = Modifier.fillMaxHeight()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                // Above the keyboard too, so that the items it would cover can still be scrolled into view.
                .windowInsetsPadding(
                    NavigationRailDefaults.windowInsets.union(WindowInsets.ime.only(WindowInsetsSides.Bottom)),
                )
                .widthIn(min = RailWidth)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 4.dp)
                .selectableGroup(),
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
                    label = { Text(stringResource(destination.label), textAlign = TextAlign.Center) },
                    modifier = Modifier.bringIntoViewRequester(requester),
                )
            }
        }
    }
}

private val RailWidth = 80.dp

@Composable
private fun DestinationIcon(destination: TopLevelDestination, selected: Boolean) {
    Icon(if (selected) destination.selectedIcon else destination.icon, contentDescription = null)
}
