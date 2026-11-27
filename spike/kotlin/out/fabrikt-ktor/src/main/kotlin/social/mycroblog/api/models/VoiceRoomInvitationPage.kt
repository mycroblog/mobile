package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of the invitations of a voice chat room that were not revoked, oldest first.
 */
@Serializable
public data class VoiceRoomInvitationPage(
  /**
   * An opaque pagination cursor. Clients pass it back unchanged and never parse it.
   */
  @SerialName("next_cursor")
  public val nextCursor: String? = null,
  @SerialName("items")
  public val items: List<VoiceRoomInvitation>,
)
