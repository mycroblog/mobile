package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The problem of a custom emoji request that cannot be decided or withdrawn (409,
 * `/api/v1/problems/custom-emoji-request-conflict`); `reason` says why.
 */
@Serializable
public data class CustomEmojiRequestProblem(
  /**
   * A URI that identifies the problem type, stable for clients to branch on.
   * It is `about:blank` when the problem means no more than its HTTP status.
   */
  @SerialName("type")
  public val type: String,
  /**
   * A short summary of the problem type, localized from `Accept-Language`.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The HTTP status code.
   */
  @SerialName("status")
  public val status: Int,
  /**
   * An explanation of this occurrence, localized from `Accept-Language`.
   */
  @SerialName("detail")
  public val detail: String? = null,
  /**
   * The request ID (the `X-Request-Id` response header), for reporting the problem.
   */
  @SerialName("request_id")
  public val requestId: String? = null,
  /**
   * The member of the request that a 422 is about, by its name in the request: a property of
   * the JSON body by its path (`display_name`, `fields[].name`, `poll.options[1]`), a form
   * field, a query parameter, or a header (`Idempotency-Key`). `detail` then says what is
   * wrong with its value without naming it, for the client to show beside that field. Absent
   * when the problem is not about one member.
   */
  @SerialName("field")
  public val `field`: String? = null,
  /**
   * Why a custom emoji request cannot be decided or withdrawn. `not_pending`: it was already
   * approved, rejected, withdrawn, or lapsed. `requester_cannot_create`: the requester no longer
   * holds `create_custom_emojis` or can no longer use the server. `shortcode_taken`: a local
   * emoji has the shortcode of the request. The last two can only be rejected.
   */
  @SerialName("reason")
  public val reason: CustomEmojiRequestConflictReason? = null,
)
