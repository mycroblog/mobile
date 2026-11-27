package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What a moderator sends with a report forwarded to the target's server.
 */
@Serializable
public data class ForwardReportRequest(
  /**
   * Text for the moderators of the target's server, as plain text, up to 1,000 characters:
   * the `content` of the `Flag`. Omitted or empty sends none. The reporter's comment is never
   * sent by a forward after filing.
   */
  @SerialName("comment")
  public val comment: String? = null,
)
