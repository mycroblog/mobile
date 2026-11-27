package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The accounts, best match first.
 */
@Serializable
public data class AccountList(
  @SerialName("items")
  public val items: List<Account>,
)
