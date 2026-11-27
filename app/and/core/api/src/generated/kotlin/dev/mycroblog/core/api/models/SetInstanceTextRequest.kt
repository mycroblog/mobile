package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new revision of an instance text.
 */
@Serializable
public data class SetInstanceTextRequest(
  /**
   * The text, in any language; empty clears it.
   */
  @SerialName("body")
  public val body: String,
)
