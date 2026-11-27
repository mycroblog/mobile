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
 * `invalid`: the line names no account address (for bookmarks, no post URL; for
 * domain mutes, no domain).
 * `duplicate`: an earlier line names the same account, post, or domain.
 */
@Serializable(with = DataImportSkippedReason.Serializer::class)
public enum class DataImportSkippedReason(
  public val `value`: String,
) {
  @SerialName("invalid")
  INVALID("invalid"),
  @SerialName("duplicate")
  DUPLICATE("duplicate"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<DataImportSkippedReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.DataImportSkippedReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: DataImportSkippedReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DataImportSkippedReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, DataImportSkippedReason> =
        entries.associateBy(DataImportSkippedReason::value)

    public fun fromValue(`value`: String): DataImportSkippedReason = mapping[value] ?: UNRECOGNIZED
  }
}
