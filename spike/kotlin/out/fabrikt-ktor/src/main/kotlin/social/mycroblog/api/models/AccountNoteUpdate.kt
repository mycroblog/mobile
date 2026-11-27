package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The viewer's private note on an account.
 */
@Serializable
public data class AccountNoteUpdate(
  /**
   * The note, up to 2,000 characters (Unicode code points). An empty note removes the one
   * the viewer has.
   */
  @SerialName("note")
  public val note: String,
)
