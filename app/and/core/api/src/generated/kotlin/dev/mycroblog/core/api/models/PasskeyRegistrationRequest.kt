package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The name of a new passkey and what confirms it: the user's `password`, a `passkey` of
 * the user, or an `external_confirmation`.
 */
@Serializable
public data class PasskeyRegistrationRequest(
  /**
   * What the user calls the passkey; spaces around it are dropped.
   */
  @SerialName("name")
  public val name: String,
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
