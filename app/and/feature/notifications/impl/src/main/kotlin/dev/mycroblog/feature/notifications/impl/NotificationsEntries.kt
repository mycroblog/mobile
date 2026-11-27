package dev.mycroblog.feature.notifications.impl

import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.core.designsystem.PlaceholderScreen
import dev.mycroblog.feature.notifications.api.NotificationsKey

/**
 * The destinations of the notifications feature, for the app's NavDisplay. [navigateUp] says what the Up button
 * of the screen of a key does, or null when it has none.
 */
fun EntryProviderScope<NavKey>.notificationsEntries(navigateUp: (NavKey) -> (() -> Unit)?) {
    entry<NotificationsKey> { key ->
        PlaceholderScreen(stringResource(R.string.notifications_title), onUp = navigateUp(key))
    }
}
