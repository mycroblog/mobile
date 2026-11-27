package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The OAuth apps that the authenticated user registered, oldest first; there are at most 25.
 */
@Serializable
public data class OAuthAppList(
  @SerialName("items")
  public val items: List<OAuthApp>,
  /**
   * Whether the user may register an app now: the instance setting `user_app_registration`
   * is on. When it is false, `POST /oauth_apps` answers 403
   * `/api/v1/problems/oauth-app-registration-disabled`. It does not count the cap of 25
   * apps (the length of `items`) or the rate limit of registrations.
   */
  @SerialName("can_create")
  public val canCreate: Boolean,
)
