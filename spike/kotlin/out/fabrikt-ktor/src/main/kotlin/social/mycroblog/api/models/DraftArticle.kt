package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What makes a draft that of an article: the draft's `text` is the body in Markdown. The title
 * and the summary may be empty while the user writes; publishing the draft checks them as
 * `createArticle` does.
 */
@Serializable
public data class DraftArticle(
  /**
   * The title, as plain text; empty while there is none.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The summary, as plain text; empty makes one from the body when the draft is published.
   */
  @SerialName("summary")
  public val summary: String,
  /**
   * The cover image; omitted when there is none.
   */
  @SerialName("cover")
  public val cover: StatusMediaItem? = null,
  /**
   * The hashtags, as the user gave them.
   */
  @SerialName("tags")
  public val tags: List<String>,
  /**
   * The IDs of the user's own uploads that the body shows as images (`![alt](media:ID)`),
   * each once, ordered by ID; the server finds them in the body at each save, and keeps the
   * uploads while the draft names them.
   */
  @SerialName("images")
  public val images: List<@Contextual UUID>,
)
