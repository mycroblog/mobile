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
 * How the report was resolved, present only while it is resolved. `actioned`: a moderator
 * took an action on the target. `dismissed`: there was nothing to act on.
 */
@Serializable(with = AdminReportOutcome.Serializer::class)
public enum class AdminReportOutcome(
  public val `value`: String,
) {
  @SerialName("actioned")
  ACTIONED("actioned"),
  @SerialName("dismissed")
  DISMISSED("dismissed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminReportOutcome> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminReportOutcome", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminReportOutcome) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminReportOutcome {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminReportOutcome> =
        entries.associateBy(AdminReportOutcome::value)

    public fun fromValue(`value`: String): AdminReportOutcome = mapping[value] ?: UNRECOGNIZED
  }
}
