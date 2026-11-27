package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The delivery of an activity to one inbox of a remote server, with its attempts. `sender`,
 * `activity_uri`, and a personal `inbox_url` are absent when `mask` is present.
 */
@Serializable
public data class AdminDelivery(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("domain")
  public val domain: String,
  /**
   * The type of the activity, such as `Create`, `Follow`, or `Accept`.
   */
  @SerialName("activity_type")
  public val activityType: String,
  /**
   * When the activity was recorded.
   */
  @SerialName("activity_created_at")
  public val activityCreatedAt: Instant,
  /**
   * When the inbox was added to the activity.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * Whether the inbox is a shared inbox of the server, which names no recipient.
   */
  @SerialName("shared_inbox")
  public val sharedInbox: Boolean,
  /**
   * Why the sender, the activity URI, and a personal inbox of a delivery are masked. `private`,
   * `direct`, `local`: the activity is about a post of that visibility. `approves_followers`: a
   * follow, its undo, an accept, or a reject of a local account that approves its followers. `unknown`:
   * the post or the account is gone, so what it was is no longer known.
   */
  @SerialName("mask")
  public val mask: AdminDeliveryMask? = null,
  /**
   * The local account that sent the activity.
   */
  @SerialName("sender")
  public val sender: AdminDeliverySender? = null,
  @SerialName("activity_uri")
  public val activityUri: String? = null,
  @SerialName("inbox_url")
  public val inboxUrl: String? = null,
  /**
   * The attempts, oldest first.
   */
  @SerialName("attempts")
  public val attempts: List<AdminDeliveryAttempt>,
)
