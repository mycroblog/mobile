package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The settings of a mute.
 */
@Serializable
public data class MuteAccountRequest(
  /**
   * Also hide the account's notifications.
   */
  @SerialName("hide_notifications")
  public val hideNotifications: Boolean = true,
  /**
   * How long the mute lasts, in seconds; 0 mutes until unmuted.
   */
  @SerialName("duration")
  public val duration: Int = 0,
)
