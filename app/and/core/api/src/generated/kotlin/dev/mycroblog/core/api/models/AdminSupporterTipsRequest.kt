package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Enables the tips with their amounts, all in one currency, or disables them (`enabled` false,
 * without amounts).
 */
@Serializable
public data class AdminSupporterTipsRequest(
  @SerialName("enabled")
  public val enabled: Boolean,
  @SerialName("amounts")
  public val amounts: List<Money>? = null,
)
