package social.mycroblog.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A role: a named set of permissions. A higher position outranks a lower one;
 * a user may grant, revoke, create, change, and delete only the roles at or below their
 * highest one, and give or take only the permissions they hold.
 */
@Serializable
public data class AdminRole(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The name of the role, such as `admin` or `moderator`.
   */
  @SerialName("key")
  public val key: String,
  /**
   * The rank of the role, from 0 to 999, and 1000 for `admin`.
   */
  @SerialName("position")
  public val position: Int,
  /**
   * The permissions that the role grants, in the order of `Permission`.
   */
  @SerialName("permissions")
  public val permissions: List<Permission>,
  /**
   * The badge of a role, which its holders may show on their profile. The label is
   * the staff's own text, the same in every locale: clients show it as user content
   * (`dir="auto"`), never translated.
   */
  @SerialName("badge")
  public val badge: RoleBadge? = null,
  /**
   * The supporter plan that grants a role to its subscribers, and so manages it.
   */
  @SerialName("supporter_plan")
  public val supporterPlan: AdminRoleSupporterPlan? = null,
)
