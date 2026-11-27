package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A note to add to a report.
 */
@Serializable
public data class CreateReportNoteRequest(
  /**
   * The note as plain text; it may not be blank.
   */
  @SerialName("body")
  public val body: String,
)
