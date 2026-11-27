package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The hashtags that an account features on its profile, at most 10, in the order it chose.
 */
@Serializable
public data class FeaturedHashtagCollection(
  @SerialName("items")
  public val items: List<FeaturedHashtag>,
)
