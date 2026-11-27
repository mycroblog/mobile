package dev.mycroblog.feature.settings.impl

import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.core.designsystem.PlaceholderScreen
import dev.mycroblog.feature.settings.api.SettingsKey

/** The destinations of the settings feature, for the app's NavDisplay. */
fun EntryProviderScope<NavKey>.settingsEntries() {
    entry<SettingsKey> { PlaceholderScreen(stringResource(R.string.settings_title)) }
}
