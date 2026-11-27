package dev.mycroblog.feature.profile.impl

import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.core.designsystem.PlaceholderScreen
import dev.mycroblog.feature.profile.api.ProfileKey

/**
 * The destinations of the profile feature, for the app's NavDisplay. [navigateUp] says what the Up button
 * of the screen of a key does, or null when it has none.
 */
fun EntryProviderScope<NavKey>.profileEntries(navigateUp: (NavKey) -> (() -> Unit)?) {
    entry<ProfileKey> { key ->
        PlaceholderScreen(stringResource(R.string.profile_title), onUp = navigateUp(key))
    }
}
