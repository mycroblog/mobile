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
 * Whether users sign in, sign up, and link their accounts with the provider. The sign-in
 * screen shows only the enabled providers.
 */
@Serializable(with = AdminLoginProviderState.Serializer::class)
public enum class AdminLoginProviderState(
  public val `value`: String,
) {
  @SerialName("enabled")
  ENABLED("enabled"),
  @SerialName("disabled")
  DISABLED("disabled"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminLoginProviderState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminLoginProviderState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminLoginProviderState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminLoginProviderState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminLoginProviderState> =
        entries.associateBy(AdminLoginProviderState::value)

    public fun fromValue(`value`: String): AdminLoginProviderState = mapping[value] ?: UNRECOGNIZED
  }
}
