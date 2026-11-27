package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of statuses.
 */
@Serializable
public data class StatusPage(
  /**
   * An opaque pagination cursor. Clients pass it back unchanged and never parse it.
   */
  @SerialName("next_cursor")
  public val nextCursor: String? = null,
  /**
   * The statuses of the page.
   */
  @SerialName("items")
  public val items: List<Status>,
)
