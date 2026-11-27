package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Enables or disables the supporter program.
 */
@Serializable
public data class AdminSupporterProgramRequest(
  @SerialName("enabled")
  public val enabled: Boolean,
)
