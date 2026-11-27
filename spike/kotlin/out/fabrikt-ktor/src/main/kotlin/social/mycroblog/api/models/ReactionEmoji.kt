package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The emoji of a reaction: a Unicode emoji, or a custom emoji with its image. A custom
 * emoji of another server is shown as it is and cannot be used to react from here.
 */
@Serializable
public data class ReactionEmoji(
  /**
   * The emoji as `listStatusReactions` takes it: the Unicode emoji, the shortcode of a
   * custom emoji of this server, or `shortcode@domain` for a custom emoji of another
   * server.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The shortcode of a custom emoji, without colons; omitted for a Unicode emoji.
   */
  @SerialName("shortcode")
  public val shortcode: String? = null,
  /**
   * The server of a custom emoji of another server; omitted otherwise.
   */
  @SerialName("domain")
  public val domain: String? = null,
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
