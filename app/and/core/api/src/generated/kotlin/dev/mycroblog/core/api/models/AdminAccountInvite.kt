package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The invite that a local account signed up with, and who created it, which invited the user.
 */
@Serializable
public data class AdminAccountInvite(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
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
)
