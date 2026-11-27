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
 * `merge` adds the file's entries and keeps what the user has; `overwrite` also removes
 * what the user has that the file does not list.
 */
@Serializable(with = DataImportMode.Serializer::class)
public enum class DataImportMode(
  public val `value`: String,
) {
  @SerialName("merge")
  MERGE("merge"),
  @SerialName("overwrite")
  OVERWRITE("overwrite"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<DataImportMode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.DataImportMode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: DataImportMode) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DataImportMode {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, DataImportMode> = entries.associateBy(DataImportMode::value)

    public fun fromValue(`value`: String): DataImportMode = mapping[value] ?: UNRECOGNIZED
  }
}
