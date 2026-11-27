package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The changes to a role; what is omitted stays.
 */
@Serializable
public data class UpdateRoleRequest(
  /**
   * The new rank of the role, from 0 to 999.
   */
  @SerialName("position")
  public val position: Int? = null,
  /**
   * The new permissions of the role, each once; empty removes them all.
   */
  @SerialName("permissions")
  public val permissions: List<Permission>? = null,
  /**
   * The badge of a role, which its holders may show on their profile. The label is
   * the staff's own text, the same in every locale: clients show it as user content
   * (`dir="auto"`), never translated.
   */
  @SerialName("badge")
  public val badge: RoleBadge? = null,
  /**
   * `true` removes the badge of the role, and every holder's choice to show it; a new badge
   * starts hidden. Sending it with `badge` is a 422 whose `detail` names `remove_badge`.
   */
  @SerialName("remove_badge")
  public val removeBadge: Boolean? = null,
)
