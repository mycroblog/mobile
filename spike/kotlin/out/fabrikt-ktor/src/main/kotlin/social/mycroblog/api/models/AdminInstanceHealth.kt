package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A remote server, whether deliveries go to it, and what it runs.
 */
@Serializable
public data class AdminInstanceHealth(
  @SerialName("domain")
  public val domain: String,
  /**
   * `available` servers get every delivery; `unavailable` ones only `Follow`, `Accept`, and
   * `Reject`.
   */
  @SerialName("state")
  public val state: AdminInstanceState,
  /**
   * When the server entered its state; omitted for a server without failures.
   */
  @SerialName("state_changed_at")
  public val stateChangedAt: Instant? = null,
  /**
   * The number of distinct UTC days of the latest run of failed deliveries.
   */
  @SerialName("failure_days")
  public val failureDays: Int,
  /**
   * The latest of those days; omitted without failures.
   */
  @SerialName("last_failure_day")
  public val lastFailureDay: LocalDate? = null,
  /**
   * When this instance first stored a document of the server; omitted for a server known
   * only from deliveries.
   */
  @SerialName("first_seen_at")
  public val firstSeenAt: Instant? = null,
  /**
   * The software a remote server last reported in its NodeInfo; omitted for a server whose
   * NodeInfo was never read.
   */
  @SerialName("software")
  public val software: AdminInstanceSoftware? = null,
)
