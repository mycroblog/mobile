package social.mycroblog.api.models

import kotlin.Boolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An attendee of an event, who answered `going` or `maybe`.
 */
@Serializable
public data class EventAttendee(
  /**
   * An account, as clients show it.
   */
  @SerialName("account")
  public val account: Account,
  /**
   * An answer to an event.
   */
  @SerialName("answer")
  public val answer: EventAnswer,
  /**
   * Whether the attendee sees the places shown to the approved: the organizer or a co-organizer
   * approved the answer, or the attendee organizes the event (`organizes`).
   */
  @SerialName("approved")
  public val approved: Boolean,
  /**
   * Whether the attendee is the organizer or a co-organizer of the event now. Such an attendee
   * is always `approved`, and their approval cannot be changed.
   */
  @SerialName("organizes")
  public val organizes: Boolean,
)
