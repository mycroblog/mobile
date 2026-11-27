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
 * Why the user may not create invites of their own now, the first that holds of
 * `registration_closed`: the registration mode is `closed`, which takes no new accounts;
 * `not_permitted`: the instance setting `invite_creators` is not `everyone` and the user's
 * roles lack `create_invites`; `not_approved`: the registration of the account is not
 * approved; `restricted`: the account is suspended or limited.
 */
@Serializable(with = InvitesNotAllowedReason.Serializer::class)
public enum class InvitesNotAllowedReason(
  public val `value`: String,
) {
  @SerialName("registration_closed")
  REGISTRATION_CLOSED("registration_closed"),
  @SerialName("not_permitted")
  NOT_PERMITTED("not_permitted"),
  @SerialName("not_approved")
  NOT_APPROVED("not_approved"),
  @SerialName("restricted")
  RESTRICTED("restricted"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InvitesNotAllowedReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.InvitesNotAllowedReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InvitesNotAllowedReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InvitesNotAllowedReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InvitesNotAllowedReason> =
        entries.associateBy(InvitesNotAllowedReason::value)

    public fun fromValue(`value`: String): InvitesNotAllowedReason = mapping[value] ?: UNRECOGNIZED
  }
}
