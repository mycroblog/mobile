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
 * Who performed the operation. `user`: a signed-in moderator or administrator.
 * `cli`: the `my` command line, which acts as no user. `system`: the server itself.
 * `remote`: another server.
 */
@Serializable(with = AdminAuditActorKind.Serializer::class)
public enum class AdminAuditActorKind(
  public val `value`: String,
) {
  @SerialName("user")
  USER("user"),
  @SerialName("cli")
  CLI("cli"),
  @SerialName("system")
  SYSTEM("system"),
  @SerialName("remote")
  REMOTE("remote"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAuditActorKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminAuditActorKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAuditActorKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAuditActorKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAuditActorKind> =
        entries.associateBy(AdminAuditActorKind::value)

    public fun fromValue(`value`: String): AdminAuditActorKind = mapping[value] ?: UNRECOGNIZED
  }
}
