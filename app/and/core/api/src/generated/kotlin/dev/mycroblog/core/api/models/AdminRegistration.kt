package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A user's registration as the moderators review it.
 */
@Serializable
public data class AdminRegistration(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The user's local account; omitted when it is gone.
   */
  @SerialName("account_id")
  public val accountId: String? = null,
  /**
   * The username of the user's local account; omitted when it is gone.
   */
  @SerialName("username")
  public val username: String? = null,
  /**
   * Where a registration stands: `pending` waits for a moderator, `approved` lets the user
   * sign in, and `rejected` keeps them out.
   */
  @SerialName("state")
  public val state: RegistrationState,
  /**
   * When the registration entered its state.
   */
  @SerialName("state_changed_at")
  public val stateChangedAt: Instant,
  /**
   * The user's primary email address, shown only while the registration is pending, for its
   * review; omitted when the user has none and once the registration is decided.
   */
  @SerialName("email")
  public val email: String? = null,
  /**
   * Whether the user confirmed the email address; present together with `email`.
   */
  @SerialName("email_verified")
  public val emailVerified: Boolean? = null,
  /**
   * The moderators' note on the decision, as plain text; omitted while the registration is
   * pending and when the decision has no note.
   */
  @SerialName("decision_note")
  public val decisionNote: String? = null,
  /**
   * Why the user wants to join, as plain text; omitted when they gave none.
   */
  @SerialName("reason")
  public val reason: String? = null,
  /**
   * The invite the user signed up with; omitted for none.
   */
  @SerialName("invite_id")
  public val inviteId: String? = null,
  /**
   * When the user signed up.
   */
  @SerialName("submitted_at")
  public val submittedAt: Instant,
)
