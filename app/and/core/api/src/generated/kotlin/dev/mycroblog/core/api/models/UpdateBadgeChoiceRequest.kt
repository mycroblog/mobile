package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Whether to show the badge of the role on the authenticated user's profile.
 */
@Serializable
public data class UpdateBadgeChoiceRequest(
  @SerialName("displayed")
  public val displayed: Boolean,
)
