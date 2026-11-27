package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new cost item. The schedule and the start date never change: to change them, end the
 * item and record a new one. `ends_on` is the last date a payment can fall on, omitted for
 * no end; `price` is the price of every payment from the start date, 0 or more.
 */
@Serializable
public data class CostItemCreateRequest(
  /**
   * The unit of a cost item's payment schedule: `once` (one payment on the start
   * date), or every `interval` `weeks`, `months`, or `years`, counted from the start date. A
   * monthly payment on a day that a month does not have falls on the month's last day.
   */
  @SerialName("schedule")
  public val schedule: CostSchedule,
  /**
   * The N of every N weeks, months, or years, 1 to 99; 1 for `once`.
   */
  @SerialName("interval")
  public val interval: Int,
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
  public val endsOn: String? = null,
  /**
   * 1 to 100 characters without control characters.
   */
  @SerialName("name")
  public val name: String,
  /**
   * 1 to 100 characters without control characters.
   */
  @SerialName("provider")
  public val provider: String,
  /**
   * The fixed category of a cost; clients translate its name.
   */
  @SerialName("category")
  public val category: CostCategory,
  /**
   * Up to 10,000 characters, never published.
   */
  @SerialName("memo")
  public val memo: String? = null,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("price")
  public val price: Money,
)
