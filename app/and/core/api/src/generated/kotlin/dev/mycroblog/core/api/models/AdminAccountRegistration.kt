package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The registration application of a local account.
 */
@Serializable
public data class AdminAccountRegistration(
  /**
   * The ID that `approveRegistration` and `rejectRegistration` take.
   */
  @SerialName("id")
  public val id: String,
  @SerialName("submitted_at")
  public val submittedAt: Instant,
  /**
   * Why the applicant wants to join; omitted when not asked or not given.
   */
  @SerialName("reason")
  public val reason: String? = null,
  /**
   * The moderators' note on the decision, as plain text; omitted while the registration is
   * pending and when the decision has no note.
   */
  @SerialName("decision_note")
  public val decisionNote: String? = null,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("invite_id")
  public val inviteId: UUID? = null,
  /**
   * The invite that a local account signed up with, and who created it, which invited the user.
   */
  @SerialName("invite")
  public val invite: AdminAccountInvite? = null,
  /**
   * When the registration entered its `registration_state`.
   */
  @SerialName("state_changed_at")
  public val stateChangedAt: Instant,
)
