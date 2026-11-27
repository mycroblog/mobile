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
 * The state of a subscription. `pending`: started, not paid yet. `active`:
 * paid. `canceling`: canceled at the end of the period. `past_due`: a payment failed less
 * than 7 days ago. `lapsed`: a payment failed more than 7 days ago, or the subscription is
 * paused. `expired`: ended. `revoked`: a full refund or a chargeback. `abandoned`: ended
 * before its first payment. The role is held while `active`, `canceling`, or `past_due`.
 */
@Serializable(with = SupporterSubscriptionState.Serializer::class)
public enum class SupporterSubscriptionState(
  public val `value`: String,
) {
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("active")
  ACTIVE("active"),
  @SerialName("canceling")
  CANCELING("canceling"),
  @SerialName("past_due")
  PAST_DUE("past_due"),
  @SerialName("lapsed")
  LAPSED("lapsed"),
  @SerialName("expired")
  EXPIRED("expired"),
  @SerialName("revoked")
  REVOKED("revoked"),
  @SerialName("abandoned")
  ABANDONED("abandoned"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<SupporterSubscriptionState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.SupporterSubscriptionState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: SupporterSubscriptionState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): SupporterSubscriptionState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, SupporterSubscriptionState> =
        entries.associateBy(SupporterSubscriptionState::value)

    public fun fromValue(`value`: String): SupporterSubscriptionState = mapping[value] ?: UNRECOGNIZED
  }
}
