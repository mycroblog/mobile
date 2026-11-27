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
 * `pending` until the user answers; then `accepted` or `rejected`.
 */
@Serializable(with = InteractionRequestState.Serializer::class)
public enum class InteractionRequestState(
  public val `value`: String,
) {
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("accepted")
  ACCEPTED("accepted"),
  @SerialName("rejected")
  REJECTED("rejected"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InteractionRequestState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InteractionRequestState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InteractionRequestState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InteractionRequestState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InteractionRequestState> =
        entries.associateBy(InteractionRequestState::value)

    public fun fromValue(`value`: String): InteractionRequestState = mapping[value] ?: UNRECOGNIZED
  }
}
