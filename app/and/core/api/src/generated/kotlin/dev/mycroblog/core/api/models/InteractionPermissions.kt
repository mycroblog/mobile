package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the authenticated user may do with a status, by each kind of interaction.
 */
@Serializable
public data class InteractionPermissions(
  /**
   * Whether the user may reply to the status.
   */
  @SerialName("reply")
  public val reply: InteractionPermission,
  /**
   * Whether the user may boost the status.
   */
  @SerialName("announce")
  public val announce: InteractionPermission,
  /**
   * Whether the user may quote the status.
   */
  @SerialName("quote")
  public val quote: InteractionPermission,
)
