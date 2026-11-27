package dev.mycroblog.core.api.models

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
 * How the app was registered: `first_party` for the server's own client, `user` for an app a
 * user registered, `metadata_document` for a Client ID Metadata Document, and `dynamic` for
 * dynamic client registration (self-declared).
 */
@Serializable(with = AuthorizedAppRegistration.Serializer::class)
public enum class AuthorizedAppRegistration(
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

  public object Serializer : KSerializer<AuthorizedAppRegistration> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AuthorizedAppRegistration", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AuthorizedAppRegistration) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AuthorizedAppRegistration {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AuthorizedAppRegistration> =
        entries.associateBy(AuthorizedAppRegistration::value)

    public fun fromValue(`value`: String): AuthorizedAppRegistration = mapping[value] ?: UNRECOGNIZED
  }
}
