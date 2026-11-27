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
 * What became of the user's follow of the moved account, for an `account_moved` only:
 * `followed`, the user follows `target` instead; `requested`, the user's follow of
 * `target` awaits its approval instead; and the user still follows the moved account
 * when the user blocks `target` (`blocking`), `target` blocks the user (`blocked_by`), or
 * the user mutes the server of `target` (`domain_muted`).
 */
@Serializable(with = AccountMoveOutcome.Serializer::class)
public enum class AccountMoveOutcome(
  public val `value`: String,
) {
  @SerialName("followed")
  FOLLOWED("followed"),
  @SerialName("requested")
  REQUESTED("requested"),
  @SerialName("blocking")
  BLOCKING("blocking"),
  @SerialName("blocked_by")
  BLOCKED_BY("blocked_by"),
  @SerialName("domain_muted")
  DOMAIN_MUTED("domain_muted"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AccountMoveOutcome> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AccountMoveOutcome", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AccountMoveOutcome) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AccountMoveOutcome {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AccountMoveOutcome> =
        entries.associateBy(AccountMoveOutcome::value)

    public fun fromValue(`value`: String): AccountMoveOutcome = mapping[value] ?: UNRECOGNIZED
  }
}
