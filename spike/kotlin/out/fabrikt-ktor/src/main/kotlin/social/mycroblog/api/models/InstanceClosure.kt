package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The closure of the service, present while a closure is announced and after
 * the server closes; absent when none is announced. Clients show it on every screen with the
 * way to export the data and to move the account.
 */
@Serializable
public data class InstanceClosure(
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
   * When the server closes; it may have passed already.
   */
  @SerialName("closes_at")
  public val closesAt: Instant,
  /**
   * Until when the users may still sign in and export their data after the closure.
   */
  @SerialName("export_until")
  public val exportUntil: Instant,
  /**
   * The operator's message to the users, as plain text; omitted when there is none.
   */
  @SerialName("message")
  public val message: String? = null,
)
