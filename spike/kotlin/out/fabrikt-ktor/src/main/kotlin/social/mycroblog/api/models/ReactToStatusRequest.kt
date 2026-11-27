package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The emoji to react with.
 */
@Serializable
public data class ReactToStatusRequest(
  /**
   * A Unicode emoji, or the shortcode of a custom emoji of this server (the `name` of
   * its `ReactionEmoji`; colons around it are accepted).
   */
  @SerialName("emoji")
  public val emoji: String,
)
