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
 * Who may write new articles (`createArticle`): `everyone` (every local user who is not
 * limited) or `permission` (only the users whose roles have `create_articles`). Always given.
 */
@Serializable(with = InstanceArticleAuthoring.Serializer::class)
public enum class InstanceArticleAuthoring(
  public val `value`: String,
) {
  @SerialName("everyone")
  EVERYONE("everyone"),
  @SerialName("permission")
  PERMISSION("permission"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InstanceArticleAuthoring> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.InstanceArticleAuthoring", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InstanceArticleAuthoring) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InstanceArticleAuthoring {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InstanceArticleAuthoring> =
        entries.associateBy(InstanceArticleAuthoring::value)

    public fun fromValue(`value`: String): InstanceArticleAuthoring = mapping[value] ?: UNRECOGNIZED
  }
}
