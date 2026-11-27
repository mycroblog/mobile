package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The problem of a supporter plan that breaks a rule (422,
 * `/api/v1/problems/invalid-supporter-plan`); `reason` says which.
 */
@Serializable
public data class SupporterPlanProblem(
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
   * Why a supporter plan is refused. `name`, `description`, `monthly_price`, `currency`,
   * `annual_discount_percent`, `emoji_limit`, `rank`, `visibility`: the value breaks its rule
   * (a price below the provider's minimum, of its yearly price too, or a currency the
   * provider does not take, included). `role_builtin`: `admin` and `moderator` cannot be a
   * plan's role. `role_taken`: another plan has the role. `role_held`: users hold the role.
   * `role_permissions`: the role has a permission that is not a member permission.
   * `role_position`: the role is not below every staff role, or its position is outside 0 to
   * 999. `order_rank`: another plan has the rank. `order_position`, `order_permissions`,
   * `order_emoji_limit`: a higher plan's role must be above the lower plan's, have all its
   * permissions, and let its members create at least as many custom emoji.
   */
  @SerialName("reason")
  public val reason: SupporterPlanProblemReason? = null,
)
