package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The cancellation of an event as the moderators see it.
 */
@Serializable
public data class AdminEventCancellation(
  /**
   * Why an event was canceled: by its `organizer`, by a `moderator`, or because the organizer was
   * suspended (`organizer_suspended`) or deleted (`organizer_deleted`) before it started.
   */
  @SerialName("reason")
  public val reason: EventCancelReason,
  /**
   * Who canceled the event; omitted for an automatic cancellation.
   */
  @SerialName("canceled_by_account_id")
  public val canceledByAccountId: String? = null,
  /**
   * The reason given for the cancellation; omitted for none.
   */
  @SerialName("note")
  public val note: String? = null,
  @SerialName("canceled_at")
  public val canceledAt: Instant,
)
