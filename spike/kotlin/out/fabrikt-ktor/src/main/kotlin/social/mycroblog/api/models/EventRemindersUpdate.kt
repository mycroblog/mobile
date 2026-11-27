package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A change of the reminders of events of the authenticated user.
 */
@Serializable
public data class EventRemindersUpdate(
  /**
   * The reminders, in minutes before the start: at most 3, each from 5 minutes to 7 days
   * (10080), none twice. Empty turns the reminders off. Ignored with `defaults`.
   */
  @SerialName("minutes_before")
  public val minutesBefore: List<Int>? = null,
  /**
   * Restores the defaults (one day and one hour before) instead.
   */
  @SerialName("defaults")
  public val defaults: Boolean = false,
)
