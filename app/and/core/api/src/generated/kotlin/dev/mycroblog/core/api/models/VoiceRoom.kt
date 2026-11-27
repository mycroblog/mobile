package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An open voice chat room: its settings, its host, and its participants in the order they
 * joined. Everyone who sees the room sees its participants.
 */
@Serializable
public data class VoiceRoom(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The name of the room, 1 to 100 characters.
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
   * The description of the room, up to 500 characters; empty for none.
   */
  @SerialName("description")
  public val description: String,
  /**
   * The hashtags of the room, without the `#`, as the host typed them.
   */
  @SerialName("hashtags")
  public val hashtags: List<String>,
  /**
   * Who sees a voice chat room and may join it: `local` every local user, `followers` the
   * followers of the current host, `following` those whom the host follows, `mutuals` those who
   * follow the host and whom the host follows, and `invited` only the invited users. The host, the co-hosts, the participants, and the
   * invited users always see it, and a block with the host or a co-host always hides it.
   */
  @SerialName("visibility")
  public val visibility: VoiceRoomVisibility,
  /**
   * Who may become a speaker without asking: `everyone`, `followers` of the current host,
   * `following` (those whom the host follows), `mutuals` with the host, or `manual` (nobody: the host and the co-hosts let them speak).
   */
  @SerialName("speakers")
  public val speakers: VoiceRoomSpeakers,
  /**
   * How many people the room holds, the host included: the host's maximum, or the server's
   * (`voice_max_participants` of `Instance`) when the host set none or the server's is lower.
   * A join while the room holds as many people is a 409. The room may hold more after the
   * host lowered it; nobody leaves for it.
   */
  @SerialName("max_participants")
  public val maxParticipants: Int,
  /**
   * An account, as clients show it.
   */
  @SerialName("host")
  public val host: Account,
  /**
   * When the room opened.
   */
  @SerialName("opened_at")
  public val openedAt: Instant,
  /**
   * How many people are in the room, the host included.
   */
  @SerialName("participants_count")
  public val participantsCount: Int,
  @SerialName("participants")
  public val participants: List<VoiceRoomParticipant>,
  /**
   * Whether the user may become a speaker in the room now without raising a hand
   * (`POST /voice/rooms/{id}/speaker`): true for the host and the co-hosts, and for the
   * participants with a permission to speak (a granted raised hand, a grant, or an invitation
   * as a speaker) or whom the room's speakers setting covers, unless a moderator limited them
   * and they have no permission. False for any other user who is not in the room.
   */
  @SerialName("can_speak")
  public val canSpeak: Boolean,
)
