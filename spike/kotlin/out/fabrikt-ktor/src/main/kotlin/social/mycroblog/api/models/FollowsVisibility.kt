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
 * Who may read an account's follows and followers lists (`GET /accounts/{id}/following` and
 * `GET /accounts/{id}/followers`): `public`, everyone; `followers`, the accounts whose follow
 * of it is approved; `private`, nobody. The owner always reads both, and the counts
 * (`following_count`, `followers_count`) stay visible to everyone. Other servers see the
 * number of follows and followers alone unless the lists are `public`.
 */
@Serializable(with = FollowsVisibility.Serializer::class)
public enum class FollowsVisibility(
  public val `value`: String,
) {
  @SerialName("public")
  PUBLIC("public"),
  @SerialName("followers")
  FOLLOWERS("followers"),
  @SerialName("private")
  PRIVATE("private"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<FollowsVisibility> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.FollowsVisibility", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: FollowsVisibility) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): FollowsVisibility {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, FollowsVisibility> =
        entries.associateBy(FollowsVisibility::value)

    public fun fromValue(`value`: String): FollowsVisibility = mapping[value] ?: UNRECOGNIZED
  }
}
