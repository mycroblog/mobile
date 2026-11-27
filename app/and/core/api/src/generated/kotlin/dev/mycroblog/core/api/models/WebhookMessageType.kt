package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder

/**
 * The type of a message: a `WebhookEventType`, or `webhook.test`, the test message of
 * `POST /admin/webhooks/{id}/test`.
 */
@Serializable(with = WebhookMessageType.Serializer::class)
public enum class WebhookMessageType(
  public val `value`: String,
) {
  @SerialName("report.created")
  REPORT_CREATED("report.created"),
  @SerialName("report.resolved")
  REPORT_RESOLVED("report.resolved"),
  @SerialName("report.reopened")
  REPORT_REOPENED("report.reopened"),
  @SerialName("registration.pending")
  REGISTRATION_PENDING("registration.pending"),
  @SerialName("account.created")
  ACCOUNT_CREATED("account.created"),
  @SerialName("account.updated")
  ACCOUNT_UPDATED("account.updated"),
  @SerialName("moderation_action.created")
  MODERATION_ACTION_CREATED("moderation_action.created"),
  @SerialName("moderation_action.lifted")
  MODERATION_ACTION_LIFTED("moderation_action.lifted"),
  @SerialName("appeal.created")
  APPEAL_CREATED("appeal.created"),
  @SerialName("domain_moderation_action.created")
  DOMAIN_MODERATION_ACTION_CREATED("domain_moderation_action.created"),
  @SerialName("domain_moderation_action.lifted")
  DOMAIN_MODERATION_ACTION_LIFTED("domain_moderation_action.lifted"),
  @SerialName("status.created")
  STATUS_CREATED("status.created"),
  @SerialName("custom_emoji.created")
  CUSTOM_EMOJI_CREATED("custom_emoji.created"),
  @SerialName("custom_emoji.updated")
  CUSTOM_EMOJI_UPDATED("custom_emoji.updated"),
  @SerialName("custom_emoji.deleted")
  CUSTOM_EMOJI_DELETED("custom_emoji.deleted"),
  @SerialName("error_group.created")
  ERROR_GROUP_CREATED("error_group.created"),
  @SerialName("error_group.regressed")
  ERROR_GROUP_REGRESSED("error_group.regressed"),
  @SerialName("webhook.test")
  WEBHOOK_TEST("webhook.test"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<WebhookMessageType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.WebhookMessageType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: WebhookMessageType) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): WebhookMessageType {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, WebhookMessageType> =
        entries.associateBy(WebhookMessageType::value)

    public fun fromValue(`value`: String): WebhookMessageType = mapping[value] ?: UNRECOGNIZED
  }
}
