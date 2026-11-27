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
 * `email_domain`: the domain of the email address and its subdomains. `email_hash`: one
 * email address, compared in lower case without the part of the local part from its first
 * `+`. `ip_range`: the client addresses in a CIDR prefix.
 */
@Serializable(with = AdminSignupBlockKind.Serializer::class)
public enum class AdminSignupBlockKind(
  public val `value`: String,
) {
  @SerialName("email_domain")
  EMAIL_DOMAIN("email_domain"),
  @SerialName("email_hash")
  EMAIL_HASH("email_hash"),
  @SerialName("ip_range")
  IP_RANGE("ip_range"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminSignupBlockKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminSignupBlockKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminSignupBlockKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminSignupBlockKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminSignupBlockKind> =
        entries.associateBy(AdminSignupBlockKind::value)

    public fun fromValue(`value`: String): AdminSignupBlockKind = mapping[value] ?: UNRECOGNIZED
  }
}
