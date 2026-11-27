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
 * `warn`: a notice only. `mark_statuses_sensitive` and `delete_statuses`: actions on
 * posts. `force_sensitive`: every attachment is shown as sensitive. `limit`: every post is
 * followers-only and stays off the public timelines. `restrict_posting`: no posting.
 * `restrict_interaction`: no following, favouriting, boosting, or replying.
 * `disable_login`: no logging in. `suspend`: all of these, and the profile and posts are
 * hidden. `ban`: a suspension that never ends, with the deletion of the account.
 */
@Serializable(with = AdminAccountActionKind.Serializer::class)
public enum class AdminAccountActionKind(
  public val `value`: String,
) {
  @SerialName("warn")
  WARN("warn"),
  @SerialName("mark_statuses_sensitive")
  MARK_STATUSES_SENSITIVE("mark_statuses_sensitive"),
  @SerialName("delete_statuses")
  DELETE_STATUSES("delete_statuses"),
  @SerialName("force_sensitive")
  FORCE_SENSITIVE("force_sensitive"),
  @SerialName("limit")
  LIMIT("limit"),
  @SerialName("restrict_posting")
  RESTRICT_POSTING("restrict_posting"),
  @SerialName("restrict_interaction")
  RESTRICT_INTERACTION("restrict_interaction"),
  @SerialName("disable_login")
  DISABLE_LOGIN("disable_login"),
  @SerialName("suspend")
  SUSPEND("suspend"),
  @SerialName("ban")
  BAN("ban"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAccountActionKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminAccountActionKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAccountActionKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAccountActionKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAccountActionKind> =
        entries.associateBy(AdminAccountActionKind::value)

    public fun fromValue(`value`: String): AdminAccountActionKind = mapping[value] ?: UNRECOGNIZED
  }
}
