package dev.mycroblog.core.api.models

import java.net.URI
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A hashtag that the user follows.
 */
@Serializable
public data class FollowedHashtag(
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
   * When the user followed the hashtag.
   */
  @SerialName("followed_at")
  public val followedAt: Instant,
)
