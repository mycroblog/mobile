package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The cut of an imported provider's billing months: the IANA time zone they are kept in
 * (`UTC`, `America/Los_Angeles`), as the provider bills them.
 */
@Serializable
public data class CostSourceRule(
  /**
   * A source of the billing imports (`billing_sources`): a cloud provider, or `plugin`, the
   * operator's plugin that aggregates any other providers.
   */
  @SerialName("source")
  public val source: BillingSourceName,
  @SerialName("provider")
  public val provider: String,
  @SerialName("time_zone")
  public val timeZone: String,
)
