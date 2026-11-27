package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A code that confirms an operation on two-factor authentication.
 */
@Serializable
public data class TwoFactorCodeRequest(
  /**
   * A six-digit code of the app or an unused recovery code.
   */
  @SerialName("code")
  public val code: String,
)
