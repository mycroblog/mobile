package social.mycroblog.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * The body of a message that a webhook receives (Standard Webhooks), posted as
 * `application/json` with the headers `webhook-id`, `webhook-timestamp`, and
 * `webhook-signature`. `data` is what the admin API answers for the object of the event,
 * presented in the transaction of the event: `AdminReport` for `report.*`,
 * `AdminRegistration` for `registration.pending` and `account.created`, `AdminAccount`
 * for `account.updated`, `AdminAccountAction` for `moderation_action.*`, `AdminAppeal`
 * for `appeal.created`, `AdminDomainAction` for `domain_moderation_action.*`,
 * `WebhookCustomEmoji` for `custom_emoji.*`, `ErrorGroup` for `error_group.*`,
 * `WebhookStatus` for `status.created`, and
 * `WebhookTest` for `webhook.test`. No operation returns it.
 */
@Serializable
public data class WebhookMessage(
  /**
   * The type of a message: a `WebhookEventType`, or `webhook.test`, the test message of
   * `POST /admin/webhooks/{id}/test`.
   */
  @SerialName("type")
  public val type: WebhookMessageType,
  /**
   * When the event happened.
   */
  @SerialName("timestamp")
  public val timestamp: Instant,
  @SerialName("data")
  public val `data`: JsonObject,
)
