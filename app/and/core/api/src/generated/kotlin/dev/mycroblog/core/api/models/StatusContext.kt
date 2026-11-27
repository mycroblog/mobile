package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The thread around a status.
 */
@Serializable
public data class StatusContext(
  /**
   * The statuses the status replies to, oldest first.
   */
  @SerialName("ancestors")
  public val ancestors: List<Status>,
  /**
   * The replies to the status and their replies, depth first in posting order.
   */
  @SerialName("descendants")
  public val descendants: List<Status>,
)
