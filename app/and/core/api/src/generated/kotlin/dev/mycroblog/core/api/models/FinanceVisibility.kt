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
 * How much of the instance's costs everyone may read (the instance setting
 * `finance_visibility`). `private`: nothing, and
 * `GET /instance/cost_totals` is a 404; `totals`: the total of each currency by period;
 * `breakdown`: the totals and their breakdown by provider and category. No level publishes
 * the names, memos, or individual payments of the cost items.
 */
@Serializable(with = FinanceVisibility.Serializer::class)
public enum class FinanceVisibility(
  public val `value`: String,
) {
  @SerialName("private")
  PRIVATE("private"),
  @SerialName("totals")
  TOTALS("totals"),
  @SerialName("breakdown")
  BREAKDOWN("breakdown"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<FinanceVisibility> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.FinanceVisibility", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: FinanceVisibility) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): FinanceVisibility {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, FinanceVisibility> =
        entries.associateBy(FinanceVisibility::value)

    public fun fromValue(`value`: String): FinanceVisibility = mapping[value] ?: UNRECOGNIZED
  }
}
