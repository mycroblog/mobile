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
 * What reported the errors of a group, the server or the web client.
 */
@Serializable(with = ErrorGroupSource.Serializer::class)
public enum class ErrorGroupSource(
  public val `value`: String,
) {
  @SerialName("server")
  SERVER("server"),
  @SerialName("web")
  WEB("web"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ErrorGroupSource> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.ErrorGroupSource", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ErrorGroupSource) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ErrorGroupSource {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ErrorGroupSource> =
        entries.associateBy(ErrorGroupSource::value)

    public fun fromValue(`value`: String): ErrorGroupSource = mapping[value] ?: UNRECOGNIZED
  }
}
