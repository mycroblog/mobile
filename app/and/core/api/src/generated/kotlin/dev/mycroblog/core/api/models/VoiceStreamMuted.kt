package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The data of a `muted` event of a voice chat room stream.
 */
@Serializable
public data class VoiceStreamMuted(
  /**
   * The account ID of the host or co-host who muted the session.
   */
  @SerialName("by")
  public val `by`: String,
)
