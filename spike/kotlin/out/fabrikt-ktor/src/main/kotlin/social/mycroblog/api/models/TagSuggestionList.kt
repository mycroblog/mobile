package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The tags that match.
 */
@Serializable
public data class TagSuggestionList(
  @SerialName("items")
  public val items: List<TagSuggestion>,
)
