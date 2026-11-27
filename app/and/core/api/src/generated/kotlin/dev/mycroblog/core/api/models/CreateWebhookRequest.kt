package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new webhook.
 */
@Serializable
public data class CreateWebhookRequest(
  /**
   * An absolute `https` (or `http`) URL of at most 2048 bytes, without user info or a
   * fragment. A destination on loopback or a private network is reached only when the
   * operator allows it (`webhook_allowed_destinations`).
   */
  @SerialName("url")
  public val url: String,
  /**
   * What the webhook is for, up to 200 characters.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * The events to subscribe to, at least one.
   */
  @SerialName("events")
  public val events: List<WebhookEventType>,
)
