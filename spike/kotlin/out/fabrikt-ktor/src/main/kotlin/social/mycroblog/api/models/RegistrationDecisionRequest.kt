package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderator's decision on a registration.
 */
@Serializable
public data class RegistrationDecisionRequest(
  /**
   * A note for the moderators, as plain text, which the registration shows as `decision_note`; omitted or empty for none.
   */
  @SerialName("note")
  public val note: String? = null,
)
