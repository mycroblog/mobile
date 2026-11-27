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
 * Who may sign up: `open` (anyone), `approval` (sign-ups wait for a moderator's
 * approval), `invite` (only with an invite), or `closed` (nobody). It is the mode that
 * sign-ups follow: `closed` while the closure of the service is announced or done, whatever
 * the instance setting says, on a one-person server (`single_user`), and while registration
 * is limited to the login providers and none of them lets new users sign up.
 */
@Serializable(with = InstanceRegistrationsMode.Serializer::class)
public enum class InstanceRegistrationsMode(
  public val `value`: String,
) {
  @SerialName("open")
  OPEN("open"),
  @SerialName("approval")
  APPROVAL("approval"),
  @SerialName("invite")
  INVITE("invite"),
  @SerialName("closed")
  CLOSED("closed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InstanceRegistrationsMode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InstanceRegistrationsMode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InstanceRegistrationsMode) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InstanceRegistrationsMode {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InstanceRegistrationsMode> =
        entries.associateBy(InstanceRegistrationsMode::value)

    public fun fromValue(`value`: String): InstanceRegistrationsMode = mapping[value] ?: UNRECOGNIZED
  }
}
