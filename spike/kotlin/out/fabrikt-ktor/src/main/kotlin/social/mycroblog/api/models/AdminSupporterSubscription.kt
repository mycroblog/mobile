package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A subscription as the administrators see it, without the history of its payments.
 */
@Serializable
public data class AdminSupporterSubscription(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The state of a subscription. `pending`: started, not paid yet. `active`:
   * paid. `canceling`: canceled at the end of the period. `past_due`: a payment failed less
   * than 7 days ago. `lapsed`: a payment failed more than 7 days ago, or the subscription is
   * paused. `expired`: ended. `revoked`: a full refund or a chargeback. `abandoned`: ended
   * before its first payment. The role is held while `active`, `canceling`, or `past_due`.
   */
  @SerialName("state")
  public val state: SupporterSubscriptionState,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("plan_id")
  public val planId: UUID,
  @SerialName("plan_name")
  public val planName: String,
  /**
   * How often a subscription renews.
   */
  @SerialName("interval")
  public val interval: SupporterInterval,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("price")
  public val price: Money,
  /**
   * The end of the current period; omitted for `pending` and `abandoned`.
   */
  @SerialName("period_ends_at")
  public val periodEndsAt: Instant? = null,
  /**
   * When a `past_due` subscription loses its role.
   */
  @SerialName("grace_ends_at")
  public val graceEndsAt: Instant? = null,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("scheduled_plan_id")
  public val scheduledPlanId: UUID? = null,
  /**
   * How often a subscription renews.
   */
  @SerialName("scheduled_interval")
  public val scheduledInterval: SupporterInterval? = null,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("account_id")
  public val accountId: UUID? = null,
  /**
   * The subscriber's username; omitted with `account_id` for an erased account.
   */
  @SerialName("username")
  public val username: String? = null,
  @SerialName("started_at")
  public val startedAt: Instant,
)
