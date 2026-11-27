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
 * `issue`: the list names the domain, and no action of the kind is in effect on it or a
 * parent domain. `lift`: an action that the subscription issued is no longer on the list.
 */
@Serializable(with = AdminDomainListChange.Serializer::class)
public enum class AdminDomainListChange(
  public val `value`: String,
) {
  @SerialName("issue")
  ISSUE("issue"),
  @SerialName("lift")
  LIFT("lift"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDomainListChange> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminDomainListChange", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDomainListChange) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDomainListChange {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDomainListChange> =
        entries.associateBy(AdminDomainListChange::value)

    public fun fromValue(`value`: String): AdminDomainListChange = mapping[value] ?: UNRECOGNIZED
  }
}
