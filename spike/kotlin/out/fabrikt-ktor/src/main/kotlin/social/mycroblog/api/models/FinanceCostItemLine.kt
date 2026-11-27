package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The amount of a cost item, or of a service of the imported costs, in a currency over a
 * period, with the provider and the category of its breakdown line. `provider` is the spelling
 * that the period's breakdown lines of the item's grouped provider show (`CostBreakdownLine`).
 * `name` is the item's name or the service, present only while the instance shows the names;
 * `memo` is the item's memo, present only while the instance shows the memos and the item has
 * one (an imported cost has none).
 */
@Serializable
public data class FinanceCostItemLine(
  @SerialName("provider")
  public val provider: String,
  /**
   * The fixed category of a cost; clients translate its name.
   */
  @SerialName("category")
  public val category: CostCategory,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("amount")
  public val amount: Money,
  @SerialName("name")
  public val name: String? = null,
  @SerialName("memo")
  public val memo: String? = null,
)
