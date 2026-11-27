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
 * A row of the notification settings: one or more notification types that the user turns on
 * and off together. The ordinary notifications are one row per type (`mention`, `follow`,
 * `follow_request`, `follow_accepted`, `boost`, `favourite`, `reaction`, `quote`,
 * `poll_closed`, `account_moved`, `status`), except `voice`, which is `voice_room_started` and
 * `voice_room_invited`, `event`, which is the `event_*` types (the email of an `event_reminder`
 * is sent at once, not after the notification stays unread), and `interaction_request`, which is `reply_request`, `boost_request`,
 * `quote_request`, `interaction_accepted`, and `interaction_rejected`. `status` and
 * `interaction_request` have no `email` channel. The moderator notifications are one row per type (`admin_report`,
 * `admin_sign_up`, `admin_appeal`, `admin_webhook_disabled`, `admin_custom_emoji_request`,
 * `admin_account_moved`, `admin_service_closure_due`, `admin_error_group`, `admin_hook_failing`, `admin_hook_appeal`,
 * `admin_domain_list_failing`). The instance's
 * notices to the user are `moderation` (`moderation_notice`, `report_resolved`,
 * `appeal_decided`, `relationships_severed`, `custom_emoji_request_decided`,
 * `hook_appeal_decided`),
 * `service_closure`, `supporter` (the `supporter_*` types), `announcement`, which the user
 * turns off in the app (on by default) and which has no `email` channel, and
 * `auto_delete_limit`, whose app and email channels the user chooses, both on by default, and
 * `scheduled_status_failed`, which the user turns off in the app (on by default) and which has
 * no `email` channel.
 */
@Serializable(with = NotificationGroup.Serializer::class)
public enum class NotificationGroup(
  public val `value`: String,
) {
  @SerialName("mention")
  MENTION("mention"),
  @SerialName("follow")
  FOLLOW("follow"),
  @SerialName("follow_request")
  FOLLOW_REQUEST("follow_request"),
  @SerialName("follow_accepted")
  FOLLOW_ACCEPTED("follow_accepted"),
  @SerialName("boost")
  BOOST("boost"),
  @SerialName("favourite")
  FAVOURITE("favourite"),
  @SerialName("reaction")
  REACTION("reaction"),
  @SerialName("quote")
  QUOTE("quote"),
  @SerialName("interaction_request")
  INTERACTION_REQUEST("interaction_request"),
  @SerialName("poll_closed")
  POLL_CLOSED("poll_closed"),
  @SerialName("account_moved")
  ACCOUNT_MOVED("account_moved"),
  @SerialName("voice")
  VOICE("voice"),
  @SerialName("event")
  EVENT("event"),
  @SerialName("status")
  STATUS("status"),
  @SerialName("admin_report")
  ADMIN_REPORT("admin_report"),
  @SerialName("admin_sign_up")
  ADMIN_SIGN_UP("admin_sign_up"),
  @SerialName("admin_appeal")
  ADMIN_APPEAL("admin_appeal"),
  @SerialName("admin_webhook_disabled")
  ADMIN_WEBHOOK_DISABLED("admin_webhook_disabled"),
  @SerialName("admin_hook_failing")
  ADMIN_HOOK_FAILING("admin_hook_failing"),
  @SerialName("admin_hook_appeal")
  ADMIN_HOOK_APPEAL("admin_hook_appeal"),
  @SerialName("admin_domain_list_failing")
  ADMIN_DOMAIN_LIST_FAILING("admin_domain_list_failing"),
  @SerialName("admin_custom_emoji_request")
  ADMIN_CUSTOM_EMOJI_REQUEST("admin_custom_emoji_request"),
  @SerialName("admin_account_moved")
  ADMIN_ACCOUNT_MOVED("admin_account_moved"),
  @SerialName("admin_service_closure_due")
  ADMIN_SERVICE_CLOSURE_DUE("admin_service_closure_due"),
  @SerialName("admin_error_group")
  ADMIN_ERROR_GROUP("admin_error_group"),
  @SerialName("moderation")
  MODERATION("moderation"),
  @SerialName("service_closure")
  SERVICE_CLOSURE("service_closure"),
  @SerialName("supporter")
  SUPPORTER("supporter"),
  @SerialName("announcement")
  ANNOUNCEMENT("announcement"),
  @SerialName("auto_delete_limit")
  AUTO_DELETE_LIMIT("auto_delete_limit"),
  @SerialName("scheduled_status_failed")
  SCHEDULED_STATUS_FAILED("scheduled_status_failed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<NotificationGroup> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.NotificationGroup", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: NotificationGroup) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): NotificationGroup {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, NotificationGroup> =
        entries.associateBy(NotificationGroup::value)

    public fun fromValue(`value`: String): NotificationGroup = mapping[value] ?: UNRECOGNIZED
  }
}
