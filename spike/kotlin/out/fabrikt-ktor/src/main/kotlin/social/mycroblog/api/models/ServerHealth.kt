package social.mycroblog.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The health of the server. The values are fixed, machine-readable words, never
 * localized.
 */
@Serializable
public data class ServerHealth(
  /**
   * `ok` while the server is healthy, `unavailable` while the database is unreachable or its
   * schema is not applied, `maintenance` during the maintenance mode.
   */
  @SerialName("status")
  public val status: ServerHealthStatus,
  /**
   * When this process last checked the database; absent during the maintenance mode.
   */
  @SerialName("checked_at")
  public val checkedAt: Instant? = null,
)
