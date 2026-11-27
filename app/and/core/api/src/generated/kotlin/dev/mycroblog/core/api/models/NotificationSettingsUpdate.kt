package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Changes of the authenticated user's notification settings.
 */
@Serializable
public data class NotificationSettingsUpdate(
  /**
   * The cells to change; the others keep their values. Each names a row the user gets and a
   * channel that is not fixed.
   */
  @SerialName("changes")
  public val changes: List<NotificationSettingsUpdateChanges>,
)
