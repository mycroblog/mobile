package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderator cancelling an event.
 */
@Serializable
public data class AdminEventCancel(
  /**
   * Why the event is canceled, as plain text of up to 500 characters. The event shows it
   * with its cancellation to everyone who sees the event.
   */
  @SerialName("reason")
  public val reason: String? = null,
)
