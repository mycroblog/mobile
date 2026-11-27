package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of the open voice chat rooms that the user sees: the rooms where the user follows the
 * host or a speaker first, then by the most participants.
 */
@Serializable
public data class VoiceRoomPage(
  /**
   * An opaque pagination cursor. Clients pass it back unchanged and never parse it.
   */
  @SerialName("next_cursor")
  public val nextCursor: String? = null,
  @SerialName("items")
  public val items: List<VoiceRoom>,
)
