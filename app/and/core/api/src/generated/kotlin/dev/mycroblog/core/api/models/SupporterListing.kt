package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Whether the user is on the supporter list.
 */
@Serializable
public data class SupporterListing(
  @SerialName("listed")
  public val listed: Boolean,
)
