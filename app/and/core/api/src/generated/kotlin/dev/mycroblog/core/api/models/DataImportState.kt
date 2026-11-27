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
 * `in_progress` until every entry and removal is done, then `finished`.
 */
@Serializable(with = DataImportState.Serializer::class)
public enum class DataImportState(
  public val `value`: String,
) {
  @SerialName("in_progress")
  IN_PROGRESS("in_progress"),
  @SerialName("finished")
  FINISHED("finished"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<DataImportState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.DataImportState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: DataImportState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DataImportState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, DataImportState> = entries.associateBy(DataImportState::value)

    public fun fromValue(`value`: String): DataImportState = mapping[value] ?: UNRECOGNIZED
  }
}
