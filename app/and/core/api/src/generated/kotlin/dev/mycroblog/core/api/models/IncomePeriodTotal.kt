package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A week, month, or year of the income totals (`label`: `2026-W17`, `2026-04`, or `2026`):
 * the net income of each currency (what the payments brought less the provider's fees and the
 * refunds), never converted, sorted by currency code. `balance` is the net less the costs of
 * each currency under the same basis, present only where the balance is shown: a currency of
 * one side alone shows that side's amount.
 */
@Serializable
public data class IncomePeriodTotal(
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
  @SerialName("net")
  public val net: List<Money>,
  @SerialName("balance")
  public val balance: List<Money>? = null,
)
