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
 * Why a login provider is refused. `kind`: not a known kind. `display_name`: empty, longer
 * than 100 characters, or with control characters. `issuer`: not what the kind takes (an
 * `https` URL without user info, a query, or a fragment for `oidc`; a server without a path
 * for `mastodon`; none for `github` and `discord`), or a Mastodon server that cannot be reached or serves no
 * authorization server metadata (Mastodon 4.3 or later is required). `client_id`: empty,
 * longer than 512 bytes, or with control characters, or a Mastodon server that refused to
 * register a client: register one there and give its `client_id` and `client_secret`. `client_secret`: empty, longer than 1024 bytes, or with control
 * characters. `scopes`: not scope tokens, too many, or any for `mastodon`. `conditions`:
 * claims for another kind than `oidc`, Organizations for another kind than `github`, guilds for another kind than `discord`, or
 * ones that are not valid.
 */
@Serializable(with = LoginProviderProblemReason.Serializer::class)
public enum class LoginProviderProblemReason(
  public val `value`: String,
) {
  @SerialName("kind")
  KIND("kind"),
  @SerialName("display_name")
  DISPLAY_NAME("display_name"),
  @SerialName("issuer")
  ISSUER("issuer"),
  @SerialName("client_id")
  CLIENT_ID("client_id"),
  @SerialName("client_secret")
  CLIENT_SECRET("client_secret"),
  @SerialName("scopes")
  SCOPES("scopes"),
  @SerialName("conditions")
  CONDITIONS("conditions"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<LoginProviderProblemReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.LoginProviderProblemReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: LoginProviderProblemReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): LoginProviderProblemReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, LoginProviderProblemReason> =
        entries.associateBy(LoginProviderProblemReason::value)

    public fun fromValue(`value`: String): LoginProviderProblemReason = mapping[value] ?: UNRECOGNIZED
  }
}
