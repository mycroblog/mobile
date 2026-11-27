package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new status.
 */
@Serializable
public data class CreateStatusRequest(
  /**
   * The plain text; the server links mentions, hashtags, and URLs.
   */
  @SerialName("text")
  public val text: String,
  /**
   * The content warning; omitted or empty for none. It counts toward `max_post_characters`.
   */
  @SerialName("spoiler_text")
  public val spoilerText: String? = null,
  /**
   * Whether the media are sensitive.
   */
  @SerialName("sensitive")
  public val sensitive: Boolean? = null,
  /**
   * A BCP 47 language tag; omitted means the user's default post language.
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
   * The media to attach, in order.
   */
  @SerialName("media")
  public val media: List<StatusMediaItem>? = null,
  /**
   * A poll of a new status. It ends `expires_in` seconds after the status is
   * posted; the author and the users who voted are notified then.
   */
  @SerialName("poll")
  public val poll: PollInput? = null,
  /**
   * The status to quote. It must be one the user may see and may quote
   * (`quote` of `Status.permissions` is not `denied`); a quote of a boost is a quote of the boosted status. A `local`,
   * `private`, or `direct` status may not quote a remote status: that is a 422, and nothing
   * is created. Quoting a
   * remote status while the instance does not send posts out (`Instance.federation.mode`
   * `off` or `inbound`) is the 403 `/api/v1/problems/federation-restricted`.
   */
  @SerialName("quoted_status_id")
  public val quotedStatusId: String? = null,
  /**
   * Who may reply to, boost, and quote the new status; the kinds left out take the user's
   * `default_interaction_policy`.
   */
  @SerialName("interaction_policy")
  public val interactionPolicy: InteractionPolicyChange? = null,
)
