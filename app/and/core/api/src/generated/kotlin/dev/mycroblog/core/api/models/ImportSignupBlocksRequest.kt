package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A list of values to block sign-ups by, all with the same settings.
 */
@Serializable
public data class ImportSignupBlocksRequest(
  /**
   * `email_domain`: the domain of the email address and its subdomains. `email_hash`: one
   * email address, compared in lower case without the part of the local part from its first
   * `+`. `ip_range`: the client addresses in a CIDR prefix.
   */
  @SerialName("kind")
  public val kind: AdminSignupBlockKind,
  /**
   * The list: one value per line, or a CSV row whose first field is the value; blank
   * lines and lines starting with `#` are ignored. At most 10,000 values; the request body is at most 1 MiB, past which the answer is 413.
   */
  @SerialName("text")
  public val text: String,
  /**
   * `reject`: the sign-up is refused. `require_approval`: the registration starts pending
   * for a moderator, whatever the registration mode.
   */
  @SerialName("severity")
  public val severity: AdminSignupBlockSeverity,
  /**
   * The comment of every block added.
   */
  @SerialName("comment")
  public val comment: String? = null,
  /**
   * The seconds each block lasts, at most ten years (315360000); omitted, they last until
   * they are lifted.
   */
  @SerialName("expires_in")
  public val expiresIn: Int? = null,
)
