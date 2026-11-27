package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A week, month, or year of the income totals as the administrators see it: the `gross`, the
 * provider's `fee`, and the `net` (gross less fee) of each currency, sorted by currency code,
 * and the `balance`, the net less the costs of each currency under the same basis.
 * `net_by_source` is the net of each source that the period has: the subscriptions, the tips
 * (`one_time`), and the movements of neither.
 */
@Serializable
public data class AdminIncomePeriodTotal(
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
  @SerialName("gross")
  public val gross: List<Money>,
  @SerialName("fee")
  public val fee: List<Money>,
  @SerialName("net")
  public val net: List<Money>,
  @SerialName("balance")
  public val balance: List<Money>,
  @SerialName("net_by_source")
  public val netBySource: List<IncomeSourceNet>,
)
