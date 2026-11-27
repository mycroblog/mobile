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
 * Which announcements the administrators' list keeps. `draft` and `withdrawn` keep that state.
 * Of the published ones, `scheduled` keeps those whose window starts later, `showing` those every
 * local user sees now, and `ended` those whose window has ended.
 */
@Serializable(with = AdminAnnouncementFilter.Serializer::class)
public enum class AdminAnnouncementFilter(
  public val `value`: String,
) {
  @SerialName("draft")
  DRAFT("draft"),
  @SerialName("scheduled")
  SCHEDULED("scheduled"),
  @SerialName("showing")
  SHOWING("showing"),
  @SerialName("ended")
  ENDED("ended"),
  @SerialName("withdrawn")
  WITHDRAWN("withdrawn"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAnnouncementFilter> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminAnnouncementFilter", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAnnouncementFilter) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAnnouncementFilter {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAnnouncementFilter> =
        entries.associateBy(AdminAnnouncementFilter::value)

    public fun fromValue(`value`: String): AdminAnnouncementFilter = mapping[value] ?: UNRECOGNIZED
  }
}
