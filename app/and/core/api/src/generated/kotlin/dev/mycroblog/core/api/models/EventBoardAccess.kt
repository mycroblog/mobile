package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the user may do with the board of an event.
 */
@Serializable
public data class EventBoardAccess(
  /**
   * Whether the user reads the board: the user organizes or co-organizes the event, or answers
   * `going` or `maybe`.
   */
  @SerialName("readable")
  public val readable: Boolean,
  /**
   * Whether the user may post on the board and edit their posts on it now: the event's
   * `board_writers` admit the user, and the board is not read-only. The board is read-only once
   * the event is canceled, and from 7 days after its end (for an `unscheduled` event, the end of
   * its last candidate date, its `ends_at`).
   */
  @SerialName("writable")
  public val writable: Boolean,
)
