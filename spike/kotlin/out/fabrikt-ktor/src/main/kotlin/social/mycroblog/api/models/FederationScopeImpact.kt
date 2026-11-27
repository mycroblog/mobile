package social.mycroblog.api.models

import kotlin.Int
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What switching the instance setting `federation_scope` to `allowlist` with the current
 * allowlist would end now: the accepted follows, in both directions, between the local accounts
 * and the accounts of the servers that the allowlist does not allow. The switch ends them for
 * good, and each local user who loses one is notified. While the setting is already
 * `allowlist`, it counts what is left to end, which is nothing once the switch has run.
 */
@Serializable
public data class FederationScopeImpact(
  /**
   * The current value of `federation_scope`.
   */
  @SerialName("scope")
  public val scope: FederationScopeImpactScope,
  /**
   * How many follows would end.
   */
  @SerialName("follows")
  public val follows: Int,
  /**
   * How many local accounts would lose a follow.
   */
  @SerialName("local_accounts")
  public val localAccounts: Int,
  /**
   * How many servers those follows are with.
   */
  @SerialName("domains")
  public val domains: Int,
  /**
   * The servers with the most follows that would end, at most 10, the most first.
   */
  @SerialName("top_domains")
  public val topDomains: List<FederationScopeImpactDomain>,
)
