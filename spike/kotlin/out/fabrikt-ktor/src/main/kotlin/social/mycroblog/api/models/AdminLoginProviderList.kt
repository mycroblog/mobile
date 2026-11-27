package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The login providers, newest first.
 */
@Serializable
public data class AdminLoginProviderList(
  @SerialName("items")
  public val items: List<AdminLoginProvider>,
)
