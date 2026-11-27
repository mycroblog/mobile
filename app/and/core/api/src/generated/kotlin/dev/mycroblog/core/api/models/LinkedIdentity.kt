package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An account at an external login provider that the authenticated user links.
 */
@Serializable
public data class LinkedIdentity(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("provider_id")
  public val providerId: UUID,
  /**
   * The kind of an external login provider. `oidc`: an OpenID Connect provider found by
   * its issuer's discovery document. `github`: github.com. `mastodon`: one Mastodon server. `discord`: discord.com.
   */
  @SerialName("provider_kind")
  public val providerKind: LoginProviderKind,
  /**
   * The administrators' name for the provider, shown as written.
   */
  @SerialName("provider_name")
  public val providerName: String,
  /**
   * When the user linked the account.
   */
  @SerialName("linked_at")
  public val linkedAt: Instant,
  /**
   * Whether the provider is enabled: the account signs the user in and confirms the
   * user's operations.
   */
  @SerialName("available")
  public val available: Boolean,
)
