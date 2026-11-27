package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The tips: whether the operator enabled them (`enabled`) with their `amounts`, and whether users
 * may tip now (`accepting`), which also needs the supporter program enabled on the configured
 * payment provider. `one_time_payments` reports whether that provider takes one-time payments,
 * and `limits` the smallest and largest amount of each currency that the tips may offer on it.
 * `server_limit_reached` reports that the starts of the last hour reached the limit of the whole
 * server, which may be card testing.
 */
@Serializable
public data class AdminSupporterTips(
  @SerialName("enabled")
  public val enabled: Boolean,
  @SerialName("accepting")
  public val accepting: Boolean,
  @SerialName("one_time_payments")
  public val oneTimePayments: Boolean,
  /**
   * The amounts, smallest first; empty while the tips are disabled.
   */
  @SerialName("amounts")
  public val amounts: List<Money>,
  @SerialName("limits")
  public val limits: List<AdminSupporterTipLimits>,
  @SerialName("server_limit_reached")
  public val serverLimitReached: Boolean,
  /**
   * When the tips were last enabled, changed, or disabled; omitted for never.
   */
  @SerialName("changed_at")
  public val changedAt: Instant? = null,
)
