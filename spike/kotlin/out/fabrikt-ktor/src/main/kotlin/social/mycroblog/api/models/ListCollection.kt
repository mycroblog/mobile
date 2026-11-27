package social.mycroblog.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The user's lists, oldest first.
 */
@Serializable
public data class ListCollection(
  @SerialName("items")
  public val items: kotlin.collections.List<List>,
)
