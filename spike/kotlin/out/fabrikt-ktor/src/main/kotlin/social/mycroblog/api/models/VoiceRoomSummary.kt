package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the page of a voice chat room shows to a visitor who does not see the room, such as a
 * visitor who is not signed in or a user of another server: only the users of this server
 * join its rooms. `name` is present only when the server shows the summaries of its rooms (the
 * instance setting `voice_public_summary`) and the room is for every local user (the `local`
 * visibility); `description` and `host` then come too while the room is open. The
 * participants are never shown.
 */
@Serializable
public data class VoiceRoomSummary(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * Whether the room is open or ended.
   */
  @SerialName("state")
  public val state: VoiceRoomSummaryState,
  /**
   * The name of the room.
   */
  @SerialName("name")
  public val name: String? = null,
  /**
   * The emoji that a voice chat room shows before its name: a Unicode emoji, or a custom emoji
   * of this server with its image. A room whose custom emoji is later disabled or deleted shows
   * no icon.
   */
  @SerialName("icon")
  public val icon: VoiceRoomIcon? = null,
  /**
   * The description of the room, empty for none.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * An account, as clients show it.
   */
  @SerialName("host")
  public val host: Account? = null,
)
