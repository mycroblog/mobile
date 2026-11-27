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
 * The direction the instance federates in (the instance setting `federation_mode`):
 *
 * - `off`: neither way. Following, looking up with `resolve`, and quoting remote
 *   accounts and posts are refused with the 403 `/api/v1/problems/federation-restricted`.
 * - `inbound`: the instance follows and reads remote accounts but sends none of the local
 *   posts, profiles, and reactions out. Favourites, boosts, reactions, and replies to
 *   remote posts are recorded here but do not reach their servers; quoting a remote post
 *   is refused with the 403 `/api/v1/problems/federation-restricted`, since the quote
 *   request cannot be sent.
 * - `outbound`: the instance delivers the local posts to remote followers but takes none
 *   of the remote posts and reactions in. Following and resolving remote accounts are
 *   refused with the 403 `/api/v1/problems/federation-restricted`.
 * - `both`: both ways.
 */
@Serializable(with = InstanceFederationMode.Serializer::class)
public enum class InstanceFederationMode(
  public val `value`: String,
) {
  @SerialName("off")
  OFF("off"),
  @SerialName("inbound")
  INBOUND("inbound"),
  @SerialName("outbound")
  OUTBOUND("outbound"),
  @SerialName("both")
  BOTH("both"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InstanceFederationMode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InstanceFederationMode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InstanceFederationMode) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InstanceFederationMode {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InstanceFederationMode> =
        entries.associateBy(InstanceFederationMode::value)

    public fun fromValue(`value`: String): InstanceFederationMode = mapping[value] ?: UNRECOGNIZED
  }
}
