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
 * What a keyword filter does to a post that matches it: `warn` returns the post with the filter
 * in its `filtered`, and clients fold it behind the filter's title; `hide` leaves the post out,
 * as if the user could not see it.
 */
@Serializable(with = KeywordFilterAction.Serializer::class)
public enum class KeywordFilterAction(
  public val `value`: String,
) {
  @SerialName("warn")
  WARN("warn"),
  @SerialName("hide")
  HIDE("hide"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<KeywordFilterAction> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.KeywordFilterAction", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: KeywordFilterAction) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): KeywordFilterAction {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, KeywordFilterAction> =
        entries.associateBy(KeywordFilterAction::value)

    public fun fromValue(`value`: String): KeywordFilterAction = mapping[value] ?: UNRECOGNIZED
  }
}
