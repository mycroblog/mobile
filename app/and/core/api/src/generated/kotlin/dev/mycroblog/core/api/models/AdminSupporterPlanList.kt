package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Every supporter plan, retired ones too, by rank.
 */
@Serializable
public data class AdminSupporterPlanList(
  @SerialName("items")
  public val items: List<AdminSupporterPlan>,
)
