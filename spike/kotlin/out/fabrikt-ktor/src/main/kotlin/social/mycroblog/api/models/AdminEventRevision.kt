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
 * A revision of the settings of an event with all its places. The title and the description
 * are empty, and the places are gone, once the account that wrote them is erased.
 */
@Serializable
public data class AdminEventRevision(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("title")
  public val title: String,
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
   * How many attendees may answer going; 0 for no limit.
   */
  @SerialName("capacity")
  public val capacity: Int,
  @SerialName("places")
  public val places: List<EventPlace>,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("revised_by_account_id")
  public val revisedByAccountId: UUID,
  @SerialName("revised_at")
  public val revisedAt: Instant,
)
