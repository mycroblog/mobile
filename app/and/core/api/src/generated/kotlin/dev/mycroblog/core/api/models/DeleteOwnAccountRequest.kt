package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The confirmation of the deletion of the user's own account: `password`, `passkey`, or
 * `external_confirmation`.
 */
@Serializable
public data class DeleteOwnAccountRequest(
  /**
   * The user's current password.
   */
  @SerialName("password")
  public val password: String? = null,
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
)
