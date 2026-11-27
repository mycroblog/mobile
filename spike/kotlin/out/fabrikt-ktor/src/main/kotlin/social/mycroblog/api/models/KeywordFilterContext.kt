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
 * Where a keyword filter applies: `home` (the home timeline and the timelines of lists),
 * `notifications` (the notifications, their stream, Web Push, and email), `public` (the local,
 * federated, and hashtag timelines and their streams), `thread` (the posts before and after a
 * post), and `account` (the posts and pinned posts of a profile).
 */
@Serializable(with = KeywordFilterContext.Serializer::class)
public enum class KeywordFilterContext(
  public val `value`: String,
) {
  @SerialName("home")
  HOME("home"),
  @SerialName("notifications")
  NOTIFICATIONS("notifications"),
  @SerialName("public")
  PUBLIC("public"),
  @SerialName("thread")
  THREAD("thread"),
  @SerialName("account")
  ACCOUNT("account"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<KeywordFilterContext> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.KeywordFilterContext", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: KeywordFilterContext) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): KeywordFilterContext {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, KeywordFilterContext> =
        entries.associateBy(KeywordFilterContext::value)

    public fun fromValue(`value`: String): KeywordFilterContext = mapping[value] ?: UNRECOGNIZED
  }
}
