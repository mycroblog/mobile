package social.mycroblog.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What a `service_closure`, an `admin_service_closure_due`, or a `supporter_service_closure`
 * tells of the closure of the service. `change` is what the event of a
 * `service_closure` did: `announced` (the closure was announced), `rescheduled` (its dates
 * changed), or `withdrawn` (the announcement was withdrawn; the server goes on); it is omitted
 * for the other types. `closes_at` and `export_until` are the dates of the announcement that the
 * notification tells of, omitted for a withdrawal. The current state is in `GET /instance`.
 */
@Serializable
public data class ServiceClosureNotice(
  @SerialName("change")
  public val change: ServiceClosureNoticeChange? = null,
  /**
   * When the server closes.
   */
  @SerialName("closes_at")
  public val closesAt: Instant? = null,
  /**
   * Until when the users may still sign in and export their data.
   */
  @SerialName("export_until")
  public val exportUntil: Instant? = null,
)
