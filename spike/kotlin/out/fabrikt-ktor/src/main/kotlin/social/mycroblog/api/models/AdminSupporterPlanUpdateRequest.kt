package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A change of a supporter plan; an omitted property keeps its value. A new monthly price,
 * in the plan's currency, or discount makes new prices for the new subscriptions and the
 * changes of plan; the subscriptions on the old ones keep them. `role_position` and
 * `role_permissions` change the plan's role, kept to member permissions below every staff
 * role.
 */
@Serializable
public data class AdminSupporterPlanUpdateRequest(
  @SerialName("name")
  public val name: String? = null,
  @SerialName("description")
  public val description: String? = null,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("monthly_price")
  public val monthlyPrice: Money? = null,
  @SerialName("annual_discount_percent")
  public val annualDiscountPercent: Int? = null,
  @SerialName("emoji_limit")
  public val emojiLimit: Int? = null,
  @SerialName("rank")
  public val rank: Int? = null,
  /**
   * Whether a supporter plan takes new subscriptions. `public`: offered. `hidden`: not offered;
   * its subscriptions go on.
   */
  @SerialName("visibility")
  public val visibility: SupporterPlanVisibility? = null,
  @SerialName("role_position")
  public val rolePosition: Int? = null,
  @SerialName("role_permissions")
  public val rolePermissions: List<Permission>? = null,
)
