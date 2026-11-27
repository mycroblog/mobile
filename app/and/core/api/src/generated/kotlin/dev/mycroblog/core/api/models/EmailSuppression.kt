package dev.mycroblog.core.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The suppression of the primary email address: the server stops sending to it
 * after a hard bounce or a complaint. A `hard_bounce` (the address does not exist or refuses
 * email) stops every email but the verification email that the user asks for; verifying the
 * address again lifts it. A `complaint` (the recipient marked an email as spam) stops the
 * notification and announcement emails only.
 */
@Serializable
public data class EmailSuppression(
  @SerialName("kind")
  public val kind: EmailSuppressionKind,
  /**
   * When the failure that started the suppression happened.
   */
  @SerialName("state_changed_at")
  public val stateChangedAt: Instant,
)
