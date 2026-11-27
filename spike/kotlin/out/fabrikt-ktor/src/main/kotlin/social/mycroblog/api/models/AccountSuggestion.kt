package social.mycroblog.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An account suggested to follow, with why it is suggested.
 */
@Serializable
public data class AccountSuggestion(
  /**
   * Why the account is suggested: `featured` when the administrators feature it,
   * `recently_active` when it posted recently.
   */
  @SerialName("source")
  public val source: AccountSuggestionSource,
  /**
   * An account, as clients show it.
   */
  @SerialName("account")
  public val account: Account,
)
