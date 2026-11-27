package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderation action on a remote domain and its subdomains.
 */
@Serializable
public data class AdminDomainAction(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The domain, in IDNA A-labels.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * `suspend`: no federation with the domain at all. `limit`: its posts stay off the public
   * timelines and its accounts reach only their followers. `reject_media`: its media are
   * not fetched. `reject_reports`: its reports are dropped. `force_sensitive`: its media
   * are marked sensitive.
   */
  @SerialName("kind")
  public val kind: AdminDomainActionKind,
  /**
   * The reason that may be shown publicly; omitted when there is none.
   */
  @SerialName("public_comment")
  public val publicComment: String? = null,
  /**
   * The moderators' note; omitted when there is none.
   */
  @SerialName("private_note")
  public val privateNote: String? = null,
  /**
   * Whether a public list of the domain actions hides part of the domain.
   */
  @SerialName("obfuscate")
  public val obfuscate: Boolean,
  /**
   * `active`: in effect now. `expired`: it ended at `expires_at`. `lifted`: a moderator
   * lifted it.
   */
  @SerialName("state")
  public val state: ModerationActionState,
  @SerialName("issued_at")
  public val issuedAt: Instant,
  /**
   * When the action ends by itself; omitted for one that lasts until lifted.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant? = null,
  /**
   * When the action was lifted; omitted until it is.
   */
  @SerialName("lifted_at")
  public val liftedAt: Instant? = null,
  /**
   * The subscription to a domain block list that issued the action; omitted for an action
   * a moderator issued.
   */
  @SerialName("subscription_id")
  public val subscriptionId: String? = null,
)
