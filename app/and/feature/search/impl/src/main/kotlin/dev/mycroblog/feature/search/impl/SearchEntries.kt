package dev.mycroblog.feature.search.impl

import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.core.designsystem.PlaceholderScreen
import dev.mycroblog.feature.search.api.SearchKey

/** The destinations of the search feature, for the app's NavDisplay. */
fun EntryProviderScope<NavKey>.searchEntries() {
    entry<SearchKey> { PlaceholderScreen(stringResource(R.string.search_title)) }
}
