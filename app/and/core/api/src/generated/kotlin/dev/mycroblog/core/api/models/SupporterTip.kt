package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A tip of the authenticated user.
 */
@Serializable
public data class SupporterTip(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The state of a tip: `open` until its payment or the end of its checkout, `paid` once its
   * payment settled, and `expired` when its checkout ended unpaid.
   */
  @SerialName("state")
  public val state: SupporterTipState,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("amount")
  public val amount: Money,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When the payment was recorded; omitted unless `paid`.
   */
  @SerialName("paid_at")
  public val paidAt: Instant? = null,
)
