package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The emoji that a voice chat room shows before its name: a Unicode emoji, or a custom emoji
 * of this server with its image. A room whose custom emoji is later disabled or deleted shows
 * no icon.
 */
@Serializable
public data class VoiceRoomIcon(
  /**
   * The emoji as the settings of a room take it: the Unicode emoji, or the shortcode of a
   * custom emoji of this server, without colons.
   */
  @SerialName("name")
  public val name: String,
  /**
   * A custom emoji that a text uses. The text has it as `:shortcode:`.
   * An emoji that is disabled or deleted, or whose images come from a domain whose media the
   * instance rejects, is not listed, and its `:shortcode:` stays text. Clients show
   * `static_url` when animations should not play (`prefers-reduced-motion`), and `url`
   * otherwise.
   */
  @SerialName("emoji")
  public val emoji: CustomEmoji? = null,
)
