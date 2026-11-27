package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The other accounts that used one IP address.
 */
@Serializable
public data class AdminSameIpAccounts(
  @SerialName("ip")
  public val ip: String,
  @SerialName("accounts")
  public val accounts: List<AdminAccount>,
)
