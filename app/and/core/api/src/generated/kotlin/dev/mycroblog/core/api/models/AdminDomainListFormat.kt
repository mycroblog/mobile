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
 * The format of a subscribed list. `mastodon_csv`: Mastodon's domain block CSV, read as the
 * import reads it. `mastodon_json`: the answer of Mastodon's
 * `GET /api/v1/instance/domain_blocks`; obfuscated domains are skipped. `plain_text`: one
 * domain per line, each a suspend.
 */
@Serializable(with = AdminDomainListFormat.Serializer::class)
public enum class AdminDomainListFormat(
  public val `value`: String,
) {
  @SerialName("mastodon_csv")
  MASTODON_CSV("mastodon_csv"),
  @SerialName("mastodon_json")
  MASTODON_JSON("mastodon_json"),
  @SerialName("plain_text")
  PLAIN_TEXT("plain_text"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDomainListFormat> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminDomainListFormat", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDomainListFormat) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDomainListFormat {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDomainListFormat> =
        entries.associateBy(AdminDomainListFormat::value)

    public fun fromValue(`value`: String): AdminDomainListFormat = mapping[value] ?: UNRECOGNIZED
  }
}
