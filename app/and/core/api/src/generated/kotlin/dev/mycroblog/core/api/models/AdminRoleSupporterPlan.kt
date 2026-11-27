package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The supporter plan that grants a role to its subscribers, and so manages it.
 */
@Serializable
public data class AdminRoleSupporterPlan(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The name of the plan in its current terms.
   */
  @SerialName("name")
  public val name: String,
  /**
   * Whether the plan is retired, which keeps the role of the subscriptions it still has.
   */
  @SerialName("retired")
  public val retired: Boolean,
)
