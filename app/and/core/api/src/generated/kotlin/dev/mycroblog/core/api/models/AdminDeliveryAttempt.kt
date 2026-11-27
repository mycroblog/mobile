package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One attempt of a delivery.
 */
@Serializable
public data class AdminDeliveryAttempt(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * `succeeded`: the server accepted the activity. `retryable_failure`: it failed in a way
   * that is tried again. `permanent_failure`: the server refused it, and it is not sent
   * again. `skipped`: nothing was sent, for the reason in `error`.
   */
  @SerialName("outcome")
  public val outcome: AdminDeliveryAttemptOutcome,
  /**
   * The signature scheme of the request that got the response; absent without a response.
   */
  @SerialName("signature_scheme")
  public val signatureScheme: AdminDeliveryAttemptSignatureScheme? = null,
  /**
   * The status code of the response; absent without a response.
   */
  @SerialName("status_code")
  public val statusCode: Int? = null,
  /**
   * Why the attempt failed without a response, or why nothing was sent.
   */
  @SerialName("error")
  public val error: String? = null,
  @SerialName("attempted_at")
  public val attemptedAt: Instant,
)
