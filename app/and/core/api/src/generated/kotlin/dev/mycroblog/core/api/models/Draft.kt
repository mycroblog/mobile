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
 * A draft of a post or of an article (`article`), saved on the server so that the user goes on
 * writing it on another device, and publishes it at once or at a chosen time. Only the user reads
 * it; it is never federated.
 */
@Serializable
public data class Draft(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When the draft was last saved.
   */
  @SerialName("updated_at")
  public val updatedAt: Instant,
  /**
   * The plain text.
   */
  @SerialName("text")
  public val text: String,
  /**
   * The content warning; empty for none.
   */
  @SerialName("spoiler_text")
  public val spoilerText: String,
  @SerialName("sensitive")
  public val sensitive: Boolean,
  /**
   * A BCP 47 language tag; left out for the user's default post language.
   */
  @SerialName("language")
  public val language: String? = null,
  /**
   * Who may see a status: `public` everyone, listed on public timelines;
   * `local` logged-in users of this instance and followers; `unlisted` everyone, kept off
   * public timelines; `private` followers and mentioned accounts; `direct` mentioned
   * accounts.
   */
  @SerialName("visibility")
  public val visibility: Visibility,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("in_reply_to_id")
  public val inReplyToId: UUID? = null,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("quoted_status_id")
  public val quotedStatusId: UUID? = null,
  /**
   * Who may reply to, boost, and quote the post, of the kinds the user chose.
   */
  @SerialName("interaction_policy")
  public val interactionPolicy: InteractionPolicyChange? = null,
  /**
   * The media to attach, in order.
   */
  @SerialName("media")
  public val media: List<StatusMediaItem>,
  /**
   * The poll of a draft. A draft keeps a poll still being written (fewer than two options, or
   * blank ones); publishing it checks the limits of `PollInput`. It ends `expires_in` seconds after
   * the draft is published.
   */
  @SerialName("poll")
  public val poll: DraftPoll? = null,
  /**
   * What makes the draft that of an article; left out for the draft of a post.
   */
  @SerialName("article")
  public val article: DraftArticle? = null,
  /**
   * When the draft is scheduled to be published; left out while it is not scheduled.
   */
  @SerialName("scheduled_at")
  public val scheduledAt: Instant? = null,
  /**
   * How the newest schedule that is over ended; left out when none ended.
   */
  @SerialName("last_schedule")
  public val lastSchedule: DraftScheduleOutcome? = null,
)
