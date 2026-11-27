package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The end of a cost item: `ends_on` is the last date a payment can fall on (included), on or
 * after the start date, and omitted for no end. The last payment before it still covers the
 * days up to the next payment date.
 */
@Serializable
public data class CostItemEndRequest(
  /**
   * A calendar date `YYYY-MM-DD` without a time or a time zone, such as the day
   * of a payment on a card statement or an invoice.
   */
  @SerialName("ends_on")
  public val endsOn: String? = null,
)
