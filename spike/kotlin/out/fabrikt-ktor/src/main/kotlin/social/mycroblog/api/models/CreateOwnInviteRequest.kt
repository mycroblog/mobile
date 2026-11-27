package social.mycroblog.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new invite of the user's own, within the caps of the instance settings.
 */
@Serializable
public data class CreateOwnInviteRequest(
  /**
   * How many sign-ups the invite allows, up to `member_invite_max_uses`; 1 (or the cap when it is lower) when omitted.
   */
  @SerialName("max_uses")
  public val maxUses: Int? = null,
  /**
   * How many seconds the invite works, up to `member_invite_max_lifetime_seconds`; 7 days (or the cap when it is shorter) when omitted.
   */
  @SerialName("expires_in")
  public val expiresIn: Int? = null,
)
