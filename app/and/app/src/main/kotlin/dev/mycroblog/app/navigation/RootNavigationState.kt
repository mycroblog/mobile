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
 * list ([MoreKey]), and back from their root goes to that list. The "More" item keeps its place like the others: it
 * reopens the destination last opened from it ([moreDestination]), and opens the list when it is already selected.
 */
class RootNavigationState(
    /** The back stack of each top-level destination, whose root is the destination's key. */
    val stacks: Map<TopLevelDestination, NavBackStack<NavKey>>,
    /** The back stack of the "More" list. */
    val moreStack: NavBackStack<NavKey>,
    selected: MutableState<TopLevelDestination>,
    showingMore: MutableState<Boolean>,
    moreDestination: MutableState<TopLevelDestination?>,
    barLayout: State<Boolean>,
    hidden: State<Set<TopLevelDestination>>,
) {
    /** The top-level destination shown, or the one under the "More" list when [showingMore]. */
    var selected by selected
        private set

    /** Whether the navigation bar's "More" list is shown. */
    var showingMore by showingMore
        private set

    /** The destination the bar's "More" item reopens, the last one opened from the "More" list and not left by back. */
    var moreDestination by moreDestination
        private set

    /** Whether the navigation is the bar (compact width) rather than the rail (ADR-0010 §4). */
    val barLayout by barLayout

    /** The destinations not offered now, such as a timeline the server does not provide (ADR-0002 §6). */
    val hidden by hidden

    private val shown: List<TopLevelDestination>
        get() = TopLevelDestination.entries.filterNot { it in hidden }

    /** The destinations the bar holds: the first [BAR_DESTINATIONS] of those shown, in their order. */
    private val barItems: List<TopLevelDestination>
        get() = shown.take(BAR_DESTINATIONS)

    /** Whether the bar holds [this] destination, rather than the "More" list. */
    private val TopLevelDestination.inBar: Boolean
        get() = this in barItems

    /** The destinations the navigation shows as items: in the bar those that fit, in the rail all of them. */
    val items: List<TopLevelDestination>
        get() = if (barLayout) barItems else shown

    /** The destinations the "More" list opens: those shown after the bar's. */
    val moreItems: List<TopLevelDestination>
        get() = shown.drop(BAR_DESTINATIONS)

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
        if (!destination.inBar) moreDestination = destination
    }

    /** The bar's "More" item: back to where the user was under it, or to the list when it is already selected. */
    fun openMore() {
        val destination = moreDestination
        if (moreSelected || destination == null) showingMore = true else select(destination)
    }

    /**
     * What the Up button of the screen of [key] does, or null when it has none: a screen above the root of its
     * destination goes back, and so does the root of a destination opened from the "More" list.
     */
    fun navigateUp(key: NavKey): (() -> Unit)? {
        val root = TopLevelDestination.entries.firstOrNull { it.key == key }
        val hasUp = if (root == null) key != MoreKey else barLayout && !root.inBar
        return if (hasUp) ::back else null
    }

    /** Opens [key] over the screen shown, on the back stack of the selected destination. */
    fun navigate(key: NavKey) {
        showingMore = false
        stacks.getValue(selected).add(key)
    }

    /** Shows the top-level destination whose root is [key]; any other key opens over the screen shown. */
    fun selectKey(key: NavKey) {
        TopLevelDestination.entries.firstOrNull { it.key == key }?.let(::select) ?: navigate(key)
    }

    /** Shows Home at its root, as after the reader chose a server. */
    fun showHome() {
        val stack = stacks.getValue(selected)
        while (stack.size > 1) stack.removeAt(stack.lastIndex)
        stacks.getValue(TopLevelDestination.HOME).let { home -> while (home.size > 1) home.removeAt(home.lastIndex) }
        select(TopLevelDestination.HOME)
    }

    /** Leaves a destination that is no longer offered for Home. */
    fun leaveHidden() {
        if (selected in hidden) select(TopLevelDestination.HOME)
        if (moreDestination in hidden) moreDestination = null
    }

    /** One step back, as the back gesture or button asks. */
    fun back() {
        val stack = stacks.getValue(selected)
        when {
            selectedShown && stack.size > 1 -> stack.removeAt(stack.lastIndex)

            barLayout && !showingMore && !selected.inBar -> {
                showingMore = true
                moreDestination = null
            }

            else -> select(TopLevelDestination.HOME)
        }
    }
}

/**
 * The root's navigation state, saved across configuration changes and process death as keys, for the navigation bar's
 * layout when [barLayout] and the rail's otherwise, without the [hidden] destinations.
 */
@Composable
fun rememberRootNavigationState(
    barLayout: Boolean,
    hidden: Set<TopLevelDestination> = emptySet(),
): RootNavigationState {
    val stacks = TopLevelDestination.entries.associateWith { rememberNavBackStack(it.key) }
    val moreStack = rememberNavBackStack(MoreKey)
    val selected = rememberSaveable { mutableStateOf(TopLevelDestination.HOME) }
    val showingMore = rememberSaveable { mutableStateOf(false) }
    val moreDestination = rememberSaveable { mutableStateOf<TopLevelDestination?>(null) }
    val bar = rememberUpdatedState(barLayout)
    val hiddenState = rememberUpdatedState(hidden)
    return remember {
        RootNavigationState(stacks, moreStack, selected, showingMore, moreDestination, bar, hiddenState)
    }
}

/** How many destinations the navigation bar holds before its "More" item: five items at most (ADR-0010 §4). */
private const val BAR_DESTINATIONS = 4
