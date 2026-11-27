package dev.mycroblog.core.api.models

import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A hashtag that the prefix search of hashtags found.
 */
@Serializable
public data class TagSuggestion(
  /**
   * The hashtag as first written on this server, without `#`.
   */
  @SerialName("name")
  public val name: String,
  /**
   * How many posts of visibility `public`, `local`, or `unlisted` carry the hashtag now.
   */
  @SerialName("statuses_count")
  public val statusesCount: Long,
)
