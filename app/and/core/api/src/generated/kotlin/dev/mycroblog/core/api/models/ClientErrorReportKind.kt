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
 * How the error was caught: `route_error` by the router, `boundary_error` by an error
 * boundary outside the routes, `uncaught` outside every boundary, and
 * `unhandledrejection` as a promise rejection that nothing handled.
 */
@Serializable(with = ClientErrorReportKind.Serializer::class)
public enum class ClientErrorReportKind(
  public val `value`: String,
) {
  @SerialName("route_error")
  ROUTE_ERROR("route_error"),
  @SerialName("boundary_error")
  BOUNDARY_ERROR("boundary_error"),
  @SerialName("uncaught")
  UNCAUGHT("uncaught"),
  @SerialName("unhandledrejection")
  UNHANDLEDREJECTION("unhandledrejection"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ClientErrorReportKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.ClientErrorReportKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ClientErrorReportKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ClientErrorReportKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ClientErrorReportKind> =
        entries.associateBy(ClientErrorReportKind::value)

    public fun fromValue(`value`: String): ClientErrorReportKind = mapping[value] ?: UNRECOGNIZED
  }
}
