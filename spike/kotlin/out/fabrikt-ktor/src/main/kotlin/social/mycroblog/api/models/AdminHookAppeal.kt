package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An author's appeal of a `reject` of `local.status`, with the text the author tried to post.
 * Deciding it never calls the plugin.
 */
@Serializable
public data class AdminHookAppeal(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * What a decision hook answered about one subject. It holds no text of a post.
   */
  @SerialName("decision")
  public val decision: AdminHookDecision,
  /**
   * The author.
   */
  @SerialName("account_id")
  public val accountId: String,
  /**
   * The text of the post the author tried to post, as plain text.
   */
  @SerialName("status_text")
  public val statusText: String,
  /**
   * What the author adds for the moderators, as plain text; absent when there is none.
   */
  @SerialName("comment")
  public val comment: String? = null,
  /**
   * Where an appeal of a refused post stands: `pending` until a moderator decides it,
   * `upheld` when the refusal stays, `overturned` when the moderator canceled it. After
   * `overturned` the user's posts are no longer reviewed, and the user posts again: the refused
   * post is not restored.
   */
  @SerialName("state")
  public val state: HookAppealState,
  @SerialName("submitted_at")
  public val submittedAt: Instant,
  /**
   * When a moderator decided the appeal; absent while it is pending.
   */
  @SerialName("decided_at")
  public val decidedAt: Instant? = null,
)
