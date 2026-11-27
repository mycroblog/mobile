package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The authenticated user's linked accounts and the login providers.
 */
@Serializable
public data class LinkedIdentityList(
  /**
   * The linked accounts, oldest first; at most one per provider.
   */
  @SerialName("items")
  public val items: List<LinkedIdentity>,
  /**
   * The login providers that users sign in with, oldest first.
   */
  @SerialName("providers")
  public val providers: List<LinkableLoginProvider>,
)
