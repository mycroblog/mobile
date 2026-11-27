package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The local account that sent the activity.
 */
@Serializable
public data class AdminDeliverySender(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("account_id")
  public val accountId: UUID,
  /**
   * The username of the account; absent for an account that is gone.
   */
  @SerialName("username")
  public val username: String? = null,
)
