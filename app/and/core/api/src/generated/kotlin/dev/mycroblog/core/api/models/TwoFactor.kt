package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Where the authenticated user's two-factor authentication (TOTP) stands.
 */
@Serializable
public data class TwoFactor(
  /**
   * Whether signing in asks for a code after the password.
   */
  @SerialName("enabled")
  public val enabled: Boolean,
  /**
   * When the authenticator app was enrolled; omitted when not enabled.
   */
  @SerialName("enrolled_at")
  public val enrolledAt: Instant? = null,
  /**
   * How many recovery codes are unused; omitted when not enabled.
   */
  @SerialName("recovery_codes_remaining")
  public val recoveryCodesRemaining: Int? = null,
)
