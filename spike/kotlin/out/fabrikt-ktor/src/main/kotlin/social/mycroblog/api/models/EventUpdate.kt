package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Changes to an event; an omitted field keeps its value, and `places` replaces every place. A new
 * date (`starts_at` and `ends_at` together) is checked as a new event's; on an `unscheduled`
 * event, it fixes the date without a candidate and ends the date poll.
 */
@Serializable
public data class EventUpdate(
  @SerialName("title")
  public val title: String? = null,
  @SerialName("description")
  public val description: String? = null,
  /**
   * Who sees an event: `public` every local user, `followers` the users who follow the organizer,
   * `following` the users the organizer follows, `mutuals` the users who follow the organizer and
   * whom the organizer follows, and `invited` the invited users only. The organizer, the
   * co-organizers, the invited users, and the users who answered always see it, unless a block
   * separates them from the organizer or a co-organizer.
   */
  @SerialName("visibility")
  public val visibility: EventVisibility? = null,
  @SerialName("time_zone")
  public val timeZone: String? = null,
  /**
   * Who sees the `physical` and `url` places of an event: `approved` only the attendees whom the
   * organizer or a co-organizer approved, and `viewers` everyone who sees the event. The organizer
   * and the co-organizers see them always, and a `voice` place is shown to everyone who sees the
   * event.
   */
  @SerialName("place_disclosure")
  public val placeDisclosure: EventPlaceDisclosure? = null,
  @SerialName("capacity")
  public val capacity: Int? = null,
  /**
   * Who posts on the event's board; only the organizer changes it.
   */
  @SerialName("board_writers")
  public val boardWriters: EventBoardWriters? = null,
  @SerialName("places")
  public val places: List<EventPlace>? = null,
  @SerialName("starts_at")
  public val startsAt: Instant? = null,
  @SerialName("ends_at")
  public val endsAt: Instant? = null,
)
