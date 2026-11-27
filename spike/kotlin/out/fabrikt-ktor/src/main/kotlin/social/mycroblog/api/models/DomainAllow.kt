package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A domain of the allowlist of federation, which covers its subdomains. While the instance
 * setting `federation_scope` is `allowlist`, the instance federates only with the allowed
 * domains; the domain actions on an allowed domain still apply. While it is `open`, the
 * allowlist changes nothing.
 */
@Serializable
public data class DomainAllow(
  /**
   * The domain, in canonical form (lower case, IDNA A-labels); the key of the allow.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * What the moderators keep about the allow, never published; empty for none.
   */
  @SerialName("comment")
  public val comment: String,
  /**
   * When the domain was allowed.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
)
