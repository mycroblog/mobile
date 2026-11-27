package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The cancellation of an event by its organizer.
 */
@Serializable
public data class EventCancelRequest(
  /**
   * The reason, up to 500 characters, shown with the event; omitted for none.
   */
  @SerialName("note")
  public val note: String? = null,
)
