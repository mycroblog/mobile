package dev.mycroblog.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

/**
 * The back stacks of the root (ADR-0010 §2): one per top-level destination, so that each keeps its place when the
 * user moves between them, and the destination shown. Home is the start: back from the root of another destination
 * goes to Home, and back from Home leaves the app.
 *
 * In the navigation bar's layout ([barLayout]), the destinations the bar has no room for are opened from the "More"
 * list ([MoreKey]), and back from their root goes to that list.
 */
class RootNavigationState(
    /** The back stack of each top-level destination, whose root is the destination's key. */
    val stacks: Map<TopLevelDestination, NavBackStack<NavKey>>,
    /** The back stack of the "More" list. */
    val moreStack: NavBackStack<NavKey>,
    selected: MutableState<TopLevelDestination>,
    showingMore: MutableState<Boolean>,
    barLayout: State<Boolean>,
) {
    /** The top-level destination shown, or the one under the "More" list when [showingMore]. */
    var selected by selected
        private set

    /** Whether the navigation bar's "More" list is shown. */
    var showingMore by showingMore
        private set

    /** Whether the navigation is the bar (compact width) rather than the rail (ADR-0010 §4). */
    val barLayout by barLayout

    /** The destinations the navigation shows as items: in the bar those that fit, in the rail all of them. */
    val items: List<TopLevelDestination>
        get() = if (barLayout) TopLevelDestination.entries.filter { it.inBar } else TopLevelDestination.entries

    /** The destinations the "More" list opens. */
    val moreItems: List<TopLevelDestination> = TopLevelDestination.entries.filterNot { it.inBar }

    /** Whether the bar's "More" item is the selected one. */
    val moreSelected: Boolean
        get() = barLayout && (showingMore || !selected.inBar)

    /** Whether the selected destination's back stack is shown (and not the "More" list over it). */
    private val selectedShown: Boolean
        get() = !(barLayout && showingMore)

    /** The back stacks that the root shows, bottom first: Home's, then the "More" list's, the selected one's. */
    val shownStacks: List<NavBackStack<NavKey>>
        get() = buildList {
            add(stacks.getValue(TopLevelDestination.HOME))
            if (moreSelected) add(moreStack)
            if (selectedShown && selected != TopLevelDestination.HOME) add(stacks.getValue(selected))
        }

    fun select(destination: TopLevelDestination) {
        selected = destination
        showingMore = false
    }

    fun openMore() {
        showingMore = true
    }

    /** One step back, as the back gesture or button asks. */
    fun back() {
        val stack = stacks.getValue(selected)
        when {
            selectedShown && stack.size > 1 -> stack.removeAt(stack.lastIndex)
            barLayout && !showingMore && !selected.inBar -> showingMore = true
            else -> select(TopLevelDestination.HOME)
        }
    }
}

/**
 * The root's navigation state, saved across configuration changes and process death as keys, for the navigation bar's
 * layout when [barLayout] and the rail's otherwise.
 */
@Composable
fun rememberRootNavigationState(barLayout: Boolean): RootNavigationState {
    val stacks = TopLevelDestination.entries.associateWith { rememberNavBackStack(it.key) }
    val moreStack = rememberNavBackStack(MoreKey)
    val selected = rememberSaveable { mutableStateOf(TopLevelDestination.HOME) }
    val showingMore = rememberSaveable { mutableStateOf(false) }
    val bar = rememberUpdatedState(barLayout)
    return remember { RootNavigationState(stacks, moreStack, selected, showingMore, bar) }
}
