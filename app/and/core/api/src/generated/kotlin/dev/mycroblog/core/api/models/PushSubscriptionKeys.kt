package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The keys of a browser's push subscription (RFC 8291 §3), in base64url.
 */
@Serializable
public data class PushSubscriptionKeys(
  /**
   * The browser's P-256 public key, an uncompressed point.
   */
  @SerialName("p256dh")
  public val p256dh: String,
  /**
   * The 16-byte authentication secret.
   */
  @SerialName("auth")
  public val auth: String,
)
