package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The totals of the instance's costs as the holders of `view_finance` read them over the
 * periods from the one that holds `from` to the one that holds `to`, oldest first, and the
 * instance settings that decide what they read.
 */
@Serializable
public data class FinanceCostTotals(
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
  public val periods: List<FinanceCostPeriodTotal>,
  /**
   * How the totals cut the periods, to show next to them: by calendar date
   * without a time zone, weeks from `week_start` (ISO 8601), and each imported provider's
   * billing months in its own time zone (`billing_sources`, in its order).
   */
  @SerialName("rules")
  public val rules: CostTotalsRules,
  /**
   * What the holders of `view_finance` read of the instance's finances beyond the totals and
   * their breakdown, by instance settings: `item_names`
   * (`finance_viewer_item_names`) shows the names of the cost items and the services of the
   * imported costs, `item_memos` (`finance_viewer_item_memos`) the memos of the cost items, and
   * `income` (`finance_viewer_income`) the income as totals only or with the number of members
   * of each plan.
   */
  @SerialName("viewer")
  public val viewer: FinanceViewerSettings,
)
