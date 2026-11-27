package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An appeal of an action on the user's own account.
 */
@Serializable
public data class AppealModerationActionRequest(
  /**
   * Why the moderators should reconsider the action, as plain text.
   */
  @SerialName("text")
  public val text: String,
)
