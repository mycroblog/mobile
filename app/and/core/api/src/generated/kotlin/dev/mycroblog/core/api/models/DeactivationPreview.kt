package dev.mycroblog.core.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What deactivating the user's own account would do now.
 */
@Serializable
public data class DeactivationPreview(
  /**
   * When the account would be deleted if it were deactivated now.
   */
  @SerialName("deletion_due_at")
  public val deletionDueAt: Instant,
  /**
   * Why the account cannot be deactivated now; absent when it can: moderation keeps it from
   * logging in (`login_restricted`), its deletion is scheduled (`deletion_scheduled`), it is
   * deactivated (`already_deactivated`), it moved (`moved`), its registration waits for review
   * (`registration_pending`), a paid subscription goes on (`paid_subscription`), no other
   * administrator can sign in (`last_administrator`), or it was reactivated less than 24 hours
   * ago (`recently_reactivated`).
   */
  @SerialName("refusal")
  public val refusal: DeactivationPreviewRefusal? = null,
  /**
   * When the account can be deactivated again, for `recently_reactivated`.
   */
  @SerialName("available_at")
  public val availableAt: Instant? = null,
)
