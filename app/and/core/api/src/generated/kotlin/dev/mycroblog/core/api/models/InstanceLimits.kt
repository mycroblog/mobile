package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Limits that clients check before sending a request.
 */
@Serializable
public data class InstanceLimits(
  /**
   * The maximum number of characters in the text and content warning of a post.
   */
  @SerialName("max_post_characters")
  public val maxPostCharacters: Int,
  /**
   * Whether every media file attached to a post needs an alternative text (the instance
   * setting `require_media_description`). While it is on, a post or an edit with
   * a media file without one is a 422, so clients ask for it before sending.
   */
  @SerialName("require_media_description")
  public val requireMediaDescription: Boolean,
  /**
   * The limits of drafts and scheduled posts (the instance settings of the same names). A save or a
   * schedule past one is a 422 that names it; lowering a limit deletes no draft and cancels no
   * schedule.
   */
  @SerialName("drafts")
  public val drafts: InstanceDraftLimits? = null,
  /**
   * The limits of the body of an article (the instance settings `max_article_bytes` and
   * `max_article_images`). An article over one is a 422; `previewArticle` reports both.
   */
  @SerialName("articles")
  public val articles: InstanceArticleLimits? = null,
)
