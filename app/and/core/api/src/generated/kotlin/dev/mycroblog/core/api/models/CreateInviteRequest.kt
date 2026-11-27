package dev.mycroblog.core.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new invite.
 */
@Serializable
public data class CreateInviteRequest(
  /**
   * How many sign-ups the invite allows; omitted for no limit.
   */
  @SerialName("max_uses")
  public val maxUses: Int? = null,
  /**
   * How many seconds the invite works; omitted for an invite that never expires.
   */
  @SerialName("expires_in")
  public val expiresIn: Int? = null,
)
