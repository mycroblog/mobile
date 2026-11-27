package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The scope of the tokens of an app for the authenticated user.
 */
@Serializable
public data class IssueOAuthAppTokenRequest(
  /**
   * Space-separated scopes within the app's; omitted or empty, the app's whole scope.
   */
  @SerialName("scope")
  public val scope: String? = null,
)
