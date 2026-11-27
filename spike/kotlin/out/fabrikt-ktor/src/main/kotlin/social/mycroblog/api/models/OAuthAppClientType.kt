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
 * `public`: the app has no secret (a single-page, native, or command-line app).
 * `confidential`: the app runs on a server and authenticates with its secret at the token
 * endpoint, by `client_secret_basic` or `client_secret_post`. Both use PKCE (S256). The type
 * never changes.
 */
@Serializable(with = OAuthAppClientType.Serializer::class)
public enum class OAuthAppClientType(
  public val `value`: String,
) {
  @SerialName("public")
  PUBLIC("public"),
  @SerialName("confidential")
  CONFIDENTIAL("confidential"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<OAuthAppClientType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.OAuthAppClientType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: OAuthAppClientType) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): OAuthAppClientType {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, OAuthAppClientType> =
        entries.associateBy(OAuthAppClientType::value)

    public fun fromValue(`value`: String): OAuthAppClientType = mapping[value] ?: UNRECOGNIZED
  }
}
