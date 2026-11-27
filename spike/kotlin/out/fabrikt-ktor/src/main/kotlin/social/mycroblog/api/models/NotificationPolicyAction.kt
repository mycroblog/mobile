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
 * What the notification policy does with the notifications whose source meets a condition.
 * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
 * out of the list and the unread count, in the request of their source, and delivers them
 * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
 */
@Serializable(with = NotificationPolicyAction.Serializer::class)
public enum class NotificationPolicyAction(
  public val `value`: String,
) {
  @SerialName("accept")
  ACCEPT("accept"),
  @SerialName("filter")
  FILTER("filter"),
  @SerialName("drop")
  DROP("drop"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<NotificationPolicyAction> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.NotificationPolicyAction", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: NotificationPolicyAction) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): NotificationPolicyAction {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, NotificationPolicyAction> =
        entries.associateBy(NotificationPolicyAction::value)

    public fun fromValue(`value`: String): NotificationPolicyAction = mapping[value] ?: UNRECOGNIZED
  }
}
