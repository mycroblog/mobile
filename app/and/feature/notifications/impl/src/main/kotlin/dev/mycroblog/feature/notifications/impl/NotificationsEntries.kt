package dev.mycroblog.feature.notifications.impl

import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.core.designsystem.PlaceholderScreen
import dev.mycroblog.feature.notifications.api.NotificationsKey

/** The destinations of the notifications feature, for the app's NavDisplay. */
fun EntryProviderScope<NavKey>.notificationsEntries() {
    entry<NotificationsKey> { PlaceholderScreen(stringResource(R.string.notifications_title)) }
}
