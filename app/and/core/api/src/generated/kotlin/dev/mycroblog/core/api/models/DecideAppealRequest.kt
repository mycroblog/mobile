package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The moderators' explanation of a decision on an appeal.
 */
@Serializable
public data class DecideAppealRequest(
  /**
   * Shown to the user who appealed, as plain text.
   */
  @SerialName("public_reason")
  public val publicReason: String? = null,
)
