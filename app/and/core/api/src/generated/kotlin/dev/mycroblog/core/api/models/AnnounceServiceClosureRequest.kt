package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The schedule of an announcement of the closure of the service.
 */
@Serializable
public data class AnnounceServiceClosureRequest(
  /**
   * When the server closes. It has no lower bound, so that the server can close at short notice.
   */
  @SerialName("closes_at")
  public val closesAt: Instant,
  /**
   * Until when the users may export their data after the closure; not before `closes_at`.
   * 30 days after `closes_at` when omitted.
   */
  @SerialName("export_until")
  public val exportUntil: Instant? = null,
  /**
   * The operator's message to the users, as plain text of at most 5,000 characters; omitted or empty for none.
   */
  @SerialName("message")
  public val message: String? = null,
)
