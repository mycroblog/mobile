package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of the user's own invites, newest first, with what the user may do.
 */
@Serializable
public data class OwnInvitePage(
  /**
   * An opaque pagination cursor. Clients pass it back unchanged and never parse it.
   */
  @SerialName("next_cursor")
  public val nextCursor: String? = null,
  @SerialName("items")
  public val items: List<OwnInvite>,
  /**
   * Whether the user may create invites now: the instance setting `invite_creators` is
   * `everyone` or the user's roles have `create_invites`, the account is neither
   * suspended nor limited, its registration is approved, and the registration mode is
   * not `closed`. It does not count the cap of usable invites (`usable` against
   * `limits.max_active`). When it is false, `cannot_create_reason` says why; when it is
   * true, `cannot_create_reason` is omitted.
   */
  @SerialName("can_create")
  public val canCreate: Boolean,
  /**
   * Why the user may not create invites of their own now, the first that holds of
   * `registration_closed`: the registration mode is `closed`, which takes no new accounts;
   * `not_permitted`: the instance setting `invite_creators` is not `everyone` and the user's
   * roles lack `create_invites`; `not_approved`: the registration of the account is not
   * approved; `restricted`: the account is suspended or limited.
   */
  @SerialName("cannot_create_reason")
  public val cannotCreateReason: InvitesNotAllowedReason? = null,
  /**
   * How many usable invites the user holds now, which `limits.max_active` bounds.
   */
  @SerialName("usable")
  public val usable: Int,
  /**
   * The caps and defaults of the invites that members create, from the instance settings
   * `member_invite_max_active`, `member_invite_max_uses`, and `member_invite_max_lifetime_seconds`.
   */
  @SerialName("limits")
  public val limits: MemberInviteLimits,
)
