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
 * What importing a row would do. `importable`: it issues the actions of `issue`.
 * `already_applied`: an action in effect of each of the row's kinds covers the domain
 * already, on it or on a parent domain. `no_action`: the row stands for no action (a
 * `noop` without rejections). `duplicate`: an earlier row has the same domain once
 * normalized. `own_domain`: the domain is this server's. `invalid`: the domain, the severity, or the public comment is not valid.
 */
@Serializable(with = DomainBlockImportStatus.Serializer::class)
public enum class DomainBlockImportStatus(
  public val `value`: String,
) {
  @SerialName("importable")
  IMPORTABLE("importable"),
  @SerialName("already_applied")
  ALREADY_APPLIED("already_applied"),
  @SerialName("no_action")
  NO_ACTION("no_action"),
  @SerialName("duplicate")
  DUPLICATE("duplicate"),
  @SerialName("own_domain")
  OWN_DOMAIN("own_domain"),
  @SerialName("invalid")
  INVALID("invalid"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<DomainBlockImportStatus> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.DomainBlockImportStatus", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: DomainBlockImportStatus) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DomainBlockImportStatus {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, DomainBlockImportStatus> =
        entries.associateBy(DomainBlockImportStatus::value)

    public fun fromValue(`value`: String): DomainBlockImportStatus = mapping[value] ?: UNRECOGNIZED
  }
}
