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
 * `suspend`: no federation with the domain at all. `limit`: its posts stay off the public
 * timelines and its accounts reach only their followers. `reject_media`: its media are
 * not fetched. `reject_reports`: its reports are dropped. `force_sensitive`: its media
 * are marked sensitive.
 */
@Serializable(with = AdminDomainActionKind.Serializer::class)
public enum class AdminDomainActionKind(
  public val `value`: String,
) {
  @SerialName("limit")
  LIMIT("limit"),
  @SerialName("suspend")
  SUSPEND("suspend"),
  @SerialName("reject_media")
  REJECT_MEDIA("reject_media"),
  @SerialName("reject_reports")
  REJECT_REPORTS("reject_reports"),
  @SerialName("force_sensitive")
  FORCE_SENSITIVE("force_sensitive"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDomainActionKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminDomainActionKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDomainActionKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDomainActionKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDomainActionKind> =
        entries.associateBy(AdminDomainActionKind::value)

    public fun fromValue(`value`: String): AdminDomainActionKind = mapping[value] ?: UNRECOGNIZED
  }
}
