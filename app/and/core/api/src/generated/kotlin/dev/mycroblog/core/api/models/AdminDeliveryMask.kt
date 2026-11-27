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
 * Why the sender, the activity URI, and a personal inbox of a delivery are masked. `private`,
 * `direct`, `local`: the activity is about a post of that visibility. `approves_followers`: a
 * follow, its undo, an accept, or a reject of a local account that approves its followers. `unknown`:
 * the post or the account is gone, so what it was is no longer known.
 */
@Serializable(with = AdminDeliveryMask.Serializer::class)
public enum class AdminDeliveryMask(
  public val `value`: String,
) {
  @SerialName("private")
  PRIVATE("private"),
  @SerialName("direct")
  DIRECT("direct"),
  @SerialName("local")
  LOCAL("local"),
  @SerialName("approves_followers")
  APPROVES_FOLLOWERS("approves_followers"),
  @SerialName("unknown")
  UNKNOWN("unknown"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDeliveryMask> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminDeliveryMask", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDeliveryMask) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDeliveryMask {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDeliveryMask> =
        entries.associateBy(AdminDeliveryMask::value)

    public fun fromValue(`value`: String): AdminDeliveryMask = mapping[value] ?: UNRECOGNIZED
  }
}
