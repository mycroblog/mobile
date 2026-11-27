package dev.mycroblog.feature.bookmarks.impl

import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.core.designsystem.PlaceholderScreen
import dev.mycroblog.feature.bookmarks.api.BookmarksKey

/** The destinations of the bookmarks feature, for the app's NavDisplay. */
fun EntryProviderScope<NavKey>.bookmarksEntries() {
    entry<BookmarksKey> { PlaceholderScreen(stringResource(R.string.bookmarks_title)) }
}
