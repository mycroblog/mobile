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
 * Why a difference waits for a moderator. `draft_mode`: the subscription is in the draft
 * mode. `unknown_severity`: the list gives the domain a severity this server does not know.
 * `mass_lift`: one fetch would lift more than 10 actions and more than half of the
 * subscription's actions, which a broken or empty list would do.
 */
@Serializable(with = AdminDomainListHold.Serializer::class)
public enum class AdminDomainListHold(
  public val `value`: String,
) {
  @SerialName("draft_mode")
  DRAFT_MODE("draft_mode"),
  @SerialName("unknown_severity")
  UNKNOWN_SEVERITY("unknown_severity"),
  @SerialName("mass_lift")
  MASS_LIFT("mass_lift"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDomainListHold> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminDomainListHold", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDomainListHold) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDomainListHold {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDomainListHold> =
        entries.associateBy(AdminDomainListHold::value)

    public fun fromValue(`value`: String): AdminDomainListHold = mapping[value] ?: UNRECOGNIZED
  }
}
