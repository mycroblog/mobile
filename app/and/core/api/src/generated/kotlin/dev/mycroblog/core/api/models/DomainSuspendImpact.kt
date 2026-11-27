package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What a `suspend` of a domain would end now: the accepted follows, in both
 * directions, between the local accounts and the accounts of the domain and its subdomains.
 * The suspension ends them for good, and each local user who loses one is notified.
 */
@Serializable
public data class DomainSuspendImpact(
  /**
   * The domain counted for, normalized.
   */
  @SerialName("domain")
  public val domain: String,
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
)
