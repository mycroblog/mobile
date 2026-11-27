package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An announcement with its Markdown text and its state.
 */
@Serializable
public data class AdminAnnouncement(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The heading, in plain text.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The text, in Markdown.
   */
  @SerialName("text")
  public val text: String,
  /**
   * The text as the users get it, an HTML fragment (`Announcement.content`).
   */
  @SerialName("content")
  public val content: String,
  /**
   * `draft`: no user sees it. `published`: every local user sees it within its window.
   * `withdrawn`: taken down for good.
   */
  @SerialName("state")
  public val state: AdminAnnouncementState,
  /**
   * When it starts to show once published; omitted to show it from its publication.
   */
  @SerialName("starts_at")
  public val startsAt: Instant? = null,
  /**
   * When it stops showing; omitted to show it until it is withdrawn.
   */
  @SerialName("ends_at")
  public val endsAt: Instant? = null,
  /**
   * When it was last published; omitted for a draft.
   */
  @SerialName("published_at")
  public val publishedAt: Instant? = null,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When its text or window last changed.
   */
  @SerialName("revised_at")
  public val revisedAt: Instant,
)
