package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The body of an article as publishing it would store it.
 */
@Serializable
public data class ArticlePreview(
  /**
   * The body as sanitized HTML, the same as the published article's `content`.
   */
  @SerialName("content")
  public val content: String,
  /**
   * The summary the article would get.
   */
  @SerialName("summary")
  public val summary: String,
  /**
   * Whether `summary` is made from the body, because the one sent was blank.
   */
  @SerialName("summary_from_body")
  public val summaryFromBody: Boolean,
  /**
   * The UTF-8 bytes of `content`; over `max_bytes`, publishing is a 422.
   */
  @SerialName("bytes")
  public val bytes: Int,
  /**
   * The most bytes of `content` (`Instance.limits.articles.max_bytes`).
   */
  @SerialName("max_bytes")
  public val maxBytes: Int,
  /**
   * The number of the user's own uploads that the body shows as images; over `max_images`, publishing is a 422.
   */
  @SerialName("images")
  public val images: Int,
  /**
   * The most images of the body (`Instance.limits.articles.max_images`).
   */
  @SerialName("max_images")
  public val maxImages: Int,
)
