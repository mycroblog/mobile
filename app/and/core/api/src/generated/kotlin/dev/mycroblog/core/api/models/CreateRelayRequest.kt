package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A relay to register and join.
 */
@Serializable
public data class CreateRelayRequest(
  /**
   * How a relay is joined. `mastodon`: at its inbox, by a `Follow` of the Public
   * collection. `litepub`: at its actor, by a `Follow` of the actor.
   */
  @SerialName("protocol")
  public val protocol: RelayProtocol? = null,
  /**
   * The relay's inbox (`mastodon`) or actor (`litepub`), an https URL.
   */
  @SerialName("url")
  public val url: String,
  /**
   * Whether to take in the public posts it relays; `true` when omitted.
   */
  @SerialName("receive")
  public val receive: Boolean? = null,
)
