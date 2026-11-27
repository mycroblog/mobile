package social.mycroblog.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An event of the local users of this server, as the user sees it. Times are in UTC;
 * `time_zone` is the event's own time zone, to show next to the user's. `places` holds only the
 * places that the user sees: `hidden_places` lists the kinds of the others, whose details never
 * reach the client.
 */
@Serializable
public data class Event(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The organizer; `unavailable` when the user may not see the account (it blocks the user, is
   * deactivated or suspended, or moderation hides its profile).
   */
  @SerialName("organizer")
  public val organizer: Account,
  /**
   * The co-organizers, in the order the organizer named them; up to 3. Those the user may not
   * see, as for `organizer`, are left out.
   */
  @SerialName("coorganizers")
  public val coorganizers: List<Account>,
  @SerialName("title")
  public val title: String,
  /**
   * Plain text; empty for none.
   */
  @SerialName("description")
  public val description: String,
  /**
   * Who sees an event: `public` every local user, `followers` the users who follow the organizer,
   * `following` the users the organizer follows, `mutuals` the users who follow the organizer and
   * whom the organizer follows, and `invited` the invited users only. The organizer, the
   * co-organizers, the invited users, and the users who answered always see it, unless a block
   * separates them from the organizer or a co-organizer.
   */
  @SerialName("visibility")
  public val visibility: EventVisibility,
  /**
   * The IANA name of the event's time zone, such as `Asia/Tokyo`.
   */
  @SerialName("time_zone")
  public val timeZone: String,
  /**
   * Who sees the `physical` and `url` places of an event: `approved` only the attendees whom the
   * organizer or a co-organizer approved, and `viewers` everyone who sees the event. The organizer
   * and the co-organizers see them always, and a `voice` place is shown to everyone who sees the
   * event.
   */
  @SerialName("place_disclosure")
  public val placeDisclosure: EventPlaceDisclosure,
  /**
   * How many attendees may answer `going`; 0 for no limit.
   */
  @SerialName("capacity")
  public val capacity: Int,
  /**
   * Who posts on the board of an event, among those who read it: `organizer` the organizer alone,
   * `coorganizers` the organizer and the co-organizers, and `readers` everyone who reads the board.
   * The board is read by the organizer, the co-organizers, and the users who see the event and
   * answer `going` or `maybe`.
   */
  @SerialName("board_writers")
  public val boardWriters: EventBoardWriters,
  @SerialName("places")
  public val places: List<EventPlace>,
  /**
   * The kinds of the places that the user does not see.
   */
  @SerialName("hidden_places")
  public val hiddenPlaces: List<EventPlaceKind>,
  /**
   * The start of the event; for an `unscheduled` event, the earliest start of its candidate
   * dates (`GET /events/{id}/candidates`).
   */
  @SerialName("starts_at")
  public val startsAt: Instant,
  /**
   * The end of the event; for an `unscheduled` event, the latest end of its candidate dates.
   */
  @SerialName("ends_at")
  public val endsAt: Instant,
  /**
   * Whether an event is `unscheduled` (it polls its date and no candidate was confirmed yet),
   * `scheduled`, or `canceled`. An event that ended is still `scheduled`: its `ends_at` is in the
   * past.
   */
  @SerialName("state")
  public val state: EventState,
  /**
   * The cancellation of an event.
   */
  @SerialName("cancellation")
  public val cancellation: EventCancellation? = null,
  /**
   * How many attendees answer `going`.
   */
  @SerialName("going_count")
  public val goingCount: Int,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When the settings were last changed (the creation for an event never changed).
   */
  @SerialName("revised_at")
  public val revisedAt: Instant,
  /**
   * How many versions the settings have had, the first included.
   */
  @SerialName("revision_count")
  public val revisionCount: Int,
  /**
   * What an event says of the user who reads it.
   */
  @SerialName("viewer")
  public val viewer: EventViewer,
)
