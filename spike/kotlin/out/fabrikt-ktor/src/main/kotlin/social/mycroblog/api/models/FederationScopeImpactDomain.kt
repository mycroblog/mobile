package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The follows with one server that switching to the allowlist would end.
 */
@Serializable
public data class FederationScopeImpactDomain(
  /**
   * The server's domain.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * How many follows with its accounts would end.
   */
  @SerialName("follows")
  public val follows: Int,
)
