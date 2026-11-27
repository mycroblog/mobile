package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A block on sign-ups from the registration page.
 */
@Serializable
public data class AdminSignupBlock(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * `email_domain`: the domain of the email address and its subdomains. `email_hash`: one
   * email address, compared in lower case without the part of the local part from its first
   * `+`. `ip_range`: the client addresses in a CIDR prefix.
   */
  @SerialName("kind")
  public val kind: AdminSignupBlockKind,
  /**
   * What the block matches, in canonical form: a domain in IDNA A-labels
   * (`email_domain`), the hex SHA-256 of a canonical email address (`email_hash`), or a
   * masked CIDR prefix (`ip_range`).
   */
  @SerialName("value")
  public val `value`: String,
  /**
   * `reject`: the sign-up is refused. `require_approval`: the registration starts pending
   * for a moderator, whatever the registration mode.
   */
  @SerialName("severity")
  public val severity: AdminSignupBlockSeverity,
  /**
   * The moderators' comment; omitted when there is none.
   */
  @SerialName("comment")
  public val comment: String? = null,
  /**
   * The ban the block was made from; omitted for a block added on its own.
   */
  @SerialName("moderation_action_id")
  public val moderationActionId: String? = null,
  /**
   * `active`: in effect now. `expired`: it ended at `expires_at`. `lifted`: a moderator
   * lifted it.
   */
  @SerialName("state")
  public val state: ModerationActionState,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When the block ends by itself; omitted for one that lasts until lifted.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant? = null,
  /**
   * When the block was lifted; omitted until it is.
   */
  @SerialName("lifted_at")
  public val liftedAt: Instant? = null,
)
