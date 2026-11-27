package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How the income totals cut the periods and place the payments, to show
 * next to them: by calendar date without a time zone, weeks from `week_start` (ISO 8601),
 * each money movement on its date in its payment provider's reporting time zone
 * (`providers`), and under the `amortized` basis a payment that covers more than
 * `amortize_after_days` days spread by day over the days it covers, with its refunds and
 * chargebacks taking back the days before them on their own date and cancelling the later
 * ones.
 */
@Serializable
public data class IncomeTotalsRules(
  @SerialName("week_start")
  public val weekStart: IncomeTotalsRulesWeekStart,
  @SerialName("providers")
  public val providers: List<IncomeProviderRule>,
  @SerialName("amortize_after_days")
  public val amortizeAfterDays: Int,
)
