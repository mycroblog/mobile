package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A session of the user in a voice chat room.
 */
@Serializable
public data class VoiceSession(
  /**
   * The `session` of the join.
   */
  @SerialName("session")
  public val session: String,
)
