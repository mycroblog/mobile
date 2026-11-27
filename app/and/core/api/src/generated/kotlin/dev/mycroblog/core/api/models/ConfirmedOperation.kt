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
 * An operation that the user confirms: registering a passkey (`passkey_registration`),
 * changing or setting the password (`password_change`), removing it (`password_removal`),
 * deleting the account (`account_deletion`), deactivating it (`account_deactivation`), moving it (`account_move`), and linking an
 * account at a login provider (`link`).
 */
@Serializable(with = ConfirmedOperation.Serializer::class)
public enum class ConfirmedOperation(
  public val `value`: String,
) {
  @SerialName("passkey_registration")
  PASSKEY_REGISTRATION("passkey_registration"),
  @SerialName("password_change")
  PASSWORD_CHANGE("password_change"),
  @SerialName("password_removal")
  PASSWORD_REMOVAL("password_removal"),
  @SerialName("account_deletion")
  ACCOUNT_DELETION("account_deletion"),
  @SerialName("account_deactivation")
  ACCOUNT_DEACTIVATION("account_deactivation"),
  @SerialName("account_move")
  ACCOUNT_MOVE("account_move"),
  @SerialName("link")
  LINK("link"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ConfirmedOperation> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.ConfirmedOperation", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ConfirmedOperation) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ConfirmedOperation {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ConfirmedOperation> =
        entries.associateBy(ConfirmedOperation::value)

    public fun fromValue(`value`: String): ConfirmedOperation = mapping[value] ?: UNRECOGNIZED
  }
}
