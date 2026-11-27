package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The relays, in registration order.
 */
@Serializable
public data class AdminRelayList(
  @SerialName("items")
  public val items: List<AdminRelay>,
)
