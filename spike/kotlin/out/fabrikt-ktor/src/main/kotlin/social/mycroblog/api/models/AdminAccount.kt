package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An account as the moderators see it. It never includes an email or IP address.
 */
@Serializable
public data class AdminAccount(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * An account, as clients show it.
   */
  @SerialName("account")
  public val account: Account,
  /**
   * Whether the account is on this server.
   */
  @SerialName("local")
  public val local: Boolean,
  /**
   * The state of an account, the first that applies: `deleted` (deleted here or on its
   * server), `suspended` (a `suspend` or `ban`), `disabled` (a `disable_login`),
   * `deactivated` (by its owner), `rejected` and `pending` (the registration of a local account), `limited` (a `limit`), and
   * `active`.
   */
  @SerialName("state")
  public val state: AdminAccountState,
  /**
   * What the account's own actions take away now, in a fixed order.
   * The domain actions on its domain are listed apart.
   */
  @SerialName("restrictions")
  public val restrictions: List<AdminAccountRestriction>,
  /**
   * Where the registration of a local account stands; a user made with the command line is
   * `approved`. Omitted for a remote account.
   */
  @SerialName("registration_state")
  public val registrationState: RegistrationState? = null,
)
