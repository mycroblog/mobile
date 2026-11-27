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
 * Why the account is suggested: `featured` when the administrators feature it,
 * `recently_active` when it posted recently.
 */
@Serializable(with = AccountSuggestionSource.Serializer::class)
public enum class AccountSuggestionSource(
  public val `value`: String,
) {
  @SerialName("featured")
  FEATURED("featured"),
  @SerialName("recently_active")
  RECENTLY_ACTIVE("recently_active"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AccountSuggestionSource> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AccountSuggestionSource", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AccountSuggestionSource) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AccountSuggestionSource {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AccountSuggestionSource> =
        entries.associateBy(AccountSuggestionSource::value)

    public fun fromValue(`value`: String): AccountSuggestionSource = mapping[value] ?: UNRECOGNIZED
  }
}
