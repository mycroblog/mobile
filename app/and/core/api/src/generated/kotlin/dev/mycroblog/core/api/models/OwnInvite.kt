package dev.mycroblog.core.api.models

import java.net.URI
import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An invite that the user created as a member, which always has a limit of uses and an expiry.
 */
@Serializable
public data class OwnInvite(
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
   * How many sign-ups the invite allows.
   */
  @SerialName("max_uses")
  public val maxUses: Int,
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
   * When the invite stops working.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant,
  @SerialName("created_at")
  public val createdAt: Instant,
)
