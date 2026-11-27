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
 * Whom an interaction policy admits, narrowest first: `nobody` the author alone, `mentioned`
 * the accounts the status mentions, `followers` the author's followers and the accounts the
 * status mentions, `everyone` anyone who may see the status. The author always may interact.
 */
@Serializable(with = InteractionAudience.Serializer::class)
public enum class InteractionAudience(
  public val `value`: String,
) {
  @SerialName("nobody")
  NOBODY("nobody"),
  @SerialName("mentioned")
  MENTIONED("mentioned"),
  @SerialName("followers")
  FOLLOWERS("followers"),
  @SerialName("everyone")
  EVERYONE("everyone"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InteractionAudience> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InteractionAudience", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InteractionAudience) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InteractionAudience {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InteractionAudience> =
        entries.associateBy(InteractionAudience::value)

    public fun fromValue(`value`: String): InteractionAudience = mapping[value] ?: UNRECOGNIZED
  }
}
