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
 * `invalid`: not a valid value for the kind. `duplicate`: an earlier line has the same
 * value in canonical form. `already_blocked`: a block in effect of the same kind and
 * value with a severity at least as strong covers it.
 */
@Serializable(with = SignupBlockImportSkippedReason.Serializer::class)
public enum class SignupBlockImportSkippedReason(
  public val `value`: String,
) {
  @SerialName("invalid")
  INVALID("invalid"),
  @SerialName("duplicate")
  DUPLICATE("duplicate"),
  @SerialName("already_blocked")
  ALREADY_BLOCKED("already_blocked"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<SignupBlockImportSkippedReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.SignupBlockImportSkippedReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: SignupBlockImportSkippedReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): SignupBlockImportSkippedReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, SignupBlockImportSkippedReason> =
        entries.associateBy(SignupBlockImportSkippedReason::value)

    public fun fromValue(`value`: String): SignupBlockImportSkippedReason = mapping[value] ?: UNRECOGNIZED
  }
}
