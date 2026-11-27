package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The totals of the instance's costs over the periods from the one that holds `from` to the
 * one that holds `to`, oldest first.
 */
@Serializable
public data class CostTotals(
  /**
   * The periods of the totals: ISO 8601 weeks (Monday to Sunday), calendar months, or calendar
   * years, cut by date.
   */
  @SerialName("granularity")
  public val granularity: CostGranularity,
  /**
   * How the totals place a payment. `amortized` (the default) spreads each
   * payment of a schedule longer than a month (every 2 or more months, every year, every 5 or
   * more weeks) by day over the days up to the next payment, with the cumulative rounding;
   * the other payments stay on their dates. `cash` places every payment on its date. Imported
   * usage charges spread by day over their billing period under both.
   */
  @SerialName("basis")
  public val basis: CostBasis,
  @SerialName("periods")
  public val periods: List<CostPeriodTotal>,
  /**
   * How the totals cut the periods, to show next to them: by calendar date
   * without a time zone, weeks from `week_start` (ISO 8601), and each imported provider's
   * billing months in its own time zone (`billing_sources`, in its order).
   */
  @SerialName("rules")
  public val rules: CostTotalsRules,
)
