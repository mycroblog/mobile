package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new OAuth app.
 */
@Serializable
public data class CreateOAuthAppRequest(
  /**
   * The app's name, 1 to 100 characters on one line.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The app's home page, an absolute `https` or `http` URL of at most 2048 bytes.
   */
  @SerialName("website")
  public val website: String? = null,
  /**
   * 1 to 10 redirect URIs (RFC 8252): an `https` URL, a private-use scheme in reverse domain
   * notation (`com.example.app:/callback`), or an `http` loopback URL on `127.0.0.1` or
   * `[::1]` (not `localhost`), none with a fragment or user info.
   */
  @SerialName("redirect_uris")
  public val redirectUris: List<String>,
  /**
   * The most the app may request, space-separated scopes: not empty, and without the
   * first-party-only scopes (`admin`, `admin:read`, `admin:write`, `write:supporters`).
   */
  @SerialName("scope")
  public val scope: String,
  /**
   * `public`: the app has no secret (a single-page, native, or command-line app).
   * `confidential`: the app runs on a server and authenticates with its secret at the token
   * endpoint, by `client_secret_basic` or `client_secret_post`. Both use PKCE (S256). The type
   * never changes.
   */
  @SerialName("client_type")
  public val clientType: OAuthAppClientType,
)
