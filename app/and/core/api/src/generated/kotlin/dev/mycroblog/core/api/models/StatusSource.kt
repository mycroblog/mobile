package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the author typed, for editing a status.
 */
@Serializable
public data class StatusSource(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The plain text; for an article, its Markdown body.
   */
  @SerialName("text")
  public val text: String,
  /**
   * The content warning; empty when there is none.
   */
  @SerialName("spoiler_text")
  public val spoilerText: String,
  /**
   * The BCP 47 language tag; `und` when it is unknown.
   */
  @SerialName("language")
  public val language: String,
  /**
   * The rest of what the author wrote of an article; omitted for a post.
   */
  @SerialName("article")
  public val article: ArticleSource? = null,
)
