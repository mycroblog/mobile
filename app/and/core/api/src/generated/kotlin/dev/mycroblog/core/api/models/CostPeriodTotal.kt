package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A week, month, or year of the totals (`label`: `2026-W17`, `2026-04`, or `2026`): the total
 * of each currency, never converted, sorted by currency code, and the breakdown
 * by provider, category, and currency. A currency appears when a cost meets the period, even
 * with a zero amount. `estimated` reports that an imported usage charge of a billing month
 * that its provider has not closed meets the period: its amount may still
 * change.
 */
@Serializable
public data class CostPeriodTotal(
  @SerialName("label")
  public val label: String,
  /**
   * A calendar date `YYYY-MM-DD` without a time or a time zone, such as the day
   * of a payment on a card statement or an invoice.
   */
  @SerialName("starts_on")
  public val startsOn: String,
  /**
   * A calendar date `YYYY-MM-DD` without a time or a time zone, such as the day
   * of a payment on a card statement or an invoice.
   */
  @SerialName("ends_on")
  public val endsOn: String,
  @SerialName("totals")
  public val totals: List<Money>,
  @SerialName("breakdown")
  public val breakdown: List<CostBreakdownLine>,
  @SerialName("estimated")
  public val estimated: Boolean,
)
