package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The viewer's preferences of the follow; a preference left out keeps its value.
 */
@Serializable
public data class FollowAccountRequest(
  /**
   * Show the account's boosts in the home timeline. New follows show them.
   */
  @SerialName("show_boosts")
  public val showBoosts: Boolean? = null,
  /**
   * Subscribe to the account's new posts (true) or stop (false), as `subscribeAccount` and
   * `unsubscribeAccount` do; left out, the subscription stays as it is. For an account on another
   * server, the subscription starts when that server accepts the follow.
   */
  @SerialName("notify")
  public val notify: Boolean? = null,
)
