package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The login provider to link an account at, and what confirms it: the user's `password`, a
 * `passkey` of the user, or an `external_confirmation`.
 */
@Serializable
public data class LinkIdentityRequest(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("provider_id")
  public val providerId: UUID,
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
  /**
   * The path on this server, such as `/settings/linked-accounts`, that the login provider
   * sends the browser back to; the settings of the linked accounts when it is missing or not
   * such a path.
   */
  @SerialName("return_to")
  public val returnTo: String? = null,
)
