package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The attempts that failed since the last success; omitted when the last attempt
 * succeeded. The server disables the webhook once they span 72 hours and number 20.
 */
@Serializable
public data class AdminWebhookFailure(
  @SerialName("consecutive_failures")
  public val consecutiveFailures: Int,
  @SerialName("first_failed_at")
  public val firstFailedAt: Instant,
)
