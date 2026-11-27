package dev.mycroblog.feature.search.impl

import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.core.designsystem.PlaceholderScreen
import dev.mycroblog.feature.search.api.SearchKey

/**
 * The destinations of the search feature, for the app's NavDisplay. [navigateUp] says what the Up button
 * of the screen of a key does, or null when it has none.
 */
fun EntryProviderScope<NavKey>.searchEntries(navigateUp: (NavKey) -> (() -> Unit)?) {
    entry<SearchKey> { key ->
        PlaceholderScreen(stringResource(R.string.search_title), onUp = navigateUp(key))
    }
}
