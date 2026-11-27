package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What an event says of the user who reads it.
 */
@Serializable
public data class EventViewer(
  /**
   * Whether the user organizes the event.
   */
  @SerialName("organizer")
  public val organizer: Boolean,
  /**
   * Whether the user co-organizes the event.
   */
  @SerialName("coorganizer")
  public val coorganizer: Boolean,
  /**
   * Whether the user has an invitation that was not revoked.
   */
  @SerialName("invited")
  public val invited: Boolean,
  /**
   * The user's answer; omitted before the user answers.
   */
  @SerialName("answer")
  public val answer: EventAnswer? = null,
  /**
   * Whether the user sees the places shown to the approved: the organizer or a co-organizer
   * approved the user's answer, or the user is the organizer or a co-organizer.
   */
  @SerialName("approved")
  public val approved: Boolean,
  /**
   * What the user may do with the board of an event.
   */
  @SerialName("board")
  public val board: EventBoardAccess,
)
