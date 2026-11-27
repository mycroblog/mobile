package social.mycroblog.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The caps and defaults of the invites that members create, from the instance settings
 * `member_invite_max_active`, `member_invite_max_uses`, and `member_invite_max_lifetime_seconds`.
 */
@Serializable
public data class MemberInviteLimits(
  /**
   * How many usable invites one member may hold at once; 0 lets members create none.
   */
  @SerialName("max_active")
  public val maxActive: Int,
  /**
   * The most sign-ups one invite may allow.
   */
  @SerialName("max_uses")
  public val maxUses: Int,
  /**
   * The longest time, in seconds, that one invite may work.
   */
  @SerialName("max_expires_in")
  public val maxExpiresIn: Int,
  /**
   * The sign-ups of an invite created without `max_uses`.
   */
  @SerialName("default_max_uses")
  public val defaultMaxUses: Int,
  /**
   * The seconds an invite created without `expires_in` works.
   */
  @SerialName("default_expires_in")
  public val defaultExpiresIn: Int,
)
