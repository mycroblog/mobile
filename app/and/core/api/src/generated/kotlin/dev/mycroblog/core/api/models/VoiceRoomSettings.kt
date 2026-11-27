package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The settings of a new voice chat room.
 */
@Serializable
public data class VoiceRoomSettings(
  /**
   * 1 to 100 characters on one line.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The emoji shown before the name: one Unicode emoji, or the shortcode of an enabled custom
   * emoji of this server, with or without colons. Omitted or empty for none. Any other value,
   * such as a custom emoji of another server, is refused with `422`.
   */
  @SerialName("icon")
  public val icon: String? = null,
  /**
   * Up to 500 characters; line breaks are kept.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * Up to 5 hashtags, with or without the `#`, each one a hashtag as in a post.
   */
  @SerialName("hashtags")
  public val hashtags: List<String>? = null,
  /**
   * Who sees the room; `local` when omitted.
   */
  @SerialName("visibility")
  public val visibility: VoiceRoomVisibility? = null,
  /**
   * Who may become a speaker without asking; `manual` when omitted.
   */
  @SerialName("speakers")
  public val speakers: VoiceRoomSpeakers? = null,
  /**
   * How many people the room holds, the host included, from 2 to the server's maximum
   * (`voice_max_participants` of `Instance`); the server's maximum when omitted. Any other
   * number is a 422.
   */
  @SerialName("max_participants")
  public val maxParticipants: Int? = null,
  /**
   * The post that the host writes when opening a voice chat room. Asking for one needs the
   * `write:statuses` scope besides `write:voice`, or the request is a 403
   * `/api/v1/problems/insufficient-scope`. The link of the room is appended to the text on a line of its own. The post is the host's own: `local` for a room of
   * the `local` visibility and `private` for a room of the `followers` visibility, federated like
   * any other post, and the host deletes it like any other. A room of the `following`, `mutuals`,
   * or `invited` visibility has no start post.
   */
  @SerialName("post")
  public val post: VoiceStartPost? = null,
)
