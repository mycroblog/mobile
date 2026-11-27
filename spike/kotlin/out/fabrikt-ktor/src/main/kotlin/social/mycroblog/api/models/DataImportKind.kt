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
 * What a CSV file lists: `following` (the accounts the user follows), `followed_tags` (the
 * hashtags the user follows, `followed_hashtags.csv`: one hashtag per line without its `#`,
 * a file of this server that Mastodon does not have), `blocks` (the accounts the user
 * blocks), `mutes` (the accounts the user mutes), `domain_mutes` (the domains the user mutes;
 * Mastodon's `blocked_domains.csv` with the header `#domain`, which a Mastodon user's blocked
 * domains become here), or `bookmarks` (the posts the user bookmarked).
 */
@Serializable(with = DataImportKind.Serializer::class)
public enum class DataImportKind(
  public val `value`: String,
) {
  @SerialName("following")
  FOLLOWING("following"),
  @SerialName("followed_tags")
  FOLLOWED_TAGS("followed_tags"),
  @SerialName("blocks")
  BLOCKS("blocks"),
  @SerialName("mutes")
  MUTES("mutes"),
  @SerialName("domain_mutes")
  DOMAIN_MUTES("domain_mutes"),
  @SerialName("bookmarks")
  BOOKMARKS("bookmarks"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<DataImportKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.DataImportKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: DataImportKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DataImportKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, DataImportKind> = entries.associateBy(DataImportKind::value)

    public fun fromValue(`value`: String): DataImportKind = mapping[value] ?: UNRECOGNIZED
  }
}
