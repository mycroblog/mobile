package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The latest occurrence of an error group, scrubbed. `request_id` and `trace_id` find it in
 * the logs and the traces.
 */
@Serializable
public data class ErrorGroupSample(
  /**
   * The chain of the types of the error, outermost first.
   */
  @SerialName("type")
  public val type: String,
  @SerialName("message")
  public val message: String,
  /**
   * The stack, in the form of the runtime that reported it, without the arguments of the frames.
   */
  @SerialName("stack")
  public val stack: String,
  /**
   * The request ID of the occurrence; omitted when it had none.
   */
  @SerialName("request_id")
  public val requestId: String? = null,
  /**
   * The trace ID of the occurrence; omitted when it had none.
   */
  @SerialName("trace_id")
  public val traceId: String? = null,
  /**
   * The version of the server of the occurrence.
   */
  @SerialName("version")
  public val version: String,
  /**
   * The role of the process that reported it; the web client reports have the role of the server that received them.
   */
  @SerialName("role")
  public val role: ErrorGroupSampleRole,
  @SerialName("occurred_at")
  public val occurredAt: Instant,
)
