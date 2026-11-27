package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A domain to allow.
 */
@Serializable
public data class CreateDomainAllowRequest(
  /**
   * The domain, a host without a scheme, path, or port; it also covers its subdomains.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * A note for the moderators, never published.
   */
  @SerialName("comment")
  public val comment: String? = null,
)
