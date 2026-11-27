package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The current instance texts, by key.
 */
@Serializable
public data class AdminInstanceTextList(
  @SerialName("items")
  public val items: List<AdminInstanceText>,
)
