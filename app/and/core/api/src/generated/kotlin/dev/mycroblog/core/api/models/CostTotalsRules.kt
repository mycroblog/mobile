package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How the totals cut the periods, to show next to them: by calendar date
 * without a time zone, weeks from `week_start` (ISO 8601), and each imported provider's
 * billing months in its own time zone (`billing_sources`, in its order).
 */
@Serializable
public data class CostTotalsRules(
  @SerialName("week_start")
  public val weekStart: CostTotalsRulesWeekStart,
  @SerialName("sources")
  public val sources: List<CostSourceRule>,
)
