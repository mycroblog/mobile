package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A host to deny.
 */
@Serializable
public data class DenyOAuthClientHostRequest(
  /**
   * The host name of `client_id` URLs, without a scheme, a port or a path; it is lowercased.
   */
  @SerialName("host")
  public val host: String,
)
