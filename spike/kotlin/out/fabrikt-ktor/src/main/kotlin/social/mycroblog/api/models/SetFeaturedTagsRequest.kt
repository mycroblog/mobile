package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The hashtags to feature on the user's profile, in the order to show them.
 */
@Serializable
public data class SetFeaturedTagsRequest(
  /**
   * The hashtags, with or without `#`, at most 10; a hashtag given twice counts once. An
   * empty list features none.
   */
  @SerialName("names")
  public val names: List<String>,
)
