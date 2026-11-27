package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Where the browser goes to sign in at a login provider.
 */
@Serializable
public data class ExternalAuthorizationStart(
  /**
   * The URL on this server that sends the browser on to the provider.
   */
  @SerialName("start_url")
  public val startUrl: String,
  /**
   * When `start_url` stops working.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant,
)
