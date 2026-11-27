package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The state of the job queue.
 */
@Serializable
public data class AdminQueue(
  /**
   * The queue the server runs, the `queue` setting (`db`, `inline`, `cloudtasks`).
   */
  @SerialName("adapter")
  public val adapter: String,
  /**
   * Whether the queue lists its pending jobs (`queued`, `running`, `retrying`).
   */
  @SerialName("pending_listing")
  public val pendingListing: Boolean,
  /**
   * The jobs by state (`queued`, `running`, `retrying`, `dead`) then type; states and
   * types without jobs are left out. Empty without `pending_listing`.
   */
  @SerialName("counts")
  public val counts: List<AdminJobCount>,
  /**
   * How long each type with due jobs that no worker holds has kept a job waiting, by type.
   * Empty without `pending_listing`.
   */
  @SerialName("waits")
  public val waits: List<AdminJobWait>,
  /**
   * The earliest `run_at` among the due jobs that no worker holds: how long the queue
   * has kept a job waiting. Omitted when no job waits, or without `pending_listing`.
   */
  @SerialName("oldest_ready_at")
  public val oldestReadyAt: Instant? = null,
)
