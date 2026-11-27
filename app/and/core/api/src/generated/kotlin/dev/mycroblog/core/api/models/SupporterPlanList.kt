package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The supporter plans that take subscriptions, by rank.
 */
@Serializable
public data class SupporterPlanList(
  @SerialName("items")
  public val items: List<SupporterPlan>,
)
