package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A keyword filter as the user writes it. A field that is not one is a 422 naming it; the
 * same word twice (once normalized) is kept once.
 */
@Serializable
public data class KeywordFilterRequest(
  /**
   * 1 to 50 characters (grapheme clusters) on one line, without control characters. White
   * space around it is removed.
   */
  @SerialName("title")
  public val title: String,
  /**
   * What a keyword filter does to a post that matches it: `warn` returns the post with the filter
   * in its `filtered`, and clients fold it behind the filter's title; `hide` leaves the post out,
   * as if the user could not see it.
   */
  @SerialName("action")
  public val action: KeywordFilterAction,
  /**
   * Where the filter applies, at least one.
   */
  @SerialName("contexts")
  public val contexts: List<KeywordFilterContext>,
  /**
   * 1 to 100 words. White space around each is removed. The user's filters have at most
   * 1,000 words in all.
   */
  @SerialName("keywords")
  public val keywords: List<KeywordFilterKeyword>,
  /**
   * When the filter stops applying; omit it for a filter that never does.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant? = null,
)
