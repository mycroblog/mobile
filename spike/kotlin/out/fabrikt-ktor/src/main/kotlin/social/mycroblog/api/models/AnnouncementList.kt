package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The announcements.
 */
@Serializable
public data class AnnouncementList(
  @SerialName("items")
  public val items: List<Announcement>,
)
