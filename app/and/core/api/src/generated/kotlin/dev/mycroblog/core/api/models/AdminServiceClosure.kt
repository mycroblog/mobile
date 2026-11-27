package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The service closure as its administrators see it.
 */
@Serializable
public data class AdminServiceClosure(
  /**
   * Where the server is in its closure:
   *
   * - `none`: no closure is announced.
   * - `announced`: the closure is announced; the server runs as before until it closes.
   * - `closed`: the server is closed and delivers the deletion of its local accounts to the
   *   other servers. The users can still sign in and export their data until `export_until`.
   * - `delivery_completed`: the server is closed and those deliveries have ended.
   */
  @SerialName("state")
  public val state: ServiceClosureState,
  /**
   * When the server closes; omitted for `none`.
   */
  @SerialName("closes_at")
  public val closesAt: Instant? = null,
  /**
   * Until when the users may export their data after the closure; omitted for `none`.
   */
  @SerialName("export_until")
  public val exportUntil: Instant? = null,
  /**
   * The operator's message to the users; omitted when there is none.
   */
  @SerialName("message")
  public val message: String? = null,
  /**
   * When the announcement in effect, or its latest change of the dates, was made; omitted for `none`.
   */
  @SerialName("announced_at")
  public val announcedAt: Instant? = null,
  /**
   * When the state last changed; omitted for `none`.
   */
  @SerialName("changed_at")
  public val changedAt: Instant? = null,
  /**
   * Whether the announcement gives the users less than 30 days from `announced_at` to
   * `closes_at`, which the admin screen and the CLI warn about: the users may not have the
   * time to export their data and move their accounts. A short notice is allowed.
   */
  @SerialName("short_notice")
  public val shortNotice: Boolean,
  /**
   * How far the closure's `Delete` of every local actor has gone, in `GET
   * /api/v1/admin/closure` once the server is closed. The deliveries go on while the workers run;
   * `delivery_completed` is recorded when no fan-out is left and every delivery has ended.
   */
  @SerialName("progress")
  public val progress: AdminServiceClosureProgress? = null,
)
