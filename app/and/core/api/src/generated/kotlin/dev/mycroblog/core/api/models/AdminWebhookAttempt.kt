package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The last attempt of a delivery.
 */
@Serializable
public data class AdminWebhookAttempt(
  /**
   * `succeeded`: a 2xx answer. `http_error`: another answer, a redirect included.
   * `timeout`: no answer within 15 seconds. `connection_error`: the connection failed.
   * `forbidden_destination`: the server may not reach the URL (a loopback or private address
   * that `webhook_allowed_destinations` does not list).
   */
  @SerialName("outcome")
  public val outcome: AdminWebhookAttemptOutcome,
  /**
   * The status code of the answer; omitted without one.
   */
  @SerialName("status_code")
  public val statusCode: Int? = null,
  @SerialName("duration_ms")
  public val durationMs: Int,
  @SerialName("attempted_at")
  public val attemptedAt: Instant,
)
