package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A run of a job that did not succeed.
 */
@Serializable
public data class AdminJobAttempt(
  /**
   * The number of the run, from 1.
   */
  @SerialName("attempt")
  public val attempt: Int,
  /**
   * The job type.
   */
  @SerialName("type")
  public val type: String,
  /**
   * `retry`: the job runs again. `dead`: it failed for good.
   */
  @SerialName("outcome")
  public val outcome: AdminJobAttemptOutcome,
  /**
   * Why the run failed, at most 2,000 bytes.
   */
  @SerialName("error")
  public val error: String,
  /**
   * The worker that ran it.
   */
  @SerialName("worker")
  public val worker: String,
  @SerialName("started_at")
  public val startedAt: Instant,
  @SerialName("finished_at")
  public val finishedAt: Instant,
)
