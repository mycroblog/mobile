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
 * Why a relay operation is refused. `url`: not an https URL of at most 2048 bytes.
 * `exists`: a relay with the URL is registered. `domain_suspended`: a domain action
 * suspends its server. `not_actor`: the URL of a `litepub` relay serves no actor with an
 * inbox. `state`: the relay is not in a state that allows it (joining a `pending` or
 * `accepted` relay, leaving or deleting one that is not).
 */
@Serializable(with = RelayProblemReason.Serializer::class)
public enum class RelayProblemReason(
  public val `value`: String,
) {
  @SerialName("url")
  URL("url"),
  @SerialName("exists")
  EXISTS("exists"),
  @SerialName("domain_suspended")
  DOMAIN_SUSPENDED("domain_suspended"),
  @SerialName("not_actor")
  NOT_ACTOR("not_actor"),
  @SerialName("state")
  STATE("state"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<RelayProblemReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.RelayProblemReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: RelayProblemReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): RelayProblemReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, RelayProblemReason> =
        entries.associateBy(RelayProblemReason::value)

    public fun fromValue(`value`: String): RelayProblemReason = mapping[value] ?: UNRECOGNIZED
  }
}
