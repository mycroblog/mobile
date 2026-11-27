package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The income totals as the administrators see them over the periods from the one that holds
 * `from` to the one that holds `to`, oldest first, with the number of members of each
 * supporter plan now, and `tips`, whether the server takes tips now.
 */
@Serializable
public data class AdminIncomeTotals(
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
  public val periods: List<AdminIncomePeriodTotal>,
  @SerialName("plans")
  public val plans: List<IncomePlanCount>,
  @SerialName("tips")
  public val tips: Boolean,
  /**
   * How the income totals cut the periods and place the payments, to show
   * next to them: by calendar date without a time zone, weeks from `week_start` (ISO 8601),
   * each money movement on its date in its payment provider's reporting time zone
   * (`providers`), and under the `amortized` basis a payment that covers more than
   * `amortize_after_days` days spread by day over the days it covers, with its refunds and
   * chargebacks taking back the days before them on their own date and cancelling the later
   * ones.
   */
  @SerialName("rules")
  public val rules: IncomeTotalsRules,
)
