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
 * The role of the process that reported it; the web client reports have the role of the server that received them.
 */
@Serializable(with = ErrorGroupSampleRole.Serializer::class)
public enum class ErrorGroupSampleRole(
  public val `value`: String,
) {
  @SerialName("all")
  ALL("all"),
  @SerialName("web")
  WEB("web"),
  @SerialName("worker")
  WORKER("worker"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ErrorGroupSampleRole> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.ErrorGroupSampleRole", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ErrorGroupSampleRole) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ErrorGroupSampleRole {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ErrorGroupSampleRole> =
        entries.associateBy(ErrorGroupSampleRole::value)

    public fun fromValue(`value`: String): ErrorGroupSampleRole = mapping[value] ?: UNRECOGNIZED
  }
}
