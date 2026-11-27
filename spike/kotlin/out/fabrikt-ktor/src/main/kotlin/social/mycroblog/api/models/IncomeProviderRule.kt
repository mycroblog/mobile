package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The IANA time zone (`UTC`, `Asia/Tokyo`) in which a payment provider's money movements fall
 * on their dates: the provider's reporting time zone, or `UTC` when it could not be read.
 */
@Serializable
public data class IncomeProviderRule(
  @SerialName("provider")
  public val provider: String,
  @SerialName("time_zone")
  public val timeZone: String,
)
