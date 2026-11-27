package social.mycroblog.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A message to a webhook and its attempts.
 */
@Serializable
public data class AdminWebhookDelivery(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The `webhook-id` of the message, the same on every attempt.
   */
  @SerialName("message_id")
  public val messageId: String,
  /**
   * The type of a message: a `WebhookEventType`, or `webhook.test`, the test message of
   * `POST /admin/webhooks/{id}/test`.
   */
  @SerialName("event_type")
  public val eventType: WebhookMessageType,
  /**
   * `pending`: not sent yet. `succeeded`: an attempt got a 2xx answer. `failing`: every
   * attempt so far failed; it is retried over about 27 hours, 8 attempts in all, and can be
   * sent again by hand after that.
   */
  @SerialName("status")
  public val status: AdminWebhookDeliveryStatus,
  /**
   * How many times it was sent.
   */
  @SerialName("attempts")
  public val attempts: Int,
  /**
   * The last attempt of a delivery.
   */
  @SerialName("last_attempt")
  public val lastAttempt: AdminWebhookAttempt? = null,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * The body of the request that every attempt sent, byte for byte as the receiver got it: a
   * JSON document, which can carry personal data (an applicant's email address, a report's
   * comment). It is kept as long as the delivery, 14 days. Omitted where the reader lacks the
   * permission that subscribing to its event needs besides `manage_webhooks`.
   */
  @SerialName("request_body")
  public val requestBody: String? = null,
)
