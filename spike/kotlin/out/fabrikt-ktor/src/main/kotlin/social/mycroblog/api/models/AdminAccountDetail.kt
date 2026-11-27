package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The details of an account.
 */
@Serializable
public data class AdminAccountDetail(
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
  /**
   * The registration application of a local account.
   */
  @SerialName("registration")
  public val registration: AdminAccountRegistration? = null,
  /**
   * Whether the primary email address of a local account is verified; omitted when
   * it has none.
   */
  @SerialName("email_verified")
  public val emailVerified: Boolean? = null,
  /**
   * The suppression of the primary email address of a local account; omitted when it
   * is not suppressed. A moderator lifts it with
   * `POST /admin/accounts/{id}/email_suppression/lift`.
   */
  @SerialName("email_suppression")
  public val emailSuppression: EmailSuppression? = null,
  /**
   * Whether the user of a local account signs in with two-factor authentication;
   * omitted for a remote account.
   */
  @SerialName("two_factor_enabled")
  public val twoFactorEnabled: Boolean? = null,
  /**
   * The keys of the user's roles, highest position first; empty for a remote account.
   */
  @SerialName("roles")
  public val roles: List<String>,
  /**
   * The newest 20 actions on the account, newest first.
   */
  @SerialName("actions")
  public val actions: List<AdminAccountAction>,
  /**
   * The newest 20 reports of the account, newest first; omitted unless the viewer also has
   * the `manage_reports` permission.
   */
  @SerialName("reports")
  public val reports: List<AdminReport>? = null,
)
