package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A billing month of a source: an `estimate` while the provider has not closed it, `final`
 * once it has, `correction` once a value changed after that; with its totals by currency.
 */
@Serializable
public data class BillingImportMonth(
  /**
   * The billing month, `YYYY-MM` in the source's time zone.
   */
  @SerialName("month")
  public val month: String,
  @SerialName("kind")
  public val kind: BillingImportMonthKind,
  @SerialName("totals")
  public val totals: List<Money>,
)
