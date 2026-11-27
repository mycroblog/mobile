package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The authenticated user's subscription to a supporter plan. `price` is what it renews at,
 * which may be lower than the plan's current price (a price change does not apply to the
 * subscriptions that go on). The plan's name is written by the administrators in any
 * language; render it with `dir="auto"`.
 */
@Serializable
public data class SupporterSubscription(
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
   * The plan's `rank`, given also for a hidden plan, which the plan list leaves out: a change
   * to a plan of a higher rank applies at once, to a lower one at the end of the period.
   */
  @SerialName("plan_rank")
  public val planRank: Int,
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
   * The end of the current period: when it renews, or, while `canceling`, when it ends;
   * omitted for `pending`.
   */
  @SerialName("period_ends_at")
  public val periodEndsAt: Instant? = null,
  /**
   * When a `past_due` subscription loses the plan's role unless a payment succeeds.
   */
  @SerialName("grace_ends_at")
  public val graceEndsAt: Instant? = null,
  /**
   * Why a `lapsed`, `expired`, or `revoked` subscription lost the plan's role; omitted in the
   * other states. `lapsed`: no payment succeeded within 7 days after a payment failed, or the
   * subscription is paused. `reversed`: a payment was refunded in full or charged back.
   * `plan_retired`: the administrators retired the plan. `account_suspended`: the account was
   * suspended. `account_closed`: the account was deleted. `staff`: an administrator canceled
   * it. `canceled`: it was canceled, or it ended at the payment provider.
   */
  @SerialName("end_reason")
  public val endReason: SupporterEndReason? = null,
  /**
   * A change to a lower plan or to the monthly price that waits for the end of the period.
   */
  @SerialName("scheduled_change")
  public val scheduledChange: SupporterScheduledChange? = null,
  @SerialName("started_at")
  public val startedAt: Instant,
)
