package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An enabled login provider that users sign in with.
 */
@Serializable
public data class LinkableLoginProvider(
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
   * The administrators' name for the provider, shown as written.
   */
  @SerialName("display_name")
  public val displayName: String,
  /**
   * Whether users link accounts at the provider to their account.
   */
  @SerialName("allow_link")
  public val allowLink: Boolean,
)
