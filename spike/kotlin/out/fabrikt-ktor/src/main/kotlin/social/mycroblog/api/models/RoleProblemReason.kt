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
 * Why a role change is refused. `key`: not 1 to 64 lowercase ASCII letters, digits, `_`,
 * and `-`. `key_taken`: another role has the key. `position`: outside 0 to 999.
 * `permissions`: an unknown permission, or one twice. `builtin`: `admin` changes only its
 * badge, and neither `admin` nor `moderator` can be deleted. `held`: users hold the role.
 * `missing_permission`: the change adds or removes permissions that the viewer's roles
 * do not grant (`missing_permissions`). `badge_label`: a badge label that is not 1 to 32
 * characters on one line. `badge_color`: an unknown badge color.
 */
@Serializable(with = RoleProblemReason.Serializer::class)
public enum class RoleProblemReason(
  public val `value`: String,
) {
  @SerialName("key")
  KEY("key"),
  @SerialName("key_taken")
  KEY_TAKEN("key_taken"),
  @SerialName("position")
  POSITION("position"),
  @SerialName("permissions")
  PERMISSIONS("permissions"),
  @SerialName("builtin")
  BUILTIN("builtin"),
  @SerialName("held")
  HELD("held"),
  @SerialName("missing_permission")
  MISSING_PERMISSION("missing_permission"),
  @SerialName("badge_label")
  BADGE_LABEL("badge_label"),
  @SerialName("badge_color")
  BADGE_COLOR("badge_color"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<RoleProblemReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.RoleProblemReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: RoleProblemReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): RoleProblemReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, RoleProblemReason> =
        entries.associateBy(RoleProblemReason::value)

    public fun fromValue(`value`: String): RoleProblemReason = mapping[value] ?: UNRECOGNIZED
  }
}
