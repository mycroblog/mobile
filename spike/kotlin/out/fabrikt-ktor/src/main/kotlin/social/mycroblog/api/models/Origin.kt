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

@Serializable(with = Origin.Serializer::class)
public enum class Origin(
  public val `value`: String,
) {
  @SerialName("local")
  LOCAL("local"),
  @SerialName("remote")
  REMOTE("remote"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<Origin> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.Origin", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: Origin) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): Origin {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, Origin> = entries.associateBy(Origin::value)

    public fun fromValue(`value`: String): Origin = mapping[value] ?: UNRECOGNIZED
  }
}
