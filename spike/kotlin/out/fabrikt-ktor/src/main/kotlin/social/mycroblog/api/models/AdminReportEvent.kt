package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The event that a report points at, as the moderators review it: its organizer, its current
 * date, its cancellation, and every revision of its settings with all its places, whoever the
 * places were shown to. `reported_revision_id` is the revision that the reporter saw.
 */
@Serializable
public data class AdminReportEvent(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("organizer_account_id")
  public val organizerAccountId: UUID,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * Whether an event is `unscheduled` (it polls its date and no candidate was confirmed yet),
   * `scheduled`, or `canceled`. An event that ended is still `scheduled`: its `ends_at` is in the
   * past.
   */
  @SerialName("state")
  public val state: EventState,
  /**
   * When the event starts; omitted while it has no date.
   */
  @SerialName("starts_at")
  public val startsAt: Instant? = null,
  /**
   * When the event ends; omitted while it has no date.
   */
  @SerialName("ends_at")
  public val endsAt: Instant? = null,
  /**
   * How the event was canceled; omitted while it is not.
   */
  @SerialName("cancellation")
  public val cancellation: AdminEventCancellation? = null,
  /**
   * The revision of the settings that the reporter saw.
   */
  @SerialName("reported_revision_id")
  public val reportedRevisionId: String,
  /**
   * The revisions of the settings, oldest first.
   */
  @SerialName("revisions")
  public val revisions: List<AdminEventRevision>,
)
