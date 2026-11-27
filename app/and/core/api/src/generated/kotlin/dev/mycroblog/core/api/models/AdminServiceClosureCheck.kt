package dev.mycroblog.core.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the administrator reviews before closing the server. The admin
 * screen and the CLI show it with the confirmation of the closure.
 */
@Serializable
public data class AdminServiceClosureCheck(
  /**
   * The local accounts of users that are not deleted, each of which the closure sends a `Delete` of.
   */
  @SerialName("local_accounts")
  public val localAccounts: Int,
  /**
   * The shared inboxes known to the server, which each `Delete` goes to.
   */
  @SerialName("delivery_inboxes")
  public val deliveryInboxes: Int,
  /**
   * The estimate of the deliveries of the closure, `local_accounts` times `delivery_inboxes`.
   */
  @SerialName("estimated_deletes")
  public val estimatedDeletes: Int,
  /**
   * The supporter subscriptions that go on, which the closure cancels at once without a refund, except a payment for one still pending.
   */
  @SerialName("live_supporter_subscriptions")
  public val liveSupporterSubscriptions: Int,
  /**
   * The account migrations (the sending of a `Move` and the jobs that move the followers) that have not ended. They should end before the closure delivers its `Delete`;
   * the admin screen and the CLI warn while it is not 0.
   */
  @SerialName("pending_migrations")
  public val pendingMigrations: Int,
  /**
   * The accounts under a retention hold. While it is not 0, the operator exports
   * their records with the CLI before discarding the data of the closed server.
   */
  @SerialName("retention_holds")
  public val retentionHolds: Int,
)
