package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The current text of a key, the same in every locale.
 */
@Serializable
public data class AdminInstanceText(
  /**
   * The key of an instance text: the name (one line), the short and full descriptions,
   * the terms of service and the privacy policy (Markdown), the alternative texts of the
   * preview image and the thumbnail, and the commerce disclosures of the supporter plans
   * (Markdown, shown at `/about/commerce`; the server does not vouch for what they say).
   */
  @SerialName("key")
  public val key: InstanceTextKey,
  /**
   * The text: Markdown for `terms` and `privacy_policy`, plain text for the other keys.
   * Empty only in the answer to clearing it.
   */
  @SerialName("body")
  public val body: String,
  /**
   * When the revision was recorded.
   */
  @SerialName("revised_at")
  public val revisedAt: Instant,
)
