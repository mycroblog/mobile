package dev.mycroblog.core.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One emoji reaction to a status, with the account that reacted.
 */
@Serializable
public data class StatusReactor(
  /**
   * An account, as clients show it.
   */
  @SerialName("account")
  public val account: Account,
  /**
   * The emoji of a reaction: a Unicode emoji, or a custom emoji with its image. A custom
   * emoji of another server is shown as it is and cannot be used to react from here.
   */
  @SerialName("emoji")
  public val emoji: ReactionEmoji,
  /**
   * When the account reacted.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
)
