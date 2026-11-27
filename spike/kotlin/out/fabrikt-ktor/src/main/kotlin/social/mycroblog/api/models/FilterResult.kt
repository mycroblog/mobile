package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A keyword filter of the user with the action `warn` that a post matched, and the words it matched.
 */
@Serializable
public data class FilterResult(
  /**
   * The keyword filter that a post matched, as far as a folded post shows it.
   */
  @SerialName("filter")
  public val filter: MatchedKeywordFilter,
  /**
   * The words of the filter that the post matched, as the user typed them.
   */
  @SerialName("keyword_matches")
  public val keywordMatches: List<String>,
)
