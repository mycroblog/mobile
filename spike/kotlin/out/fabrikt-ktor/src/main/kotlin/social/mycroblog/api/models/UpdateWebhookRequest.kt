package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The changes to a webhook; what is omitted stays.
 */
@Serializable
public data class UpdateWebhookRequest(
  @SerialName("url")
  public val url: String? = null,
  /**
   * The new description; empty removes it.
   */
  @SerialName("description")
  public val description: String? = null,
  @SerialName("events")
  public val events: List<WebhookEventType>? = null,
)
