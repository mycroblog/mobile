package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The problem of a webhook that breaks a rule (422, `/api/v1/problems/invalid-webhook`);
 * `reason` says which. Another 422 (a malformed request) has none.
 */
@Serializable
public data class WebhookProblem(
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
   * Why a webhook is refused. `url`: not an absolute http or https URL of at most 2048
   * bytes without user info or a fragment. `description`: longer than 200 characters or with
   * control characters. `events`: none, or one twice. `missing_permission`: the user lacks
   * the permission of an event (`missing_permissions`).
   */
  @SerialName("reason")
  public val reason: WebhookProblemReason? = null,
  /**
   * The permissions that the events need and the user lacks, for `missing_permission`.
   */
  @SerialName("missing_permissions")
  public val missingPermissions: List<Permission>? = null,
)
