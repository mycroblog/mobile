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
 * `draft`: no user sees it. `published`: every local user sees it within its window.
 * `withdrawn`: taken down for good.
 */
@Serializable(with = AdminAnnouncementState.Serializer::class)
public enum class AdminAnnouncementState(
  public val `value`: String,
) {
  @SerialName("draft")
  DRAFT("draft"),
  @SerialName("published")
  PUBLISHED("published"),
  @SerialName("withdrawn")
  WITHDRAWN("withdrawn"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAnnouncementState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminAnnouncementState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAnnouncementState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAnnouncementState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAnnouncementState> =
        entries.associateBy(AdminAnnouncementState::value)

    public fun fromValue(`value`: String): AdminAnnouncementState = mapping[value] ?: UNRECOGNIZED
  }
}
