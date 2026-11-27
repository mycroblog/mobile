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
 * The kind of an external login provider. `oidc`: an OpenID Connect provider found by
 * its issuer's discovery document. `github`: github.com. `mastodon`: one Mastodon server. `discord`: discord.com.
 */
@Serializable(with = LoginProviderKind.Serializer::class)
public enum class LoginProviderKind(
  public val `value`: String,
) {
  @SerialName("oidc")
  OIDC("oidc"),
  @SerialName("github")
  GITHUB("github"),
  @SerialName("mastodon")
  MASTODON("mastodon"),
  @SerialName("discord")
  DISCORD("discord"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<LoginProviderKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.LoginProviderKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: LoginProviderKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): LoginProviderKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, LoginProviderKind> =
        entries.associateBy(LoginProviderKind::value)

    public fun fromValue(`value`: String): LoginProviderKind = mapping[value] ?: UNRECOGNIZED
  }
}
