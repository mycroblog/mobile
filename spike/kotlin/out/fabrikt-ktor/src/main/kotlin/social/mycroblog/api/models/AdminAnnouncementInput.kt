package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The title, the text, and the window of an announcement.
 */
@Serializable
public data class AdminAnnouncementInput(
  /**
   * The heading, in plain text: 1 to 100 grapheme clusters on one line, without control
   * characters. White space around it is trimmed.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The text, in Markdown.
   */
  @SerialName("text")
  public val text: String,
  /**
   * When it starts to show once published; omitted to show it from its publication.
   */
  @SerialName("starts_at")
  public val startsAt: Instant? = null,
  /**
   * When it stops showing, after `starts_at`; omitted to show it until it is withdrawn.
   */
  @SerialName("ends_at")
  public val endsAt: Instant? = null,
)
