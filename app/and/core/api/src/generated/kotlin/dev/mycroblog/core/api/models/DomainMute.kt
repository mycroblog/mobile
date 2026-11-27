package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A domain that the viewer mutes, with its subdomains.
 */
@Serializable
public data class DomainMute(
  /**
   * The domain, in IDNA A-labels.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * When the viewer muted the domain.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
)
