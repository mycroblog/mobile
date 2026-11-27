package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A webhook endpoint. Its secret is never shown again after it is made.
 */
@Serializable
public data class AdminWebhook(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * Where the messages are posted.
   */
  @SerialName("url")
  public val url: String,
  /**
   * What the webhook is for; omitted for none.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * The events it subscribes to, in a stable order.
   */
  @SerialName("events")
  public val events: List<WebhookEventType>,
  /**
   * Whether the webhook receives the messages of its events.
   */
  @SerialName("state")
  public val state: AdminWebhookState,
  /**
   * Who set `state` last. `admin`: a staff member, or the `my` command line. `gone`: the
   * server disabled it after the endpoint answered 410 Gone. `failing`: the server disabled
   * it after the deliveries kept failing, without a success, for 72 hours and 20 attempts.
   */
  @SerialName("state_reason")
  public val stateReason: AdminWebhookStateReason,
  @SerialName("state_changed_at")
  public val stateChangedAt: Instant,
  /**
   * The attempts that failed since the last success; omitted when the last attempt
   * succeeded. The server disables the webhook once they span 72 hours and number 20.
   */
  @SerialName("failure")
  public val failure: AdminWebhookFailure? = null,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When the URL, the description, or the events last changed.
   */
  @SerialName("revised_at")
  public val revisedAt: Instant,
)
