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
 * The state of an account, the first that applies: `deleted` (deleted here or on its
 * server), `suspended` (a `suspend` or `ban`), `disabled` (a `disable_login`),
 * `deactivated` (by its owner), `rejected` and `pending` (the registration of a local account), `limited` (a `limit`), and
 * `active`.
 */
@Serializable(with = AdminAccountState.Serializer::class)
public enum class AdminAccountState(
  public val `value`: String,
) {
  @SerialName("active")
  ACTIVE("active"),
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("rejected")
  REJECTED("rejected"),
  @SerialName("limited")
  LIMITED("limited"),
  @SerialName("disabled")
  DISABLED("disabled"),
  @SerialName("deactivated")
  DEACTIVATED("deactivated"),
  @SerialName("suspended")
  SUSPENDED("suspended"),
  @SerialName("deleted")
  DELETED("deleted"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAccountState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminAccountState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAccountState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAccountState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAccountState> =
        entries.associateBy(AdminAccountState::value)

    public fun fromValue(`value`: String): AdminAccountState = mapping[value] ?: UNRECOGNIZED
  }
}
