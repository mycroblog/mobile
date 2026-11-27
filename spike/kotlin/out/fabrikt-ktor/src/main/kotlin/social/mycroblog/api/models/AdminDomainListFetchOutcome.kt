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
 * `applied`: the list was read and replaced the domains the subscription holds.
 * `not_modified`: the list did not change. `failed`: nothing was read and nothing changed.
 */
@Serializable(with = AdminDomainListFetchOutcome.Serializer::class)
public enum class AdminDomainListFetchOutcome(
  public val `value`: String,
) {
  @SerialName("applied")
  APPLIED("applied"),
  @SerialName("not_modified")
  NOT_MODIFIED("not_modified"),
  @SerialName("failed")
  FAILED("failed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDomainListFetchOutcome> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminDomainListFetchOutcome", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDomainListFetchOutcome) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDomainListFetchOutcome {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDomainListFetchOutcome> =
        entries.associateBy(AdminDomainListFetchOutcome::value)

    public fun fromValue(`value`: String): AdminDomainListFetchOutcome = mapping[value] ?: UNRECOGNIZED
  }
}
