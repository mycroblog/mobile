package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How far this month's net income goes toward the instance's monthly target (the instance
 * setting `funding_meter_visibility`): the income and the target are prorated as the totals
 * under the basis `amortized` are, and the month is the calendar month that holds today in UTC.
 * Only the income in the target's currency counts; no amount is converted. The target is the
 * one the operators set, or this month's costs while they are in one currency. `percent` alone
 * is published while the costs and the income are both private, so that no public amount turns
 * it into a private one; `income` and `target` are added only while both are public.
 */
@Serializable
public data class FundingMeter(
  /**
   * `percent`: only the percentage; `amounts`: also `income`, `target`, and `target_is_costs`.
   */
  @SerialName("visibility")
  public val visibility: FundingMeterVisibility,
  /**
   * The month, such as `2026-11`.
   */
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
  /**
   * The net income over the target in whole percent, rounded down to 1%, so that 100 is
   * reached only when `covered` is true. It goes past 100, and is 0 while the net income is
   * not positive.
   */
  @SerialName("percent")
  public val percent: Long,
  /**
   * Whether the net income reaches the target, that is, this month is covered.
   */
  @SerialName("covered")
  public val covered: Boolean,
  /**
   * Whether the target is this month's costs and an imported usage charge of a billing month
   * that its provider has not closed is among them: the percentage may still change.
   */
  @SerialName("estimated")
  public val estimated: Boolean,
  /**
   * Whether the web client shows the meter in its right rail as well as on the about page (the
   * instance setting `funding_meter_rail`).
   */
  @SerialName("rail")
  public val rail: Boolean,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("income")
  public val income: Money? = null,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("target")
  public val target: Money? = null,
  /**
   * Whether the target is this month's costs; only with `amounts`.
   */
  @SerialName("target_is_costs")
  public val targetIsCosts: Boolean? = null,
)
