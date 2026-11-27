package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A browser's push subscription and the notification types to push to it.
 */
@Serializable
public data class PushSubscriptionRequest(
  /**
   * The push service URL (`PushSubscription.endpoint`), an `https` URL.
   */
  @SerialName("endpoint")
  public val endpoint: String,
  /**
   * The keys of a browser's push subscription (RFC 8291 §3), in base64url.
   */
  @SerialName("keys")
  public val keys: PushSubscriptionKeys,
  /**
   * The notification types to push, each at most once; empty pushes none. A third-party
   * client's subscription leaves out the moderator notification types.
   */
  @SerialName("alerts")
  public val alerts: List<NotificationType>,
)
