package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A cost of the instance that is not deleted, with its current label and end
 * and every price revision. Never published: the public totals carry no item.
 */
@Serializable
public data class CostItem(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The unit of a cost item's payment schedule: `once` (one payment on the start
   * date), or every `interval` `weeks`, `months`, or `years`, counted from the start date. A
   * monthly payment on a day that a month does not have falls on the month's last day.
   */
  @SerialName("schedule")
  public val schedule: CostSchedule,
  /**
   * The N of every N weeks, months, or years; 1 for `once`.
   */
  @SerialName("interval")
  public val interval: Int,
  /**
   * A calendar date `YYYY-MM-DD` without a time or a time zone, such as the day
   * of a payment on a card statement or an invoice.
   */
  @SerialName("starts_on")
  public val startsOn: String,
  /**
   * A calendar date `YYYY-MM-DD` without a time or a time zone, such as the day
   * of a payment on a card statement or an invoice.
   */
  @SerialName("ends_on")
  public val endsOn: String? = null,
  @SerialName("name")
  public val name: String,
  /**
   * Who is paid, as written; the breakdown groups it in NFKC and lower case.
   */
  @SerialName("provider")
  public val provider: String,
  /**
   * The fixed category of a cost; clients translate its name.
   */
  @SerialName("category")
  public val category: CostCategory,
  /**
   * The operator's note, never published; empty for none.
   */
  @SerialName("memo")
  public val memo: String,
  /**
   * Every price revision, in the order they were recorded; the first one starts on
   * `starts_on`. `ends_on` is the last date a payment can fall on (included), omitted for
   * no end; `starts_on` is the first payment date, the payment date of `once`.
   */
  @SerialName("prices")
  public val prices: List<CostPriceRevision>,
  @SerialName("created_at")
  public val createdAt: Instant,
)
