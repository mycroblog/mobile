package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Where one sign-in came from.
 */
@Serializable
public data class AdminAccountSignIn(
  /**
   * The IP address; omitted when unknown.
   */
  @SerialName("ip")
  public val ip: String? = null,
  /**
   * The User-Agent; omitted when unknown.
   */
  @SerialName("user_agent")
  public val userAgent: String? = null,
  @SerialName("signed_in_at")
  public val signedInAt: Instant,
)
