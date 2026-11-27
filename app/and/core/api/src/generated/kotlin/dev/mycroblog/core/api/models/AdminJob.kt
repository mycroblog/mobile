package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A job of the job queue.
 */
@Serializable
public data class AdminJob(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The job type, such as `federation.deliver`.
   */
  @SerialName("type")
  public val type: String,
  /**
   * The state of a job. `queued`: it has not run yet and waits for its `run_at` or a worker.
   * `running`: a worker holds it. `retrying`: it ran and waits to run again. `dead`: it
   * failed for good (its handler gave up, or it used up its attempts).
   */
  @SerialName("state")
  public val state: AdminJobState,
  /**
   * The job's payload, a JSON document as text.
   */
  @SerialName("payload")
  public val payload: String,
  @SerialName("idempotency_key")
  public val idempotencyKey: String? = null,
  @SerialName("ordering_key")
  public val orderingKey: String? = null,
  /**
   * The number of runs the job had, the one in progress included.
   */
  @SerialName("attempts")
  public val attempts: Int,
  /**
   * Why the last run failed; omitted for a job whose runs did not fail.
   */
  @SerialName("last_error")
  public val lastError: String? = null,
  /**
   * When a pending job is due; omitted for a dead job.
   */
  @SerialName("run_at")
  public val runAt: Instant? = null,
  /**
   * The worker that runs a `running` job.
   */
  @SerialName("worker")
  public val worker: String? = null,
  /**
   * When the lease of a `running` job ends; another worker may run it after.
   */
  @SerialName("locked_until")
  public val lockedUntil: Instant? = null,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When a dead job failed.
   */
  @SerialName("failed_at")
  public val failedAt: Instant? = null,
)
