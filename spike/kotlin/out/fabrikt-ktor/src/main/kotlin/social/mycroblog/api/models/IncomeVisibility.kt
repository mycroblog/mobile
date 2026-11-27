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
 * How much of the instance's income everyone may read (the instance setting
 * `income_visibility`), apart from `finance_visibility`. `private`:
 * nothing, and `GET /instance/income_totals` is a 404; `totals`: the net income of each
 * currency by period; `plans`: also the number of members of each supporter plan now. No
 * level publishes individual payments, the subscribers, the amounts of each plan, or the
 * gross and the fees.
 */
@Serializable(with = IncomeVisibility.Serializer::class)
public enum class IncomeVisibility(
  public val `value`: String,
) {
  @SerialName("private")
  PRIVATE("private"),
  @SerialName("totals")
  TOTALS("totals"),
  @SerialName("plans")
  PLANS("plans"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<IncomeVisibility> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.IncomeVisibility", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: IncomeVisibility) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): IncomeVisibility {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, IncomeVisibility> =
        entries.associateBy(IncomeVisibility::value)

    public fun fromValue(`value`: String): IncomeVisibility = mapping[value] ?: UNRECOGNIZED
  }
}
