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
 * `active`: in effect now. `expired`: it ended at `expires_at`. `lifted`: a moderator
 * lifted it.
 */
@Serializable(with = ModerationActionState.Serializer::class)
public enum class ModerationActionState(
  public val `value`: String,
) {
  @SerialName("active")
  ACTIVE("active"),
  @SerialName("expired")
  EXPIRED("expired"),
  @SerialName("lifted")
  LIFTED("lifted"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ModerationActionState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.ModerationActionState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ModerationActionState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ModerationActionState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ModerationActionState> =
        entries.associateBy(ModerationActionState::value)

    public fun fromValue(`value`: String): ModerationActionState = mapping[value] ?: UNRECOGNIZED
  }
}
