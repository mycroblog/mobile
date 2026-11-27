package dev.mycroblog.core.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The deactivation of the user's own account.
 */
@Serializable
public data class Deactivation(
  /**
   * When the account is deleted unless the user reactivates it first.
   */
  @SerialName("deletion_due_at")
  public val deletionDueAt: Instant,
)
