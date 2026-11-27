package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of subscriptions to domain block lists, newest first.
 */
@Serializable
public data class AdminDomainListSubscriptionPage(
  /**
   * An opaque pagination cursor. Clients pass it back unchanged and never parse it.
   */
  @SerialName("next_cursor")
  public val nextCursor: String? = null,
  @SerialName("items")
  public val items: List<AdminDomainListSubscription>,
)
