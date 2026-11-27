package dev.mycroblog.core.api.models

import kotlin.Double
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How translation is used in the current month, and the providers that this server has.
 */
@Serializable
public data class AdminTranslationOverview(
  /**
   * The month that the numbers cover, as `YYYY-MM` in UTC.
   */
  @SerialName("month")
  public val month: String,
  /**
   * The characters of posts sent to providers this month, without the markers that stand in for
   * mentions, links, and the like. A call to a provider that fails counts too. This is what the
   * limits count, and it can differ from what a provider bills.
   */
  @SerialName("characters")
  public val characters: Long,
  /**
   * The requests answered this month from a translation the server kept.
   */
  @SerialName("cache_hits")
  public val cacheHits: Long,
  /**
   * The requests that went to a provider this month.
   */
  @SerialName("cache_misses")
  public val cacheMisses: Long,
  /**
   * The share of those requests answered from a kept translation, from 0 to 1; omitted before
   * there is any.
   */
  @SerialName("cache_hit_rate")
  public val cacheHitRate: Double? = null,
  /**
   * Every provider that this server has, enabled or not. API keys are not part of this.
   */
  @SerialName("providers")
  public val providers: List<TranslationProvider>,
)
