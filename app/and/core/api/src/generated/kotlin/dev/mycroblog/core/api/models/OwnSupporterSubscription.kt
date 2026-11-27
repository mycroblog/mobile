package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The authenticated user's subscription that goes on, and whether they may subscribe.
 */
@Serializable
public data class OwnSupporterSubscription(
  /**
   * The authenticated user's subscription to a supporter plan. `price` is what it renews at,
   * which may be lower than the plan's current price (a price change does not apply to the
   * subscriptions that go on). The plan's name is written by the administrators in any
   * language; render it with `dir="auto"`.
   */
  @SerialName("subscription")
  public val subscription: SupporterSubscription? = null,
  /**
   * Why the user may not subscribe to a supporter plan or tip: `not_local`, not a user
   * of this server; `email_unverified`, the primary email address is not verified;
   * `restricted`, the account is suspended or its deletion is done or scheduled. Omitted when
   * the user may.
   */
  @SerialName("ineligible_reason")
  public val ineligibleReason: SupporterIneligibleReason? = null,
)
