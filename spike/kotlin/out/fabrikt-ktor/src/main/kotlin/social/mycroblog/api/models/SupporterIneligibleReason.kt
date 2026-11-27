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
 * Why the user may not subscribe to a supporter plan or tip: `not_local`, not a user
 * of this server; `email_unverified`, the primary email address is not verified;
 * `restricted`, the account is suspended or its deletion is done or scheduled. Omitted when
 * the user may.
 */
@Serializable(with = SupporterIneligibleReason.Serializer::class)
public enum class SupporterIneligibleReason(
  public val `value`: String,
) {
  @SerialName("not_local")
  NOT_LOCAL("not_local"),
  @SerialName("email_unverified")
  EMAIL_UNVERIFIED("email_unverified"),
  @SerialName("restricted")
  RESTRICTED("restricted"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<SupporterIneligibleReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.SupporterIneligibleReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: SupporterIneligibleReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): SupporterIneligibleReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, SupporterIneligibleReason> =
        entries.associateBy(SupporterIneligibleReason::value)

    public fun fromValue(`value`: String): SupporterIneligibleReason = mapping[value] ?: UNRECOGNIZED
  }
}
