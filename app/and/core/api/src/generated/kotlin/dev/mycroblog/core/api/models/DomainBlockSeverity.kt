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
 * The `#severity` of Mastodon's domain block CSV. `suspend`: the action `suspend`.
 * `silence`: Mastodon's name of the action `limit`. `noop`: no action but the rejections.
 */
@Serializable(with = DomainBlockSeverity.Serializer::class)
public enum class DomainBlockSeverity(
  public val `value`: String,
) {
  @SerialName("suspend")
  SUSPEND("suspend"),
  @SerialName("silence")
  SILENCE("silence"),
  @SerialName("noop")
  NOOP("noop"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<DomainBlockSeverity> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.DomainBlockSeverity", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: DomainBlockSeverity) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DomainBlockSeverity {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, DomainBlockSeverity> =
        entries.associateBy(DomainBlockSeverity::value)

    public fun fromValue(`value`: String): DomainBlockSeverity = mapping[value] ?: UNRECOGNIZED
  }
}
