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
 * Whether the instance lets search engines index its pages (the instance setting
 * `search_indexing`). `allow`: the profiles and public posts of the accounts
 * that are `indexable`; `deny`: no page. An account can deny what the instance allows, never
 * the reverse.
 */
@Serializable(with = SearchIndexing.Serializer::class)
public enum class SearchIndexing(
  public val `value`: String,
) {
  @SerialName("allow")
  ALLOW("allow"),
  @SerialName("deny")
  DENY("deny"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<SearchIndexing> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.SearchIndexing", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: SearchIndexing) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): SearchIndexing {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, SearchIndexing> = entries.associateBy(SearchIndexing::value)

    public fun fromValue(`value`: String): SearchIndexing = mapping[value] ?: UNRECOGNIZED
  }
}
