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
 * Who may read something public about the instance, the value of the instance settings
 * `local_timeline_visibility`, `federated_timeline_visibility`, `domain_actions_visibility`,
 * `domain_actions_rationale_visibility`, and `supporter_list_visibility`: `off`, nobody, and the
 * resource is a 404; `signed_in`, the users signed in to this server, and an anonymous request is
 * a 401; `everyone`, everyone, anonymous visitors included.
 */
@Serializable(with = InstanceAudience.Serializer::class)
public enum class InstanceAudience(
  public val `value`: String,
) {
  @SerialName("off")
  OFF("off"),
  @SerialName("signed_in")
  SIGNED_IN("signed_in"),
  @SerialName("everyone")
  EVERYONE("everyone"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InstanceAudience> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InstanceAudience", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InstanceAudience) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InstanceAudience {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InstanceAudience> =
        entries.associateBy(InstanceAudience::value)

    public fun fromValue(`value`: String): InstanceAudience = mapping[value] ?: UNRECOGNIZED
  }
}
