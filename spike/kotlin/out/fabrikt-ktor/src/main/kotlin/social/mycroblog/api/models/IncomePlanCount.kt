package social.mycroblog.api.models

import java.util.UUID
import kotlin.Long
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The number of members of a supporter plan now: the subscriptions that hold
 * the plan's role (active, canceling, or past due within the grace period). A retired plan
 * appears while it has members. Never the plan's amounts.
 */
@Serializable
public data class IncomePlanCount(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("plan_id")
  public val planId: UUID,
  @SerialName("name")
  public val name: String,
  @SerialName("members")
  public val members: Long,
)
