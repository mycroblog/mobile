package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A week, month, or year of the totals, as in `CostPeriodTotal`, with the item lines of the
 * period: empty unless the instance shows the names or the memos of the items, and sorted by
 * provider, category, name, and currency; without the names, by provider,
 * category, currency, and the larger amount first.
 */
@Serializable
public data class FinanceCostPeriodTotal(
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
  @SerialName("items")
  public val items: List<FinanceCostItemLine>,
)
