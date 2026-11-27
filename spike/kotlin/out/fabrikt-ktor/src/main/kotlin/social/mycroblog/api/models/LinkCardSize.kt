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
 * How a client shows link cards.
 */
@Serializable(with = LinkCardSize.Serializer::class)
public enum class LinkCardSize(
  public val `value`: String,
) {
  @SerialName("large")
  LARGE("large"),
  @SerialName("small")
  SMALL("small"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<LinkCardSize> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.LinkCardSize", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: LinkCardSize) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): LinkCardSize {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, LinkCardSize> = entries.associateBy(LinkCardSize::value)

    public fun fromValue(`value`: String): LinkCardSize = mapping[value] ?: UNRECOGNIZED
  }
}
