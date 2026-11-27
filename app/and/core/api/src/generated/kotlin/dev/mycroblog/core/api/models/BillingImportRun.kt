package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.Long
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The last attempt of a billing import; omitted before the first.
 */
@Serializable
public data class BillingImportRun(
  @SerialName("outcome")
  public val outcome: BillingImportRunOutcome,
  /**
   * Why it failed: `permission` (the credentials may not read the billing data),
   * `not_enabled` (the billing data is not enabled), `not_found` (the dataset, table, or
   * subscription does not exist), `throttled` and `unavailable` (retried), `request_cap`
   * (the cap of the day or the month is reached; nothing was called), `bytes_cap` (the
   * query would scan too much), `invalid_response`; `none` for a success.
   */
  @SerialName("error_kind")
  public val errorKind: BillingImportRunErrorKind,
  /**
   * The billed requests it made.
   */
  @SerialName("requests")
  public val requests: Int,
  @SerialName("bytes_billed")
  public val bytesBilled: Long,
  @SerialName("occurred_at")
  public val occurredAt: Instant,
)
