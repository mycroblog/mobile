package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The last attempt of the reconciliation with the payment provider; omitted before the first.
 */
@Serializable
public data class PaymentReconciliationRun(
  /**
   * `succeeded`; `partial` when it used its `payments_reconcile_max_requests` and a
   * continuation takes over; `failed` when the provider failed.
   */
  @SerialName("outcome")
  public val outcome: PaymentReconciliationRunOutcome,
  /**
   * Why it stopped: `request_cap` (a partial attempt), `permission` (the API key is wrong
   * or lacks permissions), `not_found`, `invalid_request`, `throttled` and `unavailable`
   * (the provider is busy or down; the next reconciliation tries again),
   * `invalid_signature`, `stale_signature`, `mode_mismatch` (the key's mode differs from
   * the account's), `invalid_response`; `none` for a success.
   */
  @SerialName("error_kind")
  public val errorKind: PaymentReconciliationRunErrorKind,
  /**
   * The requests it sent to the provider.
   */
  @SerialName("requests")
  public val requests: Int,
  /**
   * The subscriptions it read again from the provider.
   */
  @SerialName("checked_subscriptions")
  public val checkedSubscriptions: Int,
  /**
   * The subscriptions among them whose state it changed here.
   */
  @SerialName("corrected_subscriptions")
  public val correctedSubscriptions: Int,
  @SerialName("occurred_at")
  public val occurredAt: Instant,
)
