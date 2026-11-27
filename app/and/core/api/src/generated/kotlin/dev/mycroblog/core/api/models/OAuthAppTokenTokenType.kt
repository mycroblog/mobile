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

@Serializable(with = OAuthAppTokenTokenType.Serializer::class)
public enum class OAuthAppTokenTokenType(
  public val `value`: String,
) {
  @SerialName("Bearer")
  BEARER("Bearer"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<OAuthAppTokenTokenType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.OAuthAppTokenTokenType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: OAuthAppTokenTokenType) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): OAuthAppTokenTokenType {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, OAuthAppTokenTokenType> =
        entries.associateBy(OAuthAppTokenTokenType::value)

    public fun fromValue(`value`: String): OAuthAppTokenTokenType = mapping[value] ?: UNRECOGNIZED
  }
}
