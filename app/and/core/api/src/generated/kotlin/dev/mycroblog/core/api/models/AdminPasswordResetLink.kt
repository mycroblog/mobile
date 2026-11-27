package dev.mycroblog.core.api.models

import java.net.URI
import kotlin.Boolean
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A password reset link that an administrator issued for a local account. It is
 * shown once.
 */
@Serializable
public data class AdminPasswordResetLink(
  /**
   * The link that sets a new password; it works once.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
  /**
   * When the link stops working.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant,
  /**
   * Whether registration and sign-in are limited to the login providers now. While they are, the
   * link sets no password; it works only if the limit ends before `expires_at`.
   */
  @SerialName("login_providers_only")
  public val loginProvidersOnly: Boolean,
)
