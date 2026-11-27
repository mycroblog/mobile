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
 * What a `silence` of the list issues on this server; `ignore` issues nothing.
 */
@Serializable(with = AdminDomainListSilenceAs.Serializer::class)
public enum class AdminDomainListSilenceAs(
  public val `value`: String,
) {
  @SerialName("limit")
  LIMIT("limit"),
  @SerialName("ignore")
  IGNORE("ignore"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDomainListSilenceAs> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminDomainListSilenceAs", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDomainListSilenceAs) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDomainListSilenceAs {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDomainListSilenceAs> =
        entries.associateBy(AdminDomainListSilenceAs::value)

    public fun fromValue(`value`: String): AdminDomainListSilenceAs = mapping[value] ?: UNRECOGNIZED
  }
}
