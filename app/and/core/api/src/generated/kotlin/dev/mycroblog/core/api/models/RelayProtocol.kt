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
 * How a relay is joined. `mastodon`: at its inbox, by a `Follow` of the Public
 * collection. `litepub`: at its actor, by a `Follow` of the actor.
 */
@Serializable(with = RelayProtocol.Serializer::class)
public enum class RelayProtocol(
  public val `value`: String,
) {
  @SerialName("mastodon")
  MASTODON("mastodon"),
  @SerialName("litepub")
  LITEPUB("litepub"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<RelayProtocol> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.RelayProtocol", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: RelayProtocol) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): RelayProtocol {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, RelayProtocol> = entries.associateBy(RelayProtocol::value)

    public fun fromValue(`value`: String): RelayProtocol = mapping[value] ?: UNRECOGNIZED
  }
}
