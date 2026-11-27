package social.mycroblog.api.models

import java.util.UUID
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A reply to, a boost of, or a quote of a status of the authenticated user that waits for the
 * user's approval, because only the manual rule of the status's interaction policy admits the
 * account that interacts.
 */
@Serializable
public data class InteractionRequest(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * An interaction that an interaction policy governs (a reply, a boost, or a quote).
   */
  @SerialName("kind")
  public val kind: InteractionKind,
  /**
   * `pending` until the user answers; then `accepted` or `rejected`.
   */
  @SerialName("state")
  public val state: InteractionRequestState,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * The account that interacts.
   */
  @SerialName("account")
  public val account: Account,
  /**
   * The status of the user that the account interacts with.
   */
  @SerialName("status")
  public val status: Status,
  /**
   * The reply or the quoting status, when the server has it and the user may see it; omitted
   * for a boost, and for a reply that a remote server sends only once it is approved.
   */
  @SerialName("interacting_status")
  public val interactingStatus: Status? = null,
)
