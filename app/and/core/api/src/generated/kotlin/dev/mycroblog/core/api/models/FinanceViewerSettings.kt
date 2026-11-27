package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the holders of `view_finance` read of the instance's finances beyond the totals and
 * their breakdown, by instance settings: `item_names`
 * (`finance_viewer_item_names`) shows the names of the cost items and the services of the
 * imported costs, `item_memos` (`finance_viewer_item_memos`) the memos of the cost items, and
 * `income` (`finance_viewer_income`) the income as totals only or with the number of members
 * of each plan.
 */
@Serializable
public data class FinanceViewerSettings(
  @SerialName("item_names")
  public val itemNames: Boolean,
  @SerialName("item_memos")
  public val itemMemos: Boolean,
  /**
   * How much of the income the holders of `view_finance` read (the instance setting
   * `finance_viewer_income`). `totals`: the total of each currency by period;
   * `plans`: also the number of members of each plan. No value shows the amounts of each plan or
   * who paid.
   */
  @SerialName("income")
  public val income: FinanceViewerIncome,
)
