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
 * Why a webhook is refused. `url`: not an absolute http or https URL of at most 2048
 * bytes without user info or a fragment. `description`: longer than 200 characters or with
 * control characters. `events`: none, or one twice. `missing_permission`: the user lacks
 * the permission of an event (`missing_permissions`).
 */
@Serializable(with = WebhookProblemReason.Serializer::class)
public enum class WebhookProblemReason(
  public val `value`: String,
) {
  @SerialName("url")
  URL("url"),
  @SerialName("description")
  DESCRIPTION("description"),
  @SerialName("events")
  EVENTS("events"),
  @SerialName("missing_permission")
  MISSING_PERMISSION("missing_permission"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<WebhookProblemReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.WebhookProblemReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: WebhookProblemReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): WebhookProblemReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, WebhookProblemReason> =
        entries.associateBy(WebhookProblemReason::value)

    public fun fromValue(`value`: String): WebhookProblemReason = mapping[value] ?: UNRECOGNIZED
  }
}
