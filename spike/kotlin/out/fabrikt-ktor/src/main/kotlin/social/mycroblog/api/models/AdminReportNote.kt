package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderator's internal note on a report; never shown to the target or the reporter.
 */
@Serializable
public data class AdminReportNote(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The account of the moderator who wrote the note; omitted for a note from the
   * command line and when the moderator has no account.
   */
  @SerialName("author_account_id")
  public val authorAccountId: String? = null,
  /**
   * The note as plain text, up to 2,000 characters.
   */
  @SerialName("body")
  public val body: String,
  @SerialName("created_at")
  public val createdAt: Instant,
)
