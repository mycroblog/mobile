package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A browser signed in to the authenticated user's account.
 */
@Serializable
public data class BrowserSession(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * Whether this is the browser that asks.
   */
  @SerialName("current")
  public val current: Boolean,
  /**
   * The IP address the browser signed in from, not where it is now. Absent when it is unknown
   * or was erased after its retention.
   */
  @SerialName("ip")
  public val ip: String? = null,
  /**
   * The `User-Agent` of the sign-in. Absent when it is unknown or was erased after its retention.
   */
  @SerialName("user_agent")
  public val userAgent: String? = null,
  /**
   * When the browser signed in.
   */
  @SerialName("signed_in_at")
  public val signedInAt: Instant,
  /**
   * When the browser was last used, exact to the hour. Absent when no use was recorded.
   */
  @SerialName("last_used_at")
  public val lastUsedAt: Instant? = null,
)
