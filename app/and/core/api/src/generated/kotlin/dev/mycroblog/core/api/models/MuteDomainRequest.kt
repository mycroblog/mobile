package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The domain to mute.
 */
@Serializable
public data class MuteDomainRequest(
  /**
   * A remote domain, such as `mastodon.example`; its subdomains are muted too.
   */
  @SerialName("domain")
  public val domain: String,
)
