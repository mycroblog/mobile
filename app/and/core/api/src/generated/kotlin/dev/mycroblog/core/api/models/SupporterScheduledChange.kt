package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A change to a lower plan or to the monthly price that waits for the end of the period.
 */
@Serializable
public data class SupporterScheduledChange(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("plan_id")
  public val planId: UUID,
  @SerialName("plan_name")
  public val planName: String,
  /**
   * How often a subscription renews.
   */
  @SerialName("interval")
  public val interval: SupporterInterval,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("price")
  public val price: Money,
  @SerialName("effective_at")
  public val effectiveAt: Instant,
)
