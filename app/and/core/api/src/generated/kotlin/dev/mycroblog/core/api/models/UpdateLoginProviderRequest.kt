package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The changes to a login provider; what is omitted stays. The kind does not change, and the
 * client secret changes only by a rotation.
 */
@Serializable
public data class UpdateLoginProviderRequest(
  /**
   * The name on the administration screens and on the sign-in button of an `oidc`
   * provider, 1 to 100 characters without control characters.
   */
  @SerialName("display_name")
  public val displayName: String? = null,
  /**
   * For `oidc`, the issuer: an `https` URL without user info, a query, or a fragment,
   * exactly as the ID tokens name it. For `mastodon`, the server, as a domain or as
   * `https://<host>`. None for `github` and `discord`.
   */
  @SerialName("issuer")
  public val issuer: String? = null,
  /**
   * The client ID that the provider gave, up to 512 bytes.
   */
  @SerialName("client_id")
  public val clientId: String? = null,
  /**
   * Scopes to ask for besides the ones the kind needs, at most 20 (`read:org` for the
   * Organizations of `github`); none for `mastodon`. The guild condition of `discord`
   * adds `guilds` itself.
   */
  @SerialName("scopes")
  public val scopes: List<String>? = null,
  /**
   * Whether new users may sign up with the provider.
   */
  @SerialName("allow_signup")
  public val allowSignup: Boolean? = null,
  /**
   * Whether users may link their account to the provider.
   */
  @SerialName("allow_link")
  public val allowLink: Boolean? = null,
  /**
   * The conditions on the claims of the ID token, for `oidc` only, at most 10.
   */
  @SerialName("claims")
  public val claims: List<LoginProviderClaimCondition>? = null,
  /**
   * The GitHub Organizations a user must belong to one of, for `github` only, at most 50.
   */
  @SerialName("organizations")
  public val organizations: List<String>? = null,
  /**
   * The IDs of the Discord servers (guilds) a user must belong to one of, for `discord`
   * only, at most 50; each is a decimal ID of up to 20 digits.
   */
  @SerialName("guilds")
  public val guilds: List<String>? = null,
)
