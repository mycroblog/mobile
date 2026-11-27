package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The reminders of events of the authenticated user.
 */
@Serializable
public data class EventReminders(
  /**
   * How many minutes before the start of an event each reminder is, from the earliest; empty
   * for no reminders.
   */
  @SerialName("minutes_before")
  public val minutesBefore: List<Int>,
  /**
   * Whether these are the defaults (one day and one hour before), as the user has not chosen.
   */
  @SerialName("defaults")
  public val defaults: Boolean,
)
