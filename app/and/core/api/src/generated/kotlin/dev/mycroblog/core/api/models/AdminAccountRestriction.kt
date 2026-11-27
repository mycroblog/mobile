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
 * A capability that an action takes away. `login`: logging in and the
 * API. `post`: posting, replying, boosting, quoting, editing. `interact`: following, favouriting,
 * boosting, voting. `visibility_ceiling_private`: posts are followers-only.
 * `public_discovery`: posts stay off the public timelines and search.
 * `unsolicited_reach`: notifications reach followers only. `media_sensitive`: media shows
 * as sensitive. `profile_view`: the profile and posts are hidden. `federation`: nothing is
 * delivered or received.
 */
@Serializable(with = AdminAccountRestriction.Serializer::class)
public enum class AdminAccountRestriction(
  public val `value`: String,
) {
  @SerialName("login")
  LOGIN("login"),
  @SerialName("post")
  POST("post"),
  @SerialName("interact")
  INTERACT("interact"),
  @SerialName("visibility_ceiling_private")
  VISIBILITY_CEILING_PRIVATE("visibility_ceiling_private"),
  @SerialName("public_discovery")
  PUBLIC_DISCOVERY("public_discovery"),
  @SerialName("unsolicited_reach")
  UNSOLICITED_REACH("unsolicited_reach"),
  @SerialName("media_sensitive")
  MEDIA_SENSITIVE("media_sensitive"),
  @SerialName("profile_view")
  PROFILE_VIEW("profile_view"),
  @SerialName("federation")
  FEDERATION("federation"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAccountRestriction> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminAccountRestriction", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAccountRestriction) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAccountRestriction {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAccountRestriction> =
        entries.associateBy(AdminAccountRestriction::value)

    public fun fromValue(`value`: String): AdminAccountRestriction = mapping[value] ?: UNRECOGNIZED
  }
}
