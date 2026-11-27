package social.mycroblog.api.models

import java.net.URI
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A custom emoji that a text uses. The text has it as `:shortcode:`.
 * An emoji that is disabled or deleted, or whose images come from a domain whose media the
 * instance rejects, is not listed, and its `:shortcode:` stays text. Clients show
 * `static_url` when animations should not play (`prefers-reduced-motion`), and `url`
 * otherwise.
 */
@Serializable
public data class CustomEmoji(
  /**
   * The shortcode, without the colons around it.
   */
  @SerialName("shortcode")
  public val shortcode: String,
  /**
   * The URL of the image, animated when the emoji is.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
  /**
   * The URL of a still image of the emoji; the same as `url` when no still version is served.
   */
  @Contextual
  @SerialName("static_url")
  public val staticUrl: URI,
)
