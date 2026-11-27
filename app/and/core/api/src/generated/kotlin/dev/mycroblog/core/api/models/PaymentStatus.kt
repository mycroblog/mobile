package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The state of the payment provider.
 */
@Serializable
public data class PaymentStatus(
  /**
   * The provider of `payments` (`stripe`, `payjp`).
   */
  @SerialName("provider")
  public val provider: String,
  /**
   * `test` for test credentials, with which nothing is charged, and `live` for live
   * credentials.
   */
  @SerialName("mode")
  public val mode: PaymentStatusMode,
  /**
   * The IANA time zone of the provider's reports (`UTC`), in which the income is dated.
   */
  @SerialName("time_zone")
  public val timeZone: String,
  /**
   * When the newest webhook delivery was received; omitted when none is recorded (the
   * deliveries are kept 45 days).
   */
  @SerialName("last_webhook_at")
  public val lastWebhookAt: Instant? = null,
  /**
   * The last attempt of the reconciliation with the payment provider; omitted before the first.
   */
  @SerialName("last_reconciliation")
  public val lastReconciliation: PaymentReconciliationRun? = null,
  /**
   * What the operator should look at: `no_webhook` (no delivery is recorded) and
   * `reconciliation_corrected` (the last reconciliation corrected subscriptions, which a webhook
   * delivery should have done) are signs that the provider's webhook is not set up right, and
   * `duplicate_charge` means that a second subscription of one payment is not canceled and
   * refunded yet.
   */
  @SerialName("warnings")
  public val warnings: List<PaymentStatusWarnings>,
)
