package dev.mycroblog.feature.profile.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/** The profile of the signed-in account. */
@Serializable
data object ProfileKey : NavKey
