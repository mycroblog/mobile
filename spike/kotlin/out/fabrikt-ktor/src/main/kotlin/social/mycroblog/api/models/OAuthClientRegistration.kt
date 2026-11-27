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
 * The route by which an OAuth client was registered: `first_party` is one of the
 * server's own clients, `user` a client that a signed-in user registered, `metadata_document` a
 * client whose `client_id` is the URL of a Client ID Metadata Document, and `dynamic` a client
 * that registered itself (RFC 7591).
 */
@Serializable(with = OAuthClientRegistration.Serializer::class)
public enum class OAuthClientRegistration(
  public val `value`: String,
) {
  @SerialName("first_party")
  FIRST_PARTY("first_party"),
  @SerialName("user")
  USER("user"),
  @SerialName("metadata_document")
  METADATA_DOCUMENT("metadata_document"),
  @SerialName("dynamic")
  DYNAMIC("dynamic"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<OAuthClientRegistration> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.OAuthClientRegistration", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: OAuthClientRegistration) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): OAuthClientRegistration {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, OAuthClientRegistration> =
        entries.associateBy(OAuthClientRegistration::value)

    public fun fromValue(`value`: String): OAuthClientRegistration = mapping[value] ?: UNRECOGNIZED
  }
}
