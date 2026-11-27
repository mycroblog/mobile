package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The revisions of a status.
 */
@Serializable
public data class StatusHistory(
  /**
   * The revisions, oldest first.
   */
  @SerialName("items")
  public val items: List<StatusEdit>,
)
