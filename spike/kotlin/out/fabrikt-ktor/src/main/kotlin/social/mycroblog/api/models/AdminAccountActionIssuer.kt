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
 * `moderator`: a moderator or the CLI.
 */
@Serializable(with = AdminAccountActionIssuer.Serializer::class)
public enum class AdminAccountActionIssuer(
  public val `value`: String,
) {
  @SerialName("moderator")
  MODERATOR("moderator"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAccountActionIssuer> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminAccountActionIssuer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAccountActionIssuer) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAccountActionIssuer {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAccountActionIssuer> =
        entries.associateBy(AdminAccountActionIssuer::value)

    public fun fromValue(`value`: String): AdminAccountActionIssuer = mapping[value] ?: UNRECOGNIZED
  }
}
