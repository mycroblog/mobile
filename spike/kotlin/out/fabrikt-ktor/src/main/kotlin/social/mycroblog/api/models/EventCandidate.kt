package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A candidate date of an event's date poll, with how many users answered it each way. Times are
 * in UTC.
 */
@Serializable
public data class EventCandidate(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("starts_at")
  public val startsAt: Instant,
  @SerialName("ends_at")
  public val endsAt: Instant,
  @SerialName("yes_count")
  public val yesCount: Int,
  @SerialName("maybe_count")
  public val maybeCount: Int,
  @SerialName("no_count")
  public val noCount: Int,
  /**
   * The user's own answer; absent before the user answers.
   */
  @SerialName("vote")
  public val vote: EventVote? = null,
  /**
   * Whether the organizers fixed the event's date to this candidate.
   */
  @SerialName("confirmed")
  public val confirmed: Boolean,
)
