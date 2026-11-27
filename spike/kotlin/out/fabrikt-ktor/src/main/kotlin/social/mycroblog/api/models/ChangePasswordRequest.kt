package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A change of the user's password, confirmed with `current_password`, `passkey`, or
 * `external_confirmation`.
 */
@Serializable
public data class ChangePasswordRequest(
  /**
   * The user's current password, which confirms the change.
   */
  @SerialName("current_password")
  public val currentPassword: String? = null,
  /**
   * The browser's answer to a confirmation with a passkey, which confirms an operation.
   */
  @SerialName("passkey")
  public val passkey: PasskeyAssertion? = null,
  /**
   * The `external_confirmation` token of a re-authentication with a linked login provider
   * (`POST /linked_identities/confirmation`) for this operation, in place of the password.
   */
  @SerialName("external_confirmation")
  public val externalConfirmation: String? = null,
  /**
   * The new password, 8 to 128 characters.
   */
  @SerialName("new_password")
  public val newPassword: String,
)
