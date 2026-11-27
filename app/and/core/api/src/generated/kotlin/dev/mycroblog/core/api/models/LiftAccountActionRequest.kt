package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Why an action on an account is lifted.
 */
@Serializable
public data class LiftAccountActionRequest(
  @SerialName("reason")
  public val reason: String? = null,
)
