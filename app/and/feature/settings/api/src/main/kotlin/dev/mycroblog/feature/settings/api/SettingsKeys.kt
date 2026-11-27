package dev.mycroblog.feature.settings.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/** The settings. */
@Serializable
data object SettingsKey : NavKey

/** The screen that looks up a server by its address and shows its instance, the first step of adding an account. */
@Serializable
data object ChooseServerKey : NavKey
