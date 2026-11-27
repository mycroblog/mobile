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
 * `succeeded`; `partial` when it used its `payments_reconcile_max_requests` and a
 * continuation takes over; `failed` when the provider failed.
 */
@Serializable(with = PaymentReconciliationRunOutcome.Serializer::class)
public enum class PaymentReconciliationRunOutcome(
  public val `value`: String,
) {
  @SerialName("succeeded")
  SUCCEEDED("succeeded"),
  @SerialName("partial")
  PARTIAL("partial"),
  @SerialName("failed")
  FAILED("failed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<PaymentReconciliationRunOutcome> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.PaymentReconciliationRunOutcome", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: PaymentReconciliationRunOutcome) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): PaymentReconciliationRunOutcome {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, PaymentReconciliationRunOutcome> =
        entries.associateBy(PaymentReconciliationRunOutcome::value)

    public fun fromValue(`value`: String): PaymentReconciliationRunOutcome = mapping[value] ?: UNRECOGNIZED
  }
}
