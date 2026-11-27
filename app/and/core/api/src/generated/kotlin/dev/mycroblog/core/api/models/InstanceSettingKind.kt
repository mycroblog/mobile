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
 * The type of a setting's value. `bool`: `true` or `false`. `int`: a decimal integer from
 * `minimum` to `maximum`. `enum`: one of `choices`. `locale`: a BCP 47 language tag.
 * `media`: the ID of an image uploaded with `POST /media`, or empty for none. `account`:
 * the username of a local account, or empty for none. `email`: an email address such as
 * `admin@example.com`, or empty for none. `uri`: an `https:` or `mailto:` URI, or empty for
 * none. `accounts`: the usernames of up to 20 local accounts separated by spaces (commas and
 * a leading `@` are accepted), in order, or empty for none. `client_limits`: comma-separated
 * `client_id=requests` pairs, or empty for none. `provider_list`: comma-separated translation
 * provider IDs, the first tried first, or empty for none. `provider_visibilities`:
 * comma-separated `provider=visibility|visibility` pairs, or empty for none. `decimal`: a
 * decimal number that is not negative in the major unit of a currency (`1650`, `12.34`), or
 * empty for none. `currency`: an ISO 4217 code such as `JPY`, or empty for none.
 */
@Serializable(with = InstanceSettingKind.Serializer::class)
public enum class InstanceSettingKind(
  public val `value`: String,
) {
  @SerialName("bool")
  BOOL("bool"),
  @SerialName("int")
  INT("int"),
  @SerialName("enum")
  ENUM("enum"),
  @SerialName("locale")
  LOCALE("locale"),
  @SerialName("media")
  MEDIA("media"),
  @SerialName("account")
  ACCOUNT("account"),
  @SerialName("email")
  EMAIL("email"),
  @SerialName("uri")
  URI("uri"),
  @SerialName("accounts")
  ACCOUNTS("accounts"),
  @SerialName("client_limits")
  CLIENT_LIMITS("client_limits"),
  @SerialName("provider_list")
  PROVIDER_LIST("provider_list"),
  @SerialName("provider_visibilities")
  PROVIDER_VISIBILITIES("provider_visibilities"),
  @SerialName("decimal")
  DECIMAL("decimal"),
  @SerialName("currency")
  CURRENCY("currency"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InstanceSettingKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.InstanceSettingKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InstanceSettingKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InstanceSettingKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InstanceSettingKind> =
        entries.associateBy(InstanceSettingKind::value)

    public fun fromValue(`value`: String): InstanceSettingKind = mapping[value] ?: UNRECOGNIZED
  }
}
