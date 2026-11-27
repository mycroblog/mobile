package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The problem of a role change that breaks a rule (422, `/api/v1/problems/invalid-role`);
 * `reason` says which. Another 422 (a malformed request) has none.
 */
@Serializable
public data class RoleProblem(
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
   * Why a role change is refused. `key`: not 1 to 64 lowercase ASCII letters, digits, `_`,
   * and `-`. `key_taken`: another role has the key. `position`: outside 0 to 999.
   * `permissions`: an unknown permission, or one twice. `builtin`: `admin` changes only its
   * badge, and neither `admin` nor `moderator` can be deleted. `held`: users hold the role.
   * `missing_permission`: the change adds or removes permissions that the viewer's roles
   * do not grant (`missing_permissions`). `badge_label`: a badge label that is not 1 to 32
   * characters on one line. `badge_color`: an unknown badge color.
   */
  @SerialName("reason")
  public val reason: RoleProblemReason? = null,
  /**
   * The permissions that the change needs and the viewer lacks, for `missing_permission`.
   */
  @SerialName("missing_permissions")
  public val missingPermissions: List<Permission>? = null,
)
