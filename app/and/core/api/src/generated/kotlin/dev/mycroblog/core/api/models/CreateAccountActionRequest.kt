package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.LinkedHashSet
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderation action to take on an account.
 */
@Serializable
public data class CreateAccountActionRequest(
  /**
   * `warn`: a notice only. `mark_statuses_sensitive` and `delete_statuses`: actions on
   * posts. `force_sensitive`: every attachment is shown as sensitive. `limit`: every post is
   * followers-only and stays off the public timelines. `restrict_posting`: no posting.
   * `restrict_interaction`: no following, favouriting, boosting, or replying.
   * `disable_login`: no logging in. `suspend`: all of these, and the profile and posts are
   * hidden. `ban`: a suspension that never ends, with the deletion of the account.
   */
  @SerialName("kind")
  public val kind: AdminAccountActionKind,
  @SerialName("reason_category")
  public val reasonCategory: AdminAccountActionCategory = AdminAccountActionCategory.OTHER,
  /**
   * The reason shown to the account.
   */
  @SerialName("public_reason")
  public val publicReason: String? = null,
  /**
   * A note for the moderators only.
   */
  @SerialName("private_note")
  public val privateNote: String? = null,
  /**
   * The report of the account that the action follows from. The action resolves an open
   * report as `actioned` in the same operation; a resolved report stays as it is.
   */
  @SerialName("report_id")
  public val reportId: String? = null,
  /**
   * Whether a local account is told of the action; ignored for a remote one.
   */
  @SerialName("notify_target")
  public val notifyTarget: Boolean = true,
  /**
   * The seconds the action lasts, at most ten years (315360000); omitted, it lasts until it
   * is lifted. A `ban` and the actions on posts take none.
   */
  @SerialName("expires_in")
  public val expiresIn: Int? = null,
  /**
   * The posts of the account that `delete_statuses` or `mark_statuses_sensitive` acts
   * on; required for them and taken by no other kind.
   */
  @SerialName("status_ids")
  public val statusIds: LinkedHashSet<@Contextual UUID>? = null,
  /**
   * Whether a `ban` also blocks sign-ups with the email address of the account's user (a
   * `reject` sign-up block of kind `email_hash`); only a `ban` of a local
   * account takes it, and a user without an email address is a 422. Omitted, nothing is
   * blocked.
   */
  @SerialName("block_email")
  public val blockEmail: Boolean? = null,
)
