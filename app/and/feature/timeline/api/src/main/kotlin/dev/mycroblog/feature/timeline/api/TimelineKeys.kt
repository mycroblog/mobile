package dev.mycroblog.feature.timeline.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/** The home timeline. */
@Serializable
data object HomeTimelineKey : NavKey

/** The server's local timeline. */
@Serializable
data object LocalTimelineKey : NavKey

/** The server's federated timeline. */
@Serializable
data object FederatedTimelineKey : NavKey
