package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The new content of an article.
 */
@Serializable
public data class EditArticleRequest(
  /**
   * The title of an article, as plain text; surrounding spaces are dropped, and a blank title is a 422.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The summary of an article, as plain text. Omitted or blank, the server makes one from the
   * start of the body (about 200 characters, cut at the end of a sentence or word). It is a
   * summary, never a content warning.
   */
  @SerialName("summary")
  public val summary: String? = null,
  /**
   * The cover image of an article: one of the user's own uploads that finished processing (422
   * otherwise), with its alternative text; omitted for none.
   */
  @SerialName("cover")
  public val cover: ArticleCoverInput? = null,
  /**
   * The body of an article in Markdown, as `createArticle` describes it. Its HTML may be at most
   * `Instance.limits.articles.max_bytes` bytes.
   */
  @SerialName("text")
  public val text: String,
  /**
   * The hashtags of an article, with or without `#`; the hashtag timelines and search show the
   * article under them. A name that is no hashtag is a 422. The `#name` in the body are text.
   */
  @SerialName("tags")
  public val tags: List<String>? = null,
  /**
   * A BCP 47 language tag; omitted means the user's default post language.
   */
  @SerialName("language")
  public val language: String? = null,
  /**
   * Whether the body and the cover are folded behind the title and the summary.
   */
  @SerialName("sensitive")
  public val sensitive: Boolean? = null,
)
