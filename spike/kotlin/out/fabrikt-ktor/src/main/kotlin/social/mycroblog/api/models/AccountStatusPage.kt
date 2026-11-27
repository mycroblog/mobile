package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of the statuses of an account.
 */
@Serializable
public data class AccountStatusPage(
  /**
   * An opaque pagination cursor. Clients pass it back unchanged and never parse it.
   */
  @SerialName("next_cursor")
  public val nextCursor: String? = null,
  /**
   * The statuses of the page.
   */
  @SerialName("items")
  public val items: List<Status>,
  /**
   * `true` when this request started fetching the remote account's recent public
   * posts from its server. The fetch runs in the background; read the
   * first page again a few seconds later to see what it stored. Absent otherwise,
   * including when a fetch started in the last day.
   */
  @SerialName("remote_fetch_started")
  public val remoteFetchStarted: Boolean? = null,
)
