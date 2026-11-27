package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How long a job type has kept a job waiting.
 */
@Serializable
public data class AdminJobWait(
  @SerialName("type")
  public val type: String,
  /**
   * The earliest `run_at` among the due jobs of the type that no worker holds.
   */
  @SerialName("oldest_ready_at")
  public val oldestReadyAt: Instant,
)
