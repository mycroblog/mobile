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
 * Why the account cannot be deactivated now; absent when it can: moderation keeps it from
 * logging in (`login_restricted`), its deletion is scheduled (`deletion_scheduled`), it is
 * deactivated (`already_deactivated`), it moved (`moved`), its registration waits for review
 * (`registration_pending`), a paid subscription goes on (`paid_subscription`), no other
 * administrator can sign in (`last_administrator`), or it was reactivated less than 24 hours
 * ago (`recently_reactivated`).
 */
@Serializable(with = DeactivationPreviewRefusal.Serializer::class)
public enum class DeactivationPreviewRefusal(
  public val `value`: String,
) {
  @SerialName("login_restricted")
  LOGIN_RESTRICTED("login_restricted"),
  @SerialName("deletion_scheduled")
  DELETION_SCHEDULED("deletion_scheduled"),
  @SerialName("already_deactivated")
  ALREADY_DEACTIVATED("already_deactivated"),
  @SerialName("moved")
  MOVED("moved"),
  @SerialName("registration_pending")
  REGISTRATION_PENDING("registration_pending"),
  @SerialName("paid_subscription")
  PAID_SUBSCRIPTION("paid_subscription"),
  @SerialName("last_administrator")
  LAST_ADMINISTRATOR("last_administrator"),
  @SerialName("recently_reactivated")
  RECENTLY_REACTIVATED("recently_reactivated"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<DeactivationPreviewRefusal> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.DeactivationPreviewRefusal", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: DeactivationPreviewRefusal) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DeactivationPreviewRefusal {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, DeactivationPreviewRefusal> =
        entries.associateBy(DeactivationPreviewRefusal::value)

    public fun fromValue(`value`: String): DeactivationPreviewRefusal = mapping[value] ?: UNRECOGNIZED
  }
}
