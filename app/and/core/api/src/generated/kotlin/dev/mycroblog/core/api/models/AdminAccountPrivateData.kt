package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The email address and the IP addresses of a local account. Showing them is
 * recorded in the audit log.
 */
@Serializable
public data class AdminAccountPrivateData(
  /**
   * The primary email address; omitted when there is none.
   */
  @SerialName("email")
  public val email: String? = null,
  /**
   * Whether the primary email address is verified; omitted with `email`.
   */
  @SerialName("email_verified")
  public val emailVerified: Boolean? = null,
  /**
   * The IP address the account registered from; omitted for a registration from an
   * unknown address, by the command line, or past the retention.
   */
  @SerialName("registration_ip")
  public val registrationIp: String? = null,
  /**
   * When the account registered from `registration_ip`; omitted with it.
   */
  @SerialName("registered_at")
  public val registeredAt: Instant? = null,
  /**
   * The newest 20 sign-ins within the retention, newest first; `GET .../sign_ins` pages
   * through them all.
   */
  @SerialName("sign_ins")
  public val signIns: List<AdminAccountSignIn>,
  /**
   * For each IP address of `sign_ins` and `registration_ip`, newest first, up to 20
   * other accounts that registered or signed in from it, newest first; `GET
   * .../same_ip_accounts` pages through them all.
   */
  @SerialName("same_ip_accounts")
  public val sameIpAccounts: List<AdminSameIpAccounts>,
)
