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
 * Where an appeal of a refused post stands: `pending` until a moderator decides it,
 * `upheld` when the refusal stays, `overturned` when the moderator canceled it. After
 * `overturned` the user's posts are no longer reviewed, and the user posts again: the refused
 * post is not restored.
 */
@Serializable(with = HookAppealState.Serializer::class)
public enum class HookAppealState(
  public val `value`: String,
) {
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("upheld")
  UPHELD("upheld"),
  @SerialName("overturned")
  OVERTURNED("overturned"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<HookAppealState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.HookAppealState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: HookAppealState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): HookAppealState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, HookAppealState> = entries.associateBy(HookAppealState::value)

    public fun fromValue(`value`: String): HookAppealState = mapping[value] ?: UNRECOGNIZED
  }
}
