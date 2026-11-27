package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A price for the payments on or after `effective_on`, on or after the start date: a raise,
 * a cut, or the correction of a mistake (`effective_on` = the start date corrects every
 * payment).
 */
@Serializable
public data class CostPriceRevisionRequest(
  /**
   * A calendar date `YYYY-MM-DD` without a time or a time zone, such as the day
   * of a payment on a card statement or an invoice.
   */
  @SerialName("effective_on")
  public val effectiveOn: String,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("price")
  public val price: Money,
)
