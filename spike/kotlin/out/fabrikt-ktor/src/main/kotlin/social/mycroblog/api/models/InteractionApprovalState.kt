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
 * The approval of the authenticated user's interaction (a reply, a boost, or a quote) by the
 * author of the status it is with, while it is not given:
 *
 * - `pending`: it waits for the author's answer. A reply that waits is shown to its author
 *   alone, and goes out once it is approved; a boost that waits is recorded once it is approved.
 * - `rejected`: the author refused it.
 */
@Serializable(with = InteractionApprovalState.Serializer::class)
public enum class InteractionApprovalState(
  public val `value`: String,
) {
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("rejected")
  REJECTED("rejected"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InteractionApprovalState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InteractionApprovalState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InteractionApprovalState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InteractionApprovalState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InteractionApprovalState> =
        entries.associateBy(InteractionApprovalState::value)

    public fun fromValue(`value`: String): InteractionApprovalState = mapping[value] ?: UNRECOGNIZED
  }
}
