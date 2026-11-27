package dev.mycroblog.core.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The deliveries of the closure's `Delete`s by state.
 */
@Serializable
public data class AdminServiceClosureDeliveries(
  /**
   * Not attempted yet, or failed and attempted again later.
   */
  @SerialName("pending")
  public val pending: Int,
  /**
   * Accepted by the inbox.
   */
  @SerialName("succeeded")
  public val succeeded: Int,
  /**
   * Refused for good.
   */
  @SerialName("permanent_failure")
  public val permanentFailure: Int,
  /**
   * Not sent, because the domain was suspended or the server unavailable.
   */
  @SerialName("skipped")
  public val skipped: Int,
  /**
   * Used up their attempts; an operator may requeue their dead jobs.
   */
  @SerialName("dead")
  public val dead: Int,
)
