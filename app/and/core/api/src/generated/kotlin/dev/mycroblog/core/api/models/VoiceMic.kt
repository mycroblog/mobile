package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The microphone of a session of the user in a voice chat room.
 */
@Serializable
public data class VoiceMic(
  /**
   * The `session` of the join.
   */
  @SerialName("session")
  public val session: String,
  /**
   * Whether the microphone is on.
   */
  @SerialName("on")
  public val on: Boolean,
)
