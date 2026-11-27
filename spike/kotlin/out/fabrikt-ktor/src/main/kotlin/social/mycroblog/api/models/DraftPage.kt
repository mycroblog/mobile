package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of drafts, newest first.
 */
@Serializable
public data class DraftPage(
  /**
   * An opaque pagination cursor. Clients pass it back unchanged and never parse it.
   */
  @SerialName("next_cursor")
  public val nextCursor: String? = null,
  /**
   * The drafts of the page.
   */
  @SerialName("items")
  public val items: List<Draft>,
)
