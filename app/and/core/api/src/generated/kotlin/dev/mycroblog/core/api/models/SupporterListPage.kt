package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of the supporter list: the members by plan, in the order of `plans`, newest first
 * within a plan.
 */
@Serializable
public data class SupporterListPage(
  /**
   * An opaque pagination cursor. Clients pass it back unchanged and never parse it.
   */
  @SerialName("next_cursor")
  public val nextCursor: String? = null,
  /**
   * The plans that are not retired, in their order (the lowest first), on every page.
   */
  @SerialName("plans")
  public val plans: List<SupporterListPlan>,
  @SerialName("items")
  public val items: List<SupporterListEntry>,
)
