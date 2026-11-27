package dev.mycroblog.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.app.R
import dev.mycroblog.feature.bookmarks.api.BookmarksKey
import dev.mycroblog.feature.messages.api.MessagesKey
import dev.mycroblog.feature.notifications.api.NotificationsKey
import dev.mycroblog.feature.profile.api.ProfileKey
import dev.mycroblog.feature.search.api.SearchKey
import dev.mycroblog.feature.settings.api.SettingsKey
import dev.mycroblog.feature.timeline.api.FederatedTimelineKey
import dev.mycroblog.feature.timeline.api.HomeTimelineKey
import dev.mycroblog.feature.timeline.api.LocalTimelineKey

/**
 * The top-level destinations of ADR-0002 §6, in its order. The navigation rail shows them all; the navigation bar,
 * which holds at most five items (ADR-0010 §4), shows those [inBar] and a "More" item that opens the rest.
 *
 * The local and federated timelines are shown while the app knows no server; once it does, only the timelines the
 * server provides are shown (ADR-0002 §6).
 */
enum class TopLevelDestination(
    val key: NavKey,
    @param:StringRes val label: Int,
    val selectedIcon: ImageVector,
    val icon: ImageVector,
    val inBar: Boolean,
) {
    HOME(HomeTimelineKey, R.string.destination_home, Icons.Filled.Home, Icons.Outlined.Home, inBar = true),
    LOCAL(LocalTimelineKey, R.string.destination_local, Icons.Filled.Groups, Icons.Outlined.Groups, inBar = false),
    FEDERATED(
        FederatedTimelineKey, R.string.destination_federated, Icons.Filled.Public, Icons.Outlined.Public, inBar = false,
    ),
    NOTIFICATIONS(
        NotificationsKey, R.string.destination_notifications, Icons.Filled.Notifications, Icons.Outlined.Notifications,
        inBar = true,
    ),
    SEARCH(SearchKey, R.string.destination_search, Icons.Filled.Search, Icons.Outlined.Search, inBar = true),
    MESSAGES(MessagesKey, R.string.destination_messages, Icons.Filled.Mail, Icons.Outlined.Mail, inBar = true),
    PROFILE(ProfileKey, R.string.destination_profile, Icons.Filled.Person, Icons.Outlined.Person, inBar = false),
    BOOKMARKS(
        BookmarksKey, R.string.destination_bookmarks, Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder, inBar = false,
    ),
    SETTINGS(SettingsKey, R.string.destination_settings, Icons.Filled.Settings, Icons.Outlined.Settings, inBar = false),
}
