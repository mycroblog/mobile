package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The suggested accounts.
 */
@Serializable
public data class AccountSuggestionList(
  @SerialName("items")
  public val items: List<AccountSuggestion>,
)
