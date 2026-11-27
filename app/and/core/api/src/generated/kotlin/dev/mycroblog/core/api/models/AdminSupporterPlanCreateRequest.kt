package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new supporter plan on a role that nobody holds, whose permissions are member
 * permissions and whose position is below every staff role. The currency never changes.
 */
@Serializable
public data class AdminSupporterPlanCreateRequest(
  /**
   * The key of the role.
   */
  @SerialName("role")
  public val role: String,
  /**
   * 1 to 50 characters on one line.
   */
  @SerialName("name")
  public val name: String,
  /**
   * Up to 1,000 characters of plain text.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("monthly_price")
  public val monthlyPrice: Money,
  /**
   * 0 to 90.
   */
  @SerialName("annual_discount_percent")
  public val annualDiscountPercent: Int,
  /**
   * How many custom emoji the members may create each month (UTC), 0 to 10,000.
   */
  @SerialName("emoji_limit")
  public val emojiLimit: Int,
  /**
   * 0 to 1,000,000, unique among the plans that are not retired.
   */
  @SerialName("rank")
  public val rank: Int,
  /**
   * Whether a supporter plan takes new subscriptions. `public`: offered. `hidden`: not offered;
   * its subscriptions go on.
   */
  @SerialName("visibility")
  public val visibility: SupporterPlanVisibility,
)
