package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The webhooks, newest first.
 */
@Serializable
public data class AdminWebhookList(
  @SerialName("items")
  public val items: List<AdminWebhook>,
)
