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
 * The kind of the row: `ordinary` (what other users did; in the app on and email off by
 * default), `moderator` (to the holders of the permission it needs; both on by default),
 * or `instance` (the instance's notices to the user; always in the app, but for
 * `announcement`, `auto_delete_limit`, and `scheduled_status_failed`, which are on by default).
 */
@Serializable(with = NotificationSettingRowSection.Serializer::class)
public enum class NotificationSettingRowSection(
  public val `value`: String,
) {
  @SerialName("ordinary")
  ORDINARY("ordinary"),
  @SerialName("moderator")
  MODERATOR("moderator"),
  @SerialName("instance")
  INSTANCE("instance"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<NotificationSettingRowSection> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.NotificationSettingRowSection", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: NotificationSettingRowSection) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): NotificationSettingRowSection {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, NotificationSettingRowSection> =
        entries.associateBy(NotificationSettingRowSection::value)

    public fun fromValue(`value`: String): NotificationSettingRowSection = mapping[value] ?: UNRECOGNIZED
  }
}
