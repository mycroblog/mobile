package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A voice chat room that the user hosted, as its settings stand last.
 */
@Serializable
public data class VoiceRoomHistoryEntry(
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
   * When the room opened.
   */
  @SerialName("opened_at")
  public val openedAt: Instant,
  /**
   * When the room ended; omitted while it is open.
   */
  @SerialName("ended_at")
  public val endedAt: Instant? = null,
  /**
   * Why the room ended; omitted while it is open.
   */
  @SerialName("end_reason")
  public val endReason: VoiceEndReason? = null,
)
