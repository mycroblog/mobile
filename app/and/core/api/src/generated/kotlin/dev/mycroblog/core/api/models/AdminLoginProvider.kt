package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An external login provider that users sign in, sign up, and link their accounts with.
 * Its client secret is never shown.
 */
@Serializable
public data class AdminLoginProvider(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The kind of an external login provider. `oidc`: an OpenID Connect provider found by
   * its issuer's discovery document. `github`: github.com. `mastodon`: one Mastodon server. `discord`: discord.com.
   */
  @SerialName("kind")
  public val kind: LoginProviderKind,
  /**
   * The name of the provider on the administration screens, and on the sign-in button of
   * an `oidc` provider as it is written.
   */
  @SerialName("display_name")
  public val displayName: String,
  /**
   * The OpenID Connect issuer of an `oidc` provider, or the server's URL
   * (`https://<host>`) of a `mastodon` one; omitted for `github` and `discord`.
   */
  @SerialName("issuer")
  public val issuer: String? = null,
  @SerialName("client_id")
  public val clientId: String,
  /**
   * The scopes asked for besides the ones the kind needs, sorted.
   */
  @SerialName("scopes")
  public val scopes: List<String>,
  /**
   * Whether new users may sign up with the provider.
   */
  @SerialName("allow_signup")
  public val allowSignup: Boolean,
  /**
   * Whether users may link their account to the provider.
   */
  @SerialName("allow_link")
  public val allowLink: Boolean,
  /**
   * The conditions of an `oidc` provider, by claim; empty lets every user in.
   */
  @SerialName("claims")
  public val claims: List<LoginProviderClaimCondition>,
  /**
   * The GitHub Organizations, in lower case and sorted, one of which a user of a `github`
   * provider must belong to; empty lets every GitHub user in.
   */
  @SerialName("organizations")
  public val organizations: List<String>,
  /**
   * The IDs of the Discord servers (guilds), sorted, one of which a user of a `discord`
   * provider must belong to; empty lets every Discord user in.
   */
  @SerialName("guilds")
  public val guilds: List<String>,
  /**
   * Whether users sign in, sign up, and link their accounts with the provider. The sign-in
   * screen shows only the enabled providers.
   */
  @SerialName("state")
  public val state: AdminLoginProviderState,
  @SerialName("state_changed_at")
  public val stateChangedAt: Instant,
  /**
   * The redirect URI to register at the provider:
   * `https://<host>/auth/external/<id>/callback`.
   */
  @SerialName("callback_url")
  public val callbackUrl: String,
  /**
   * The number of users whose accounts are linked to the provider; disabling or deleting it
   * keeps them from signing in with it.
   */
  @SerialName("linked_users")
  public val linkedUsers: Int,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When the settings last changed.
   */
  @SerialName("revised_at")
  public val revisedAt: Instant,
)
