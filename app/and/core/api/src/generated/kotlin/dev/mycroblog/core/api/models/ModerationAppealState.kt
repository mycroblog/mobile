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
 * Where an appeal stands: `pending` awaits the moderators, `upheld` kept the action, and
 * `overturned` lifted it.
 */
@Serializable(with = ModerationAppealState.Serializer::class)
public enum class ModerationAppealState(
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

  public object Serializer : KSerializer<ModerationAppealState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.ModerationAppealState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ModerationAppealState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ModerationAppealState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ModerationAppealState> =
        entries.associateBy(ModerationAppealState::value)

    public fun fromValue(`value`: String): ModerationAppealState = mapping[value] ?: UNRECOGNIZED
  }
}
