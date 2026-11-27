package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new role.
 */
@Serializable
public data class CreateRoleRequest(
  /**
   * The name of the role, 1 to 64 lowercase ASCII letters, digits, `_`, and `-`, other than `new`.
   */
  @SerialName("key")
  public val key: String,
  /**
   * The rank of the role, from 0 to 999 (below `admin`).
   */
  @SerialName("position")
  public val position: Int,
  /**
   * The permissions that the role grants, each once.
   */
  @SerialName("permissions")
  public val permissions: List<Permission>,
)
