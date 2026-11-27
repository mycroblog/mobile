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
 * Where a registration stands: `pending` waits for a moderator, `approved` lets the user
 * sign in, and `rejected` keeps them out.
 */
@Serializable(with = RegistrationState.Serializer::class)
public enum class RegistrationState(
  public val `value`: String,
) {
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("approved")
  APPROVED("approved"),
  @SerialName("rejected")
  REJECTED("rejected"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<RegistrationState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.RegistrationState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: RegistrationState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): RegistrationState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, RegistrationState> =
        entries.associateBy(RegistrationState::value)

    public fun fromValue(`value`: String): RegistrationState = mapping[value] ?: UNRECOGNIZED
  }
}
