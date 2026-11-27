package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An account that the viewer mutes, with the mute's settings.
 */
@Serializable
public data class MutedAccount(
  /**
   * An account, as clients show it.
   */
  @SerialName("account")
  public val account: Account,
  /**
   * Whether the mute also hides the account's notifications.
   */
  @SerialName("hide_notifications")
  public val hideNotifications: Boolean,
  /**
   * When the mute ends; left out for a mute until unmuted.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant? = null,
)
