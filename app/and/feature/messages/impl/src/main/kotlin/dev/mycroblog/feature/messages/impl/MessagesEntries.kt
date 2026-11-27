package dev.mycroblog.feature.messages.impl

import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.core.designsystem.PlaceholderScreen
import dev.mycroblog.feature.messages.api.MessagesKey

/** The destinations of the messages feature, for the app's NavDisplay. */
fun EntryProviderScope<NavKey>.messagesEntries() {
    entry<MessagesKey> { PlaceholderScreen(stringResource(R.string.messages_title)) }
}
