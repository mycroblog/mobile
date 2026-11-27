package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The voice chat room that a report points at, as the moderators review it: every revision
 * of its settings, and the records of its participants while the server keeps them (90 days
 * after the room ends by default, `voice_participants_retention_days`, and as long as a report
 * points at the room).
 */
@Serializable
public data class AdminReportVoiceRoom(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The revision of the settings when the room was reported.
   */
  @SerialName("reported_revision_id")
  public val reportedRevisionId: String,
  @SerialName("opened_at")
  public val openedAt: Instant,
  /**
   * When the room ended; omitted while it is open.
   */
  @SerialName("ended_at")
  public val endedAt: Instant? = null,
  /**
   * Why the room ended; omitted while it is open.
   */
  @SerialName("end_reason")
  public val endReason: VoiceEndReason? = null,
  /**
   * Who ended the room; omitted for an automatic end and while it is open.
   */
  @SerialName("ended_by_account_id")
  public val endedByAccountId: String? = null,
  /**
   * The reason that a moderator gave for ending the room; omitted for none.
   */
  @SerialName("end_note")
  public val endNote: String? = null,
  /**
   * The revisions of the settings, oldest first.
   */
  @SerialName("revisions")
  public val revisions: List<AdminVoiceRoomRevision>,
  /**
   * Who took part and when, in the order they joined.
   */
  @SerialName("participations")
  public val participations: List<AdminVoiceRoomParticipation>,
  /**
   * The accounts removed from the room, who may not join it again.
   */
  @SerialName("bans")
  public val bans: List<AdminVoiceRoomBan>,
)
