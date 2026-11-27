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
 * Who may see a status: `public` everyone, listed on public timelines;
 * `local` logged-in users of this instance and followers; `unlisted` everyone, kept off
 * public timelines; `private` followers and mentioned accounts; `direct` mentioned
 * accounts.
 */
@Serializable(with = Visibility.Serializer::class)
public enum class Visibility(
  public val `value`: String,
) {
  @SerialName("public")
  PUBLIC("public"),
  @SerialName("local")
  LOCAL("local"),
  @SerialName("unlisted")
  UNLISTED("unlisted"),
  @SerialName("private")
  PRIVATE("private"),
  @SerialName("direct")
  DIRECT("direct"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<Visibility> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.Visibility", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: Visibility) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): Visibility {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, Visibility> = entries.associateBy(Visibility::value)

    public fun fromValue(`value`: String): Visibility = mapping[value] ?: UNRECOGNIZED
  }
}
