package dev.mycroblog.core.api.models

import java.net.URI
import java.util.UUID
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The Web Push subscription of a grant.
 */
@Serializable
public data class PushSubscription(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The push service URL of the browser's subscription.
   */
  @Contextual
  @SerialName("endpoint")
  public val endpoint: URI,
  /**
   * The notification types that are pushed, in a stable order.
   */
  @SerialName("alerts")
  public val alerts: List<NotificationType>,
  /**
   * The instance's VAPID public key now. When it differs from the key the browser
   * subscribed with, the subscription receives nothing; subscribe again.
   */
  @SerialName("server_key")
  public val serverKey: String,
)
