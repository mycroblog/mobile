package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderator ending an open voice chat room.
 */
@Serializable
public data class AdminVoiceRoomEnd(
  /**
   * Why the room ends, as plain text for the moderators, up to 500 characters. Nothing
   * shows it to the participants.
   */
  @SerialName("reason")
  public val reason: String? = null,
)
