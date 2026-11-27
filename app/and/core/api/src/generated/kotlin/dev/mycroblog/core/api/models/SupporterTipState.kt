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
 * The state of a tip: `open` until its payment or the end of its checkout, `paid` once its
 * payment settled, and `expired` when its checkout ended unpaid.
 */
@Serializable(with = SupporterTipState.Serializer::class)
public enum class SupporterTipState(
  public val `value`: String,
) {
  @SerialName("open")
  OPEN("open"),
  @SerialName("paid")
  PAID("paid"),
  @SerialName("expired")
  EXPIRED("expired"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<SupporterTipState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.SupporterTipState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: SupporterTipState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): SupporterTipState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, SupporterTipState> =
        entries.associateBy(SupporterTipState::value)

    public fun fromValue(`value`: String): SupporterTipState = mapping[value] ?: UNRECOGNIZED
  }
}
