package social.mycroblog.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The role of a supporter plan, which only its subscriptions grant.
 */
@Serializable
public data class AdminSupporterPlanRole(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("key")
  public val key: String,
  @SerialName("position")
  public val position: Int,
  @SerialName("permissions")
  public val permissions: List<Permission>,
)
