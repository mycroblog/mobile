package social.mycroblog.api.models

import java.net.URI
import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An invite that lets people sign up while the registration mode is `open`, `approval`, or `invite`.
 */
@Serializable
public data class AdminInvite(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The code people enter on the registration page, 16 characters.
   */
  @SerialName("code")
  public val code: String,
  /**
   * The registration page with the code filled in, to send to people.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
  /**
   * How many sign-ups the invite allows; omitted when there is no limit.
   */
  @SerialName("max_uses")
  public val maxUses: Int? = null,
  /**
   * How many people have signed up with the invite.
   */
  @SerialName("uses")
  public val uses: Int,
  /**
   * What the invite allows now. `usable`: people may sign up with it. `revoked`: it was
   * revoked. `expired`: it passed `expires_at`. `exhausted`: it was used `max_uses` times.
   * `creator_ineligible`: a member created it who may no longer create invites (their
   * permission or plan ended, the instance setting `invite_creators` changed, or the account is
   * suspended, limited, or deleted); it works again when the member may.
   */
  @SerialName("state")
  public val state: AdminInviteState,
  /**
   * How the invite was created. `staff`: by a holder of `manage_invites` or with the CLI; no cap
   * applies and its creator is not checked. `member`: by a member through their own invites,
   * under the caps of the instance settings; it works only while its creator may create invites.
   */
  @SerialName("creator_kind")
  public val creatorKind: InviteCreatorKind,
  /**
   * An account, as clients show it.
   */
  @SerialName("created_by")
  public val createdBy: Account? = null,
  /**
   * Whether a user created the invite whose account no longer exists.
   */
  @SerialName("created_by_deleted")
  public val createdByDeleted: Boolean,
  /**
   * When the invite stops working; omitted when it never does.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant? = null,
  @SerialName("created_at")
  public val createdAt: Instant,
)
