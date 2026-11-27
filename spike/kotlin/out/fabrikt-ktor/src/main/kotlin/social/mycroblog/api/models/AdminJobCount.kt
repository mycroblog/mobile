package social.mycroblog.api.models

import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The number of jobs of one type in one state.
 */
@Serializable
public data class AdminJobCount(
  /**
   * The state of a job. `queued`: it has not run yet and waits for its `run_at` or a worker.
   * `running`: a worker holds it. `retrying`: it ran and waits to run again. `dead`: it
   * failed for good (its handler gave up, or it used up its attempts).
   */
  @SerialName("state")
  public val state: AdminJobState,
  @SerialName("type")
  public val type: String,
  @SerialName("count")
  public val count: Long,
)
