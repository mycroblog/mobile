package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The full-text search of statuses on this server. It is separate from the search of accounts and
 * hashtags, which every server has. Clients read this to know whether to show an entry to it,
 * without a search that fails.
 */
@Serializable
public data class InstanceFullTextSearch(
  /**
   * Whether the server has a search engine, so that statuses can be searched.
   */
  @SerialName("available")
  public val available: Boolean,
  /**
   * The visibilities of the statuses that the search covers, only `public` and `local`; empty
   * while it is not available.
   */
  @SerialName("visibilities")
  public val visibilities: List<Visibility>,
)
