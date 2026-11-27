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
 * `reject`: the sign-up is refused. `require_approval`: the registration starts pending
 * for a moderator, whatever the registration mode.
 */
@Serializable(with = AdminSignupBlockSeverity.Serializer::class)
public enum class AdminSignupBlockSeverity(
  public val `value`: String,
) {
  @SerialName("reject")
  REJECT("reject"),
  @SerialName("require_approval")
  REQUIRE_APPROVAL("require_approval"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminSignupBlockSeverity> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminSignupBlockSeverity", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminSignupBlockSeverity) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminSignupBlockSeverity {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminSignupBlockSeverity> =
        entries.associateBy(AdminSignupBlockSeverity::value)

    public fun fromValue(`value`: String): AdminSignupBlockSeverity = mapping[value] ?: UNRECOGNIZED
  }
}
