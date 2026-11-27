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
 * `pending` while the archive is being built, `ready` while it can be downloaded,
 * `failed` when it could not be built (request another one).
 */
@Serializable(with = DataExportState.Serializer::class)
public enum class DataExportState(
  public val `value`: String,
) {
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("ready")
  READY("ready"),
  @SerialName("failed")
  FAILED("failed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<DataExportState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.DataExportState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: DataExportState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DataExportState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, DataExportState> = entries.associateBy(DataExportState::value)

    public fun fromValue(`value`: String): DataExportState = mapping[value] ?: UNRECOGNIZED
  }
}
