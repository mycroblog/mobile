package social.mycroblog.api.models

import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The board of an event that a status is posted on (`listEventBoard`). A board post is seen only
 * by the readers of the board, is never boosted or quoted, and is replied to only from the
 * board, by the users who may post on it.
 */
@Serializable
public data class StatusEventBoard(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("event_id")
  public val eventId: UUID,
)
