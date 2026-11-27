package social.mycroblog.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A candidate date of an event's date poll, in UTC.
 */
@Serializable
public data class EventCandidateCreate(
  /**
   * After now and at most a year ahead.
   */
  @SerialName("starts_at")
  public val startsAt: Instant,
  /**
   * After `starts_at` and at most 7 days later.
   */
  @SerialName("ends_at")
  public val endsAt: Instant,
)
