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
 * `suspend`: no federation with the domain at all. `limit`: its posts stay off the
 * public timelines and its accounts reach only their followers.
 */
@Serializable(with = InstanceDomainActionKind.Serializer::class)
public enum class InstanceDomainActionKind(
  public val `value`: String,
) {
  @SerialName("limit")
  LIMIT("limit"),
  @SerialName("suspend")
  SUSPEND("suspend"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InstanceDomainActionKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InstanceDomainActionKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InstanceDomainActionKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InstanceDomainActionKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InstanceDomainActionKind> =
        entries.associateBy(InstanceDomainActionKind::value)

    public fun fromValue(`value`: String): InstanceDomainActionKind = mapping[value] ?: UNRECOGNIZED
  }
}
