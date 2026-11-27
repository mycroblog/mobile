package social.mycroblog.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How far the closure's `Delete` of every local actor has gone, in `GET
 * /api/v1/admin/closure` once the server is closed. The deliveries go on while the workers run;
 * `delivery_completed` is recorded when no fan-out is left and every delivery has ended.
 */
@Serializable
public data class AdminServiceClosureProgress(
  /**
   * The local accounts whose `Delete` is recorded.
   */
  @SerialName("accounts")
  public val accounts: Int,
  /**
   * The recorded `Delete`s whose inboxes are still being computed.
   */
  @SerialName("fanouts_pending")
  public val fanoutsPending: Int,
  /**
   * The deliveries of the closure's `Delete`s by state.
   */
  @SerialName("deliveries")
  public val deliveries: AdminServiceClosureDeliveries,
)
