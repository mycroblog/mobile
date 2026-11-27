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
 * The kind of reason for an action.
 */
@Serializable(with = AdminAccountActionCategory.Serializer::class)
public enum class AdminAccountActionCategory(
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

  public object Serializer : KSerializer<AdminAccountActionCategory> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminAccountActionCategory", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAccountActionCategory) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAccountActionCategory {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAccountActionCategory> =
        entries.associateBy(AdminAccountActionCategory::value)

    public fun fromValue(`value`: String): AdminAccountActionCategory = mapping[value] ?: UNRECOGNIZED
  }
}
