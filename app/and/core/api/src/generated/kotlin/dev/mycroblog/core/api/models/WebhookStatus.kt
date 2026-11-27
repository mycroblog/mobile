package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The post of a `status.created` message, always `public` and local.
 */
@Serializable
public data class WebhookStatus(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("uri")
  public val uri: String,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("account_id")
  public val accountId: UUID,
  @SerialName("visibility")
  public val visibility: String,
  @SerialName("in_reply_to_uri")
  public val inReplyToUri: String? = null,
  @SerialName("spoiler_text")
  public val spoilerText: String,
  @SerialName("sensitive")
  public val sensitive: Boolean,
  @SerialName("language")
  public val language: String,
  /**
   * Sanitized HTML.
   */
  @SerialName("content")
  public val content: String,
  @SerialName("created_at")
  public val createdAt: Instant,
)
