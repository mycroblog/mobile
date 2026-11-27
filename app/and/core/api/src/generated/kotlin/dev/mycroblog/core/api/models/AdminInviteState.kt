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
 * What the invite allows now. `usable`: people may sign up with it. `revoked`: it was
 * revoked. `expired`: it passed `expires_at`. `exhausted`: it was used `max_uses` times.
 * `creator_ineligible`: a member created it who may no longer create invites (their
 * permission or plan ended, the instance setting `invite_creators` changed, or the account is
 * suspended, limited, or deleted); it works again when the member may.
 */
@Serializable(with = AdminInviteState.Serializer::class)
public enum class AdminInviteState(
  public val `value`: String,
) {
  @SerialName("usable")
  USABLE("usable"),
  @SerialName("revoked")
  REVOKED("revoked"),
  @SerialName("expired")
  EXPIRED("expired"),
  @SerialName("exhausted")
  EXHAUSTED("exhausted"),
  @SerialName("creator_ineligible")
  CREATOR_INELIGIBLE("creator_ineligible"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminInviteState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminInviteState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminInviteState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminInviteState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminInviteState> =
        entries.associateBy(AdminInviteState::value)

    public fun fromValue(`value`: String): AdminInviteState = mapping[value] ?: UNRECOGNIZED
  }
}
