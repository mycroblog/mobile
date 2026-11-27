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
 * Why a `lapsed`, `expired`, or `revoked` subscription lost the plan's role; omitted in the
 * other states. `lapsed`: no payment succeeded within 7 days after a payment failed, or the
 * subscription is paused. `reversed`: a payment was refunded in full or charged back.
 * `plan_retired`: the administrators retired the plan. `account_suspended`: the account was
 * suspended. `account_closed`: the account was deleted. `staff`: an administrator canceled
 * it. `canceled`: it was canceled, or it ended at the payment provider.
 */
@Serializable(with = SupporterEndReason.Serializer::class)
public enum class SupporterEndReason(
  public val `value`: String,
) {
  @SerialName("lapsed")
  LAPSED("lapsed"),
  @SerialName("reversed")
  REVERSED("reversed"),
  @SerialName("plan_retired")
  PLAN_RETIRED("plan_retired"),
  @SerialName("account_suspended")
  ACCOUNT_SUSPENDED("account_suspended"),
  @SerialName("account_closed")
  ACCOUNT_CLOSED("account_closed"),
  @SerialName("staff")
  STAFF("staff"),
  @SerialName("canceled")
  CANCELED("canceled"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<SupporterEndReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.SupporterEndReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: SupporterEndReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): SupporterEndReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, SupporterEndReason> =
        entries.associateBy(SupporterEndReason::value)

    public fun fromValue(`value`: String): SupporterEndReason = mapping[value] ?: UNRECOGNIZED
  }
}
