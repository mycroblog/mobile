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
 * A decision hook, where the server asks an external review before it accepts something:
 * `inbound.status` (posts from other servers), `inbound.follow` (follows from other servers),
 * `signup.check` (sign-ups), `media.check` (uploaded media), `local.status` (local posts),
 * `inbound.actor` (profiles of accounts on other servers), `local.profile` (local profiles).
 */
@Serializable(with = HookName.Serializer::class)
public enum class HookName(
  public val `value`: String,
) {
  @SerialName("inbound.status")
  INBOUND_STATUS("inbound.status"),
  @SerialName("inbound.follow")
  INBOUND_FOLLOW("inbound.follow"),
  @SerialName("signup.check")
  SIGNUP_CHECK("signup.check"),
  @SerialName("media.check")
  MEDIA_CHECK("media.check"),
  @SerialName("local.status")
  LOCAL_STATUS("local.status"),
  @SerialName("inbound.actor")
  INBOUND_ACTOR("inbound.actor"),
  @SerialName("local.profile")
  LOCAL_PROFILE("local.profile"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<HookName> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.HookName", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: HookName) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): HookName {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, HookName> = entries.associateBy(HookName::value)

    public fun fromValue(`value`: String): HookName = mapping[value] ?: UNRECOGNIZED
  }
}
