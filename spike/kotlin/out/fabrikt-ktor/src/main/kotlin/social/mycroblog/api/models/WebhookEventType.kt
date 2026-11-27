package social.mycroblog.api.models

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
 * An event that a webhook subscribes to. Subscribing needs, besides
 * `manage_webhooks`, `manage_reports` for `report.*`; `manage_accounts` for
 * `registration.pending`, `account.*`, `moderation_action.*`, and `appeal.created`;
 * `manage_domains` for `domain_moderation_action.*`; `manage_custom_emojis` for
 * `custom_emoji.*`; `manage_errors` for `error_group.*`; and nothing more for
 * `status.created`. `report.created`: a report,
 * local or from another server. `report.resolved`, `report.reopened`. `registration.pending`:
 * a registration awaits a moderator. `account.created`: a registration was approved, at
 * the sign-up or by a moderator. `account.updated`: a local account changed its profile.
 * `moderation_action.created`, `moderation_action.lifted`: an action on an account.
 * `appeal.created`. `domain_moderation_action.created`, `domain_moderation_action.lifted`.
 * `status.created`: a public post of a local account. `custom_emoji.created`,
 * `custom_emoji.updated` (its image, settings, aliases, or whether it is enabled), and
 * `custom_emoji.deleted`: an administrator's operation on a custom emoji.
 * `error_group.created`: a new error group, and `error_group.regressed`: a resolved group
 * happened again on another version; a group that only web reports without a session have
 * seen is sent when a report with one comes, unless it is muted by then.
 */
@Serializable(with = WebhookEventType.Serializer::class)
public enum class WebhookEventType(
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
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<WebhookEventType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.WebhookEventType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: WebhookEventType) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): WebhookEventType {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, WebhookEventType> =
        entries.associateBy(WebhookEventType::value)

    public fun fromValue(`value`: String): WebhookEventType = mapping[value] ?: UNRECOGNIZED
  }
}
