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

@Serializable(with = AdminAvailabilityEventKind.Serializer::class)
public enum class AdminAvailabilityEventKind(
  public val `value`: String,
) {
  @SerialName("marked_unavailable")
  MARKED_UNAVAILABLE("marked_unavailable"),
  @SerialName("marked_available")
  MARKED_AVAILABLE("marked_available"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAvailabilityEventKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminAvailabilityEventKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAvailabilityEventKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAvailabilityEventKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAvailabilityEventKind> =
        entries.associateBy(AdminAvailabilityEventKind::value)

    public fun fromValue(`value`: String): AdminAvailabilityEventKind = mapping[value] ?: UNRECOGNIZED
  }
}
