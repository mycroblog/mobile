package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The amount of a provider, category, and currency in a period. The costs group by the provider
 * in NFKC and lower case, so `ConoHa`, `conoha`, and `ＣｏｎｏＨａ` share a line.
 */
@Serializable
public data class CostBreakdownLine(
  /**
   * The provider as entered, one spelling for every line of the same grouped provider
   * in the period: the provider of the cost item whose current label was revised last among the
   * items with payments in the period (the larger revision ID on a tie), or, for a provider of
   * imported costs only, the import's provider name (`AWS`, `Google Cloud`).
   */
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
)
