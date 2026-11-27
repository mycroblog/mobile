package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The net of one source over a period, by currency.
 */
@Serializable
public data class IncomeSourceNet(
  /**
   * Where a money movement comes from: a subscription to a supporter plan (`subscription`), a tip
   * (`one_time`), or neither (`unknown`: a payment taken outside this server, or a fee of no
   * subscription). A refund, a chargeback, or a reversal has the source of its payment.
   */
  @SerialName("source")
  public val source: IncomeSource,
  @SerialName("net")
  public val net: List<Money>,
)
