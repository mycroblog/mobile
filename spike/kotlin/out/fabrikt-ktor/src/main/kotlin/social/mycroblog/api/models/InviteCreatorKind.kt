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
 * How the invite was created. `staff`: by a holder of `manage_invites` or with the CLI; no cap
 * applies and its creator is not checked. `member`: by a member through their own invites,
 * under the caps of the instance settings; it works only while its creator may create invites.
 */
@Serializable(with = InviteCreatorKind.Serializer::class)
public enum class InviteCreatorKind(
  public val `value`: String,
) {
  @SerialName("staff")
  STAFF("staff"),
  @SerialName("member")
  MEMBER("member"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InviteCreatorKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InviteCreatorKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InviteCreatorKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InviteCreatorKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InviteCreatorKind> =
        entries.associateBy(InviteCreatorKind::value)

    public fun fromValue(`value`: String): InviteCreatorKind = mapping[value] ?: UNRECOGNIZED
  }
}
