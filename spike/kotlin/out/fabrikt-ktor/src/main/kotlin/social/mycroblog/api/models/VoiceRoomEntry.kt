package social.mycroblog.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The open voice chat room where an account is the host or a speaker, on its profile. Only a
 * user who sees the room gets it; a listener's profile shows no room.
 */
@Serializable
public data class VoiceRoomEntry(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The name of the room.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The emoji that a voice chat room shows before its name: a Unicode emoji, or a custom emoji
   * of this server with its image. A room whose custom emoji is later disabled or deleted shows
   * no icon.
   */
  @SerialName("icon")
  public val icon: VoiceRoomIcon? = null,
  /**
   * Whether the account hosts the room or speaks in it.
   */
  @SerialName("role")
  public val role: VoiceRoomEntryRole,
  /**
   * How many people are in the room.
   */
  @SerialName("participants_count")
  public val participantsCount: Int,
)
