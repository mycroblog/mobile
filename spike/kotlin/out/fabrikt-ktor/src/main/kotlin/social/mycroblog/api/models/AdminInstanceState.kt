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
 * `available` servers get every delivery; `unavailable` ones only `Follow`, `Accept`, and
 * `Reject`.
 */
@Serializable(with = AdminInstanceState.Serializer::class)
public enum class AdminInstanceState(
  public val `value`: String,
) {
  @SerialName("available")
  AVAILABLE("available"),
  @SerialName("unavailable")
  UNAVAILABLE("unavailable"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminInstanceState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminInstanceState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminInstanceState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminInstanceState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminInstanceState> =
        entries.associateBy(AdminInstanceState::value)

    public fun fromValue(`value`: String): AdminInstanceState = mapping[value] ?: UNRECOGNIZED
  }
}
