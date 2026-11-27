package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new block on sign-ups.
 */
@Serializable
public data class CreateSignupBlockRequest(
  /**
   * `email_domain`: the domain of the email address and its subdomains. `email_hash`: one
   * email address, compared in lower case without the part of the local part from its first
   * `+`. `ip_range`: the client addresses in a CIDR prefix.
   */
  @SerialName("kind")
  public val kind: AdminSignupBlockKind,
  /**
   * A domain such as `spam.example` or `例え.jp` (`email_domain`), an email address or
   * the hex SHA-256 of a canonical one (`email_hash`), or a CIDR prefix such as
   * `192.0.2.0/24` or a single IP address (`ip_range`).
   */
  @SerialName("value")
  public val `value`: String,
  /**
   * `reject`: the sign-up is refused. `require_approval`: the registration starts pending
   * for a moderator, whatever the registration mode.
   */
  @SerialName("severity")
  public val severity: AdminSignupBlockSeverity,
  @SerialName("comment")
  public val comment: String? = null,
  /**
   * The seconds the block lasts, at most ten years (315360000); omitted, it lasts until it
   * is lifted.
   */
  @SerialName("expires_in")
  public val expiresIn: Int? = null,
)
