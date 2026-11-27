package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The roles with a badge that the authenticated user holds, highest position first; empty when there are none.
 */
@Serializable
public data class BadgeChoiceList(
  @SerialName("items")
  public val items: List<BadgeChoice>,
)
