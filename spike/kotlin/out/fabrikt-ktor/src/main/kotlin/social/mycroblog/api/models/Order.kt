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

@Serializable(with = Order.Serializer::class)
public enum class Order(
  public val `value`: String,
) {
  @SerialName("newest")
  NEWEST("newest"),
  @SerialName("oldest")
  OLDEST("oldest"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<Order> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.Order", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: Order) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): Order {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, Order> = entries.associateBy(Order::value)

    public fun fromValue(`value`: String): Order = mapping[value] ?: UNRECOGNIZED
  }
}
