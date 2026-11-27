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
 * What an administrator should look at in the supporter program. `no_provider`: no payment
 * provider is configured. `no_email`: the server sends no email, so nobody can verify an
 * address to subscribe. `provider_mismatch`: the configured provider is not the one the
 * program was enabled with; new subscriptions and changes are refused until it is disabled
 * and enabled again (the subscribers subscribe again). `no_commerce_disclosure`: the
 * instance publishes no commerce disclosures (the `commerce_disclosure` instance text).
 */
@Serializable(with = SupporterProgramWarning.Serializer::class)
public enum class SupporterProgramWarning(
  public val `value`: String,
) {
  @SerialName("no_provider")
  NO_PROVIDER("no_provider"),
  @SerialName("no_email")
  NO_EMAIL("no_email"),
  @SerialName("provider_mismatch")
  PROVIDER_MISMATCH("provider_mismatch"),
  @SerialName("no_commerce_disclosure")
  NO_COMMERCE_DISCLOSURE("no_commerce_disclosure"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<SupporterProgramWarning> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.SupporterProgramWarning", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: SupporterProgramWarning) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): SupporterProgramWarning {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, SupporterProgramWarning> =
        entries.associateBy(SupporterProgramWarning::value)

    public fun fromValue(`value`: String): SupporterProgramWarning = mapping[value] ?: UNRECOGNIZED
  }
}
