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
 * The kind of what an audited operation acted on.
 */
@Serializable(with = AdminAuditTargetKind.Serializer::class)
public enum class AdminAuditTargetKind(
  public val `value`: String,
) {
  @SerialName("account")
  ACCOUNT("account"),
  @SerialName("status")
  STATUS("status"),
  @SerialName("domain")
  DOMAIN("domain"),
  @SerialName("report")
  REPORT("report"),
  @SerialName("appeal")
  APPEAL("appeal"),
  @SerialName("user")
  USER("user"),
  @SerialName("role")
  ROLE("role"),
  @SerialName("setting")
  SETTING("setting"),
  @SerialName("rule")
  RULE("rule"),
  @SerialName("signup_block")
  SIGNUP_BLOCK("signup_block"),
  @SerialName("custom_emoji")
  CUSTOM_EMOJI("custom_emoji"),
  @SerialName("webhook")
  WEBHOOK("webhook"),
  @SerialName("announcement")
  ANNOUNCEMENT("announcement"),
  @SerialName("invite")
  INVITE("invite"),
  @SerialName("relay")
  RELAY("relay"),
  @SerialName("billing_source")
  BILLING_SOURCE("billing_source"),
  @SerialName("cost_item")
  COST_ITEM("cost_item"),
  @SerialName("supporter_program")
  SUPPORTER_PROGRAM("supporter_program"),
  @SerialName("supporter_plan")
  SUPPORTER_PLAN("supporter_plan"),
  @SerialName("supporter_subscription")
  SUPPORTER_SUBSCRIPTION("supporter_subscription"),
  @SerialName("custom_emoji_request")
  CUSTOM_EMOJI_REQUEST("custom_emoji_request"),
  @SerialName("payment_provider")
  PAYMENT_PROVIDER("payment_provider"),
  @SerialName("oauth_client")
  OAUTH_CLIENT("oauth_client"),
  @SerialName("oauth_client_host")
  OAUTH_CLIENT_HOST("oauth_client_host"),
  @SerialName("instance")
  INSTANCE("instance"),
  @SerialName("voice_room")
  VOICE_ROOM("voice_room"),
  @SerialName("login_provider")
  LOGIN_PROVIDER("login_provider"),
  @SerialName("link_card_domain")
  LINK_CARD_DOMAIN("link_card_domain"),
  @SerialName("hook_decision")
  HOOK_DECISION("hook_decision"),
  @SerialName("domain_allow")
  DOMAIN_ALLOW("domain_allow"),
  @SerialName("domain_list_subscription")
  DOMAIN_LIST_SUBSCRIPTION("domain_list_subscription"),
  @SerialName("event")
  EVENT("event"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAuditTargetKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminAuditTargetKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAuditTargetKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAuditTargetKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAuditTargetKind> =
        entries.associateBy(AdminAuditTargetKind::value)

    public fun fromValue(`value`: String): AdminAuditTargetKind = mapping[value] ?: UNRECOGNIZED
  }
}
