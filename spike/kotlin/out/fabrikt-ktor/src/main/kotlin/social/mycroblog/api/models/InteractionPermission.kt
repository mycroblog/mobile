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
 * Whether the authenticated user may interact with a status in one way, by its interaction
 * policy, its visibility, and how the user stands with its author: `allowed` at once,
 * `pending` once its author approves it (the interaction waits for the approval), or `denied`.
 */
@Serializable(with = InteractionPermission.Serializer::class)
public enum class InteractionPermission(
  public val `value`: String,
) {
  @SerialName("allowed")
  ALLOWED("allowed"),
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("denied")
  DENIED("denied"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InteractionPermission> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InteractionPermission", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InteractionPermission) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InteractionPermission {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InteractionPermission> =
        entries.associateBy(InteractionPermission::value)

    public fun fromValue(`value`: String): InteractionPermission = mapping[value] ?: UNRECOGNIZED
  }
}
