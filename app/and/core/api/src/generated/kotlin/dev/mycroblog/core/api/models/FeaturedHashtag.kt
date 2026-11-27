package dev.mycroblog.core.api.models

import java.net.URI
import kotlin.Long
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A hashtag that an account features on its profile, with how many of the account's `public`
 * and `unlisted` posts that this server has carry it. The account's posts of the other
 * visibilities are never counted.
 */
@Serializable
public data class FeaturedHashtag(
  /**
   * The hashtag without `#`, as first written on the server.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The hashtag's page.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
  /**
   * How many of the account's `public` and `unlisted` posts carry the hashtag now.
   */
  @SerialName("statuses_count")
  public val statusesCount: Long,
  /**
   * When one of those posts was last posted or edited with the hashtag; absent when none
   * carries it.
   */
  @SerialName("last_status_at")
  public val lastStatusAt: Instant? = null,
)
