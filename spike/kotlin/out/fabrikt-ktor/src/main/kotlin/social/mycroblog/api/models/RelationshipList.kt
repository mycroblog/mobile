package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The relationships, in the order of the request.
 */
@Serializable
public data class RelationshipList(
  @SerialName("items")
  public val items: List<Relationship>,
)
