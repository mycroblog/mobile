package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An announcement of the server to every local user, as the user sees it.
 */
@Serializable
public data class Announcement(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The heading, in plain text, as the administrators wrote it; render it with `dir="auto"`.
   * Empty for an announcement without one.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The text as an HTML fragment, sanitized (paragraphs, emphasis, lists, quotes, code,
   * headings from `h2`, and `http`, `https`, and `mailto` links).
   */
  @SerialName("content")
  public val content: String,
  /**
   * When it started to show to the users, its publication or the start of its window.
   */
  @SerialName("published_at")
  public val publishedAt: Instant,
  /**
   * When it stops showing; omitted when it shows until it is withdrawn.
   */
  @SerialName("ends_at")
  public val endsAt: Instant? = null,
  /**
   * When its text or window last changed.
   */
  @SerialName("revised_at")
  public val revisedAt: Instant,
  /**
   * Whether the user has marked it read.
   */
  @SerialName("read")
  public val read: Boolean,
  /**
   * Whether its window has ended, which only the list with the past announcements
   * (`listAnnouncementHistory`) has; such an announcement can no longer be marked read.
   */
  @SerialName("ended")
  public val ended: Boolean,
)
