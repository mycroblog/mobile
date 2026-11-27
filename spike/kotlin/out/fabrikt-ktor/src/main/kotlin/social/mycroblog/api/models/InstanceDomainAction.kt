package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderation action in effect on a remote domain and its subdomains, as the instance lists it publicly.
 */
@Serializable
public data class InstanceDomainAction(
  /**
   * The domain, in IDNA A-labels. When the moderators chose to obfuscate it, the
   * characters of its middle half other than dots are replaced with `*`.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * The SHA-256 of the domain (not obfuscated) in lower-case hexadecimal, to check a known domain against an obfuscated item.
   */
  @SerialName("digest")
  public val digest: String,
  /**
   * `suspend`: no federation with the domain at all. `limit`: its posts stay off the
   * public timelines and its accounts reach only their followers.
   */
  @SerialName("kind")
  public val kind: InstanceDomainActionKind,
  /**
   * The public reason for the action; omitted when there is none or the viewer may not read it.
   */
  @SerialName("comment")
  public val comment: String? = null,
)
