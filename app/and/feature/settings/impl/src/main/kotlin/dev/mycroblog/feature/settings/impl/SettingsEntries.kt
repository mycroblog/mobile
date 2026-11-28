package dev.mycroblog.feature.settings.impl

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.core.model.ServerAddress
import dev.mycroblog.feature.settings.api.ChooseServerKey
import dev.mycroblog.feature.settings.api.SettingsKey

/**
 * The destinations of the settings feature, for the app's NavDisplay. [navigateUp] says what the Up button
 * of the screen of a key does, or null when it has none, [navigate] opens a key over the shown destination, and
 * [readServer] makes a server the one the reader reads without an account.
 */
fun EntryProviderScope<NavKey>.settingsEntries(
    navigateUp: (NavKey) -> (() -> Unit)?,
    navigate: (NavKey) -> Unit,
    readServer: (ServerAddress) -> Unit,
) {
    entry<SettingsKey> { key ->
        SettingsRoute(
            onChangeServer = { navigate(ChooseServerKey) },
            onUp = navigateUp(key),
        )
    }
    entry<ChooseServerKey> { key ->
        ChooseServerRoute(onUp = navigateUp(key), onRead = readServer)
    }
}
