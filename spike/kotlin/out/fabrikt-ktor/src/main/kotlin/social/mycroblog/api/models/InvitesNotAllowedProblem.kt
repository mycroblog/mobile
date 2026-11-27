package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The problem of a user who may not create invites of their own now (403,
 * `/api/v1/problems/invites-not-allowed`); `reason` says why, and `detail` says it to the
 * user. Another 403 has none.
 */
@Serializable
public data class InvitesNotAllowedProblem(
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
   * Why the user may not create invites of their own now, the first that holds of
   * `registration_closed`: the registration mode is `closed`, which takes no new accounts;
   * `not_permitted`: the instance setting `invite_creators` is not `everyone` and the user's
   * roles lack `create_invites`; `not_approved`: the registration of the account is not
   * approved; `restricted`: the account is suspended or limited.
   */
  @SerialName("reason")
  public val reason: InvitesNotAllowedReason? = null,
)
