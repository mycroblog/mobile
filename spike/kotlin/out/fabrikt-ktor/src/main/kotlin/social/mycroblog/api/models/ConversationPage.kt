package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of the user's direct messages by conversation.
 */
@Serializable
public data class ConversationPage(
  /**
   * An opaque pagination cursor. Clients pass it back unchanged and never parse it.
   */
  @SerialName("next_cursor")
  public val nextCursor: String? = null,
  /**
   * The conversations of the page, the one with the newest direct post first.
   */
  @SerialName("items")
  public val items: List<Conversation>,
)
