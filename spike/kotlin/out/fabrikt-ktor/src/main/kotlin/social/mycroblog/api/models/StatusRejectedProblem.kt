package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The problem of a post that the server's automated review refused (422,
 * `/api/v1/problems/status-rejected`). Nothing was posted. `title` and `detail` are fixed text
 * that holds nothing of the review's answer. The author may appeal the decision with
 * `POST /hook_decisions/{hook_decision_id}/appeal` until `appeal_deadline`, sending the text
 * of the post. Another 422 (a malformed request) has neither member.
 */
@Serializable
public data class StatusRejectedProblem(
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
   * The decision to appeal.
   */
  @SerialName("hook_decision_id")
  public val hookDecisionId: String? = null,
  /**
   * The last moment the author can appeal the decision.
   */
  @SerialName("appeal_deadline")
  public val appealDeadline: Instant? = null,
)
