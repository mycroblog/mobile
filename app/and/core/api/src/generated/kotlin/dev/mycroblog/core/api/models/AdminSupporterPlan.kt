package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A supporter plan as the administrators see it. The yearly price is the
 * monthly one × 12, discounted by `annual_discount_percent`, rounded down. A plan takes
 * subscriptions while it is public, not retired, and `prices_ready` (the provider has the
 * prices of its current terms).
 */
@Serializable
public data class AdminSupporterPlan(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("name")
  public val name: String,
  @SerialName("description")
  public val description: String,
  /**
   * A higher rank is a higher plan, which includes everything of the lower ones.
   */
  @SerialName("rank")
  public val rank: Int,
  /**
   * Whether a supporter plan takes new subscriptions. `public`: offered. `hidden`: not offered;
   * its subscriptions go on.
   */
  @SerialName("visibility")
  public val visibility: SupporterPlanVisibility,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("monthly_price")
  public val monthlyPrice: Money,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("yearly_price")
  public val yearlyPrice: Money,
  @SerialName("annual_discount_percent")
  public val annualDiscountPercent: Int,
  /**
   * How many custom emoji the members may create each month (UTC) when the role lets them.
   */
  @SerialName("emoji_limit")
  public val emojiLimit: Int,
  /**
   * The role of a supporter plan, which only its subscriptions grant.
   */
  @SerialName("role")
  public val role: AdminSupporterPlanRole,
  @SerialName("prices_ready")
  public val pricesReady: Boolean,
  @SerialName("retired")
  public val retired: Boolean,
  @SerialName("retired_at")
  public val retiredAt: Instant? = null,
  /**
   * The subscriptions that hold the role (active, canceling, or past due).
   */
  @SerialName("members")
  public val members: Int,
  @SerialName("created_at")
  public val createdAt: Instant,
  @SerialName("revised_at")
  public val revisedAt: Instant,
)
