package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The hashtags that the user follows, at most 100, the latest followed first.
 */
@Serializable
public data class FollowedHashtagCollection(
  @SerialName("items")
  public val items: List<FollowedHashtag>,
)
