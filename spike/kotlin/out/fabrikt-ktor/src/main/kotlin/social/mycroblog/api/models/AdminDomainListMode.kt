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
 * `draft`: every difference waits for a moderator. `automatic`: the differences are applied
 * at each fetch, except those that `AdminDomainListHold` names.
 */
@Serializable(with = AdminDomainListMode.Serializer::class)
public enum class AdminDomainListMode(
  public val `value`: String,
) {
  @SerialName("draft")
  DRAFT("draft"),
  @SerialName("automatic")
  AUTOMATIC("automatic"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDomainListMode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminDomainListMode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDomainListMode) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDomainListMode {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDomainListMode> =
        entries.associateBy(AdminDomainListMode::value)

    public fun fromValue(`value`: String): AdminDomainListMode = mapping[value] ?: UNRECOGNIZED
  }
}
