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
 * How much of the income the holders of `view_finance` read (the instance setting
 * `finance_viewer_income`). `totals`: the total of each currency by period;
 * `plans`: also the number of members of each plan. No value shows the amounts of each plan or
 * who paid.
 */
@Serializable(with = FinanceViewerIncome.Serializer::class)
public enum class FinanceViewerIncome(
  public val `value`: String,
) {
  @SerialName("totals")
  TOTALS("totals"),
  @SerialName("plans")
  PLANS("plans"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<FinanceViewerIncome> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.FinanceViewerIncome", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: FinanceViewerIncome) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): FinanceViewerIncome {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, FinanceViewerIncome> =
        entries.associateBy(FinanceViewerIncome::value)

    public fun fromValue(`value`: String): FinanceViewerIncome = mapping[value] ?: UNRECOGNIZED
  }
}
