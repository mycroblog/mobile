package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The move of the user's own account: the account it moves to, and the confirmation, `password`,
 * `passkey`, or `external_confirmation`.
 */
@Serializable
public data class MoveOwnAccountRequest(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("target_account_id")
  public val targetAccountId: UUID,
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
