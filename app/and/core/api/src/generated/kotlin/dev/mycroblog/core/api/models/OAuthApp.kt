package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An OAuth app that the authenticated user registered. Like every third-party app,
 * it gets no first-party-only scope and calls no account security operation.
 */
@Serializable
public data class OAuthApp(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The public identifier that the app sends as `client_id`.
   */
  @SerialName("client_id")
  public val clientId: String,
  /**
   * The name that the consent screen shows.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The app's home page; absent when it has none.
   */
  @SerialName("website")
  public val website: String? = null,
  /**
   * The URIs the app may redirect to, sorted; matched exactly, except that a loopback URI
   * matches any port.
   */
  @SerialName("redirect_uris")
  public val redirectUris: List<String>,
  /**
   * The most the app may request, space-separated scopes.
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
  /**
   * Whether an administrator disabled the app: it cannot be authorized, get tokens, or use
   * them until the administrator enables it again.
   */
  @SerialName("disabled")
  public val disabled: Boolean,
  /**
   * When the app was registered.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
)
