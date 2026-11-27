package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The follows that a `relationships_severed` tells of: the user lost them when the
 * moderators suspended a server (`domain_suspension`, `domain` is the server) or a remote
 * account (`account_suspension`, the notification's `account` is the account), or when the
 * server began to federate only with the servers it allows (`allowlist_cutoff`: `domain` is the
 * server whose allow was removed, and is omitted when the server switched to its allowlist).
 * Both directions end, follow requests too, and lifting the suspension or allowing the server
 * again does not restore them; the counts are of the accepted follows.
 */
@Serializable
public data class RelationshipSeverance(
  @SerialName("kind")
  public val kind: RelationshipSeveranceKind,
  /**
   * The suspended server of a `domain_suspension`, or the server whose allow was removed of an
   * `allowlist_cutoff`; omitted otherwise.
   */
  @SerialName("domain")
  public val domain: String? = null,
  /**
   * The accounts that followed the user and no longer do.
   */
  @SerialName("followers_lost")
  public val followersLost: Int,
  /**
   * The accounts that the user followed and no longer does.
   */
  @SerialName("following_lost")
  public val followingLost: Int,
)
