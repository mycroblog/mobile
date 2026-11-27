package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A change of the settings of a voice chat room; an omitted property keeps its value.
 */
@Serializable
public data class VoiceRoomUpdate(
  /**
   * 1 to 100 characters on one line.
   */
  @SerialName("name")
  public val name: String? = null,
  /**
   * The emoji shown before the name, as in `VoiceRoomSettings`; empty removes it.
   */
  @SerialName("icon")
  public val icon: String? = null,
  /**
   * Up to 500 characters; empty for none.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * Up to 5 hashtags, which replace the room's; empty for none.
   */
  @SerialName("hashtags")
  public val hashtags: List<String>? = null,
  /**
   * Who sees a voice chat room and may join it: `local` every local user, `followers` the
   * followers of the current host, `following` those whom the host follows, `mutuals` those who
   * follow the host and whom the host follows, and `invited` only the invited users. The host, the co-hosts, the participants, and the
   * invited users always see it, and a block with the host or a co-host always hides it.
   */
  @SerialName("visibility")
  public val visibility: VoiceRoomVisibility? = null,
  /**
   * Who may become a speaker without asking: `everyone`, `followers` of the current host,
   * `following` (those whom the host follows), `mutuals` with the host, or `manual` (nobody: the host and the co-hosts let them speak).
   */
  @SerialName("speakers")
  public val speakers: VoiceRoomSpeakers? = null,
  /**
   * How many people the room holds, from 2 to the server's maximum, as in
   * `VoiceRoomSettings`. A maximum below the people in the room is accepted: nobody leaves,
   * and joins are refused until fewer people are in it.
   */
  @SerialName("max_participants")
  public val maxParticipants: Int? = null,
)
