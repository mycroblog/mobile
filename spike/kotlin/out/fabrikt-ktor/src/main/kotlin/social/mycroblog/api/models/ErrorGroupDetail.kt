package social.mycroblog.api.models

import java.util.UUID
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An error group with its latest occurrence, its hourly counts, and the changes of its state.
 */
@Serializable
public data class ErrorGroupDetail(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The fingerprint of the errors, 32 hexadecimal digits; the same in the logs and the exporters.
   */
  @SerialName("fingerprint")
  public val fingerprint: String,
  /**
   * The state of an error group: `open` (new, unmuted, or regressed),
   * `resolved` (an administrator marked it fixed; an occurrence on another version regresses
   * it to `open`), or `muted` (it notifies nobody until it is unmuted).
   */
  @SerialName("state")
  public val state: ErrorGroupState,
  /**
   * When the group entered its state; its first occurrence while it has always been open.
   */
  @SerialName("state_changed_at")
  public val stateChangedAt: Instant,
  /**
   * The version of the server that a `resolved` group was resolved with; omitted for the other states.
   */
  @SerialName("resolved_version")
  public val resolvedVersion: String? = null,
  /**
   * What reported the errors of a group, the server or the web client.
   */
  @SerialName("source")
  public val source: ErrorGroupSource,
  /**
   * Whether the errors of a group are panics (and uncaught errors of the web client) or errors.
   */
  @SerialName("kind")
  public val kind: ErrorGroupKind,
  /**
   * Where the errors happened, a finite name: the surface and the route pattern of an HTTP
   * request, the type of a job, the name of a goroutine, or the route of the web client.
   */
  @SerialName("where")
  public val `where`: String,
  /**
   * The last type of the chain of the error (a Go type or the name of a JavaScript error).
   */
  @SerialName("type")
  public val type: String,
  /**
   * The first line of the scrubbed message of the first occurrence, at most 200 characters.
   */
  @SerialName("message")
  public val message: String,
  /**
   * The occurrences in the last 24 hours, by the hour, the current hour included.
   */
  @SerialName("count_24h")
  public val count24h: Long,
  /**
   * The occurrences in the last 7 days, by the hour, the current hour included.
   */
  @SerialName("count_7d")
  public val count7d: Long,
  /**
   * The occurrences of each of the last 7 days, oldest first, each day 24 hours and the last
   * one ending with the current hour: the last is `count_24h` and their sum `count_7d`. Only
   * the list of the error groups has it.
   */
  @SerialName("daily_counts")
  public val dailyCounts: List<Long>? = null,
  @SerialName("first_seen_at")
  public val firstSeenAt: Instant,
  @SerialName("last_seen_at")
  public val lastSeenAt: Instant,
  /**
   * The version of the server (`service.version`) of the first occurrence.
   */
  @SerialName("first_version")
  public val firstVersion: String,
  /**
   * The version of the server of the last occurrence.
   */
  @SerialName("last_version")
  public val lastVersion: String,
  /**
   * The latest occurrence of an error group, scrubbed. `request_id` and `trace_id` find it in
   * the logs and the traces.
   */
  @SerialName("sample")
  public val sample: ErrorGroupSample? = null,
  /**
   * The occurrences of each hour that had any, oldest first, for the last 90 days.
   */
  @SerialName("hourly_counts")
  public val hourlyCounts: List<ErrorGroupHourCount>,
  /**
   * The changes of the state of the group, oldest first.
   */
  @SerialName("events")
  public val events: List<ErrorGroupEvent>,
)
