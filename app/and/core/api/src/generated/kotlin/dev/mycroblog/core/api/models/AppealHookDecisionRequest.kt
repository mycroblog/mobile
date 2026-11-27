package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An appeal of a post that the automated review refused.
 */
@Serializable
public data class AppealHookDecisionRequest(
  /**
   * The text of the post the user tried to post, as plain text.
   */
  @SerialName("status_text")
  public val statusText: String,
  /**
   * Why the moderators should let the post through, as plain text.
   */
  @SerialName("comment")
  public val comment: String? = null,
)
