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
 * Why the account is reported. `spam`: spam or unwanted promotion. `legal`: content that
 * is illegal. `violation`: it breaks the instance rules that `rule_ids` cite. `other`:
 * anything else.
 */
@Serializable(with = ReportCategory.Serializer::class)
public enum class ReportCategory(
  public val `value`: String,
) {
  @SerialName("spam")
  SPAM("spam"),
  @SerialName("legal")
  LEGAL("legal"),
  @SerialName("violation")
  VIOLATION("violation"),
  @SerialName("other")
  OTHER("other"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ReportCategory> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.ReportCategory", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ReportCategory) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ReportCategory {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ReportCategory> = entries.associateBy(ReportCategory::value)

    public fun fromValue(`value`: String): ReportCategory = mapping[value] ?: UNRECOGNIZED
  }
}
