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
 * What the crawler of link cards does not do on the domain of a rule. `no_fetch`: it fetches
 * neither pages nor images, and the cards of its pages are hidden. `no_images`: it fetches pages
 * but no images, and its cards show without their images.
 */
@Serializable(with = LinkCardDomainMode.Serializer::class)
public enum class LinkCardDomainMode(
  public val `value`: String,
) {
  @SerialName("no_fetch")
  NO_FETCH("no_fetch"),
  @SerialName("no_images")
  NO_IMAGES("no_images"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<LinkCardDomainMode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.LinkCardDomainMode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: LinkCardDomainMode) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): LinkCardDomainMode {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, LinkCardDomainMode> =
        entries.associateBy(LinkCardDomainMode::value)

    public fun fromValue(`value`: String): LinkCardDomainMode = mapping[value] ?: UNRECOGNIZED
  }
}
