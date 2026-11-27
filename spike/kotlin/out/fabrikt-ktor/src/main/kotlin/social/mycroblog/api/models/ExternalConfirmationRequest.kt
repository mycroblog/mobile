package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The linked login provider to confirm an operation with.
 */
@Serializable
public data class ExternalConfirmationRequest(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("provider_id")
  public val providerId: UUID,
  /**
   * An operation that the user confirms: registering a passkey (`passkey_registration`),
   * changing or setting the password (`password_change`), removing it (`password_removal`),
   * deleting the account (`account_deletion`), deactivating it (`account_deactivation`), moving it (`account_move`), and linking an
   * account at a login provider (`link`).
   */
  @SerialName("operation")
  public val operation: ConfirmedOperation,
  /**
   * The path on this server, such as `/settings/linked-accounts`, that the login provider
   * sends the browser back to; the settings of the linked accounts when it is missing or not
   * such a path.
   */
  @SerialName("return_to")
  public val returnTo: String? = null,
)
