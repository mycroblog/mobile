package dev.mycroblog.core.api.models

import java.net.URI
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A hashtag of a status.
 */
@Serializable
public data class StatusTag(
  /**
   * The hashtag as written, without `#`.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The hashtag's page.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
)
