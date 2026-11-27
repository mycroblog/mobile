package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A tip to start, of one of the amounts of `GET /instance/tip_amounts`.
 */
@Serializable
public data class SupporterTipRequest(
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("amount")
  public val amount: Money,
)
