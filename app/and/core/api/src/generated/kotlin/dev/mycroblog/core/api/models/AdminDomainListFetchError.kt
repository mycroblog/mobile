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
 * Why a fetch failed. `http_status`: the server answered another status than 2xx or 304,
 * such as 401, 403, or 404 of a server that does not publish its domain blocks. `network`:
 * the server could not be reached or did not answer in time. `forbidden_destination`: the
 * URL or a redirect leads to an address the server does not fetch from. `too_large`: the
 * list is larger than 2 MiB. `too_many_entries`: it has more than 5000 rows. `malformed`: it
 * cannot be read in the subscription's format.
 */
@Serializable(with = AdminDomainListFetchError.Serializer::class)
public enum class AdminDomainListFetchError(
  public val `value`: String,
) {
  @SerialName("http_status")
  HTTP_STATUS("http_status"),
  @SerialName("network")
  NETWORK("network"),
  @SerialName("forbidden_destination")
  FORBIDDEN_DESTINATION("forbidden_destination"),
  @SerialName("too_large")
  TOO_LARGE("too_large"),
  @SerialName("too_many_entries")
  TOO_MANY_ENTRIES("too_many_entries"),
  @SerialName("malformed")
  MALFORMED("malformed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDomainListFetchError> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminDomainListFetchError", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDomainListFetchError) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDomainListFetchError {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDomainListFetchError> =
        entries.associateBy(AdminDomainListFetchError::value)

    public fun fromValue(`value`: String): AdminDomainListFetchError = mapping[value] ?: UNRECOGNIZED
  }
}
