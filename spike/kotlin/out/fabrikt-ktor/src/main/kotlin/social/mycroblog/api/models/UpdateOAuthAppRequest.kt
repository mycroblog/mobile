package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The changes to an OAuth app, checked as for a new one; what is omitted stays.
 */
@Serializable
public data class UpdateOAuthAppRequest(
  @SerialName("name")
  public val name: String? = null,
  /**
   * The new home page; empty removes it.
   */
  @SerialName("website")
  public val website: String? = null,
  @SerialName("redirect_uris")
  public val redirectUris: List<String>? = null,
  /**
   * The new most scope. The tokens that the app holds keep the scope they were granted.
   */
  @SerialName("scope")
  public val scope: String? = null,
)
