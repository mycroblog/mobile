package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An access token and a refresh token of the app for the authenticated user, as the token
 * endpoint returns them (RFC 6749 §5.1). The access token lasts an hour and the refresh token
 * 30 days; refreshing at `POST /oauth/token` (with the app's secret for a confidential app)
 * replaces both, and presenting a used refresh token again revokes the whole grant.
 */
@Serializable
public data class OAuthAppToken(
  @SerialName("access_token")
  public val accessToken: String,
  @SerialName("token_type")
  public val tokenType: OAuthAppTokenTokenType,
  /**
   * Seconds until the access token expires.
   */
  @SerialName("expires_in")
  public val expiresIn: Int,
  @SerialName("refresh_token")
  public val refreshToken: String,
  /**
   * The granted scopes, space-separated.
   */
  @SerialName("scope")
  public val scope: String,
)
